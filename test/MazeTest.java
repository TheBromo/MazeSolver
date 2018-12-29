import ch.bbw.solver.model.Maze;
import ch.bbw.solver.model.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;


public class MazeTest {
    private static Maze maze;


    @BeforeAll
    public static void testInit() {
        maze = new Maze(10, new Position(1, 1));
    }

    @Test
    @DisplayName("Getting test")
    public void testGet() {
        if (maze.getField(new Position(0, 0)) == null) {
            fail("Test failed  " + maze.getField(new Position(0, 0)));
        }


    }
}
