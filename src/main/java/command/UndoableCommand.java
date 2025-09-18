package command;

import error.JimmyTimmyException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * Represents a command that can be undone.
 */
public interface UndoableCommand extends Command {
    /**
     * Undo the effects of this command.
     *
     * @param tasks   the task list to operate on
     * @param ui      the user interface for feedback
     * @param storage the storage to persist changes
     * @throws JimmyTimmyException if the undo cannot be performed
     * @throws IOException         if saving to storage fails
     */
    void undo(TaskList tasks, Ui ui, Storage storage) throws JimmyTimmyException, IOException;
}
