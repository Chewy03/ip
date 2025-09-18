package command;

import error.JimmyTimmyException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * Command to add a new task to the task list.
 */
public class AddCommand implements Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        return "Got it. I've added this task:\n  " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
