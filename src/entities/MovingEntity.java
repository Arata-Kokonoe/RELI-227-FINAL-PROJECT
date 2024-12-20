package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import core.MoveOrder;
import core.TileCoords;
import core.Vector2D;
import main.Camera;
import main.GameFrame;
import main.GameState;
import room.Room;

public abstract class MovingEntity extends Entity{

    //=============================================================================================================
    //  MEMBER VARIABLES
        protected Vector2D velocity = new Vector2D(0, 0);
        protected int speed;
        protected boolean up, down, left, right;
        protected BufferedImage animationSheet;
        protected AnimationManager animationManager;
        protected MoveOrder moveOrder;
    //=============================================================================================================


    //=============================================================================================================
    //  ABSTRACT METHODS
        @Override
        public void update(GameState gameState){
            setMovement(gameState);

            int deltaX = 0;
            int deltaY = 0;
        
            if(left) deltaX--;
            if(right) deltaX++;
            if(up) deltaY--;
            if(down) deltaY++;

            velocity = new Vector2D(deltaX, deltaY);
            
            velocity.multiply(speed);

            handleCollisions(gameState);
            
            animationManager.update();
        }

            @Override
        public void draw(Graphics2D g2, Camera camera) {
            g2.drawImage(
                animationManager.getSprite(),
                this.getPosition().intX() - (int)(this.getPosition().getX() + this.getSize().getWidth() - this.getHitbox().rightX())/2 - camera.getPosition().intX(),
                this.getPosition().intY() - (int)(this.getPosition().getY() + this.getSize().getHeight() - this.getHitbox().botY()) - camera.getPosition().intY(),
                null
                ); 
            /*
            g2.drawImage(
                animationManager.getSprite(),
                this.getPosition().intX() - this.getSize().getWidth()/4 ,
                this.getPosition().intY() - (int)(this.getSize().getHeight()/1.8),
                null
                );
            drawToMove(g2);
            drawHitboxOnCamera(g2, camera);
            drawHitbox(g2);*/
        }

        public void drawToMove(Graphics2D g2){
            g2.setColor(Color.BLUE);
            this.getHitbox().apply(new Vector2D(velocity.getX(), velocity.getY())).draw(g2);
        }

        public boolean isMoving(){
            if (this.velocity.length() > 0) return true;
            else return false;
        }

        protected abstract void setMovement(GameState gameState);

