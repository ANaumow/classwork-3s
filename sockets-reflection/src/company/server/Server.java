package company.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException, IllegalAccessException, NoSuchFieldException {
        ServerSocket server = new ServerSocket(123);
        Socket       client = server.accept();

        MyClass inst = new MyClass();
        PrintWriter toClient = new PrintWriter(
                client.getOutputStream(), true);
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(
                client.getInputStream()));

        boolean isWorking = true;
        while (isWorking) {
            String[] input = fromClient.readLine().split(" ");
            switch (input[0]) {
                case "/get":
                    Field field = inst.getClass().getDeclaredField(input[1]);
                    field.setAccessible(true);
                    toClient.println(field.get(inst));
                    //System.out.println(field.get(inst));
                    break;
                case "/set":
                    field = inst.getClass().getDeclaredField(input[1]);
                    field.setAccessible(true);
                    System.out.println(field.getType());
                    if (field.getType().equals(Integer.class)){
                        field.set(inst, Integer.parseInt(input[2]));
                    } else if (field.getType().equals(String.class)) {
                        field.set(inst, input[2]);
                    } else if (field.getType().equals(Boolean.class)) {
                        field.set(inst, Boolean.parseBoolean(input[2]));
                    } else if (field.getType().equals(int.class)) {
                        field.set(inst, Integer.parseInt(input[2]));
                    }
                    break;
                case "/stop":
                    isWorking = false;
                    break;
                default:
                    System.out.println("unrecognizable");
            }
        }

    }

}
