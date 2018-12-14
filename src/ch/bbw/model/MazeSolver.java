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
        maze = new Maze(10);
        solved = false;
        paused = false;
        pledge = true;

        robot = maze.getRobot();
        goal = new Goal();
        start = new Start();

        UP = new Direction(new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(1, 1));
        RIGHT = new Direction(new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(0, -1), new Position(-1, 1));
        DOWN = new Direction(new Position(0, 1), new Position(-1, 0), new Position(0, -1), new Position(1, 0), new Position(-1, -1));
        LEFT = new Direction(new Position(-1, 0), new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(1, -1));

        turnedDegrees = 0;
        robot.setOrientation(UP);

        for(int i=0; i<10; i++)
        {
            maze.setField(new Position(i,0), new Wall());
        }

        maze.setField(new Position(0, 1), new Wall());
        for(int i=1; i<9; i++)
        {
            maze.setField(new Position(i,1), new Empty());
        }
        maze.setField(new Position(9, 1), new Wall());

        maze.setField(new Position(0, 2), new Wall());
        for(int i=1; i<9; i++)
        {
            maze.setField(new Position(i,2), new Empty());
        }
        maze.setField(new Position(9, 2), new Wall());

        maze.setField(new Position(0, 3), new Wall());
        maze.setField(new Position(1, 3), new Empty());
        maze.setField(new Position(2, 3), new Empty());
        maze.setField(new Position(3, 3), new Wall());
        maze.setField(new Position(4, 3), new Wall());
        maze.setField(new Position(5, 3), new Wall());
        maze.setField(new Position(6, 3), new Wall());
        maze.setField(new Position(7, 3), new Empty());
        maze.setField(new Position(8, 3), new Empty());
        maze.setField(new Position(9, 3), new Wall());
        maze.setField(new Position(0, 4), new Wall());
        maze.setField(new Position(1, 4), new Empty());
        maze.setField(new Position(2, 4), start);
        maze.setField(new Position(3, 4), new Wall());
        maze.setField(new Position(4, 4), new Empty());
        maze.setField(new Position(5, 4), new Empty());
        maze.setField(new Position(6, 4), new Wall());
        maze.setField(new Position(7, 4), new Empty());
        maze.setField(new Position(8, 4), new Empty());
        maze.setField(new Position(9, 4), new Wall());
        maze.setField(new Position(0, 5), new Wall());
        maze.setField(new Position(1, 5), new Empty());
        maze.setField(new Position(2, 5), new Empty());
        maze.setField(new Position(3, 5), new Wall());
        maze.setField(new Position(4, 5), new Empty());
        maze.setField(new Position(5, 5), new Empty());
        maze.setField(new Position(6, 5), new Wall());
        maze.setField(new Position(7, 5), new Empty());
        maze.setField(new Position(8, 5), new Empty());
        maze.setField(new Position(9, 5), new Wall());
        maze.setField(new Position(0, 6), new Wall());
        maze.setField(new Position(1, 6), new Empty());
        maze.setField(new Position(2, 6), new Empty());
        maze.setField(new Position(3, 6), new Wall());
        maze.setField(new Position(4, 6), new Wall());
        maze.setField(new Position(5, 6), new Wall());
        maze.setField(new Position(6, 6), new Wall());
        maze.setField(new Position(7, 6), new Empty());
        maze.setField(new Position(8, 6), new Empty());
        maze.setField(new Position(9, 6), new Wall());

        maze.setField(new Position(0, 7), new Wall());
        for(int i=1; i<9; i++)
        {
            maze.setField(new Position(i,7), new Empty());
        }
        maze.setField(new Position(9, 7), new Wall());

        maze.setField(new Position(0, 8), new Wall());
        for(int i=1; i<8; i++)
        {
            maze.setField(new Position(i,8), new Empty());
        }
        maze.setField(new Position(8, 8), goal);
        maze.setField(new Position(9, 8), new Wall());

        for(int i=0; i<10; i++)
        {
            maze.setField(new Position(i,9), new Wall());
        }

        robot.setPosition(start.getPosition());
    }

    private void turnRight(Direction currentOrientation)
    {
        if(currentOrientation == UP)
        {
            robot.setOrientation(RIGHT);
        }
        else if(currentOrientation == RIGHT)
        {
            robot.setOrientation(DOWN);
        }
        else if(currentOrientation == DOWN)
        {
            robot.setOrientation(LEFT);
        }
        else if(currentOrientation == LEFT)
        {
            robot.setOrientation(UP);
        }
    }

    private Position combinePositions(Position first, Position second)
    {
        return new Position(first.getX()+second.getX(), first.getY()+second.getY());
    }

    public void solve()
    {
        while (!solved)
        {
            if (!paused)
            {
                step();
                controller.externaldraw(maze);
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

    // boolean pledge: Is it used inside the pledge algorithm?
    private void step()
    {
        System.out.println("\n(" + robot.getPosition().getX() + "|" + robot.getPosition().getY() + ")\n");

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

            if(pledge && turnedDegrees == 0 && !(frontField instanceof Wall))
            {
                robot.goForward();
            }
            else
            {
                // Is RIGHT field or RIGHT BACK field a wall or the pledge algorithm being used?
                if (pledge || rightBackField instanceof Wall || rightField instanceof Wall)
                {
                    // Is RIGHT field a wall?
                    if (pledge || rightField instanceof Wall)
                    {
                        // Is frontal field a wall?
                        if (frontField instanceof Wall)
                        {
                            if (leftField instanceof Wall)
                            {
                                if (backField instanceof Wall)
                                {
                                    System.out.println("I'm surrounded!");
                                    solved = true;
                                    return;
                                }
                                else
                                {
                                    turnRight(orientation);
                                    turnRight(orientation);
                                    turnedDegrees += 180;
                                }
                            }
                            else
                            {
                                turnRight(orientation);
                                turnRight(orientation);
                                turnRight(orientation);
                                turnedDegrees -= 90;
                            }
                        }
                        else
                        {
                            // Go forward
                        }
                    }
                    else
                    {
                        turnRight(orientation);
                        turnedDegrees += 90;
                    }

                    robot.goForward();
                }
                else
                {
                    System.out.println("I'm in the middle of nowhere!");
                    solved = true;
                }
            }
        }
    }

    /*private void pledgeAlgorithmStep()
    {
        System.out.println("\n(" + robot.getX() + "|" + robot.getY() + ")\n");

        if (robot.getX() == goal.getX() && robot.getY() == goal.getY())
        {
            System.out.println("Freedom!");
            solved = true;
        }
        else if (robot.getOrientation() == 'u')
        {
            // Is turnedDegrees not zero?
            if (robot.getTurnedDegrees() != 0 || maze.getField(robot.getX(), robot.getY() - 1) instanceof Wall)
            {
                rightHandAlgorithmStep(true);
            }
            else
            {
                robot.goForward();
            }
        }
        else if (robot.getOrientation() == 'r')
        {
            // Is turnedDegrees not zero?
            if (robot.getTurnedDegrees() != 0 || maze.getField(robot.getX() + 1, robot.getY()) instanceof Wall)
            {
                rightHandAlgorithmStep(true);
            }
            else
            {
                robot.goForward();
            }
        }
        else if (robot.getOrientation() == 'd')
        {
            // Is turnedDegrees not zero?
            if (robot.getTurnedDegrees() != 0 || maze.getField(robot.getX(), robot.getY() + 1) instanceof Wall)
            {
                rightHandAlgorithmStep(true);
            }
            else
            {
                robot.goForward();
            }
        }
        else if (robot.getOrientation() == 'l')
        {
            // Is turnedDegrees not zero?
            if (robot.getTurnedDegrees() != 0 || maze.getField(robot.getX() - 1, robot.getY()) instanceof Wall)
            {
                rightHandAlgorithmStep(true);
            }
            else
            {
                robot.goForward();
            }
        }
        else
        {
            System.out.println("How can our eyes be real if mirrors aren't real?");
        }
    }*/

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