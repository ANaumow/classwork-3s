package company;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    // куда закидывать сообщения от сервера
    TextArea textArea;

    public Client(TextArea textArea) {
        this.textArea = textArea;
    }

    public void startConnection(String ip, Integer port) {
        try{
            this.clientSocket = new Socket(ip, port);
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            new Thread(receiveMessage).start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
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
                textArea.setText(textArea.getText() + message + "\n");
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    };
}
