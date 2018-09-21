package ch.bbw.model;

import ch.bbw.model.Fields.Empty;
import ch.bbw.model.Fields.Goal;
import ch.bbw.model.Fields.Robot;
import ch.bbw.model.Fields.Wall;

public class MazeSolver
{
	public Maze maze;
	public boolean solved;
	public Robot robot;

	public MazeSolver()
	{
		maze = new Maze(5);
		solved = false;
		robot = new Robot('u');
		maze.setField(0,0, new Wall());
		maze.setField(1,0, new Wall());
		maze.setField(2,0, new Wall());
		maze.setField(3,0, new Wall());
		maze.setField(4,0, new Wall());
		maze.setField(0,1, new Wall());
		maze.setField(1,1, robot);
		maze.setField(2,1, new Empty());
		maze.setField(3,1, new Empty());
		maze.setField(4,1, new Wall());
		maze.setField(0,2, new Wall());
		maze.setField(1,2, new Empty());
		maze.setField(2,2, new Empty());
		maze.setField(3,2, new Empty());
		maze.setField(4,2, new Wall());
		maze.setField(0,3, new Wall());
		maze.setField(1,3, new Empty());
		maze.setField(2,3, new Empty());
		maze.setField(3,3, new Empty());
		maze.setField(4,3, new Wall());
		maze.setField(0,4, new Wall());
		maze.setField(1,4, new Wall());
		maze.setField(2,4, new Wall());
		maze.setField(3,4, new Empty());
		maze.setField(4,4, new Wall());

		System.out.println("X: " + robot.getX() + "\nY: " + robot.getY() + "\n");
	}

	public void solve()
	{
		while(solved == false)
		{
			step();
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

	public void step()
	{
		if(robot.getOrientation() == 'u')
		{
			if(maze.getField(robot.getX(), robot.getY()-1) instanceof Empty
			&& maze.getField(robot.getX()+1, robot.getY()) instanceof Wall)
			{
				robot.goUp();
			}
			else if(maze.getField(robot.getX(), robot.getY()-1) instanceof Empty
					&& maze.getField(robot.getX()+1, robot.getY()+1) instanceof Wall)
			{
				robot.goUp();
				System.out.println("^ around a corner");
			}
			else if(maze.getField(robot.getX(), robot.getY()-1) instanceof Wall)
			{
				if(maze.getField(robot.getX()-1, robot.getY()) instanceof Empty)
				{
					robot.goLeft();
				}
				else if(maze.getField(robot.getX(), robot.getY()+1) instanceof Empty)
				{
					robot.goDown();
				}
				else if(maze.getField(robot.getX()+1, robot.getY()) instanceof Empty)
				{
					robot.goRight();
				}
				else
				{
					System.out.println("You're stuck!");
				}
				return;
			}
			else if(maze.getField(robot.getX(), robot.getY()-1) instanceof Goal)
			{
				System.out.println("Freedom!");
				solved = true;
				return;
			}
		}

		if(robot.getOrientation() == 'r')
		{
			if(maze.getField(robot.getX()+1, robot.getY()) instanceof Empty
					&& maze.getField(robot.getX(), robot.getY()+1) instanceof Wall)
			{
				robot.goRight();
			}
			else if(maze.getField(robot.getX()+1, robot.getY()) instanceof Empty
					&& maze.getField(robot.getX()-1, robot.getY()+1) instanceof Wall)
			{
				robot.goRight();
				System.out.println("^ around a corner");
			}
			else if(maze.getField(robot.getX()+1, robot.getY()) instanceof Wall)
			{
				if(maze.getField(robot.getX(), robot.getY()-1) instanceof Empty)
				{
					robot.goUp();
				}
				else if(maze.getField(robot.getX()-1, robot.getY()) instanceof Empty)
				{
					robot.goLeft();
				}
				else if(maze.getField(robot.getX(), robot.getY()+1) instanceof Empty)
				{
					robot.goDown();
				}
				else
				{
					System.out.println("You're stuck!");
				}
				return;
			}
			else if(maze.getField(robot.getX()+1, robot.getY()) instanceof Goal)
			{
				System.out.println("Freedom!");
				solved = true;
				return;
			}
		}

		if(robot.getOrientation() == 'd')
		{
			if(maze.getField(robot.getX(), robot.getY()+1) instanceof Empty
					&& maze.getField(robot.getX()-1, robot.getY()) instanceof Wall)
			{
				robot.goDown();
			}
			else if(maze.getField(robot.getX(), robot.getY()+1) instanceof Empty
					&& maze.getField(robot.getX()-1, robot.getY()-1) instanceof Wall)
			{
				robot.goDown();
				System.out.println("^ around a corner");
			}
			else if(maze.getField(robot.getX(), robot.getY()+1) instanceof Wall)
			{
				if(maze.getField(robot.getX()+1, robot.getY()) instanceof Empty)
				{
					robot.goRight();
				}
				else if(maze.getField(robot.getX(), robot.getY()-1) instanceof Empty)
				{
					robot.goUp();
				}
				else if(maze.getField(robot.getX()-1, robot.getY()) instanceof Empty)
				{
					robot.goLeft();
				}
				else
				{
					System.out.println("You're stuck!");
				}
				return;
			}
			else if(maze.getField(robot.getX(), robot.getY()+1) instanceof Goal)
			{
				System.out.println("Freedom!");
				solved = true;
				return;
			}
		}

		if(robot.getOrientation() == 'l')
		{
			if(maze.getField(robot.getX()-1, robot.getY()) instanceof Empty
					&& maze.getField(robot.getX(), robot.getY()-1) instanceof Wall)
			{
				robot.goLeft();
			}
			else if(maze.getField(robot.getX()-1, robot.getY()) instanceof Empty
					&& maze.getField(robot.getX()+1, robot.getY()-1) instanceof Wall)
			{
				robot.goLeft();
				System.out.println("^ around a corner");
			}
			else if(maze.getField(robot.getX()-1, robot.getY()) instanceof Wall)
			{
				if(maze.getField(robot.getX(), robot.getY()+1) instanceof Empty)
				{
					robot.goDown();
				}
				else if(maze.getField(robot.getX()+1, robot.getY()) instanceof Empty)
				{
					robot.goRight();
				}
				else if(maze.getField(robot.getX(), robot.getY()-1) instanceof Empty)
				{
					robot.goUp();
				}
				else
				{
					System.out.println("You're stuck!");
				}
				return;
			}
			else if(maze.getField(robot.getX()-1, robot.getY()) instanceof Goal)
			{
				System.out.println("Freedom!");
				solved = true;
				return;
			}
		}

		System.out.println("\nX: " + robot.getX() + "\nY: " + robot.getY() + "\n");
	}
}