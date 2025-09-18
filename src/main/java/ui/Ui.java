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
        System.out.println("Hello! I'm JimmyTimmy");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Shows the goodbye message when the program exits.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
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
            showMessage("Your task list is empty!");
            return;
        }

        showMessage("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            System.out.println((i + 1) + ". " + task);
        }
    }

    /**
     * Displays an error message when loading tasks from a file fails.
     */
    public void showLoadingError() {
        System.out.println("Failed to load tasks from file. Starting with an empty list.");
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
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays a message after a task has been removed.
     *
     * @param task       the task that was removed
     * @param totalTasks the current total number of tasks
     */
    public void showTaskRemoved(Task task, int totalTasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays a message after a task has been marked as done.
     *
     * @param task the task that was marked as done
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays a message after a task has been marked as not done.
     *
     * @param task the task that was marked as not done
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
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
            throw new JimmyTimmyException("Your list is empty!");
        } else {
            System.out.println("Here are the tasks in your list:");
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
            System.out.println("No matching tasks found!");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            int count = 1;
            for (Task task : tasks) {
                System.out.println(count + "." + task);
                count++;
            }
        }
    }
}
