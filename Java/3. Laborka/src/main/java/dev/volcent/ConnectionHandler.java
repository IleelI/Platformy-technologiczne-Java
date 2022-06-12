package dev.volcent;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket socket;

    public ConnectionHandler(Socket socket) {
        System.out.println("Handler created...");
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Sending initial message
            Message initMsg = new Message(Message.ready);
            out.writeObject(initMsg);

            Message clientMsg;
            boolean exit = false;
            int messagesCount = 0;
            while (!exit && (clientMsg = (Message)in.readObject()) != null) {
                switch (clientMsg.getContent()) {
                    case Message.ready -> {
                        clientMsg.printNumbers();
                        messagesCount = clientMsg.getNumbers()[0];
                        Message serverMsg = new Message(Message.readForMessages);
                        out.writeObject(serverMsg);
                    }
                    case Message.readForMessages -> {
                        clientMsg.printNumbers();
                        if (messagesCount == 1) {
                            Message serverMsg = new Message(Message.finished);
                            out.writeObject(serverMsg);
                        } else {
                            messagesCount -= 1;
                        }
                    }
                    case Message.finished -> {
                        System.out.println("Finished! Closing connection...");
                        exit = true;
                        break;
                    }
                    default -> {
                        System.err.println("Unknown message type, aborting connection...");
                        Message serverMsg = new Message(Message.error);
                        out.writeObject(serverMsg);
                        exit = true;
                    }
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } finally {
            System.out.println("Handler finished...");
        }
    }
}
