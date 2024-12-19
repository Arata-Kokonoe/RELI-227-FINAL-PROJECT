package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class UI {

    GameState gameState;
    Font gotHeroin, beyondWonderland;
    UtilityTool utool;
    public boolean messageOn = false;
    //public String message = "";
    //int messageCounter = 0;
    ArrayList<String> message = new ArrayList<String>();
    ArrayList<Integer> messageCounter = new ArrayList<Integer>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public BufferedImage currentCloseup = null;
    public int commandNum = 0;
    public int upgradeNum = 0;
    public int titleScreenState = 0; //0: first screen, 1: second screen
    public double playTime;
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GameState gameState){
        this.gameState = gameState;
        utool = new UtilityTool();
        
        try {
            InputStream is = getClass().getResourceAsStream("/res/fonts/GotHeroin.ttf");
            gotHeroin = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/res/fonts/BeyondWonderland.ttf");
            beyondWonderland = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    /*
    public void drawInventory(){

        int x = gp.tileSize/2;
        int initX = x;
        int y = gp.tileSize + gp.tileSize/4;

        int width = gp.tileSize;
        int height = gp.tileSize;

        for (int i = 0; i < gp.player.MAX_PASSIVES; i++){
            drawSubWindow(x, y, width, height);
            if(gp.player.currentPassives[i] != null) g2.drawImage(gp.player.currentPassives[i].icon, x, y, null);
            x += gp.tileSize + gp.tileSize/4;
        }

        x = initX;
        y += gp.tileSize + gp.tileSize/4;
        for(int i = 0; i < gp.player.MAX_WEAPONS; i++){
            drawSubWindow(x, y, width, height);
            if(gp.player.currentWeapons[i] != null) g2.drawImage(gp.player.currentWeapons[i].icon, x, y, null);
            x += gp.tileSize + gp.tileSize/4;
        }

    }*/

    public void drawWalkingScreen(Graphics2D g2){

    }

    public void drawBattleScreen(Graphics2D g2){

    }

    public void drawTitleScreen(Graphics2D g2){

        if(titleScreenState == 0){
            //BACKGROUND
            g2.setColor(new Color(67, 23, 35));
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
            g2.setColor(new Color(146, 142, 128));
            g2.drawString(text, x, y);

            //CHS IMAGE
            //x = gp.screenWidth/2 - (gp.tileSize*4)/2;
            //y += gp.tileSize * 1.5;
            //g2.drawImage(gp.player.right0, x, y, gp.tileSize*4, gp.tileSize*4, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));

            text = "NEW GAME";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*6;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-GameFrame.ORIGINAL_TILE_SIZE, y);
            }

            text = "LOAD GAME";
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
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "CONTROLS:";
            int x = getXForCenteredText(text, g2);
            int y = GameFrame.ORIGINAL_TILE_SIZE*2;
            g2.drawString(text, x, y);

            text = "WASD or ARROW KEYS to move";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*2;
            g2.drawString(text, x, y);

            text = "SPACE to interact and continue dialogue";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*2;
            g2.drawString(text, x, y);

            text = "ESC to exit dialogue";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*2;
            g2.drawString(text, x, y);

            text = "P to pause game";
            x = getXForCenteredText(text, g2);
            y += GameFrame.ORIGINAL_TILE_SIZE*2;
            g2.drawString(text, x, y);

            //add more controls here

            text = "ESC to exit this menu";
            g2.setFont(g2.getFont().deriveFont(20F));
            g2.setColor(new Color(90, 90, 90));
            x = (int)(GameFrame.GAME_WIDTH - g2.getFontMetrics().getStringBounds(text, g2).getWidth() - (GameFrame.ORIGINAL_TILE_SIZE * 0.25));
            y = GameFrame.GAME_HEIGHT - (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            g2.drawString(text, x, y);

        }
    }

    public void drawPauseScreen(Graphics2D g2){

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        g2.setColor(Color.WHITE);
        utool.changeAlpha(g2, 1f);
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(Graphics2D g2){

        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        if(currentCloseup != null){
            g2.drawImage(utool.scaleImage(currentCloseup, 144, 144), x+25, y+25, null);
            g2.drawRoundRect(x+25, y+25, gp.tileSize*3, gp.tileSize*3, 15, 15);
        }

        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 18F));
        x += gp.tileSize*4;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 20;
        }

        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 12F));
        String subText = "Esc to close dialogue window.";
        x = getXForAlignToRightText(subText, gp.tileSize*2 + width - 10);
        y = gp.tileSize/2 + height - 20;
        utool.changeAlpha(g2, 0.4f);
        g2.drawString(subText, x, y);
        utool.changeAlpha(g2, 1f);

        subText = "Space to continue dialogue.";
        x = getXForAlignToRightText(subText, gp.tileSize*2 + width - 10);
        y += 10;
        utool.changeAlpha(g2, 0.4f);
        g2.drawString(subText, x, y);
        utool.changeAlpha(g2, 1f);

    }

    /*
    public void drawCharacterScreen(){
        //CREATE A FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        //NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Damage Multiplier", textX, textY);
        textY += lineHeight;
        g2.drawString("Damage Reduction", textX, textY);
        textY += lineHeight;
        g2.drawString("Luck", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;

        //VALUES
        int tailX = (frameX + frameWidth) - 30;
        //RESET textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
    
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.damageMultiplier);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.damageReduction);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.luck);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

    }*/

    public void drawEndScreen(Graphics2D g2){
        //BACKGROUND
        g2.setColor(new Color(67, 23, 35));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "GAME OVER";
        int x = getXForCenteredText(text);
        int y = gp.tileSize*3;
        
        //SHADOW
        g2.setColor(new Color(0, 1, 0));
        g2.drawString(text, x+5, y+5);
        //MAIN COLOR
        g2.setColor(new Color(146, 142, 128));
        g2.drawString(text, x, y);

        //CHS IMAGE
        x = gp.screenWidth/2 - (gp.tileSize*4)/2;
        y += gp.tileSize * 1.5;
        g2.drawImage(gp.player.right0, x, y, gp.tileSize*4, gp.tileSize*4, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));

        text = "PLAY AGAIN?";
        x = getXForCenteredText(text);
        y += gp.tileSize*6;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }

    private void drawSubWindow(int x, int y, int width, int height, Graphics2D g2){
        Color c = new Color(0,0,0, 210); //4th number is alpha number (opacity level)
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5)); //width of outlines of graphics
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    private void drawSubWindow(int x, int y, int width, int height, int stroke, Graphics2D g2){
        Color c = new Color(0,0,0, 210); //4th number is alpha number (opacity level)
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(stroke)); //width of outlines of graphics
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    private int getXForCenteredText(String text, Graphics2D g2){

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = GameFrame.GAME_WIDTH/2 - length/2;
        return x;
    }

    private int getXForCenteredText(String text, int x1, int x2, Graphics2D g2){

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = ((x2-x1)/2) + x1 - length/2;
        return x;
    }

    private int getXForAlignToRightText(String text, int tailX, Graphics2D g2){

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}