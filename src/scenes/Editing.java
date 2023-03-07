package scenes;

import helpz.ImgFix;
import helpz.LoadSave;
import main.Game;
import managers.LevelManager;
import objects.Block;
import ui.Toolbar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import static helpz.Constants.Dimensions.*;
import static helpz.Constants.names.LEVEL_NAME;

public class Editing extends GameScene implements SceneMethods{

    private Block selectedBlock;
    private Toolbar toolbar;
    private Block[][] level;

    private boolean drawSelected = false;

    private int mouseX, mouseY;
    private int lastTileX, lastTileY, lastTileId;
    private LevelManager levelManager;

    private int width;
    private int height;
    private int shiftX = 0;
    private int shiftY = 0;
    public Editing(Game game) {
        super(game);
        levelManager = new LevelManager(3,0);
        toolbar = new Toolbar(0, CAMERA_HEIGHT, SCREEN_WIDTH, 160, this);

        loadLevel();
        this.height = level.length;
        this.width = level[0].length;
    }

    private void loadLevel() {
        int[][] ids = LoadSave.loadLevel(LEVEL_NAME);
        if(ids == null){
           ids = LoadSave.getDefaultLevel();
        }
        level = Arrays.stream(ids)
                .map(e->Arrays.stream(e)
                        .mapToObj(id->new Block(id,0,0))
                        .toArray(Block[]::new))
                .toArray(Block[][]::new);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(levelManager.getBackground(),0,0,CAMERA_WIDTH,CAMERA_HEIGHT,null);
//        drawGrid(g);
        drawLevel(g);
        toolbar.draw(g);
        drawSelectedBlock(g);
    }

    private void drawGrid(Graphics g) {
        for (int y = 0; y < level.length; y++) {
        g.drawLine(0,y*BLOCK_SIZE,CAMERA_HEIGHT,y*BLOCK_SIZE);
        }
        for (int x = 0; x < level[0].length; x++) {
            g.drawLine(x*BLOCK_SIZE,0,x*BLOCK_SIZE,CAMERA_WIDTH);
        }
    }

    public void update(){
        updateTick();
    }

    private void drawLevel(Graphics g){
        for (int y = shiftY; y < level.length; y++) {
            for (int x = shiftX ; x < level[0].length; x++) {
                int id = level[y][x].getId();
                    g.drawImage(levelManager.getBlock(id), x * BLOCK_SIZE - shiftX*BLOCK_SIZE, y * BLOCK_SIZE - shiftY*BLOCK_SIZE,BLOCK_SIZE,BLOCK_SIZE, null);
            }
        }
        drawDimension(g);
    }


    private void drawSelectedBlock(Graphics g) {
        if (selectedBlock != null && drawSelected) {
            g.drawImage(levelManager.getBlock(selectedBlock.getId()), mouseX, mouseY, BLOCK_SIZE, BLOCK_SIZE, null);
        }
    }

    public void setSelectedBlock(Block block) {
        this.selectedBlock = block;
        drawSelected = true;
    }
    private void drawDimension(Graphics g) {
        g.setColor(Color.black);
        g.drawString("Dimensions: "+ level.length + " Y | " + level[0].length + " X",10,15);
        g.drawString("curent pos: "+ shiftY + " Y | " + shiftX + " X",10,30);
    }
//

    private void changeTile(int x, int y) {
        if (selectedBlock != null) {
            int tileX = x / BLOCK_SIZE + shiftX;
            int tileY = y / BLOCK_SIZE + shiftY;

                if (lastTileX == tileX && lastTileY == tileY &&
                        lastTileId == selectedBlock.getId()) {
                    return;
                }
                lastTileX = tileX;
                lastTileY = tileY;
                lastTileId = selectedBlock.getId();
                level[tileY][tileX] = selectedBlock;
            }
    }
    private void clearTile(int x, int y) {
        int tileX = x / BLOCK_SIZE + shiftX;
        int tileY = y / BLOCK_SIZE + shiftY;
        level[tileY][tileX] = new Block(0,0,0);
    }

