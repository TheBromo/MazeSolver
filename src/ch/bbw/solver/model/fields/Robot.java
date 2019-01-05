package ch.bbw.solver.model.fields;

import ch.bbw.solver.model.Direction;
import ch.bbw.solver.model.Position;

public class Robot extends Field
{
    private Direction orientation;
    private int turnedDegrees;

    public Robot(Position startPosition)
    {
        super(startPosition);
        turnedDegrees = 0;
    }

    public void goForward()
    {
        setPosition(new Position(getPosition().getX()+orientation.getFront().getX(), getPosition().getY()+orientation.getFront().getY()));
    }

    public Direction getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Direction orientation)
    {
        this.orientation = orientation;
    }

    public int getTurnedDegrees()
    {
        return turnedDegrees;
    }

    public void turn(int turnDegrees)
    {
        turnedDegrees += turnDegrees;
    }
}