package ui;

import helpz.Constants;
import helpz.ImageLoader;
import helpz.LoadSave;
import helpz.TOC;
import objects.Block;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static main.GameStates.*;

public class Toolbar extends Bar {
    private Editing editing;
    Button save;
    Button menu;

    private BufferedImage pathStartImage,pathEndImage;

    private Block selectedBlock;

    private Map<Button, Block[]> map = new HashMap<>();

    private Button currentButton;
    private int currentIndex = 0;

    int[][] blockButtonIds;
    Button[] blockButtons;

    public Toolbar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        initButtons();
    }

    private void initButtons() {
        char c = Constants.names.LEVEL_NAME.charAt(0);
        switch(c){
            case 't':
            case '4':
              blockButtonIds = TOC.trainingZoneButtonIds;
                break;
            case '1':
                blockButtonIds = TOC.snowZoneButtonIds;
                break;
            case '2':
                blockButtonIds = TOC.forestZoneButtonIds;
                break;
            case '3':
                blockButtonIds = TOC.desertZoneButtonIds;
                break;
        }
        blockButtons = new Button[blockButtonIds.length];
        save = new Button("Save", 2, y+2, 100, 30);
        menu = new Button("Menu", 2, y+34, 100, 30);
        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = y+10;
        int xOffset = (int) (w * 1.1f);

        for( int i = 0; i < blockButtons.length;i++) {
            initMapButton(blockButtons[i],blockButtonIds[i],xStart, yStart, xOffset, w, h,i);
        }
    }

    private void initMapButton(Button b, int[]ids, int x, int y, int xOff, int w, int h, int index) {
        if(index>4){
            y+=50;
            index-=4;
        }
        b = new Button("", x + xOff * index, y, w, h, index);
        Block[] blocks = new Block[ids.length];
        int i = 0;
        for(int id : ids){
            blocks[i++] = new Block(id,0,0);
        }
        map.put(b, blocks);
    }

    public void rotateSprite(int i){
        currentIndex+=i;
        System.out.println(i + " " + currentIndex);
        if(currentIndex>= map.get(currentButton).length)
            currentIndex = 0;
        if(currentIndex< 0)
            currentIndex = map.get(currentButton).length-1;
        selectedBlock = map.get(currentButton)[currentIndex];
        editing.setSelectedBlock(selectedBlock);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(220, 120, 15));
        g.fillRect(x, y, width, height);
        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        save.draw(g);
        menu.draw(g);

        drawSelectedTile(g);
        drawMapButtons(g);
    }

    private void drawMapButtons(Graphics g) {
        for (Map.Entry<Button, Block[]> entry : map.entrySet()) {
            Button b = entry.getKey();
            BufferedImage img = editing.getLevelManager().getBlock(entry.getValue()[0].getId());
            g.drawImage(img, b.x, b.y, b.width, b.height, null);
            drawButtonFeedBack(g, b);
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedBlock != null) {
            g.drawImage(editing.getLevelManager().getBlock(selectedBlock.getId()), 50, y+100, 50, 50, null);
            g.setColor(Color.black);
            g.drawRect(550, y+10, 50, 50);
        }
    }

    public void mouseClicked(int x, int y) {
        if (save.getBounds().contains(x, y)) {
            saveLevel();
            editing.getGame().getPlaying().setLevel(editing.getLevel());
        }else  if (menu.getBounds().contains(x, y)) {
            setGameState(MENU);
        }else {
            for (Button b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    selectedBlock = map.get(b)[0];
                    editing.setSelectedBlock(selectedBlock);
                    currentButton = b;
                    currentIndex = 0;
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        save.setMouseOver(false);
        menu.setMouseOver(false);

        for (Button b : map.keySet()) {
            b.setMouseOver(false);
        }
            for (Button b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    return;
                }
        }
    }

    public void mousePressed(int x, int y) {
        if (save.getBounds().contains(x, y)) {
            save.setMousePressed(true);
        } else if (menu.getBounds().contains(x, y)) {
            menu.setMousePressed(true);
        } else {
            for (Button b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        save.resetBooleans();
        menu.resetBooleans();
        for (Button b : map.keySet()) {
            b.resetBooleans();
        }
    }


    private void saveLevel() {
        editing.saveLevel();
    }
}
