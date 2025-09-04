package task;

import error.JimmyTimmyException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TaskListTest {

    @Test
    public void addAndDeleteTask() throws JimmyTimmyException {
        task.TaskList tasks = new task.TaskList(new ArrayList<>());
        Task t = new task.ToDo("Test todo");
        tasks.addTask(t);

        assertEquals(1, tasks.size());
        assertEquals("Test todo", tasks.getTask(0).description);

        Task removed = tasks.deleteTask(0);
        assertEquals(t, removed);
        assertEquals(0, tasks.size());
    }

    @Test
    public void markAndUnmarkTask() throws JimmyTimmyException {
        task.TaskList tasks = new task.TaskList();
        Task t = new task.ToDo("Another todo");
        tasks.addTask(t);

        tasks.markTask(0);
        assertTrue(tasks.getTask(0).isDone);

        tasks.unmarkTask(0);
        assertFalse(tasks.getTask(0).isDone);
    }
}
