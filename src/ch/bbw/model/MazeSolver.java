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
    private int degrees;

    public MazeSolver(FXMLController controller)
    {
        degrees = 0;
        paused = false;
        this.controller = controller;
        controller.setSolver(this);
        maze = new Maze(10);
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
        maze.setField(7, 0, new Wall());
        maze.setField(8, 0, new Wall());
        maze.setField(9, 0, new Wall());
        maze.setField(0, 1, new Wall());
        maze.setField(1, 1, new Empty());
        maze.setField(2, 1, new Empty());
        maze.setField(3, 1, new Empty());
        maze.setField(4, 1, new Empty());
        maze.setField(5, 1, new Empty());
        maze.setField(6, 1, new Empty());
        maze.setField(7, 1, new Empty());
        maze.setField(8, 1, new Empty());
        maze.setField(9, 1, new Wall());
        maze.setField(0, 2, new Wall());
        maze.setField(1, 2, new Empty());
        maze.setField(2, 2, new Empty());
        maze.setField(3, 2, new Empty());
        maze.setField(4, 2, new Empty());
        maze.setField(5, 2, new Empty());
        maze.setField(6, 2, new Empty());
        maze.setField(7, 2, new Empty());
        maze.setField(8, 2, new Empty());
        maze.setField(9, 2, new Wall());
        maze.setField(0, 3, new Wall());
        maze.setField(1, 3, new Empty());
        maze.setField(2, 3, new Empty());
        maze.setField(3, 3, new Wall());
        maze.setField(4, 3, new Wall());
        maze.setField(5, 3, new Wall());
        maze.setField(6, 3, new Wall());
        maze.setField(7, 3, new Empty());
        maze.setField(8, 3, new Empty());
        maze.setField(9, 3, new Wall());
        maze.setField(0, 4, new Wall());
        maze.setField(1, 4, new Empty());
        maze.setField(2, 4, start);
        maze.setField(3, 4, new Wall());
        maze.setField(4, 4, new Empty());
        maze.setField(5, 4, new Empty());
        maze.setField(6, 4, new Wall());
        maze.setField(7, 4, new Empty());
        maze.setField(8, 4, new Empty());
        maze.setField(9, 4, new Wall());
        maze.setField(0, 5, new Wall());
        maze.setField(1, 5, new Empty());
        maze.setField(2, 5, new Empty());
        maze.setField(3, 5, new Wall());
        maze.setField(4, 5, new Empty());
        maze.setField(5, 5, new Empty());
        maze.setField(6, 5, new Wall());
        maze.setField(7, 5, new Empty());
        maze.setField(8, 5, new Empty());
        maze.setField(9, 5, new Wall());
        maze.setField(0, 6, new Wall());
        maze.setField(1, 6, new Empty());
        maze.setField(2, 6, new Empty());
        maze.setField(3, 6, new Wall());
        maze.setField(4, 6, new Wall());
        maze.setField(5, 6, new Wall());
        maze.setField(6, 6, new Wall());
        maze.setField(7, 6, new Empty());
        maze.setField(8, 6, new Empty());
        maze.setField(9, 6, new Wall());
        maze.setField(0, 7, new Wall());
        maze.setField(1, 7, new Empty());
        maze.setField(2, 7, new Empty());
        maze.setField(3, 7, new Empty());
        maze.setField(4, 7, new Empty());
        maze.setField(5, 7, new Empty());
        maze.setField(6, 7, new Empty());
        maze.setField(7, 7, new Empty());
        maze.setField(8, 7, new Empty());
        maze.setField(9, 7, new Wall());
        maze.setField(0, 8, new Wall());
        maze.setField(1, 8, new Empty());
        maze.setField(2, 8, new Empty());
        maze.setField(3, 8, new Empty());
        maze.setField(4, 8, new Empty());
        maze.setField(5, 8, new Empty());
        maze.setField(6, 8, new Empty());
        maze.setField(7, 8, new Empty());
        maze.setField(8, 8, goal);
        maze.setField(9, 8, new Wall());
        maze.setField(0, 9, new Wall());
        maze.setField(1, 9, new Wall());
        maze.setField(2, 9, new Wall());
        maze.setField(3, 9, new Wall());
        maze.setField(4, 9, new Wall());
        maze.setField(5, 9, new Wall());
        maze.setField(6, 9, new Wall());
        maze.setField(7, 9, new Wall());
        maze.setField(8, 9, new Wall());
        maze.setField(9, 9, new Wall());

        robot.setX(start.getX());
        robot.setY(start.getY());
    }

    public void solve()
    {
        while (!solved)
        {
            if (!paused)
            {
                rightHandAlgorithmStep();
                //pledgeAlgorithmStep();
                controller.externalDraw(maze);
                try
                {
                    Thread.sleep(500);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void rightHandAlgorithmStep()
    {
        System.out.println("\n(" + robot.getX() + "|" + robot.getY() + ")\n");

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
    }

    private void pledgeAlgorithmStep()
    {
        System.out.println("\n(" + robot.getX() + "|" + robot.getY() + ")\n");

        if (robot.getX() == goal.getX() && robot.getY() == goal.getY())
        {
            System.out.println("Freedom!");
            solved = true;
        }
        else if (robot.getOrientation() == 'u')
        {
            if (degrees != 0 || maze.getField(robot.getX(), robot.getY() - 1) instanceof Wall)
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
                                    degrees -= 180;
                                }
                            }
                            else
                            {
                                robot.goLeft();
                                degrees -= 90;
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
                        degrees += 90;
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
                robot.goForward();
            }
        }
        else if (robot.getOrientation() == 'r')
        {
            if (degrees != 0 || maze.getField(robot.getX() + 1, robot.getY()) instanceof Wall)
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
                                    degrees -= 180;
                                }
                            }
                            else
                            {
                                robot.goLeft();
                                degrees -= 90;
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
                        degrees += 90;
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
                robot.goForward();
            }
        }
        else if (robot.getOrientation() == 'd')
        {
            if (degrees != 0 || maze.getField(robot.getX(), robot.getY() + 1) instanceof Wall)
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
                                    degrees -= 180;
                                }
                            }
                            else
                            {
                                robot.goLeft();
                                degrees -= 90;
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
                        degrees += 90;
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
                robot.goForward();
            }
        }
        else if (robot.getOrientation() == 'l')
        {
            if (degrees != 0 || maze.getField(robot.getX() - 1, robot.getY()) instanceof Wall)
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
                                    degrees -= 180;
                                }
                            }
                            else
                            {
                                robot.goLeft();
                                degrees -= 90;
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
                        degrees += 90;
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
                robot.goForward();
            }
        }
        else
        {
            System.out.println("How can our eyes be real if mirrors aren't real?");
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