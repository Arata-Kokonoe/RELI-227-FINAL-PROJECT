package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import battles.Battle;
import entities.Hitbox;


public class UI {

    GameState gameState;
    Font gotHeroin, beyondWonderland, tangerine;
    UtilityTool utool;
    public boolean messageOn = false;
    //public String message = "";
    //int messageCounter = 0;
    public boolean gameFinished = false;
    public ArrayList<String> dialogue;
    public String currentDialogue = "";
    public boolean dialogueOver;
    public Battle currentBattle;
    public BufferedImage currentCloseup = null;
    public int commandNum = 0;
    public int upgradeNum = 0;
    public int titleScreenState = 0; //0: first screen, 1: second screen
    public double playTime;
    public int slotCol = 0;
    public int slotRow = 0;
    public Hitbox battleWindow;
    public BufferedImage attack, pray;

    public UI(GameState gameState){
        this.gameState = gameState;
        utool = new UtilityTool();
        
        try {
            InputStream is = getClass().getResourceAsStream("/res/fonts/GotHeroin.ttf");
            gotHeroin = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/res/fonts/BeyondWonderland.ttf");
            beyondWonderland = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/res/fonts/Tangerine.ttf");
            tangerine = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        attack = utool.setup("/actions/attack");
        attack = utool.scaleImage(attack, GameFrame.ORIGINAL_TILE_SIZE*2, GameFrame.ORIGINAL_TILE_SIZE*2);
        pray = utool.setup("/actions/pray");
        pray = utool.scaleImage(pray, GameFrame.ORIGINAL_TILE_SIZE*2, GameFrame.ORIGINAL_TILE_SIZE*2);
    }

    public void drawBattleScreen(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);

        int x = (int)(GameFrame.ORIGINAL_TILE_SIZE * GameFrame.MAX_COL*(1.0/6));
        int y = (int)(GameFrame.ORIGINAL_TILE_SIZE * GameFrame.MAX_ROW * (1.0/8));

        int width = (int)(GameFrame.ORIGINAL_TILE_SIZE * GameFrame.MAX_COL*(2.0/3));
        int height = (int)(GameFrame.ORIGINAL_TILE_SIZE * GameFrame.MAX_ROW * (6.0/8));

        drawRoundedSubWindow(x, y, width, height, g2);
        battleWindow = new Hitbox(new Rectangle(x+7, y+7, width-14, height-14), null);

        //PLAYER HEALTH BAR
        x += GameFrame.ORIGINAL_TILE_SIZE/2;
        y = GameFrame.GAME_HEIGHT - GameFrame.ORIGINAL_TILE_SIZE*3;
        g2.setColor(new Color(59, 5, 5));
        g2.fillRect(x, y, GameFrame.ORIGINAL_TILE_SIZE * 8, GameFrame.ORIGINAL_TILE_SIZE/2);

        g2.setColor(new Color(186, 7, 7));
        g2.fillRect(x, y, (int)((GameFrame.ORIGINAL_TILE_SIZE * 8) * ((double)gameState.getPlayer().currentHealth/gameState.getPlayer().MAX_HEALTH)), GameFrame.ORIGINAL_TILE_SIZE/2);
        
        x += (GameFrame.ORIGINAL_TILE_SIZE * 8);
        y += (int)(GameFrame.ORIGINAL_TILE_SIZE * (1.0/2));
        g2.drawString(gameState.getPlayer().currentHealth + "/20", x, y); 

        x += GameFrame.ORIGINAL_TILE_SIZE*2;
        y -= (int)(GameFrame.ORIGINAL_TILE_SIZE*1.2);
        width = GameFrame.ORIGINAL_TILE_SIZE*8;
        height = GameFrame.ORIGINAL_TILE_SIZE*2;
        drawSharpSubWindow(x, y, width, height, g2);

        g2.setFont(tangerine.deriveFont(Font.PLAIN, 12F));
        g2.drawString("Z to fight", x + GameFrame.ORIGINAL_TILE_SIZE, y + (int)(GameFrame.ORIGINAL_TILE_SIZE*1.3));
        Color c = new Color(0,0,0, 210); //4th number is alpha number (opacity level)
        g2.setColor(c);
        g2.fillRect(x, y, (int)(width * (gameState.actionTimer/300.0)), height);

        x += GameFrame.ORIGINAL_TILE_SIZE*8;
        drawSharpSubWindow(x, y, width, height, g2);

        g2.setFont(tangerine.deriveFont(Font.PLAIN, 12F));
        g2.drawString("X to pray", x + GameFrame.ORIGINAL_TILE_SIZE, y + (int)(GameFrame.ORIGINAL_TILE_SIZE*1.3));
        g2.setColor(c);
        g2.fillRect(x, y, (int)(width * (gameState.actionTimer/300.0)), height);
    }

