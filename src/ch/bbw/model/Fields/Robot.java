package ch.bbw.model.Fields;

public class Robot extends Field {
    public char orientation;

    public Robot(char orientation) {
        this.orientation = orientation;
    }

    public int getDegrees() {
        int degrees = 0;
        switch (orientation) {
            case 'r':
                degrees = 90;
                break;
            case 'l':
                degrees = 270;
                break;
            case 'd':
                degrees = 180;
                break;
        }
        return degrees;
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