        protected void handleCollisions(GameState gameState){
            TileCoords tile1, tile2;
            Room room = gameState.getCurrentRoom();
            List<Entity> collidedEntities = gameState.getCollidingGameObjects(getHitbox());

            if(velocity.getY() > 0){
                Hitbox toMove = getHitbox().apply(new Vector2D(0, velocity.getY()));
                if(toMove.botY() >= room.getHeight()){
                    //went out of bounds of room
                    velocity.multiplyY(0);
                    position.setY(room.getHeight() - getHitbox().getBounds().getHeight() - 1);

                    if(room.getSouthRoom() != null){
                        gameState.setCurrentRoom(room.getSouthRoom());
                        position.set(room.getSouthRoom().getNorthSpawn());
                    }
                }
                else{
                    tile1 = room.positionToCoords(toMove.botLeftCorner());
                    tile2 = room.positionToCoords(toMove.botRightCorner());
                    collidedEntities = gameState.getCollidingGameObjects(toMove);
                    if(room.getTileArr()[tile1.getCol()][tile1.getRow()].getCollision() || room.getTileArr()[tile2.getCol()][tile2.getRow()].getCollision()){
                        velocity.multiplyY(0);
                        position.setY((tile1.getRow() * GameFrame.ORIGINAL_TILE_SIZE) - getHitbox().getBounds().getHeight() - 1);
                    }
                    else if (!collidedEntities.isEmpty()){
                        for (Entity e : collidedEntities) {
                            velocity.multiplyY(0);
                            position.setY((e.getHitbox().getBounds().getMinY()) - getHitbox().getBounds().getHeight() - 1);
                        }
                    }
                }
            }
            else if(velocity.getY() < 0){
                Hitbox toMove = getHitbox().apply(new Vector2D(0, velocity.getY()));
                if(toMove.topY() <= 0){
                    //went out of bounds of room
                    velocity.multiplyY(0);
                    position.setY(0 + GameFrame.ORIGINAL_TILE_SIZE);

                    if(room.getNorthRoom() != null){
                        gameState.setCurrentRoom(room.getNorthRoom());
                        position.set(room.getNorthRoom().getSouthSpawn());
                    }
                }
                else{
                    tile1 = room.positionToCoords(toMove.topLeftCorner());
                    tile2 = room.positionToCoords(toMove.topRightCorner());
                    collidedEntities = gameState.getCollidingGameObjects(toMove);
                    if(room.getTileArr()[tile1.getCol()][tile1.getRow()].getCollision() || room.getTileArr()[tile2.getCol()][tile2.getRow()].getCollision()){
                        velocity.multiplyY(0);
                        position.setY((tile1.getRow() * GameFrame.ORIGINAL_TILE_SIZE) + GameFrame.ORIGINAL_TILE_SIZE);
                    }
                    else if(!collidedEntities.isEmpty()){
                        for (Entity e : collidedEntities) {
                            velocity.multiplyY(0);
                            position.setY((e.getHitbox().getBounds().getMaxY()) + 1);
                        }
                    }
                }
            }

            if(velocity.getX() < 0){
                Hitbox toMove = getHitbox().apply(new Vector2D(velocity.getX(), 0));
                if(toMove.leftX() <= 0){
                    //went out of bounds of room
                    velocity.multiplyX(0);
                    position.setX(0);

                    if(room.getWestRoom() != null){
                        gameState.setCurrentRoom(room.getWestRoom());
                        position.set(room.getWestRoom().getEastSpawn());
                    }
                }
                else{
                    tile1 = room.positionToCoords(toMove.botLeftCorner());
                    tile2 = room.positionToCoords(toMove.topLeftCorner());
                    collidedEntities = gameState.getCollidingGameObjects(toMove);
                    if(room.getTileArr()[tile1.getCol()][tile1.getRow()].getCollision() || room.getTileArr()[tile2.getCol()][tile2.getRow()].getCollision()){
                        velocity.multiplyX(0);
                        position.setX((tile1.getCol() * GameFrame.ORIGINAL_TILE_SIZE) + GameFrame.ORIGINAL_TILE_SIZE);
                    }
                    else if(!collidedEntities.isEmpty()){
                        for (Entity e : collidedEntities) {
                            velocity.multiplyX(0);
                            position.setX((e.getHitbox().getBounds().getMaxX()) + 1);
                        }
                    }
                }
            }
            else if(velocity.getX() > 0){
                Hitbox toMove = getHitbox().apply(new Vector2D(velocity.getX(), 0));
                System.out.println(toMove.rightX());
                if(toMove.rightX() >= room.getWidth()){
                    //went out of bounds of room
                    velocity.multiplyX(0);
                    position.setX(room.getWidth() - getHitbox().getBounds().getWidth() - 1);

                    if(room.getEastRoom() != null){
                        gameState.setCurrentRoom(room.getEastRoom());
                        position.set(room.getEastRoom().getWestSpawn());
                    }
                }
                else{
                    tile1 = room.positionToCoords(toMove.botRightCorner());
                    tile2 = room.positionToCoords(toMove.topRightCorner());
                    collidedEntities = gameState.getCollidingGameObjects(toMove);
                    if(room.getTileArr()[tile1.getCol()][tile1.getRow()].getCollision() || room.getTileArr()[tile2.getCol()][tile2.getRow()].getCollision()){
                        velocity.multiplyX(0);
                        position.setX((tile1.getCol() * GameFrame.ORIGINAL_TILE_SIZE) - getHitbox().getBounds().getWidth() -1);
                    }
                    else if(!collidedEntities.isEmpty()){
                        for (Entity e : collidedEntities) {
                            velocity.multiplyX(0);
                            position.setX((e.getHitbox().getBounds().getMinX()) - getHitbox().getBounds().getWidth());
                        }
                    }
                }
            }

            position.apply(velocity);
        }

    //=============================================================================================================


    //=============================================================================================================
    //  GETTERS
        public BufferedImage getAnimationSheet(){
            return animationSheet;
        }

        public MoveOrder getMoveOrder(){
            return moveOrder;
        }

        public Vector2D getVelocity(){
            return velocity;
        }

        public String getDirection(){
            double x = velocity.getX();
            double y = velocity.getY();
        
            if(x == 0 && y > 0) return "S";
            if(x < 0 && y == 0) return "W";
            if(x == 0 && y < 0) return "N";
            if(x > 0 && y == 0) return "E";
            if(x < 0 && y > 0) return "SW";
            if(x < 0 && y < 0) return "NW";
            if(x > 0 && y < 0) return "NE";
            if(x > 0 && y > 0) return "SE";
    
            return "null";
        }
    //=============================================================================================================

}
