package ch.bbw.model;

import ch.bbw.controller.FXMLController;
import ch.bbw.model.Fields.*;

public class MazeSolver implements Runnable
{
    private FXMLController controller;
    private Maze maze;
    private boolean solved, paused, pledge;

    private Robot robot;
    private Start start;
    private Goal goal;

    private final Direction UP, RIGHT, DOWN, LEFT;

    private int turnedDegrees;

    public MazeSolver(FXMLController controller)
    {
        this.controller = controller;
        controller.setSolver(this);

        solved = false;
        paused = false;
        pledge = true;

        UP = new Direction(new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(1, 1));
        RIGHT = new Direction(new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(0, -1), new Position(-1, 1));
        DOWN = new Direction(new Position(0, 1), new Position(-1, 0), new Position(0, -1), new Position(1, 0), new Position(-1, -1));
        LEFT = new Direction(new Position(-1, 0), new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(1, -1));

        goal = new Goal(new Position(8, 8));
        start = new Start(new Position(4, 4));

        maze = new Maze(10, start.getPosition());
        robot = maze.getRobot();
        robot.setOrientation(UP);

        turnedDegrees = 0;

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
        for (int i = 0; i < fields.length; i++)
        {
            switch(mapSetup[i])
            {
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
    }

    private void turnRight(Robot robot)
    {
        if(robot.getOrientation() == UP)
        {
            robot.setOrientation(RIGHT);
        }
        else if(robot.getOrientation() == RIGHT)
        {
            robot.setOrientation(DOWN);
        }
        else if(robot.getOrientation() == DOWN)
        {
            robot.setOrientation(LEFT);
        }
        else if(robot.getOrientation() == LEFT)
        {
            robot.setOrientation(UP);
        }
    }

    private boolean isWall(Field field)
    {
        return field instanceof Wall;
    }

    private Position combinePositions(Position first, Position second)
    {
        return new Position(first.getX()+second.getX(), first.getY()+second.getY());
    }

    private void solve()
    {
        while (!solved)
        {
            if (!paused)
            {
                System.out.println("x=" + robot.getPosition().getX() + " y=" + robot.getPosition().getY()
                        + "degrees=" + turnedDegrees + "\n");

                controller.externalDraw(maze);
                step();

                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void step()
    {
        if (robot.getPosition().getX() == goal.getPosition().getX() && robot.getPosition().getY() == goal.getPosition().getY())
        {
            System.out.println("Freedom!");
            solved = true;
        }
        else
        {
            Direction orientation = robot.getOrientation();

            Field frontField = maze.getField(combinePositions(orientation.getFront(), robot.getPosition()));
            Field rightField = maze.getField(combinePositions(orientation.getRight(), robot.getPosition()));
            Field backField = maze.getField(combinePositions(orientation.getBack(), robot.getPosition()));
            Field leftField = maze.getField(combinePositions(orientation.getLeft(), robot.getPosition()));
            Field rightBackField = maze.getField(combinePositions(orientation.getRightBack(), robot.getPosition()));

            if ((!isWall(frontField) && isWall(rightField)) || (!isWall(frontField) && pledge && turnedDegrees == 0))
            {
                robot.goForward();
            }
            else if(isWall(rightBackField) && !isWall(rightField))
            {
                turnRight(robot);
                turnedDegrees -= 90;
                robot.goForward();
            }
            else if(!isWall(leftField))
            {
                turnRight(robot);
                turnRight(robot);
                turnRight(robot);
                turnedDegrees += 90;
                robot.goForward();
            }
            else if(!isWall(backField))
            {
                turnRight(robot);
                turnRight(robot);
                turnedDegrees += 180;
                robot.goForward();
            }
            else if(!isWall(rightField))
            {
                turnRight(robot);
                turnedDegrees -= 90;
                robot.goForward();
            }
        }
    }

    public Maze getMaze()
    {
        return maze;
    }

    public void setMaze(Maze maze)
    {
        this.maze = maze;
    }

    public boolean isSolved()
    {
        return solved;
    }

    public void setSolved(boolean solved)
    {
        this.solved = solved;
    }

    public Robot getRobot()
    {
        return robot;
    }

    public void setRobot(Robot robot)
    {
        this.robot = robot;
    }

    public Goal getGoal()
    {
        return goal;
    }

    public void setGoal(Goal goal)
    {
        this.goal = goal;
    }

    public boolean isPaused()
    {
        return paused;
    }

    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }

    @Override
    public void run()
    {
        solve();
    }
}