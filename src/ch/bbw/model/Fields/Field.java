package ch.bbw.model.Fields;

import ch.bbw.model.Position;

public class Field
{
    private Position position;

    public Field()
    {
    }

    public Field(Position position)
    {
        this.position = position;
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }
}
