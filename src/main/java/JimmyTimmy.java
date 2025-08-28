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
            try {
                if (input.equalsIgnoreCase("bye")) {
                    System.out.println(lb + "\n" + logoff);
                    break;
                } else if (input.equalsIgnoreCase("list")) {
                    if (taskCount == 0) {
                        throw new JimmyTimmyException("Your list is empty!");
                    }
                    System.out.println(lb + "Here are the tasks in your list:\n");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                    System.out.println(lb);
                } else if (input.startsWith("mark ")) {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsDone();
                        System.out.println(lb + "Nice! I've marked this task as done:\n   " + tasks[index] + "\n" + lb);
                    } else {
                        throw new JimmyTimmyException("Task number does not exist!");
                    }
                } else if (input.startsWith("unmark ")) {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsNotDone();
                        System.out.println(lb + "OK, I've marked this task as not done yet:\n   " +
                                tasks[index] + "\n" + lb);
                    } else {
                        throw new JimmyTimmyException("Task number does not exist!");
                    }
                } else if (input.startsWith("todo")) {
                        String desc = input.length() > 4 ? input.substring(4).trim() : "";
                        if (desc.isEmpty()) {
                            throw new JimmyTimmyException("The description of a todo cannot be empty.");
                        }
                        tasks[taskCount] = new ToDo(desc);
                        taskCount++;
                    System.out.println(lb + "Got it. I've added this task:\n" + tasks[taskCount - 1] + "\nNow you have " +
                            taskCount + " tasks in the list.\n" + lb);
                } else if (input.startsWith("deadline")) {
                    String sliced = input.length() > 8 ? input.substring(8).trim() : "";
                    if (sliced.isEmpty()) {
                        throw new JimmyTimmyException("The description of a deadline cannot be empty.");
                    }

                    String[] details = sliced.split(" /by ", 2);

                    if (details.length < 2) {
                        throw new JimmyTimmyException("Deadline must have a description and a /by time.");
                    }
                    String desc = details[0];
                    String dueDate = details[1];
                    tasks[taskCount] = new Deadline(desc, dueDate);
                    taskCount++;
                    System.out.println(lb + "Got it. I've added this task:\n" + tasks[taskCount - 1] + "\nNow you have " +
                            taskCount + " tasks in the list.\n" + lb);
                } else if (input.startsWith("event")) {
                    String sliced = input.length() > 5 ? input.substring(5).trim() : "";
                    if (sliced.isEmpty()) {
                        throw new JimmyTimmyException("The description of an event cannot be empty.");
                    }
                    String[] details = input.substring(6).split(" /from | /to ");
                    if (details.length < 3) {
                        throw new JimmyTimmyException("Event must have description, /from, and /to.");
                    }
                    String desc = details[0];
                    String start = details[1];
                    String end = details[2];
                    tasks[taskCount] = new Event(desc, start, end);
                    taskCount++;
                    System.out.println(lb + "Got it. I've added this task:\n" + tasks[taskCount - 1] + "\nNow you have " +
                            taskCount + " tasks in the list.\n" + lb);
                } else {
                    throw new JimmyTimmyException("I don’t know what that means.");
                }
            } catch (JimmyTimmyException e) {
                System.out.println(lb + "OOPS!!! " + e.getMessage() + "\n" + lb);
            }
        }
        scanner.close();
    }
}