package org.example.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.commandManager.CommandConsoleManager;
import org.example.commandManager.CommandHelper;
import org.example.commandManager.CommandTCPManager;
import org.example.commandManager.IExecutable;
import org.example.commandManager.IConsolable;
import org.example.jsonLogic.ZonedDateTimeAdapter;
import org.example.messages.MessageFromClient;
import org.example.messages.MessageFromServer;

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
    private final LinkedHashMap<String, IExecutable> commandTCPManager = new CommandTCPManager().getCommandManager();
    private final LinkedHashMap<String, IConsolable> commandConsoleManager = new CommandConsoleManager().getCommandManager();

    public void init() {
        System.out.println("Server application start work :)");
        System.out.print("> ");
        launcher();
    }

    private void launcher() {
        try (
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8000));

            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();

                // Read request in bytes
                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                socketChannel.read(buffer);
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String request = new String(bytes);
                buffer.clear();

                // Request in custom class
                JsonObject jsonObject = JsonParser.parseString(request).getAsJsonObject();
                MessageFromClient message = gson.fromJson(jsonObject, MessageFromClient.class);

                // Do request
                CommandHelper.setHistory(message.getCommandName());
                CommandHelper.setArgument(message.getArgument());
                CommandHelper.setRoute(message.getRoute());
                MessageFromServer answer = commandTCPManager.get(message.getCommandName()).execute();

                // Send answer
                buffer = ByteBuffer.wrap(gson.toJson(answer, MessageFromServer.class).getBytes());
                socketChannel.write(buffer);
                buffer.clear();
                buffer.flip();

                if (bufferedReader.ready()) {
                    consoleCommand();
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void consoleCommand() {
        try {
            String[] splitCommand = bufferedReader.readLine().trim().split(" ");
            if (splitCommand.length > 1) {
                CommandHelper.setArgument(splitCommand[1]);
            }
            try {
                System.out.println(commandConsoleManager.get(splitCommand[0]).execute());
                System.out.print("> ");
            } catch (NullPointerException ex) {
                System.out.print("Incorrect input. write \"help\" to see all commands and their arguments\n> ");
            }
        } catch (NullPointerException ex) {
            System.out.println("Ctrl + D detected");
            System.exit(0);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
