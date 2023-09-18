package org.example.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.commandManager.CommandHelper;
import org.example.commandManager.CommandManager;
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
    private final static UserInputService userInputService = new UserInputService();
    private final Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).create();
    private BufferedReader bufferedReader;
    private CommandHelper commandHelper = new CommandHelper();
    private LinkedHashMap<String, IExecutable> commandManager = new CommandManager().getCommandManager();

    public void init() {
        userInputService.setMode(Mode.INTERNET);
        System.out.println("Hello! And welcome to the Los Pollos Hermanos family! My name is Gustavo, but you can call me Gus...");
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
                commandHelper.setHistory(message.getCommand());
                commandHelper.setArguments(message.getRoute(), message.getArgument());
                String answer = commandManager.get(message.getCommand()).execute();

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
        //TODO: just do it
    }

    public void fileCommand() {
        //TODO: rekursia
    }
}
