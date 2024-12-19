package core;

import java.awt.Rectangle;

import entities.Hitbox;
import main.GameFrame;

public class TileCoords {
    private int col, row;

    public TileCoords(int c, int r){
        col = c;
        row = r;
    }

    public int getCol(){
        return col;
    }

    public int getRow(){
        return row;
    }

    public Hitbox getTileHitbox(){
        return new Hitbox(new Rectangle(col * GameFrame.ORIGINAL_TILE_SIZE, row * GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE));
    }
}
