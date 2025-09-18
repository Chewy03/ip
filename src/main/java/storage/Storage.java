package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

/**
 * The {@code Storage} class is responsible for saving and loading tasks
 * to and from a local file. It ensures data persistence for the
 * JimmyTimmy application across program runs.
 */
public class Storage {
    /** The file where tasks are stored. */
    private final File file;

    /** Date-time formatter used for deadlines and event times. */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /** Task type identifier for {@link ToDo}. */
    private static final String TYPE_TODO = "T";

    /** Task type identifier for {@link Deadline}. */
    private static final String TYPE_DEADLINE = "D";

    /** Task type identifier for {@link Event}. */
    private static final String TYPE_EVENT = "E";

    /**
     * Constructs a new {@code Storage} object for a specific file path.
     *
     * @param filePath the path to the file used for saving and loading tasks
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Ensures that the storage file exists.
     * If the file or its parent directories do not exist, they are created.
     *
     * @throws IOException if the file cannot be created
     */
    private void checkFile() throws IOException {
        assert file != null : "File object must not be null";

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    /**
     * Parses a single line from the storage file into a {@link Task} object.
     * <p>
     * The expected format for each line is:
     * <ul>
     *     <li>ToDo: {@code T | isDone | description}</li>
     *     <li>Deadline: {@code D | isDone | description | yyyy-MM-dd HHmm}</li>
     *     <li>Event: {@code E | isDone | description | yyyy-MM-dd HHmm | yyyy-MM-dd HHmm}</li>
     * </ul>
     * </p>
     *
     * @param line the line from the storage file representing a task
     * @return a {@code Task} object corresponding to the parsed line
     * @throws Exception if the line is corrupted, contains an unknown type,
     *                   or dates are incorrectly formatted
     */
    private Task parseLine(String line) throws Exception {
        String[] data = line.split(" \\| ");
        String type = data[0].trim();
        boolean isDone = "1".equals(data[1].trim());
        String description = data[2].trim();

        switch (type) {
            case TYPE_TODO:
                ToDo todo = new ToDo(description);
                if (isDone) todo.markAsDone();
                return todo;
            case TYPE_DEADLINE:
                LocalDateTime by = LocalDateTime.parse(data[3].trim(), FORMATTER);
                Deadline deadline = new Deadline(description, by);
                if (isDone) deadline.markAsDone();
                return deadline;
            case TYPE_EVENT:
                LocalDateTime start = LocalDateTime.parse(data[3].trim(), FORMATTER);
                LocalDateTime end = LocalDateTime.parse(data[4].trim(), FORMATTER);
                Event event = new Event(description, start, end);
                if (isDone) event.markAsDone();
                return event;
            default:
                throw new IOException("Unknown task type: " + type);
        }
    }

    /**
     * Loads all tasks from the storage file.
     * <p>
     * Each line in the file is parsed using {@link #parseLine(String)},
     * which converts a plain-text representation into a {@link Task} object.
     * Corrupted lines or lines with unknown task types are skipped, with a warning printed
     * to the console.
     * </p>
     *
     * @return an {@link ArrayList} of {@link Task} objects read from the file
     * @throws IOException if the file cannot be read or created
     */
    public ArrayList<Task> load() throws IOException {
        checkFile();
        ArrayList<Task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = parseLine(line);
                    tasks.add(task);
                } catch (Exception e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
        }

        return tasks;
    }

    /**
     * Saves a list of tasks to the storage file.
     * Each task is serialized into a plain-text line according to its type.
     *
     * @param tasks the list of tasks to save
     * @throws IOException if the file cannot be written
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        checkFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                String line;
                if (task instanceof ToDo) {
                    line = "T | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription();
                } else if (task instanceof Deadline) {
                    Deadline d = (Deadline) task;
                    line = "D | " + (task.isDone() ? "1" : "0") + " | " + d.getDescription()
                            + " | " + d.getDueDate().format(FORMATTER);
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    line = "E | " + (task.isDone() ? "1" : "0") + " | " + e.getDescription()
                            + " | " + e.getStart().format(FORMATTER)
                            + " | " + e.getEnd().format(FORMATTER);
                } else {
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }
        }
    }
}