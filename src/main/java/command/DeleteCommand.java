package command;

import error.JimmyTimmyException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand implements Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage)
            throws IOException, JimmyTimmyException {
        Task removed = tasks.deleteTask(index);
        storage.save(tasks.getTasks());
        return "Noted. I've removed this task:\n  " + removed +
                "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
