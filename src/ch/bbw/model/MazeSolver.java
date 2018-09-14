package ch.bbw.model;

import ch.bbw.model.Fields.Empty;
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
		robot = new Robot();
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
			break;
		}
	}

	public void step()
	{
		if(maze.getField(robot.getX(), robot.getY()-1) instanceof Empty)
		{
			System.out.println("A");
		}
		if(maze.getField(robot.getX()+1, robot.getY()) instanceof Empty)
		{
			System.out.println("B");
		}
		if(maze.getField(robot.getX(), robot.getY()+1) instanceof Empty)
		{
			System.out.println("C");
		}
		if(maze.getField(robot.getX()-1, robot.getY()) instanceof Empty)
		{
			System.out.println("D");
		}
		System.out.println("X: " + robot.getX() + "\nY: " + robot.getY() + "\n");
	}
}