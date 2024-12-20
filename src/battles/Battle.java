package battles;

import java.awt.event.KeyEvent;

import core.Position;
import entities.PlayerSoul;
import entities.enemies.StandingEnemy;
import main.GameFrame;
import main.GameState;

public class Battle {
    String name;

    public Battle(String name){
        this.name = name;

        initializeBattle(name);
    }

    public static Battle getBattle(String name){
        return new Battle(name);
    }

    public void start(GameState gameState){
        gameState.getInput().unPress(KeyEvent.VK_Z);
        gameState.status = GameState.BATTLE_STATUS;
        gameState.currentBattle = this;
        gameState.stopMusic();
        gameState.playMusic(GameState.BATTLE_MUSIC);
        gameState.setPlayerSoul(new PlayerSoul(gameState.getPlayer().purity));
        gameState.battleEntities.add(gameState.getPlayerSoul());
        switch (name) {
            case "Vergil":
                gameState.currentEnemy = new StandingEnemy("Vergil", new Position(GameFrame.GAME_WIDTH/2, GameFrame.GAME_HEIGHT/2 - GameFrame.ORIGINAL_TILE_SIZE*4));
                gameState.battleEntities.add(gameState.currentEnemy);
                break;
        
            default:
                break;
        }
    }

    public void end(GameState gameState){
        gameState.stopMusic();
        gameState.status = GameState.GAMEOVER_STATUS;
    }

    public void initializeBattle(String name){
        switch (name) {
            case "Vergil":
                
                break;
        
            default:
                break;
        }
    }
}
