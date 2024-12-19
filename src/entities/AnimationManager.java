package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationManager {
    private BufferedImage currentAnimationSheet;
    private int updatesPerFrame;
    private int currentFrameTime;
    private int frameIndex;
    private int directionIndex;
    private MovingEntity entity;

    public AnimationManager(MovingEntity entity) {
        currentAnimationSheet = entity.getAnimationSheet();
        this.updatesPerFrame = 20;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        this.directionIndex = 0;
        this.entity = entity;
    }

    public Image getSprite() {
        //System.out.println("x = " + frameIndex * entity.getSize().getWidth() + " || y = " + directionIndex * entity.getSize().getHeight() + " || width = " + entity.getSize().getWidth() + " || height = " + entity.getSize().getHeight());
        return currentAnimationSheet.getSubimage(
                frameIndex * entity.getSize().getWidth(),
                directionIndex * entity.getSize().getHeight(),
                entity.getSize().getWidth(),
                entity.getSize().getHeight()
        );
    }

    public void update() {
        if(entity.up || entity.down || entity.left || entity.right){

            currentFrameTime++;

            String currentDirection = entity.getMoveOrder().getCurrent();

            if (currentDirection == "left") directionIndex = 3;
            else if (currentDirection == "right") directionIndex = 2;
            else if (currentDirection == "up") directionIndex = 1;
            else if (currentDirection == "down") directionIndex = 0;

            if(currentFrameTime >= updatesPerFrame) {
                currentFrameTime = 0;
                frameIndex++;

                if(frameIndex >= currentAnimationSheet.getWidth() / entity.getSize().getWidth()) {
                    frameIndex = 0;
                }
            }
        }
        else {
            directionIndex = 0;
            frameIndex = 0;
        }

    }

}