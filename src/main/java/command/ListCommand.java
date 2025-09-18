package command;

import error.JimmyTimmyException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand implements Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JimmyTimmyException {
        if (tasks.isEmpty()) {
            return "Your task list is empty!";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            sb.append((i + 1)).append(". ").append(task).append("\n");
        }
        return sb.toString().trim();
    }
}
