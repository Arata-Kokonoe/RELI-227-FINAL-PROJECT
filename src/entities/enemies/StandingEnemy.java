package entities.enemies;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;

import battles.Battle;
import core.Position;
import core.Size;
import main.Camera;
import main.GameFrame;
import main.GameState;

public class StandingEnemy extends Enemy{
    private ArrayList<String> dialogue;
    public boolean interacted;
    
    public StandingEnemy(String name, Position pos){
        super(name, pos);
        dialogue = new ArrayList<String>();
        size = new Size(GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE*2);

        nameSwitch(name);
    }

    @Override
    public void update(GameState gameState){
        if(interacted){
            if(!gameState.getUI().dialogueOver){
                gameState.status = GameState.DIALOGUE_STATUS;
                gameState.getUI().dialogue = dialogue;
                gameState.getUI().currentDialogue = dialogue.get(0);
            }
            else{
                battle.start(gameState);
            }
            interacted = false;
        }
        if(damaged){
            health--;
            damaged = false;
            if (health == 0) dead = true;
        }
        if(prayedTo){
            saveMeter++;
            prayedTo = false;
            if(saveMeter == saveRequirement) saved = true;
        }
        
    }

    public void interaction(){
        interacted = true;
    }

    public void nameSwitch(String name){
        switch (name) {
            case "Vergil":
                dialogue.add("The corpse of the one you most revered under God\nblocks the only path forwards.");
                dialogue.add("You must make a choice :  attack and destroy the obstacle,\nor pray and give him salvation.");

                health = 3;
                saveMeter = 0;
                maxHealth = 3;
                saveRequirement = 3;
                dead = false;
                saved = false;
                break;
        
            default:
                break;
        }
    }

    @Override
    public void draw(Graphics2D g2, Camera camera){
        g2.drawImage(
            sprite,
            this.getPosition().intX() - (int)(this.getPosition().getX() + this.getSize().getWidth() - this.getHitbox().rightX())/2 - camera.getPosition().intX(),
            this.getPosition().intY() - (int)(this.getPosition().getY() + this.getSize().getHeight() - this.getHitbox().botY()) - camera.getPosition().intY(),
            null
            ); 
    }

    @Override
    public void setSpriteSheet(){
        sprite = setup("/enemies/" + name);
    }

    

}
