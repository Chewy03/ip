import java.util.Scanner;

public class JimmyTimmy {
    public static void main(String[] args) {
        String lb = "____________________________________________________________\n";
        String greet = lb + "Hello! I'm JimmyTimmy\n" + "What can I do for you?\n" + lb;
        String logoff = "Bye. Hope to see you again soon!\n" + lb;
        System.out.println(greet);

        Scanner scanner = new Scanner(System.in);
        String echo = "";
        while (true) {
            echo = scanner.nextLine();
            if (echo.equalsIgnoreCase("bye")) {
                System.out.println(lb + "\n" + logoff);
                break;
            } else {
                System.out.println(lb + echo + "\n" + lb);
            }
        }
        scanner.close();
    }
}