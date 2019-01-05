package ch.bbw.solver.model;

import ch.bbw.solver.controller.FXMLController;
import ch.bbw.solver.model.fields.*;

public class MazeSolver implements Runnable {
    private final Direction UP, RIGHT, DOWN, LEFT;
    private FXMLController controller;
    private Maze maze;
    private boolean solved, paused;
    private Robot robot;
    private Start start;
    private Goal goal;

    public MazeSolver(FXMLController controller) {
        this.controller = controller;
        controller.setSolver(this);

        solved = false;
        paused = false;

        UP = new Direction(new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(1, 1));
        RIGHT = new Direction(new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(0, -1), new Position(-1, 1));
        DOWN = new Direction(new Position(0, 1), new Position(-1, 0), new Position(0, -1), new Position(1, 0), new Position(-1, -1));
        LEFT = new Direction(new Position(-1, 0), new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(1, -1));

        if (controller.getImportedMap() == null) {
            //if no map is imported
            goal = new Goal(new Position(8, 8));
            start = new Start(new Position(4, 4));

            maze = new Maze(10, start.getPosition());
            robot = maze.getRobot();
            robot.setOrientation(UP);

            // Backup in case no map was uploaded before the user presses start
            char[] mapSetup =
                    {
                            'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
                            'w', 'w', ' ', ' ', ' ', ' ', ' ', 'w', ' ', 'w',
                            'w', ' ', ' ', ' ', ' ', ' ', 'w', 'w', ' ', 'w',
                            'w', ' ', ' ', 'w', 'w', ' ', ' ', ' ', ' ', 'w',
                            'w', ' ', ' ', 'w', 's', ' ', ' ', 'w', 'w', 'w',
                            'w', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'w',
                            'w', ' ', 'w', ' ', ' ', ' ', ' ', ' ', 'w', 'w',
                            'w', 'w', 'w', ' ', 'w', ' ', ' ', ' ', ' ', 'w',
                            'w', ' ', ' ', ' ', 'w', ' ', 'w', ' ', 'g', 'w',
                            'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'
                    };
            Field[] fields = maze.getFields();
            for (int i = 0; i < fields.length; i++) {
                switch (mapSetup[i]) {
                    case ' ':
                        fields[i] = new Empty(fields[i].getPosition());
                        break;
                    case 'w':
                        fields[i] = new Wall(fields[i].getPosition());
                        break;
                    case 's':
                        fields[i] = start;
                        break;
                    case 'g':
                        fields[i] = goal;
                        break;
                }
            }
        } else {
            Field[][] fields = controller.getImportedMap();
            for (int x = 0; x < fields.length; x++) {
                for (int y = 0; y < fields[x].length; y++) {
                    Field field = fields[x][y];
                    if (field instanceof Goal) {
                        goal = (Goal) field;
                    } else if (field instanceof Start) {
                        start = (Start) field;
                    }
                }
            }


            maze = new Maze(fields.length, start.getPosition());

            robot = maze.getRobot();
            robot.setOrientation(UP);

            Field[] finalMap = maze.getFields();
            int count = 0;
            for (int x = 0; x < fields.length; x++) {
                for (int y = 0; y < fields[x].length; y++) {
                    finalMap[count] = fields[y][x];
                    count++;
                }
            }
        }
    }

    private void turnRight(Robot robot) {
        if (robot.getOrientation() == UP) {
            robot.setOrientation(RIGHT);
        } else if (robot.getOrientation() == RIGHT) {
            robot.setOrientation(DOWN);
        } else if (robot.getOrientation() == DOWN) {
            robot.setOrientation(LEFT);
        } else if (robot.getOrientation() == LEFT) {
            robot.setOrientation(UP);
        }
    }

    private boolean isWall(Field field) {
        return field instanceof Wall;
    }

    private Position combinePositions(Position first, Position second) {
        return new Position(first.getX() + second.getX(), first.getY() + second.getY());
    }

    private boolean positionsAreEqual(Position firstPosition, Position secondPosition)
    {
        return firstPosition.getX() == secondPosition.getX() && firstPosition.getY() == secondPosition.getY();
    }

    private void solve() {
        controller.externalDraw();
        while (!solved) {
            if (!paused) {
                System.out.println("x=" + robot.getPosition().getX() + " y=" + robot.getPosition().getY()
                        + "\ndegrees=" + robot.getTurnedDegrees() + "\n");

                step();
                controller.externalDraw();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void step() {
        if (positionsAreEqual(robot.getPosition(), goal.getPosition())) {
            System.out.println("Freedom!");
            solved = true;
        } else {
            Direction orientation = robot.getOrientation();

            Field frontField = maze.getField(combinePositions(orientation.getFront(), robot.getPosition()));
            Field rightField = maze.getField(combinePositions(orientation.getRight(), robot.getPosition()));
            Field backField = maze.getField(combinePositions(orientation.getBack(), robot.getPosition()));
            Field leftField = maze.getField(combinePositions(orientation.getLeft(), robot.getPosition()));
            Field rightBackField = maze.getField(combinePositions(orientation.getRightBack(), robot.getPosition()));

            if ((!isWall(frontField) && isWall(rightField))
                    || (!isWall(frontField) && controller.isPledge() && robot.getTurnedDegrees() == 0)) {
                robot.goForward();
            }
            else if (isWall(rightBackField) && !isWall(rightField)) {
                turnRight(robot);
                robot.turn(90);
                robot.goForward();
            }
            else if (!isWall(leftField)) {
                turnRight(robot);
                turnRight(robot);
                turnRight(robot);
                robot.turn(-90);
                robot.goForward();
            }
            else if (!isWall(backField)) {
                turnRight(robot);
                turnRight(robot);
                robot.turn(-180);
                robot.goForward();
            }
            else if (!isWall(rightField)) {
                turnRight(robot);
                robot.turn(90);
                robot.goForward();
            }
            else {
                System.out.println("I'm stuck!");
            }
        }
    }

    @Override
    public void run() {
        solve();
    }

    public Maze getMaze() {
        return maze;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}