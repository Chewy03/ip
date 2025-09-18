package command;

import error.JimmyTimmyException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * Represents an executable command entered by the user.
 */
public interface Command {
    /**
     * Executes the command with access to the task list, UI, and storage.
     *
     * @param tasks   the current task list
     * @param ui      the UI object for interaction
     * @param storage the storage handler for saving tasks
     * @return a message describing the result of the command
     * @throws JimmyTimmyException if the command cannot be executed
     * @throws IOException         if an error occurs while saving/loading
     */
    String execute(TaskList tasks, Ui ui, Storage storage) throws JimmyTimmyException, IOException;

    /**
     * Returns whether this command signals the program to exit.
     *
     * @return true if the program should exit, false otherwise
     */
    default boolean isExit() {
        return false;
    }
}
