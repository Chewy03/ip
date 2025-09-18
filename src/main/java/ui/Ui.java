package ui;

import error.JimmyTimmyException;
import task.Task;
import task.TaskList;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all interactions with the user, including reading commands
 * and displaying messages, tasks, and errors.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a Ui instance with a new Scanner for reading user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prints a horizontal line to visually separate outputs.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Shows the welcome message when the program starts.
     */
    public void showWelcome() {
        showLine();
        String welcomeMessage = """
            \uD83D\uDED2 Welcome to JimmyTimmy – Your ToDo Shopping Cart! \uD83D\uDED2
            Hello! I’m JimmyTimmy, your trusty shopping-cart buddy. I’ll help you
            keep track of your grocery items, expiry dates, and promotional deals,
            so you never forget anything in your cart.

            --- Commands ---

            1. list
               View all items currently in your cart.

            2. todo <item>
               Add a grocery item to your cart.

            3. deadline <item> /by <yyyy-MM-dd HH:mm>
               Add a grocery item with an expiry date.

            4. event <item> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>
               Add a promotional period or sale.

            5. mark <item number>
               Mark an item as purchased.

            6. unmark <item number>
               Return a purchased item back to the cart.

            7. delete <item number>
               Remove an item from the cart.

            8. undo / redo
               Undo or redo the last action.

            9. bye
               Exit JimmyTimmy and save your cart.

            --- Tips ---
            - Use correct date/time format: yyyy-MM-dd HH:mm
            - Use item numbers from 'list' when marking, unmarking, or deleting
            - Have fun! \uD83D\uDE0E
            """;
        System.out.println(welcomeMessage);
        showLine();
    }



    /**
     * Displays a generic message to the user.
     *
     * @param message the message to be displayed
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays all tasks in the given {@link TaskList}.
     *
     * @param tasks the list of tasks to display
     */
    public void showTasks(TaskList tasks) throws JimmyTimmyException {
        if (tasks.isEmpty()) {
            showMessage("Your cart is empty!");
            return;
        }

        showMessage("Here are the items in your cart:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            System.out.println((i + 1) + ". " + task);
        }
    }

    /**
     * Displays an error message when loading tasks from a file fails.
     */
    public void showLoadingError() {
        System.out.println("Failed to retrieve shopping bag. Starting with an empty cart.");
    }

    /**
     * Displays a general error message to the user.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    /**
     * Reads the next line of user input.
     *
     * @return the input string entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a message after a task has been added.
     *
     * @param task       the task that was added
     * @param totalTasks the current total number of tasks
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Here is your updated cart:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " items in your cart.");
    }

    /**
     * Displays a message after a task has been removed.
     *
     * @param task       the task that was removed
     * @param totalTasks the current total number of tasks
     */
    public void showTaskRemoved(Task task, int totalTasks) {
        System.out.println("Here is your updated cart:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " items in your cart.");
    }

    /**
     * Displays a message after a task has been marked as done.
     *
     * @param task the task that was marked as done
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've checked this item out of your cart:");
        System.out.println("  " + task);
    }

    /**
     * Displays a message after a task has been marked as not done.
     *
     * @param task the task that was marked as not done
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("Aw, I've returned this item to the cart:");
        System.out.println("  " + task);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks the list of tasks to display
     * @throws JimmyTimmyException if the list is empty
     */
    public void showTaskList(ArrayList<Task> tasks) throws JimmyTimmyException {
        if (tasks.isEmpty()) {
            throw new JimmyTimmyException("Your cart is empty!");
        } else {
            System.out.println("Here are the items in your cart:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Displays tasks that match a search query.
     * If no tasks match, a message is shown indicating no results.
     *
     * @param tasks the list of matching tasks
     */
    public void showFoundTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No matching item found!");
        } else {
            System.out.println("Here are the matching items in your cart:");
            int count = 1;
            for (Task task : tasks) {
                System.out.println(count + "." + task);
                count++;
            }
        }
    }
}
