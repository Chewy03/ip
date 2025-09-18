package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Command to exit the program.
 */
public class ExitCommand implements Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Bye. Hope to see you again soon!";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
