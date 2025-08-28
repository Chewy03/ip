import java.util.Scanner;

public class JimmyTimmy {
    public static void main(String[] args) {
        String lb = "____________________________________________________________\n";
        String greet = lb + "Hello! I'm JimmyTimmy\n" + "What can I do for you?\n" + lb;
        String logoff = "Bye. Hope to see you again soon!\n" + lb;
        System.out.println(greet);

        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
        int taskCount = 0;
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(lb + "\n" + logoff);
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println(lb);
                for (int i = 0; i< taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(lb);
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println(lb + "added:" + input + "\n" + lb);
            }
        }
        scanner.close();
    }
}