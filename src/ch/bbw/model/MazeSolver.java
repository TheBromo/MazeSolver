package ch.bbw.model;

import ch.bbw.controller.FXMLController;
import ch.bbw.model.Fields.*;

public class MazeSolver implements Runnable
{
    private Maze maze;
    private boolean solved;
    private Robot robot;
    private Goal goal;
    private FXMLController controller;
    private boolean paused;
    private Start start;

    public MazeSolver(FXMLController controller)
    {
        paused = false;
        this.controller = controller;
        controller.setSolver(this);
        maze = new Maze(7);
        solved = false;
        goal = new Goal();
        start = new Start();
        robot = maze.getRobot();

        maze.setField(0, 0, new Wall());
        maze.setField(1, 0, new Wall());
        maze.setField(2, 0, new Wall());
        maze.setField(3, 0, new Wall());
        maze.setField(4, 0, new Wall());
        maze.setField(5, 0, new Wall());
        maze.setField(6, 0, new Wall());
        maze.setField(0, 1, new Wall());
        maze.setField(1, 1, start);
        maze.setField(2, 1, new Empty());
        maze.setField(3, 1, new Empty());
        maze.setField(4, 1, new Empty());
        maze.setField(5, 1, new Empty());
        maze.setField(6, 1, new Wall());
        maze.setField(0, 2, new Wall());
        maze.setField(1, 2, new Empty());
        maze.setField(2, 2, new Empty());
        maze.setField(3, 2, new Empty());
        maze.setField(4, 2, new Empty());
        maze.setField(5, 2, new Empty());
        maze.setField(6, 2, new Wall());
        maze.setField(0, 3, new Wall());
        maze.setField(1, 3, new Empty());
        maze.setField(2, 3, new Wall());
        maze.setField(3, 3, new Empty());
        maze.setField(4, 3, new Empty());
        maze.setField(5, 3, new Empty());
        maze.setField(6, 3, new Wall());
        maze.setField(0, 4, new Wall());
        maze.setField(1, 4, new Empty());
        maze.setField(2, 4, new Empty());
        maze.setField(3, 4, new Wall());
        maze.setField(4, 4, new Empty());
        maze.setField(5, 4, new Empty());
        maze.setField(6, 4, new Wall());
        maze.setField(0, 5, new Wall());
        maze.setField(1, 5, new Empty());
        maze.setField(2, 5, new Empty());
        maze.setField(3, 5, new Wall());
        maze.setField(4, 5, new Empty());
        maze.setField(5, 5, new Empty());
        maze.setField(6, 5, new Wall());
        maze.setField(0, 6, new Wall());
        maze.setField(1, 6, new Wall());
        maze.setField(2, 6, new Wall());
        maze.setField(3, 6, new Wall());
        maze.setField(4, 6, new Wall());
        maze.setField(5, 6, new Goal());
        maze.setField(6, 6, new Wall());

        robot.setX(start.getX());
        robot.setY(start.getY());

        System.out.println("X: " + robot.getX() + "\nY: " + robot.getY() + "\n");
    }

