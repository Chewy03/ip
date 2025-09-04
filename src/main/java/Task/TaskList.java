package task;

import error.JimmyTimmyException;
import ui.Ui;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws JimmyTimmyException {
        if (index < 0 || index >= tasks.size()) {
            throw new JimmyTimmyException("Task number does not exist!");
        }
        return tasks.remove(index);
    }

    public Task getTask(int index) throws JimmyTimmyException {
        if (index < 0 || index >= tasks.size()) {
            throw new JimmyTimmyException("Task number does not exist!");
        }
        return tasks.get(index);
    }

    public void markTask(int index) throws JimmyTimmyException {
        getTask(index).markAsDone();
    }

    public void unmarkTask(int index) throws JimmyTimmyException {
        getTask(index).markAsNotDone();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void printTasks(Ui ui) throws JimmyTimmyException {
        if (tasks.isEmpty()) {
            throw new JimmyTimmyException("Your list is empty!");
        }
        ui.showTaskList(tasks);
    }


    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
