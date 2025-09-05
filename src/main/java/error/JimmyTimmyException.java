package error;

/**
 * The {@code JimmyTimmyException} class epresents a custom exception used in JimmyTimmy.
 *
 * Exception is thrown when an error specific to the application's
 * task management or command parsing occurs.
 */
public class JimmyTimmyException extends Exception {
    /**
     * Constructs a new {@code JimmyTimmyException} with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception
     */
    public JimmyTimmyException(String message) {
        super(message);
    }
}
