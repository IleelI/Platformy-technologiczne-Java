package dev.volcent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Client {

    public static int[] generateIntegers(int maxValue, final int size) {
        Random random = new Random();
        int[] output = new int[size];
        for (int i = 0; i < size; i += 1) {
            output[i] = random.nextInt(maxValue);
        }
        output[0] = maxValue;
        return output;
    }
    public static int generateInteger() {
        Random random = new Random();
        return random.nextInt(512);
    }

    public void startSession() {
        System.out.println("Making connection...");
        try (Socket socket = new Socket(Server.host, Server.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            // Get data from server and react accordingly
            Message serverMsg;
            boolean exit = false;
            int userNValue = 0;
            while(!exit && (serverMsg = (Message)in.readObject()) != null) {
                System.out.println("Server: " + serverMsg.getContent());
                switch (serverMsg.getContent()) {
                    case Message.ready:
                        while (true) {
                            try {
                                System.out.println("Enter positive integer value...");
                                userNValue = Integer.parseInt(scanner.nextLine());
                                Message clientMessage = new Message(Message.ready);
                                clientMessage.setNumbers(generateIntegers(userNValue, Message.numbersSize));
                                out.writeObject(clientMessage);
                                break;
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                                System.err.println("Enter correct integer value");
                            }
                        }
                        break;
                    case Message.readForMessages:
                        for (int i = 0; i < userNValue; i += 1) {
                            Message message = new Message(Message.readForMessages);
                            message.setNumbers(generateIntegers(generateInteger(), Message.numbersSize));
                            out.writeObject(message);
                        }
                        break;
                    case Message.finished:
                        System.out.println("Ending connection, messages sent correctly.");
                        Message clientMsg = new Message(Message.finished);
                        out.writeObject(clientMsg);
                        exit = true;
                        break;
                    default:
                        System.out.println("Ending connection, unknown response received.");
                        exit = true;
                        break;
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void main(String[] argv) {
        Client client = new Client();
        client.startSession();
    }
}
