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
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    /**
     * Loads tasks from the storage file.
     * Each line is parsed into a {@link ToDo}, {@link Deadline}, or {@link Event}.
     * Corrupted or unrecognized lines are skipped with a warning.
     *
     * @return a list of {@link Task} objects loaded from the file
     * @throws IOException if the file cannot be read
     */
    public ArrayList<Task> load() throws IOException {
        checkFile();
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(" \\| ");
                    String type = data[0];
                    boolean isDone = data[1].equals("1");
                    String description = data[2];

                    switch (type) {
                        case "T":
                            ToDo todo = new ToDo(description);
                            if (isDone) todo.markAsDone();
                            tasks.add(todo);
                            break;
                        case "D":
                            LocalDateTime by = LocalDateTime.parse(data[3], FORMATTER);
                            Deadline deadline = new Deadline(description, by);
                            if (isDone) deadline.markAsDone();
                            tasks.add(deadline);
                            break;
                        case "E":
                            LocalDateTime start = LocalDateTime.parse(data[3], FORMATTER);
                            LocalDateTime end = LocalDateTime.parse(data[4], FORMATTER);
                            Event event = new Event(description, start, end);
                            if (isDone) event.markAsDone();
                            tasks.add(event);
                            break;
                        default:
                            System.out.println("Skipping invalid line: " + line);
                    }
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