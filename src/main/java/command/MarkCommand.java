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
public class MarkCommand implements UndoableCommand {
    private final int index;
    private final boolean isMark;

    /**
     * Stores the previous state of the task (marked/unmarked)
     * before execution so we can undo later.
     */
    private boolean previousState;

    public MarkCommand(int index, boolean isMark) {
        this.index = index;
        this.isMark = isMark;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage)
            throws IOException, JimmyTimmyException {
        Task task = tasks.getTask(index);
        previousState = task.isDone();

        if (isMark) {
            task = tasks.markTask(index);
            storage.save(tasks.getTasks());
            return "Nice! I've checked this item out of your cart:\n  " + task;
        } else {
            task = tasks.unmarkTask(index);
            storage.save(tasks.getTasks());
            return "Aw, I've returned this item to the cart:\n  " + task;
        }
    }

    @Override
    public void undo(TaskList tasks, Ui ui, Storage storage) throws JimmyTimmyException, IOException {
        Task task = tasks.getTask(index);

        if (previousState) {
            tasks.markTask(index);
        } else {
            tasks.unmarkTask(index);
        }

        storage.save(tasks.getTasks());
        ui.showMessage("I've put the item back where it belonged\n  " + task);
    }
}