    public void saveLevel(){
        int[][] ids = Arrays.stream(level)
                .map(e->Arrays.stream(e)
                        .mapToInt(tile->tile.getId())
                        .toArray())
                .toArray(int[][]::new);
        LoadSave.SaveLevel(LEVEL_NAME,ids);
//        game.getPlaying().setLevel(ids);
        System.out.println("level saved");
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= CAMERA_HEIGHT) {
            toolbar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }
    public void rightClicked(int x, int y) {
        if (y >= CAMERA_HEIGHT) {
        } else {
            clearTile(mouseX, mouseY);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y>=CAMERA_HEIGHT)
            toolbar.mousePressed(x,y);
    }

    @Override
    public void mouseReleased(int x, int y) {
        toolbar.mouseReleased(x,y);
    }

    //
//    @Override
    public void mouseDragged(int x, int y) {
        if (y >=CAMERA_HEIGHT) {

        } else {
            changeTile(x, y);
        }
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_T){
            Block[][] temp = new Block[height+1][width];
            for(int y = 0; y < height;y++){
                for(int x = 0 ; x < width; x++){
                    temp[y+1][x] = level[y][x];
                }
            }
            for(int x = 0 ; x < width; x++){
                temp[0][x] = new Block(0,width*BLOCK_SIZE,0);
            }
            level=temp;
            height++;
            System.out.println("completed");
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            shiftX += 1;
            if(shiftX + 20 > width){
                Block[][] temp = new Block[height][width+1];
                for(int y = 0; y < height;y++){
                    for(int x = 0 ; x < width; x++){
                        temp[y][x] = level[y][x];
                    }
                }
                for(int i = 0; i < height;i++){
                    temp[i][width] = new Block(0,width*BLOCK_SIZE,i*BLOCK_SIZE);
                }
                level=temp;
                width = width+1;
                System.out.println("complete");
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            if(shiftX>0)
            shiftX -= 1;
            boolean b = false;
            for(int i = 0; i < height; i++){
                if(level[i][level[i].length-1].getId()!=0){
                    b = true;
                    System.out.println("bottom of level found");
                    break;
                }
            }
            if(!b){
                width--;
                Block[][] temp = new Block[height][width];
                for(int y = 0; y < height;y++){
                    for(int x = 0 ; x < width; x++){
                        temp[y][x] = level[y][x];
                    }
                }
                level=temp;
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            System.out.println("pressed");
            shiftY += 1;
            System.out.println(shiftY);
            if(shiftY + Y_BLOCK_COUNT> height-1){
                Block[][] temp = new Block[height+1][width];
                for(int y = 0; y < height;y++){
                    for(int x = 0 ; x < width; x++){
                        temp[y][x] = level[y][x];
                    }
                }
                for(int i = 0; i < width;i++){
                    temp[height][i] = new Block(0,width*BLOCK_SIZE,i*BLOCK_SIZE);
                }
                level=temp;
                height = height+1;
                System.out.println("complete");
            }
        }else if (e.getKeyCode() == KeyEvent.VK_W) {
            if(shiftY>0)
                shiftY -= 1;
            boolean b = false;
            for(int i = 0; i < width; i++){
                if(level[level.length-1][i].getId()!=0){
                    b = true;
                    System.out.println("bottom of level found");
                    break;
                }
            }
            if(!b){
                height--;
                Block[][] temp = new Block[height][width];
                for(int y = 0; y < height;y++){
                    for(int x = 0 ; x < width; x++){
                        temp[y][x] = level[y][x];
                    }
                }
                level=temp;
            }
        }
    }
    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void scroll(int i) {
        toolbar.rotateSprite(i);
    }


    public Block[][] getLevel() {
        return level;
    }
    //
    @Override
    public void mouseMoved(int x, int y) {
        if (y >= CAMERA_HEIGHT) {
            toolbar.mouseMoved(x, y);
            drawSelected = false;
        } else {
            drawSelected = true;
            mouseX = (x / BLOCK_SIZE) * BLOCK_SIZE;
            mouseY = (y / BLOCK_SIZE) * BLOCK_SIZE;
        }
    }
}
