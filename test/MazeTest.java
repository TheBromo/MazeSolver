import ch.bbw.model.Maze;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;


public class MazeTest {
    static Maze maze;


    @BeforeAll
    public static void testInit() {
        maze = new Maze(10);
    }

    @Test
    @DisplayName("Getting test")
    public void testGet() {
        if (maze.getField(0, 0) == null) {
            fail("Test failed  " + maze.getField(0, 0));
        }


    }
}
