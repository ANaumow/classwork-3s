package company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket        serverSocket;
    private List<ClientHandler> clients;

    // for example you can start server here
    public static void main(String[] args) {
        new Server().start(7320);
    }

    public Server() {
        this.clients = new ArrayList<>();
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            // постоянно ждем когда клиент подключится
            while (true) {
                Socket clientSocket = serverSocket.accept(); // ждет когда подключится новый клиент
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
                this.clients.add(clientHandler);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private class ClientHandler extends Thread {
        private Socket         clientSocket;
        private BufferedReader reader;
        private PrintWriter    writer;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.writer = new PrintWriter(this.clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("New Client");
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String message = reader.readLine();
                    for (ClientHandler client : clients) {
                        client.writer.println(message);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}

