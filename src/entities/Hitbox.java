package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.w3c.dom.css.Rect;

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
        bounds.x = (int)Math.round(bounds.x + velocity.getX());
        bounds.y = (int)Math.round(bounds.y + velocity.getY());

        return this;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.RED);
        g2.draw(bounds);
    }

    public void drawOnCamera(Graphics2D g2, Camera camera){
        g2.setColor(Color.RED);
        Rectangle tempBounds = new Rectangle((int)(bounds.getX() - camera.getPosition().intX()), (int)bounds.getY() - camera.getPosition().intY(), (int)bounds.getWidth(), (int)bounds.getHeight());
        g2.draw(tempBounds);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getMiddleX(){
        return (int)(bounds.getX() + bounds.getWidth()/2);
    }

    public int getMiddleY(){
        return (int)(bounds.getY() + bounds.getHeight()/2);
    }

}