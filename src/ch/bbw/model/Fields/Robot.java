package ch.bbw.model.Fields;

public class Robot extends Field {
    public char orientation;

    public Robot(char orientation) {
        this.orientation = orientation;
    }

    public void goUp() {
        orientation = 'u';
        this.setY(this.getY() - 1);
        //System.out.println("Robot goes up");
    }

    public void goRight() {
        orientation = 'r';
        this.setX(this.getX() + 1);
        //System.out.println("Robot goes right");
    }

    public void goDown() {
        orientation = 'd';
        this.setY(this.getY() + 1);
        //System.out.println("Robot goes down");
    }

    public void goLeft() {
        orientation = 'l';
        this.setX(this.getX() - 1);
        //System.out.println("Robot goes left");
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }
}
