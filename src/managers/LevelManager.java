package managers;

import helpz.ImageLoader;
import helpz.LoadSave;
import helpz.TOC;
import inputs.Camera;
import objects.Block;
import objects.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static helpz.Constants.Dimensions.*;
import static helpz.Constants.Dimensions.CAMERA_HEIGHT;

public class LevelManager {
    HashMap<Integer, BufferedImage> blocks;
    HashMap<Integer, BufferedImage> UI;
    BufferedImage background;

    Block[][] level;
    int worldWidth;

    public LevelManager(int zone, int sector) {
        UI = ImageLoader.loadImages("UI", TOC.UI_IMAGES);
        switch (zone) {
            case 0, 4 -> loadImages("Blocks", TOC.TRAINING_BLOCK_IMAGES, 305);
            case 1 -> loadImages("Blocks/snow", TOC.SNOW_BLOCK_IMAGES, 1604);
            case 2 -> loadImages("Blocks/forest", TOC.FOREST_BLOCK_IMAGES, 1601);
            case 3 -> loadImages("Blocks/desert", TOC.DESERT_BLOCK_IMAGES, 308);
        }
        loadLevel(zone + "." + sector);

    }

    public void loadImages(String pathname, int[] blockImageIds, int backgroundID) {
        blocks = ImageLoader.loadImages(pathname, blockImageIds);
        background = ImageLoader.loadImages("Backgrounds", new int[]{backgroundID}).get(backgroundID);
    }

    public void loadLevel(String name) {
        if (name.charAt(0) == '0') {
            name = "training_zone";
        }
        int[][] ids = LoadSave.loadLevel(name);
        if (ids == null) {
            ids = LoadSave.getDefaultLevel();
        }
        Block[][] blocks = new Block[ids.length][ids[0].length];
        for (int y = 0; y < ids.length; y++) {
            for (int x = 0; x < ids[y].length; x++) {
                blocks[y][x] = new Block(ids[y][x], x * BLOCK_SIZE, y * BLOCK_SIZE);
            }
        }
        this.level = blocks;
        worldWidth = level[0].length * BLOCK_SIZE;
    }

    public void draw(Graphics g, Camera camera, Player player) {
        Point p = camera.getBlocksInFocus(player.getX(), player.getY());
        for (int y = p.y; y < p.y + Y_BLOCK_COUNT + 1; y++) {
            for (int x = p.x; x < p.x + X_BLOCK_COUNT + 1; x++) {
                Block b = level[y][x];
                if (b.getId() != 0)
                    g.drawImage(getBlock(b.getId()),
                            x * BLOCK_SIZE - camera.x,
                            y * BLOCK_SIZE - camera.y,
                            BLOCK_SIZE, BLOCK_SIZE, null);
            }
        }
        g.fillRect(0, CAMERA_HEIGHT, CAMERA_WIDTH, CAMERA_HEIGHT);

    }

    public ArrayList<Block> getBlocksInFocus(Player player, Camera camera) {
        Point p = camera.getBlocksInFocus(player.getX(), player.getY());
        ArrayList<Block> inFocus = new ArrayList<>();
        for (int y = p.y; y < p.y + Y_BLOCK_COUNT + 1; y++) {
            for (int x = p.x; x < p.x + X_BLOCK_COUNT + 1; x++) {
                if (level[y][x].getId() != 0 ) {
                    inFocus.add(level[y][x]);
                }
            }
        }
        return inFocus;
    }

    public void checkCollisions(Player player, Camera camera) {
        if (player.getX() > worldWidth - player.getWidth()) {
            player.setX(worldWidth - player.getWidth());
        } else if (player.getX() < 0) {
            player.setX(0);
        }
        Point p = camera.getBlocksInFocus(player.getX(), player.getY());
        for (int y = p.y; y < p.y + Y_BLOCK_COUNT + 1; y++) {
            for (int x = p.x; x < p.x + X_BLOCK_COUNT + 1; x++) {
                if (level[y][x].getId() != 0 &&
                        player.getBounds().intersects(level[y][x].getBounds())) {
                    player.snap(level[y][x]);
                }
            }
        }
    }

    public HashMap<Integer, BufferedImage> getBlocks() {
        return blocks;
    }

    public BufferedImage getBlock(int id) {
        return blocks.get(id);
    }

    public BufferedImage getBackground() {
        return background;
    }

    public HashMap<Integer, BufferedImage> getUI() {
        return UI;
    }

    public Block[][] getLevel() {
        return level;
    }
}
