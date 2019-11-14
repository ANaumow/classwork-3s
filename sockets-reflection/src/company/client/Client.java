package company.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Socket clientSocket = new Socket("localhost", 123);

        PrintWriter toServer = new PrintWriter(
                clientSocket.getOutputStream(), true);
        BufferedReader fromServer = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        while (true) {
            String line = scanner.nextLine();
            toServer.println(line);
            if (line.equals("/stop"))
                break;
            if (line.substring(0, line.indexOf(" ")).equals("/get")) {
                System.out.println(fromServer.readLine());
            }
        }


    }

}
