package ch.bbw.model;

import ch.bbw.model.Fields.Field;

public class Maze {
    private Field[] fields;
    private int size;

    public Maze(int size) {
        this.size = size;
        fields = new Field[size * size];
        initFields();
    }

    private void initFields() {
        for (Field f : fields) {
            f = new Field();
        }
    }

    public Field getField(int x, int y) {
        return fields[x + y * size];
    }


}
