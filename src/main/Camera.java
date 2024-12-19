//  PACKAGE
package main;

//=================================================================================================================
//  IMPORTS
    import core.Position;
    import core.Size;

    import entities.Entity;

    import java.awt.*;
//=================================================================================================================


//=================================================================================================================
public class Camera {
//=================================================================================================================


    //=============================================================================================================
    //  MEMBER VARIABLES
        private static final int SAFETY_SPACE = 2 * GameFrame.ORIGINAL_TILE_SIZE;

        private Position position;
        private Size size;

        private Rectangle viewBounds;

        private Entity objectWithFocus;
    //=============================================================================================================


    //=============================================================================================================
    //  CONSTRUCTOR
        public Camera() {
            this.position = new Position(0, 0);
            this.size = new Size(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
            calculateViewBounds();
        }
    //=============================================================================================================


    //=============================================================================================================
        private void calculateViewBounds() {
            viewBounds = new Rectangle(
                    position.intX(),
                    position.intY(),
                    size.getWidth() + SAFETY_SPACE,
                    size.getHeight() + SAFETY_SPACE
            );
        } // calculateViewBounds()
    //  Creates a rectangle that encapsulates how much of the room we want the player to see.
    //  Slightly bigger than the user screen, so that entities do not just appear as player moves.
    //  Instead, they will be drawn even if slightly out of view allowing for smoother rendering.
    //=============================================================================================================


    //=============================================================================================================
        public void focusOn(Entity object) {
            this.objectWithFocus = object;
        } // focusOn(Entity object)
    //  Sets the camera to focus on an entity (will be the player most of the time).
    //=============================================================================================================


    //=============================================================================================================
        public void update(GameState gameState) {
            if(objectWithFocus != null) {
                Position objectPosition = objectWithFocus.getPosition();
                Size objectSize = objectWithFocus.getSize();

                this.position.setX(objectPosition.getX() - objectSize.getWidth()/2 - size.getWidth()/2);
                this.position.setY(objectPosition.getY() - objectSize.getHeight()/2 - size.getHeight()/2);

                clampWithinBounds(gameState);
                calculateViewBounds();
            }
        } // update(GameState gameState)
    //  Changes position of camera based on newly changed position of the object of focus.
    //=============================================================================================================


    //=============================================================================================================
        private void clampWithinBounds(GameState gameState) {
            if(position.getX() < 0) {
                position.setX(0);
            }

            if(position.getY() < 0) {
                position.setY(0);
            }

            if(position.getX() + size.getWidth() > gameState.getCurrentRoom().getWidth()) {
                position.setX(gameState.getCurrentRoom().getWidth() - size.getWidth());
            }

            if(position.getY() + size.getHeight() > gameState.getCurrentRoom().getHeight()) {
                position.setY(gameState.getCurrentRoom().getHeight() - size.getHeight());
            }
        } // clampWithinBounds(GameState gameState)
    //  If the player reaches the edge of a room, move the camera so that the player cannot see out of bounds.
    //=============================================================================================================


    //=============================================================================================================
        public boolean isInView(Entity entity) {
            return viewBounds.intersects(
                    entity.getPosition().intX(),
                    entity.getPosition().intY(),
                    entity.getSize().getWidth(),
                    entity.getSize().getHeight());
        } // isInView(Entity entity)
    //  Checks whether or not an entity is within the view of the camera.
    //=============================================================================================================


    //=============================================================================================================
    //  GETTERS
        public Position getPosition(){
            return position;
        }

        public Size getSize() {
            return size;
        }
    //=============================================================================================================
}