    public void drawAction(Graphics2D g2, String action){
        if(action.equals("pray")){
            g2.drawImage(pray, gameState.getPlayerSoul().getPosition().intX() - GameFrame.ORIGINAL_TILE_SIZE/2, gameState.getPlayerSoul().getPosition().intY() - GameFrame.ORIGINAL_TILE_SIZE*3, null);
        }
        else if (action.equals("attack")){
            g2.drawImage(attack, gameState.getPlayerSoul().getPosition().intX() - GameFrame.ORIGINAL_TILE_SIZE/2, gameState.getPlayerSoul().getPosition().intY() - GameFrame.ORIGINAL_TILE_SIZE*3, null);
        }
    }

    public void drawTitleScreen(Graphics2D g2){

        if(titleScreenState == 0){
            //BACKGROUND
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);

            //TITLE NAME
            g2.setFont(beyondWonderland.deriveFont(Font.PLAIN, 96F));
            String text = "Dante's Decay";
            int x = getXForCenteredText(text, g2);
            int y = GameFrame.ORIGINAL_TILE_SIZE*12;
            
            //SHADOW
            g2.setColor(new Color(0, 1, 0));
            g2.drawString(text, x+5, y+5);
            //MAIN COLOR
            g2.setColor(new Color(255, 255, 244));
            g2.drawString(text, x, y);

            //CHS IMAGE
            //x = gp.screenWidth/2 - (GameFrame.ORIGINAL_TILE_SIZE*4)/2;
            //y += GameFrame.ORIGINAL_TILE_SIZE * 1.5;
            //g2.drawImage(gp.player.right0, x, y, GameFrame.ORIGINAL_TILE_SIZE*4, GameFrame.ORIGINAL_TILE_SIZE*4, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));

            text = "NEW GAME";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*6;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-GameFrame.ORIGINAL_TILE_SIZE, y);
            }

            
            text = "SETTINGS";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*3;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-GameFrame.ORIGINAL_TILE_SIZE, y);
            }

            text = "CONTROLS";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*3;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-GameFrame.ORIGINAL_TILE_SIZE, y);
            }

            text = "QUIT";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*3;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-GameFrame.ORIGINAL_TILE_SIZE, y);
            }
        }
        else if(titleScreenState == 1){
            
            //CONTROLS SCREEN
            g2.setColor(Color.WHITE);
            g2.setFont(gotHeroin.deriveFont(42F));

            String text = "CONTROLS:";
            int x = getXForCenteredText(text, g2);
            int y = GameFrame.ORIGINAL_TILE_SIZE*6;
            g2.drawString(text, x, y);

            
            g2.setFont(g2.getFont().deriveFont(30F));
            text = "ARROW KEYS to move";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*3;
            g2.drawString(text, x, y);

            text = "Z to interact";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*3;
            g2.drawString(text, x, y);

            text = "X to get out of dialogue";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*3;
            g2.drawString(text, x, y);

            text = "M to open menu";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*3;
            g2.drawString(text, x, y);

            //add more controls here

            text = "Press X to exit this menu";
            g2.setFont(beyondWonderland.deriveFont(20F));
            g2.setColor(new Color(90, 90, 90));
            x = (int)(GameFrame.GAME_WIDTH - g2.getFontMetrics().getStringBounds(text, g2).getWidth() - (GameFrame.ORIGINAL_TILE_SIZE * 0.25));
            y = GameFrame.GAME_HEIGHT - (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            g2.drawString(text, x, y);

        }
    }

    public void drawSettingsScreen(Graphics2D g2){
        //CONTROLS SCREEN
        g2.setColor(Color.WHITE);
        g2.setFont(gotHeroin.deriveFont(42F));

        String text = "Settings:";
        int x = getXForCenteredText(text, g2);
        int y = GameFrame.ORIGINAL_TILE_SIZE*6;
        g2.drawString(text, x, y);

        text = "Volume";
        x = getXForCenteredText(text, g2);
        y += GameFrame.ORIGINAL_TILE_SIZE*3;
        g2.drawString(text, x, y);

        x = GameFrame.GAME_WIDTH/2;
        y += GameFrame.ORIGINAL_TILE_SIZE*2;
        int x1 = x-GameFrame.ORIGINAL_TILE_SIZE*5;
        int x2 = x+GameFrame.ORIGINAL_TILE_SIZE*5;
        g2.drawLine(x1, y, x2, y);
        g2.fillOval(x2 - GameFrame.ORIGINAL_TILE_SIZE - (int)((x2-x1) - ((x2-x1) * gameState.volume)), y - GameFrame.ORIGINAL_TILE_SIZE/2, GameFrame.ORIGINAL_TILE_SIZE, GameFrame.ORIGINAL_TILE_SIZE);
        if(commandNum == 0){
            g2.drawString("o", x2 - GameFrame.ORIGINAL_TILE_SIZE - (int)((x2-x1) - ((x2-x1) * gameState.volume)), y + GameFrame.ORIGINAL_TILE_SIZE/2);
        }
        
        if (gameState.fullscreen) text = "Fullscreen: On";
        else text = "Fullscreen: Off";
        x = getXForCenteredText(text, g2);
        y += GameFrame.ORIGINAL_TILE_SIZE* 3;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString("o", x-GameFrame.ORIGINAL_TILE_SIZE, y);
        }
        //add more controls here

        
        text = "Press X to exit this menu";
        g2.setFont(beyondWonderland.deriveFont(20F));
        g2.setColor(new Color(90, 90, 90));
        x = (int)(GameFrame.GAME_WIDTH - g2.getFontMetrics().getStringBounds(text, g2).getWidth() - (GameFrame.ORIGINAL_TILE_SIZE * 0.25));
        y = GameFrame.GAME_HEIGHT - (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text, x, y);
    }
    
    public void drawMenuScreen(Graphics2D g2){

        int x = (int)(GameFrame.ORIGINAL_TILE_SIZE * GameFrame.MAX_COL*(1.0/6));
        int initX = x;
        int y = (int)(GameFrame.ORIGINAL_TILE_SIZE * GameFrame.MAX_ROW * (1.0/8));

        int width = (int)(GameFrame.ORIGINAL_TILE_SIZE * GameFrame.MAX_COL*(2.0/3));
        int height = (int)(GameFrame.ORIGINAL_TILE_SIZE * GameFrame.MAX_ROW * (6.0/8));

        drawSharpSubWindow(x, y, width, height, g2);

        g2.setFont(beyondWonderland.deriveFont(Font.PLAIN,60F));
        g2.setColor(Color.WHITE);
        utool.changeAlpha(g2, 1f);
        String text = "Menu";
        x = getXForCenteredText(text, g2);
        y += GameFrame.ORIGINAL_TILE_SIZE*4;
        g2.drawString(text, x , y);

        g2.setFont(beyondWonderland.deriveFont(Font.PLAIN,32F));
        text = "PUR :  " + gameState.getPlayer().purity;
        x = initX + GameFrame.ORIGINAL_TILE_SIZE*2;
        y += GameFrame.ORIGINAL_TILE_SIZE*5;
        g2.drawString(text, x, y);

        text = "Items";
        x = initX + GameFrame.ORIGINAL_TILE_SIZE*2;
        y += GameFrame.ORIGINAL_TILE_SIZE*5;
        g2.drawString(text, x, y);

        text = "Abilities";
        x = initX + GameFrame.ORIGINAL_TILE_SIZE*2;
        y += GameFrame.ORIGINAL_TILE_SIZE*5;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(Graphics2D g2){

        //WINDOW
        int x = GameFrame.ORIGINAL_TILE_SIZE*2;
        int y = GameFrame.ORIGINAL_TILE_SIZE/2;
        int width = GameFrame.GAME_WIDTH - (GameFrame.ORIGINAL_TILE_SIZE*4);
        int height = GameFrame.ORIGINAL_TILE_SIZE*4;
        drawRoundedSubWindow(x, y, width, height, g2);

        g2.setFont(beyondWonderland.deriveFont(Font.PLAIN, 18F));
        x += GameFrame.ORIGINAL_TILE_SIZE*2;
        y += (int)(GameFrame.ORIGINAL_TILE_SIZE*1.5);

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 20;
        }

        g2.setFont(beyondWonderland.deriveFont(Font.PLAIN, 12F));
        String subText = "X to close dialogue window.";
        x = getXForAlignToRightText(subText, GameFrame.ORIGINAL_TILE_SIZE*2 + width - 10, g2);
        y = GameFrame.ORIGINAL_TILE_SIZE/2 + height - 20;
        utool.changeAlpha(g2, 0.4f);
        g2.drawString(subText, x, y);
        utool.changeAlpha(g2, 1f);

        subText = "Space to continue dialogue.";
        x = getXForAlignToRightText(subText, GameFrame.ORIGINAL_TILE_SIZE*2 + width - 10, g2);
        y += 10;
        utool.changeAlpha(g2, 0.4f);
        g2.drawString(subText, x, y);
        utool.changeAlpha(g2, 1f);

    }

    public void drawEndScreen(Graphics2D g2){
        //BACKGROUND
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
        String text = "END OF DEMO";
        int x = getXForCenteredText(text, g2);
        int y = GameFrame.ORIGINAL_TILE_SIZE*6;
        
        //SHADOW
        g2.setColor(new Color(0, 1, 0));
        g2.drawString(text, x+5, y+5);
        //MAIN COLOR
        g2.setColor(new Color(146, 142, 128));
        g2.drawString(text, x, y);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));

        text = "Sorry, you have reached";
        x = getXForCenteredText(text, g2);
        y += GameFrame.ORIGINAL_TILE_SIZE*6;
        g2.drawString(text, x, y);

        text = "the end of the demo. There is";
        x = getXForCenteredText(text, g2);
        y += GameFrame.ORIGINAL_TILE_SIZE*3;
        g2.drawString(text, x, y);

        text = "still a lot more I want to add,";
        x = getXForCenteredText(text, g2);
        y += GameFrame.ORIGINAL_TILE_SIZE*3;
        g2.drawString(text, x, y);

        text = "so check back soon!";
        x = getXForCenteredText(text, g2);
        y += GameFrame.ORIGINAL_TILE_SIZE*3;
        g2.drawString(text, x, y);
        
    }

    private void drawRoundedSubWindow(int x, int y, int width, int height, Graphics2D g2){
        Color c = Color.BLACK; //4th number is alpha number (opacity level)
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(4)); //width of outlines of graphics
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    private void drawSharpSubWindow(int x, int y, int width, int height, Graphics2D g2){
        Color c = Color.BLACK; //4th number is alpha number (opacity level)
        g2.setColor(c);
        g2.fillRect(x, y, width, height);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(4)); //width of outlines of graphics
        g2.drawRect(x+5, y+5, width-10, height-10);

    }

    private int getXForCenteredText(String text, Graphics2D g2){

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = GameFrame.GAME_WIDTH/2 - length/2;
        return x;
    }

    private int getXForAlignToRightText(String text, int tailX, Graphics2D g2){

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}