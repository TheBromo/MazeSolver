package ch.bbw.model.Fields;

public class Robot extends Field
{
    public char orientation;

    public Robot(char orientation)
    {
        this.orientation = orientation;
    }

    public void goForward()
    {
        System.out.println("To glory!");
        switch(orientation)
        {
            case 'u':
                this.setY(this.getY() - 1);
                break;
            case 'r':
                this.setX(this.getX() + 1);
                break;
            case 'd':
                this.setY(this.getY() + 1);
                break;
            case 'l':
                this.setX(this.getX() - 1);
                break;
        }
    }

    public void goRight()
    {
        System.out.println("Going right!");
        switch(orientation)
        {
            case 'u':
                orientation = 'r';
                break;
            case 'r':
                orientation = 'd';
                break;
            case 'd':
                orientation = 'l';
                break;
            case 'l':
                orientation = 'u';
                break;
        }
        goForward();
    }

    public void goLeft()
    {
        System.out.println("Going left!");
        switch(orientation)
        {
            case 'u':
                orientation = 'l';
                break;
            case 'r':
                orientation = 'u';
                break;
            case 'd':
                orientation = 'r';
                break;
            case 'l':
                orientation = 'd';
                break;
        }
        goForward();
    }

    public void goBack()
    {
        System.out.println("Going back!");
        switch(orientation)
        {
            case 'u':
                orientation = 'd';
                break;
            case 'r':
                orientation = 'l';
                break;
            case 'd':
                orientation = 'u';
                break;
            case 'l':
                orientation = 'r';
                break;
        }
        goForward();
    }

    public char getOrientation()
    {
        return orientation;
    }

    public void setOrientation(char orientation)
    {
        this.orientation = orientation;
    }
}
