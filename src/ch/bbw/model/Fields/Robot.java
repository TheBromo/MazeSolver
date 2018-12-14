package ch.bbw.model.Fields;

import ch.bbw.model.Direction;
import ch.bbw.model.Position;

public class Robot extends Field
{
    private Direction orientation;
    private int turnedDegrees;

    public Robot()
    {
        super();
        turnedDegrees = 0;
    }

    public void goForward()
    {
        System.out.println("To glory!");
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

    public void setTurnedDegrees(int turnedDegrees)
    {
        this.turnedDegrees = turnedDegrees;
    }
}
