package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import core.Position;
import core.Vector2D;
import main.Camera;

public class Hitbox{
    
    private Rectangle bounds;
    private Entity entity;

    public Hitbox(Rectangle hb, Entity entity){
        bounds = hb;
        this.entity = entity;
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
    public int middleX(){
        return bounds.x + (int)bounds.getWidth()/2;
    }
    public int rightX(){
        return bounds.x + (int)bounds.getWidth();
    }
    public int topY(){
        return bounds.y;
    }
    public int middleY(){
        return bounds.y + (int)bounds.getHeight()/2;
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

    public boolean equals(Hitbox otherHitbox){
        return (otherHitbox.getEntity() == this.getEntity());
    }
    public Entity getEntity(){
        return entity;
    }

}