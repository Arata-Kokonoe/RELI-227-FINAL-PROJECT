package entities;

import entities.Entity;
import entities.MovingEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationManager {
    private BufferedImage currentAnimationSheet;
    private int updatesPerFrame;
    private int currentFrameTime;
    private int frameIndex;
    private int directionIndex;
    private Entity entity;

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

    public void update(String direction) {
        currentFrameTime++;
        if(direction == "S" || direction == "SW" || direction == "SE") directionIndex = 0;
        else if (direction == "N" || direction == "NW" || direction == "NE") directionIndex = 1;
        else if (direction == "E") directionIndex = 2;
        else if (direction == "W") directionIndex = 3;

        if(currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;

            if(frameIndex >= currentAnimationSheet.getWidth() / entity.getSize().getWidth()) {
                frameIndex = 0;
            }
        }

    }

}