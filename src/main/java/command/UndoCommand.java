package command;

import error.JimmyTimmyException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.IOException;
import java.util.Stack;

/**
 * Command to undo the last executed undoable command.
 */
public class UndoCommand implements Command {
    private final Stack<UndoableCommand> undoStack;
    private final Stack<UndoableCommand> redoStack;

    public UndoCommand(Stack<UndoableCommand> undoStack, Stack<UndoableCommand> redoStack) {
        this.undoStack = undoStack;
        this.redoStack = redoStack;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException, JimmyTimmyException {
        if (undoStack.isEmpty()) {
            return "Nothing to undo!";
        }
        UndoableCommand lastCommand = undoStack.pop();
        lastCommand.undo(tasks, ui, storage);  // added Ui parameter
        redoStack.push(lastCommand);
        return "Undid last command.";
    }
}
