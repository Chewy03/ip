import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class JimmyTimmy {
    public static void main(String[] args) throws IOException {
        String lb = "____________________________________________________________\n";
        String greet = lb + "Hello! I'm JimmyTimmy\n" + "What can I do for you?\n" + lb;
        String logoff = "Bye. Hope to see you again soon!\n" + lb;
        System.out.println(greet);

        Storage storage = new Storage("./data/jimmyTimmy.txt");
        ArrayList<Task> tasks;
        try {
            tasks = storage.load();
        } catch (IOException e) {
            tasks = new ArrayList<>();
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                if (input.equalsIgnoreCase("bye")) {
                    System.out.println(lb + "\n" + logoff);
                    break;
                } else if (input.equalsIgnoreCase("list")) {
                    if (tasks.isEmpty()) {
                        throw new JimmyTimmyException("Your list is empty!");
                    }
                    System.out.println(lb + "Here are the tasks in your list:\n");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println(lb);
                } else if (input.startsWith("mark ")) {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        storage.save(tasks);
                        System.out.println(lb + "Nice! I've marked this task as done:\n   " + tasks.get(index) + "\n" + lb);
                    } else {
                        throw new JimmyTimmyException("Task number does not exist!");
                    }
                } else if (input.startsWith("unmark ")) {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        storage.save(tasks);
                        System.out.println(lb + "OK, I've marked this task as not done yet:\n   " +
                                tasks.get(index) + "\n" + lb);
                    } else {
                        throw new JimmyTimmyException("Task number does not exist!");
                    }
                } else if (input.startsWith("todo")) {
                        String desc = input.length() > 4 ? input.substring(4).trim() : "";
                        if (desc.isEmpty()) {
                            throw new JimmyTimmyException("The description of a todo cannot be empty.");
                        }
                        tasks.add(new ToDo(desc));
                        storage.save(tasks);

                        System.out.println(lb + "Got it. I've added this task:\n" + tasks.get(tasks.size() -1) +
                            "\nNow you have " + tasks.size() + " tasks in the list.\n" + lb);
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
                    tasks.add(new Deadline(desc, dueDate));
                    storage.save(tasks);
                    System.out.println(lb + "Got it. I've added this task:\n" + tasks.get(tasks.size() - 1) +
                            "\nNow you have " + tasks.size() + " tasks in the list.\n" + lb);
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
                    tasks.add(new Event(desc, start, end));
                    storage.save(tasks);
                    System.out.println(lb + "Got it. I've added this task:\n" + tasks.get(tasks.size() - 1) +
                            "\nNow you have " + tasks.size() + " tasks in the list.\n" + lb);
                } else if (input.startsWith("delete ")) {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new JimmyTimmyException("Task number does not exist!");
                    }
                    Task removed = tasks.remove(index);
                    storage.save(tasks);
                    System.out.println(lb + "Noted. I've removed this task:\n   " +
                            removed + "\nNow you have " + tasks.size() + " tasks in the list.\n" + lb);
                } else {
                    throw new JimmyTimmyException("I don’t know what that means.");
                }
            } catch (JimmyTimmyException e) {
                System.out.println(lb + "OOPS!!! " + e.getMessage() + "\n" + lb);
            } catch (NumberFormatException e) {
                System.out.println(lb + "OOPS!!! Please enter a valid task number.\n" + lb);
            }
        }
        scanner.close();
    }
}