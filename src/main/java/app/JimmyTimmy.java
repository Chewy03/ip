package app;

import task.Task;
import task.TaskList;
import storage.Storage;
import parser.Parser;
import error.JimmyTimmyException;
import ui.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class JimmyTimmy {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                ui.showLine();

                if (Parser.isExit(input)) {
                    isExit = true;
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

    public static void main(String[] args) {
        new JimmyTimmy("data/jimmyTimmy.txt").run();
    }
}
