package org.example.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.commandManager.CommandHelper;
import org.example.commandManager.CommandConsoleManager;
import org.example.commandManager.IExecutable;
import org.example.jsonLogic.ZonedDateTimeAdapter;
import org.example.messages.MessageFromClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;

public class LauncherService {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).create();
    private MessageFromClient message;
    private BufferedReader bufferedReader;
    private CommandHelper commandHelper = new CommandHelper();
    private LinkedHashMap<String, IExecutable> commandManager = new CommandConsoleManager().getCommandManager();

    public void init() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Client application start work :)");
        launcher();
    }

    public void launcher() {
        while (true) {
            try {
                System.out.print("> ");
                String[] splitCommand = bufferedReader.readLine().trim().split(" ");
                if (splitCommand.length > 1) {
                    commandHelper.setArgument(splitCommand[1]);
                }
                message = commandManager.get(splitCommand[0]).execute();
                System.out.println(mega(message));
            } catch (NullPointerException ex) {
                System.out.println("Ctrl + D detected");
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception ex) {
                System.out.println("Введена некорректная команда, либо указаны неправильные аргументы. Вы можете ознакомиться со списком существующих команд, используя команду \"help\"");
            }
        }
    }

    public String mega(MessageFromClient message) {
        try (
                SocketChannel socketChannel = SocketChannel.open()
        ) {
            while (true) {
                try {
                    socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));
                    break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            // Отправка команды на сервер
            String jsonMessage = gson.toJson(message, MessageFromClient.class);
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            buffer = ByteBuffer.wrap(gson.toJson(message, MessageFromClient.class).getBytes());
            socketChannel.write(buffer);
            buffer.clear();
            buffer.flip();

            // Получение ответа от сервера
            buffer = ByteBuffer.allocate(1024 * 1024);
            socketChannel.read(buffer);
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String answer = new String(bytes);
            buffer.clear();

            // Обработка ответа сервера в читаемый объект
            return answer;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
