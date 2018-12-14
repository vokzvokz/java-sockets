package multiapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class botClient {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    public botClient(){
        Scanner scan = new Scanner(System.in);
        String ip = "127.0.0.1";
        try {
            socket = new Socket(ip, botMain.Port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            botClient.Sender send = new botClient.Sender();
            System.out.println("");
            send.start();
            String command = "";
            while (!command.equals("exit")) {
                command = scan.nextLine();
                out.println(command);
            }
            send.setStop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            System.err.println("Работа программы завершена.");
        }
    }

    private class Sender extends Thread {
        private boolean stoped;

        public void setStop() {
            stoped = true;
        }
        @Override
        public void run() {
            try {
                while (!stoped) {
                    String str = in.readLine();
                    System.out.println(str);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при получении сообщения.");
                e.printStackTrace();
            }
        }
    }
}
