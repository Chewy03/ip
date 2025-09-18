package command;

import error.JimmyTimmyException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * Command to mark or unmark a task.
 */
public class MarkCommand implements Command {
    private final int index;
    private final boolean isMark;

    public MarkCommand(int index, boolean isMark) {
        this.index = index;
        this.isMark = isMark;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage)
            throws IOException, JimmyTimmyException {
        Task task;
        if (isMark) {
            task = tasks.markTask(index);
            storage.save(tasks.getTasks());
            return "Nice! I've marked this task as done:\n  " + task;
        } else {
            task = tasks.unmarkTask(index);
            storage.save(tasks.getTasks());
            return "OK, I've marked this task as not done yet:\n  " + task;
        }
    }
}
