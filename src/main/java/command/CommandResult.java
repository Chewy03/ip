package command;

/**
 * Encapsulates the result of executing a command.
 */
public class CommandResult {
    private final String message;
    private final boolean isExit;

    public CommandResult(String message, boolean isExit) {
        this.message = message;
        this.isExit = isExit;
    }

    public String getMessage() {
        return message;
    }

    public boolean isExit() {
        return isExit;
    }
}
