package app;

import command.Command;
import command.RedoCommand;
import command.UndoCommand;
import command.UndoableCommand;
import parser.Parser;
import task.Task;
import task.TaskList;
import storage.Storage;
import error.JimmyTimmyException;
import ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The {@code JimmyTimmy} class represents the main application that manages tasks through
 * user commands. It handles reading input, updating the task list,
 * saving tasks to persistent storage, and displaying output to the user.
 */
public class JimmyTimmy {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private String filePath;

    private Stack<UndoableCommand> undoStack = new Stack<>();
    private Stack<UndoableCommand> redoStack = new Stack<>();

    /**
     * Creates a new {@code JimmyTimmy} application instance.
     * Attempts to load previously saved tasks from the given file path.
     * If loading fails, initializes with an empty task list.
     *
     * @param filePath the file path to load and save tasks
     */
    public JimmyTimmy(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path cannot be null or empty";

        this.filePath = filePath;
        this.ui = new Ui();
    }

    /**
     * Initializes storage and tasks from file.
     * Must be called before run() or getResponse().
     */
    public void init() {
        assert filePath != null : "File path must not be null before init";
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
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                if (input.isBlank()) continue;
                ui.showLine();

                Command command = Parser.parse(input, undoStack, redoStack);
                String result = command.execute(tasks, ui, storage);
                ui.showMessage(result);

                if (command instanceof UndoableCommand && !(command instanceof UndoCommand) && !(command instanceof RedoCommand)) {
                    undoStack.push((UndoableCommand) command);
                    redoStack.clear();
                }

                isExit = command.isExit();

            } catch (JimmyTimmyException | IOException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }


    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input, undoStack, redoStack);
            String result = command.execute(tasks, ui, storage);

            if (command instanceof UndoableCommand && !(command instanceof UndoCommand) && !(command instanceof RedoCommand)) {
                undoStack.push((UndoableCommand) command);
                redoStack.clear();
            }

            return result;

        } catch (JimmyTimmyException | IOException e) {
            return "Error: " + e.getMessage();
        }
    }
    /**
     * The entry point for the application.
     * Creates a new (@code JimmyTimmy) instance with default save file
     * and starts program loop.
     * @param args
     */
    public static void main(String[] args) {
        JimmyTimmy app = new JimmyTimmy("data/jimmyTimmy.txt");
        app.init();
        app.run();
    }
}
