package entities.enemies;

import java.awt.Graphics2D;

import battles.Battle;
import core.Position;
import core.Size;
import entities.Entity;
import entities.MovingEntity;
import main.Camera;
import main.GameState;

public abstract class Enemy extends Entity{

    protected String name;
    protected Battle battle;
    public int health;
    public int saveMeter;
    public int maxHealth, saveRequirement;
    public boolean dead, saved, damaged, prayedTo;

    //FROM ENTITY CLASS
    /*
    public abstract void update(GameState gameState);
    public abstract void draw(Graphics2D g2, Camera camera);
    public abstract void setSpriteSheet();
    */

    public Enemy(String name, Position pos){
        this.name = name;
        this.position = pos;
        setSpriteSheet();
        battle = Battle.getBattle(name); 
    }

    public static Enemy createEnemy(String name, Position pos){
        if(name.equals("Vergil")) return new StandingEnemy(name, pos);
        else return null;
    }

    public void damage(){
        damaged = true;
        System.out.println("damaged was called");
    }

    public void save(){
        prayedTo = true;
    }
    
}
