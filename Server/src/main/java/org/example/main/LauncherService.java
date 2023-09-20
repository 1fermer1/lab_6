package org.example.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.commandManager.CommandHelper;
import org.example.commandManager.CommandTCPManager;
import org.example.commandManager.IExecutable;
import org.example.jsonLogic.ZonedDateTimeAdapter;
import org.example.messages.MessageFromClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;

public class LauncherService {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).create();
    private BufferedReader bufferedReader;
    private CommandHelper commandHelper = new CommandHelper();
    private LinkedHashMap<String, IExecutable> commandManager = new CommandTCPManager().getCommandManager();

    public void init() {
        System.out.println("Server application start work :)");
        System.out.print("> ");
        launcher();
    }

    public void launcher() {
        try (
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8000));

            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();

                // Чтение запроса в байтах
                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                socketChannel.read(buffer);
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String request = new String(bytes);
                buffer.clear();

                // Перевод в читаемый вид
                JsonObject jsonObject = JsonParser.parseString(request).getAsJsonObject();
                MessageFromClient message = gson.fromJson(jsonObject, MessageFromClient.class);

                // Выполнение запроса
                commandHelper.setHistory(message.getCommandName());
                commandHelper.setArgument(message.getArgument());
                commandHelper.setRoute(message.getRoute());
                String answer = commandManager.get(message.getCommandName()).execute();

                // Отправка ответа
                buffer = ByteBuffer.wrap(answer.getBytes());
                socketChannel.write(buffer);
                buffer.clear();
                buffer.flip();

                if (bufferedReader.ready()) {
                    defaultCommand();
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void defaultCommand() {
        String command;
        try {
            command = bufferedReader.readLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        try {
            String[] splitCommand = command.trim().split(" ");
            if (splitCommand.length > 1) {
                commandHelper.setArgument(splitCommand[1]);
            }
            try {
                commandManager.get(splitCommand[0]).execute();
                System.out.print("> ");
            } catch (Exception ex) {
                System.out.println("Введена некорректная команда, либо указаны неправильные аргументы. Вы можете ознакомиться со списком существующих команд, используя команду \"help\"");
            }
        } catch (NullPointerException ex) {
            System.out.println("Ctrl + D detected");
            System.exit(0);
        }
    }
}
