package task;

import error.JimmyTimmyException;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents a collection of tasks in the JimmyTimmy application.
 * <p>
 * Provides methods to add, remove, retrieve, mark/unmark, and search for tasks.
 * </p>
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a {@code TaskList} with an existing list of tasks.
     *
     * @param tasks an {@link ArrayList} of tasks to initialize the list
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        assert task != null : "Task cannot be null";
        
        tasks.add(task);
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index the index of the task to remove
     * @return the removed {@link Task}
     * @throws JimmyTimmyException if the index is invalid
     */
    public Task deleteTask(int index) throws JimmyTimmyException {
        assert index >= 0 : "Index must not be negative";
        assert index < tasks.size() : "Index must be within task list size";

        if (index < 0 || index >= tasks.size()) {
            throw new JimmyTimmyException("Task number does not exist!");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index the index of the task to get
     * @return the {@link Task} at the index
     * @throws JimmyTimmyException if the index is invalid
     */
    public Task getTask(int index) throws JimmyTimmyException {
        assert index >= 0 : "Index must not be negative";
        assert index < tasks.size() : "Index must be within task list size";

        if (index < 0 || index >= tasks.size()) {
            throw new JimmyTimmyException("Task number does not exist!");
        }
        return tasks.get(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index the index of the task to mark
     * @throws JimmyTimmyException if the index is invalid
     */
    public void markTask(int index) throws JimmyTimmyException {
        getTask(index).markAsDone();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index the index of the task to unmark
     * @throws JimmyTimmyException if the index is invalid
     */
    public void unmarkTask(int index) throws JimmyTimmyException {
        getTask(index).markAsNotDone();
    }

    /**
     * Returns all tasks in the list.
     *
     * @return an {@link ArrayList} of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Prints all tasks using the given {@link Ui} object.
     *
     * @param ui the UI object used to display the tasks
     * @throws JimmyTimmyException if the list is empty
     */
    public void printTasks(Ui ui) throws JimmyTimmyException {
        if (tasks.isEmpty()) {
            throw new JimmyTimmyException("Your list is empty!");
        }
        ui.showTaskList(tasks);
    }

    /**
     * Searches for tasks whose descriptions contain the specified keyword.
     * <p>
     * The search is case-insensitive, and all matching tasks are returned in a new list.
     * </p>
     *
     * @param keyword the keyword to search for in task descriptions
     * @return an {@link ArrayList} of tasks whose descriptions contain the keyword;
     *         returns an empty list if no tasks match
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(task);
            }
        }
        return matches;
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "Your task list is empty!";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns whether the task list is empty.
     *
     * @return {@code true} if the list has no tasks, {@code false} otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
