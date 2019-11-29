package company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Client {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    // here just for example
    public static void main(String[] args) {
        Client client = new Client();
        client.startConnection("localhost", 1337);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            client.sendMessage(scanner.nextLine());
        }
        //Stream.generate(scanner::nextLine).forEach(client::sendMessage);
    }

    public void startConnection(String ip, Integer port) {
        try{
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            new Thread(receiveMessage).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    private Runnable receiveMessage = () -> {
        while (true) {
            try {
                String message = reader.readLine();
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
