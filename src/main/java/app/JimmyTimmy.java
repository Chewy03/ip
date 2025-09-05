package app;

import task.Task;
import task.TaskList;
import storage.Storage;
import parser.Parser;
import error.JimmyTimmyException;
import ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
/**
 * The {@code JimmyTimmy} class represents the main application that manages tasks through
 * user commands. It handles reading input, updating the task list,
 * saving tasks to persistent storage, and displaying output to the user.
 */
public class JimmyTimmy {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a new {@code JimmyTimmy} application instance.
     * Attempts to load previously saved tasks from the given file path.
     * If loading fails, initializes with an empty task list.
     *
     * @param filePath the file path to load and save tasks
     */
    public JimmyTimmy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            ArrayList<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main application loop.
     * Continuously reads user input, executes commands,
     * updates the task list, and displays results until
     * the user issues an exit command.
     */
    public void run() {
        ui.showWelcome();
        boolean isBye = false;

        while (!isBye) {
            try {
                String input = ui.readCommand();
                ui.showLine();

                if (Parser.isBye(input)) {
                    isBye = true;
                    ui.showGoodbye();
                } else if (Parser.isList(input)) {
                    tasks.printTasks(ui);
                } else if (input.startsWith("mark")) {
                    int index = Parser.parseIndex(input);
                    tasks.markTask(index);
                    storage.save(tasks.getTasks());
                    ui.showTaskMarked(tasks.getTask(index));
                } else if (input.startsWith("unmark")) {
                    int index = Parser.parseIndex(input);
                    tasks.unmarkTask(index);
                    storage.save(tasks.getTasks());
                    ui.showTaskUnmarked(tasks.getTask(index));
                } else if (input.startsWith("delete")) {
                    int index = Parser.parseIndex(input);
                    Task removed = tasks.deleteTask(index);
                    storage.save(tasks.getTasks());
                    ui.showTaskRemoved(removed, tasks.size());
                } else if (input.startsWith("find")) {
                    String keyword = input.substring(4).trim();
                    if (keyword.isEmpty()) {
                        ui.showError("Please provide a keyword to search for.");
                    } else {
                        ArrayList<Task> results = tasks.findTasks(keyword);
                        ui.showFoundTasks(results);
                    }
                } else {
                    Task task = Parser.parseTask(input);
                    tasks.addTask(task);
                    storage.save(tasks.getTasks());
                    ui.showTaskAdded(task, tasks.size());
                }

            } catch (JimmyTimmyException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Failed to save tasks: " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * The entry point for the application.
     * Creates a new (@code JimmyTimmy) instance with default save file
     * and starts program loop.
     * @param args
     */
    public static void main(String[] args) {
        new JimmyTimmy("data/jimmyTimmy.txt").run();
    }
}
