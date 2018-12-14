package chat;
import java.util.Scanner;
public class MainChat {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Запустить программу в режиме сервера или клиента? (S(erver) / C(lient):nick)");
        while (true) {
            String [] answer = in.nextLine().split(":");
            if (Character.toLowerCase(answer[0].charAt(0)) == 's') {
                new chatServer();
                break;
            } else if (Character.toLowerCase(answer[0].charAt(0)) == 'c' && answer.length==2) {
                new chatClient(answer[1]);
                break;
            } else {
                System.out.println("Некорректный ввод. Повторите.");
            }
        }
    }

}