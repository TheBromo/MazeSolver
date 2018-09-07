import ch.bbw.model.Maze;
import org.junit.jupiter.api.Test;


public class MazeTest {
    static Maze maze;


    public static void testInit() {
        maze = new Maze(10);
    }

    @Test
    public void testGet() {
        if (maze.getField(0, 0) == null) {

        }
    }
}
