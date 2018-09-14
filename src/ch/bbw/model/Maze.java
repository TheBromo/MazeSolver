package ch.bbw.model;

import ch.bbw.model.Fields.Field;

public class Maze
{
	private Field[] fields;
	private int size;

	public Maze(int size)
	{
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
	}
}
