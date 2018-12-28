package ch.bbw.model;

import ch.bbw.model.Fields.Field;
import ch.bbw.model.Fields.Robot;

public class Maze
{
    private Robot robot;
    private Field[] fields;
    private int size;

    public Maze(int size, Position startPosition)
    {
        robot = new Robot(startPosition);
        this.size = size;
        fields = new Field[size * size];
        initFields();
    }

    private void initFields()
    {
        for (int i = 0; i < fields.length; i++)
        {
            // Works because int always rounds down:
            fields[i] = new Field(new Position(i - size * (i / size), i / size));
        }
    }

    public Field getField(Position position)
    {
        return fields[position.getX() + position.getY() * size];
    }

    public int getSize()
    {
        return size;
    }

    public void changeField(Position position, Field field)
    {
        fields[position.getX() + position.getY() * size] = field;
        field.setPosition(position);
    }

    public Field[] getFields()
    {
        return fields;
    }

    public void setFields(Field[] fields)
    {
        this.fields = fields;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public Robot getRobot()
    {
        return robot;
    }

    public void setRobot(Robot robot)
    {
        this.robot = robot;
    }
}
