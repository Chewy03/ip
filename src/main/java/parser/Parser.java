package parser;

import command.*;
import error.JimmyTimmyException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import task.ToDo;
import ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Stack;

/**
 * Parses raw user input into executable {@link Command} objects.
 * <p>
 * The {@code Parser} class is responsible only for interpreting
 * user input strings and translating them into corresponding
 * commands. It does not execute commands directly; instead,
 * execution is delegated to the {@link Command#execute} method
 * of the returned object.
 *
 * Translates commands into Cartman-style shopping cart tasks:
 * - Grocery items
 * - Expiry dates
 * - Promotional periods
 * </p>
 */
public class Parser {

    // === Command keywords ===
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_UNDO = "undo";
    private static final String CMD_REDO = "redo";
    private static final String CMD_BYE = "bye";

    /** Formatter for parsing date/time strings into {@link java.time.LocalDateTime} objects. */
    private static final java.time.format.DateTimeFormatter DATE_FORMAT =
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Parses a full user command string and returns the corresponding {@link Command}.
     *
     * @param fullCommand the full input line entered by the user
     * @return a {@code Command} representing the parsed user instruction
     * @throws JimmyTimmyException if the command is unrecognized, arguments are missing,
     *                             or a number/date is incorrectly formatted
     */
    public static Command parse(String fullCommand,
                                Stack<UndoableCommand> undoStack,
                                Stack<UndoableCommand> redoStack) throws JimmyTimmyException {
        String trimmed = fullCommand.trim();
        if (trimmed.isEmpty()) throw new JimmyTimmyException("Your cart is empty! Type something to add items.");

        String[] parts = trimmed.split(" ", 2);
        String commandWord = parts[0];
        String args = parts.length > 1 ? parts[1].trim() : "";

        try {
            switch (commandWord) {
                case CMD_LIST:
                    return new ListCommand();

                case CMD_MARK:
                    return new MarkCommand(parseIndex(args), true);

                case CMD_UNMARK:
                    return new MarkCommand(parseIndex(args), false);

                case CMD_DELETE:
                    return new DeleteCommand(parseIndex(args));

                case CMD_TODO:
                case CMD_DEADLINE:
                case CMD_EVENT:
                    return new AddCommand(parseTask(commandWord, args));

                case CMD_UNDO:
                    return new UndoCommand(undoStack, redoStack);

                case CMD_REDO:
                    return new RedoCommand(undoStack, redoStack);

                case CMD_BYE:
                    return new ExitCommand();

                default:
                    throw new JimmyTimmyException("Hmm… That item isn't in my database. Try a another!");
            }
        } catch (NumberFormatException e) {
            throw new JimmyTimmyException("Item number must be a valid integer.");
        } catch (DateTimeParseException e) {
            throw new JimmyTimmyException("Invalid date/time. " +
                    "Use yyyy-MM-dd HH:mm format for expiry dates or promotions.");
        }
    }

    /**
     * Parses arguments into a specific type of {@link Task}.
     *
     * @param commandWord the type of task to create ({@code todo}, {@code deadline}, or {@code event})
     * @param args        the raw arguments string following the command word
     * @return a newly constructed {@code Task}
     * @throws JimmyTimmyException if arguments are missing or incorrectly formatted
     */
    private static Task parseTask(String commandWord, String args) throws JimmyTimmyException {
        switch (commandWord) {
            case CMD_TODO:
                if (args.isBlank()) {
                    throw new JimmyTimmyException("You need to specify a grocery item to add to the cart!");
                }
                return new ToDo(args);

            case CMD_DEADLINE:
                String[] deadlineParts = args.split(" /by ", 2);
                if (deadlineParts.length < 2) {
                    throw new JimmyTimmyException("Expiry date requires a grocery item and /by date.");
                }
                java.time.LocalDateTime by = java.time.LocalDateTime.parse(deadlineParts[1].trim(), DATE_FORMAT);
                return new Deadline(deadlineParts[0].trim(), by);

            case CMD_EVENT:
                String[] eventFromSplit = args.split(" /from ", 2);
                if (eventFromSplit.length < 2) {
                    throw new JimmyTimmyException("Promotional period requires a description and /from date.");
                }
                String[] eventToSplit = eventFromSplit[1].split(" /to ", 2);
                if (eventToSplit.length < 2) {
                    throw new JimmyTimmyException("Promotional period requires a /to date.");
                }
                java.time.LocalDateTime start = java.time.LocalDateTime.parse(eventToSplit[0].trim(), DATE_FORMAT);
                java.time.LocalDateTime end = java.time.LocalDateTime.parse(eventToSplit[1].trim(), DATE_FORMAT);
                return new Event(eventFromSplit[0].trim(), start, end);

            default:
                throw new JimmyTimmyException("Sorry I don't recognise that type of item: " + commandWord);
        }
    }

    /**
     * Parses a task index string into an integer (0-based).
     *
     * @param arg the argument string expected to contain a number
     * @return the parsed index
     * @throws NumberFormatException if the argument is not a valid integer
     * @throws JimmyTimmyException   if the argument is blank
     */
    private static int parseIndex(String arg) throws JimmyTimmyException {
        if (arg.isBlank()) {
            throw new JimmyTimmyException("You need to specify the item number in your cart.");
        }
        return Integer.parseInt(arg) - 1;
    }
}
