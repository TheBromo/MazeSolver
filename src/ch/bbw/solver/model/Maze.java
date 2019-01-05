package ch.bbw.solver.model;

import ch.bbw.solver.model.fields.Field;
import ch.bbw.solver.model.fields.Robot;

public class Maze {
    private Robot robot;
    private Field[] fields;
    private int size;

    public Maze(int size, Position startPosition) {
        robot = new Robot(startPosition);
        this.size = size;
        fields = new Field[size * size];
        initFields();
    }

    private void initFields() {
        for (int i = 0; i < fields.length; i++) {
            // only works properly because int objects always round down
            fields[i] = new Field(new Position(i - size * (i / size), i / size));
        }
    }

    public Field getField(Position position) {
        return fields[position.getX() + position.getY() * size];
    }

    public int getSize() {
        return size;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Robot getRobot() {
        return robot;
    }
}