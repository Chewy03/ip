package parser;

import error.JimmyTimmyException;
import org.junit.jupiter.api.Test;
import task.Task;
import task.ToDo;
import task.Deadline;
import task.Event;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void testParseToDo() throws Exception {
        Task task = Parser.parseTask("todo Buy milk");
        assertTrue(task instanceof ToDo);
        ToDo todo = (ToDo) task;
        assertEquals("Buy milk", task.getDescription());
    }

    @Test
    void testParseDeadline() throws Exception {
        Task task = Parser.parseTask("deadline Submit report /by 2025-09-05 1800");
        assertTrue(task instanceof Deadline);
        Deadline d = (Deadline) task;
        LocalDateTime expected = LocalDateTime.of(2025, 9, 5, 18, 0);
        assertEquals(expected, d.getDueDate());
    }

    @Test
    void testParseEvent() throws Exception {
        Task task = Parser.parseTask("event Meeting /from 2025-09-05 0900 /to 2025-09-05 1000");
        assertTrue(task instanceof Event);
        Event e = (Event) task;
        assertEquals(LocalDateTime.of(2025,9,5,9,0), e.getStart());
        assertEquals(LocalDateTime.of(2025,9,5,10,0), e.getEnd());
    }

    @Test
    void testParseInvalidTask() {
        Exception exception = assertThrows(Exception.class, () -> Parser.parseTask("random"));
        assertEquals("I don’t know what that means.", exception.getMessage());
    }
}
