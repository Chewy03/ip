package storage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

public class Storage {
    private final File file;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    private void checkFile() throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

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