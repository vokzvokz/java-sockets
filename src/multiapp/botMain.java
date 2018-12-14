package multiapp;

import java.util.Scanner;

public class botMain {
    public final static int Port = 2503;

    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Запустить программу в режиме сервера или клиента? (S(erver) / C(lient))");
        while (true) {
            String answer = in.nextLine();
            if (Character.toLowerCase(answer.charAt(0)) == 's') {
                new botServer();
                break;
            } else if (Character.toLowerCase(answer.charAt(0)) == 'c') {
                new botClient();
                break;
            } else {
                System.out.println("Некорректный ввод. Повторите.");
            }
        }
    }
}
