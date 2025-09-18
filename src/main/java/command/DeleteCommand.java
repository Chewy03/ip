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
public class DeleteCommand implements UndoableCommand {
    private final int index;
    private Task removedTask;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage)
            throws IOException, JimmyTimmyException {
        removedTask = tasks.deleteTask(index);
        storage.save(tasks.getTasks());
        return "Noted. I've removed this task:\n  " + removedTask +
                "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    @Override
    public void undo(TaskList tasks, Ui ui, Storage storage) throws JimmyTimmyException, IOException {
        if (removedTask == null) {
            throw new JimmyTimmyException("No task to undo deletion.");
        }
        tasks.addTaskAt(index, removedTask);
        storage.save(tasks.getTasks());
    }}
