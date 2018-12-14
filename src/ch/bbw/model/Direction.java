package ch.bbw.model;

public class Direction
{
    private Position front, right, back, left, rightBack;

    public Direction(Position front, Position right, Position back, Position left, Position rightBack)
    {
        this.front = front;
        this.right = right;
        this.back = back;
        this.left = left;
        this.rightBack = rightBack;
    }

    public Position getFront()
    {
        return front;
    }

    public void setFront(Position front)
    {
        this.front = front;
    }

    public Position getRight()
    {
        return right;
    }

    public void setRight(Position right)
    {
        this.right = right;
    }

    public Position getBack()
    {
        return back;
    }

    public void setBack(Position back)
    {
        this.back = back;
    }

    public Position getLeft()
    {
        return left;
    }

    public void setLeft(Position left)
    {
        this.left = left;
    }

    public Position getRightBack()
    {
        return rightBack;
    }

    public void setRightBack(Position rightBack)
    {
        this.rightBack = rightBack;
    }
}