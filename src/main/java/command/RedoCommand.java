package command;

import error.JimmyTimmyException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.IOException;
import java.util.Stack;

/**
 * Command to redo the last undone command.
 */
public class RedoCommand implements Command {
    private final Stack<UndoableCommand> undoStack;
    private final Stack<UndoableCommand> redoStack;

    public RedoCommand(Stack<UndoableCommand> undoStack, Stack<UndoableCommand> redoStack) {
        this.undoStack = undoStack;
        this.redoStack = redoStack;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException, JimmyTimmyException {
        if (redoStack.isEmpty()) {
            return "Nothing to redo!";
        }
        UndoableCommand lastUndone = redoStack.pop();
        lastUndone.execute(tasks, ui, storage);
        undoStack.push(lastUndone);
        return "Redid last undone command.";
    }
}
