package ch.bbw.model;

import ch.bbw.model.Fields.Field;
import ch.bbw.model.Fields.Robot;

public class Maze
{
    private Robot robot;
    private Field[] fields;
    private int size;

    public Maze(int size)
    {
        robot = new Robot('d');
        this.size = size;
        fields = new Field[size * size];
        initFields();
    }

    private void initFields()
    {
        for (int i = 0; i < fields.length; i++)
        {
            fields[i] = new Field();
        }
    }

    public Field getField(int x, int y)
    {
        return fields[x + y * size];
    }

    public int getSize()
    {
        return size;
    }

    public void setField(int x, int y, Field field)
    {
        fields[x + y * size] = field;
        field.setX(x);
        field.setY(y);
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
