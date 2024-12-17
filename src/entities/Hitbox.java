package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import core.Position;
import core.Size;
import core.Vector2D;

public class Hitbox{
    
    private Rectangle bounds;

    public Hitbox(Rectangle hb){
        bounds = hb;
    }

    public boolean collidesWith(Hitbox other) {
        return bounds.intersects(other.getBounds());
    }

    public void apply(Vector2D velocity){
        bounds.x += velocity.getX();
        bounds.y += velocity.getY();
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.RED);
        g2.draw(bounds);
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