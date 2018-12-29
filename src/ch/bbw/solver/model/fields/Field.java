package ch.bbw.solver.model.fields;

import ch.bbw.solver.model.Position;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

public class Field {
    private Position position;

    public Field(Position position) {
        this.position = position;
    }

    public Field() {
        position = new Position();
    }

    public static void writeString(String val, ByteBuffer buffer) {
        byte[] data = val.getBytes();
        buffer.putInt(data.length);
        buffer.put(data);
    }

    public static String readString(ByteBuffer buffer) {
        byte[] data = new byte[buffer.getInt()];
        buffer.get(data);
        return new String(data);
    }

    public static Field createField(String className) {
        try {
            Class<Field> packetClass = Class.forName(className).asSubclass((Class) Field.class);
            return packetClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void compileField(Field field, ByteBuffer byteBuffer) {
        writeString(field.getClass().getName(), byteBuffer);
        byteBuffer.putInt(field.position.getX());
        byteBuffer.putInt(field.position.getY());
    }

    public static Field decompileField(ByteBuffer byteBuffer) {
        Field field = createField(readString(byteBuffer));
        field.setPosition(new Position());
        field.position.setX(byteBuffer.getInt());
        field.position.setY(byteBuffer.getInt());
        return field;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
