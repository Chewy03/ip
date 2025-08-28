import java.util.Scanner;

public class JimmyTimmy {
    public static void main(String[] args) {
        String lb = "____________________________________________________________\n";
        String greet = lb + "Hello! I'm JimmyTimmy\n" + "What can I do for you?\n" + lb;
        String logoff = "Bye. Hope to see you again soon!\n" + lb;
        System.out.println(greet);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(lb + "\n" + logoff);
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println(lb + "Here are the tasks in your list:\n");
                for (int i = 0; i< taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(lb);
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].markAsDone();
                    System.out.println(lb + "Nice! I've marked this task as done:\n  " + tasks[index] + "\n" + lb);
                }
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].markAsNotDone();
                    System.out.println(lb + "OK, I've marked this task as not done yet:\n  " + tasks[index] + "\n" + lb);
                }
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(lb + "added:" + input + "\n" + lb);
            }
        }
        scanner.close();
    }
}