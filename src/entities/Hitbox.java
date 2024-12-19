package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import core.Position;
import core.Vector2D;
import main.Camera;

public class Hitbox{
    
    private Rectangle bounds;

    public Hitbox(Rectangle hb){
        bounds = hb;
    }

    public boolean collidesWith(Hitbox other) {
        return bounds.intersects(other.getBounds());
    }

    public Hitbox apply(Vector2D velocity){
        bounds.x = (int)(bounds.x + velocity.getX());
        bounds.y = (int)(bounds.y + velocity.getY());

        return this;
    }

    public void draw(Graphics2D g2){
        g2.draw(bounds);
        g2.drawLine(leftX(), topY(), rightX(), topY());
        g2.drawLine(leftX(), topY(), leftX(), botY());
        g2.drawLine(rightX(), topY(), rightX(), botY());
        g2.drawLine(leftX(), botY(), rightX(), botY());
    }

    public void drawOnCamera(Graphics2D g2, Camera camera){
        g2.setColor(Color.RED);
        Rectangle tempBounds = new Rectangle((int)(leftX() - camera.getPosition().intX()), topY() - camera.getPosition().intY(), (int)bounds.getWidth(), (int)bounds.getHeight());
        g2.draw(tempBounds);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int leftX(){
        return bounds.x;
    }
    public int rightX(){
        return bounds.x + (int)bounds.getWidth();
    }
    public int topY(){
        return bounds.y;
    }
    public int botY(){
        return bounds.y + (int)bounds.getHeight();
    }

    public Position topRightCorner(){
        return new Position(bounds.x + (int)bounds.getWidth(), bounds.y);
    }
    public Position topLeftCorner(){
        return new Position(bounds.x, bounds.y);
    }
    public Position botRightCorner(){
        return new Position(bounds.x + (int)bounds.getWidth(), bounds.y + (int)bounds.getHeight());
    }
    public Position botLeftCorner(){
        return new Position(bounds.x, bounds.y + (int)bounds.getHeight());
    }

}