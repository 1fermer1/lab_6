package org.example.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.commandManager.CommandHelper;
import org.example.commandManager.CommandConsoleManager;
import org.example.commandManager.IExecutable;
import org.example.jsonLogic.ZonedDateTimeAdapter;
import org.example.messages.MessageFromClient;
import org.example.messages.MessageFromServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;

public class LauncherService {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).create();
    private BufferedReader bufferedReader;
    private final LinkedHashMap<String, IExecutable> commandConsoleManager = new CommandConsoleManager().getCommandManager();

    public void init() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Client application start work :)");
        launcher();
    }

    private void launcher() {
        while (true) {
            try {
                System.out.print("> ");
                String[] splitCommand = bufferedReader.readLine().trim().split(" ");
                if (splitCommand.length > 1) {
                    CommandHelper.setArgument(splitCommand[1]);
                }
                try {
                    String answer = mega(commandConsoleManager.get(splitCommand[0]).execute());
                    System.out.print(answer == null ? "" : answer + "\n");
                } catch (NullPointerException ex) {
                    System.out.println("Incorrect input. write \"help\" to see all commands and their arguments");
                } catch (ConnectException ex) {
                    System.out.println("Sorry, but server is off");
                }
            } catch (NullPointerException ex) {
                System.out.println("Ctrl + D detected");
                System.exit(0);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // The best file sender
    public String mega(MessageFromClient message) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));

        // Send command to server
        ByteBuffer buffer = ByteBuffer.wrap(gson.toJson(message, MessageFromClient.class).getBytes());
        socketChannel.write(buffer);
        buffer.clear();
        buffer.flip();

        // Get answer and make string
        buffer = ByteBuffer.allocate(1024 * 1024);
        socketChannel.read(buffer);
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        String answer = new String(bytes);
        buffer.clear();

        // Give answer another method and close socket
        socketChannel.close();
        JsonObject jsonObject = JsonParser.parseString(answer).getAsJsonObject();
        return gson.fromJson(jsonObject, MessageFromServer.class).getAnswer();
    }
}