    public void followWallWithRightHand()
    {
        while (!solved)
        {
            if (!paused)
            {
                step();
                controller.externaldraw(maze);
                try
                {
                    Thread.sleep(1000);
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

        if (robot.getX() == goal.getX() && robot.getY() == goal.getY())
        {
            System.out.println("Freedom!");
            solved = true;
        }
        else if (robot.getOrientation() == 'u')
        {
            if (maze.getField(robot.getX() + 1, robot.getY() + 1) instanceof Wall
                    || maze.getField(robot.getX() + 1, robot.getY()) instanceof Wall) // Is back right field or right field a wall?
            {
                if (maze.getField(robot.getX() + 1, robot.getY()) instanceof Wall) // Is right field a wall?
                {
                    if (maze.getField(robot.getX(), robot.getY() - 1) instanceof Wall) // Is frontal field a wall?
                    {
                        if (maze.getField(robot.getX() - 1, robot.getY()) instanceof Wall)
                        {
                            if (maze.getField(robot.getX(), robot.getY() + 1) instanceof Wall)
                            {
                                System.out.println("Game over man, game over!");
                                solved = true;
                            }
                            else
                            {
                                robot.goBack();
                            }
                        }
                        else
                        {
                            robot.goLeft();
                        }
                    }
                    else
                    {
                        robot.goForward();
                    }
                }
                else
                {
                    robot.goRight();
                }
            }
            else
            {
                System.out.println("FATAL ERROR");
                solved = true;
            }
        }
        else if (robot.getOrientation() == 'r')
        {
            if (maze.getField(robot.getX() - 1, robot.getY() + 1) instanceof Wall
                    || maze.getField(robot.getX(), robot.getY() + 1) instanceof Wall) // Is back right field or right field a wall?
            {
                if (maze.getField(robot.getX(), robot.getY() + 1) instanceof Wall) // Is right field a wall?
                {
                    if (maze.getField(robot.getX() + 1, robot.getY()) instanceof Wall) // Is frontal field a wall?
                    {
                        if (maze.getField(robot.getX(), robot.getY() - 1) instanceof Wall)
                        {
                            if (maze.getField(robot.getX() - 1, robot.getY()) instanceof Wall)
                            {
                                System.out.println("Game over man, game over!");
                                solved = true;
                            }
                            else
                            {
                                robot.goBack();
                            }
                        }
                        else
                        {
                            robot.goLeft();
                        }
                    }
                    else
                    {
                        robot.goForward();
                    }
                }
                else
                {
                    robot.goRight();
                }
            }
            else
            {
                System.out.println("FATAL ERROR");
                solved = true;
            }
        }
        else if (robot.getOrientation() == 'd')
        {
            if (maze.getField(robot.getX() - 1, robot.getY() - 1) instanceof Wall
                    || maze.getField(robot.getX() - 1, robot.getY()) instanceof Wall) // Is back right field or right field a wall?
            {
                if (maze.getField(robot.getX() - 1, robot.getY()) instanceof Wall) // Is right field a wall?
                {
                    if (maze.getField(robot.getX(), robot.getY() + 1) instanceof Wall) // Is frontal field a wall?
                    {
                        if (maze.getField(robot.getX() + 1, robot.getY()) instanceof Wall)
                        {
                            if (maze.getField(robot.getX(), robot.getY() - 1) instanceof Wall)
                            {
                                System.out.println("Game over man, game over!");
                                solved = true;
                            }
                            else
                            {
                                robot.goBack();
                            }
                        }
                        else
                        {
                            robot.goLeft();
                        }
                    }
                    else
                    {
                        robot.goForward();
                    }
                }
                else
                {
                    robot.goRight();
                }
            }
            else
            {
                System.out.println("FATAL ERROR");
                solved = true;
            }
        }
        else if (robot.getOrientation() == 'l')
        {
            if (maze.getField(robot.getX() + 1, robot.getY() - 1) instanceof Wall
                    || maze.getField(robot.getX(), robot.getY() - 1) instanceof Wall) // Is back right field or right field a wall?
            {
                if (maze.getField(robot.getX(), robot.getY() - 1) instanceof Wall) // Is right field a wall?
                {
                    if (maze.getField(robot.getX() - 1, robot.getY()) instanceof Wall) // Is frontal field a wall?
                    {
                        if (maze.getField(robot.getX(), robot.getY() + 1) instanceof Wall)
                        {
                            if (maze.getField(robot.getX() + 1, robot.getY()) instanceof Wall)
                            {
                                System.out.println("Game over man, game over!");
                                solved = true;
                            }
                            else
                            {
                                robot.goBack();
                            }
                        }
                        else
                        {
                            robot.goLeft();
                        }
                    }
                    else
                    {
                        robot.goForward();
                    }
                }
                else
                {
                    robot.goRight();
                }
            }
            else
            {
                System.out.println("FATAL ERROR");
                solved = true;
            }
        }
        else
        {
            System.out.println("How can our eyes be real if mirrors aren't real?");
        }

        System.out.println("\n(" + robot.getX() + "|" + robot.getY() + ")\n");
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
        followWallWithRightHand();
    }
}