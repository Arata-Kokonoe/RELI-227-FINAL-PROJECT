package core;

public class Position {
    private double x;
    private double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public int intX() {
        return (int)(x);
    }

    public int intY() {
        return (int)(y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double newX) {
        x = newX;
    }

    public void setY(double newY) {
        y = newY;
    }

    public void set(Position pos){
        this.x = pos.x;
        this.y = pos.y;
    }

    public void apply(Vector2D velocity) {
        x += velocity.getX();
        y += velocity.getY();
    }

    public void applyX(int xVel) {
        this.x += xVel;
    }

    public void applyY(int yVel) {
        this.y += yVel;
    }
}
