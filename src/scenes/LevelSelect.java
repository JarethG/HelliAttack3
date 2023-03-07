package scenes;

import helpz.ImageLoader;
import main.Game;
import objects.Player;
import objects.Weapon;
import ui.NavigationButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static helpz.Constants.Dimensions.*;
import static helpz.TOC.getWeaponUnlocks;
import static main.GameStates.*;

public class LevelSelect extends GameScene implements SceneMethods {

    private HashMap<Integer, BufferedImage> images;
    private final int[] imageIds = new int[]{83, 1664, 1648, 1651, 1654, 1657, 1660};
    private int currentZone = 2;
    private int currentSector = 4;
    public ArrayList<Weapon> playerWeapons;
    private final int[] zoneIds = new int[]{1648, 1651, 1654, 1657, 1660};

    private NavigationButton back, previous, next, start;
    private final NavigationButton[] buttons = new NavigationButton[4];

    public LevelSelect(Game game) {
        super(game);
        loadAssets();
        loadButtons();
    }

    private void loadAssets() {
        images = ImageLoader.loadImages("UI", imageIds);
    }

    private void loadButtons() {
        back = new NavigationButton(MENU, "back", CAMERA_WIDTH / 2 - 50, CAMERA_HEIGHT - 100, 100, 40);
        previous = new NavigationButton(null, "previous", 50, CAMERA_HEIGHT - 100, 100, 40);
        next = new NavigationButton(null, "next", CAMERA_WIDTH - 150, CAMERA_HEIGHT - 100, 100, 40);
        start = new NavigationButton(PLAYING, "start", CAMERA_WIDTH - 150, CAMERA_HEIGHT - 50, 100, 40);
        buttons[0] = back;
        buttons[1] = previous;
        buttons[2] = next;
        buttons[3] = start;

    }

    @Override
    public void render(Graphics g) {
        drawBackground(g);
        drawZones(g);
        drawButtons(g);
        g.drawString("Zone " + currentZone + "-" + currentSector, 50, 100);
    }

    private void drawButtons(Graphics g) {
        back.draw(g);
        previous.draw(g);
        next.draw(g);
        start.draw(g);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(images.get(83), 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, null);
    }

    private void drawZones(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(20, 100, CAMERA_WIDTH - 40, 120);
        for (int i = 0; i < zoneIds.length; i++) {
            g.drawImage(images.get(zoneIds[i]), 50 + i * BLOCK_SIZE * 2, 150, null);
            if (i != currentZone)
                g.drawImage(images.get(1664), 50 + i * BLOCK_SIZE * 2, 150, null);
        }
    }

    private void increaseSector() {
        if (currentZone == 0) {
            currentZone++;
            currentSector = 1;
        } else {
            if (currentSector < 4) {
                currentSector++;
            } else if (currentZone < 4) {
                currentSector = 1;
                currentZone++;
            }
        }
    }

    public void decreaseSector() {
        if (currentZone > 0)
            currentSector--;
        if (currentSector < 1) {
            if (currentZone == 1) {
                currentZone = 0;
                currentSector = 1;
            } else {
                currentSector = 4;
                currentZone--;
            }
        }
    }


    @Override
    public void mouseClicked(int x, int y) {
        if (back.getBounds().contains(x, y)) {
            setGameState(MENU);
        }
        if (next.getBounds().contains(x, y)) {
            increaseSector();
        }
        if (previous.getBounds().contains(x, y)) {
            decreaseSector();
        }
        if (start.getBounds().contains(x, y)) {
            Player player = game.getPlayer();
            player.setY(0);
            player.addWeaponUnlock(getWeaponUnlocks(currentZone, currentSector));
            if (currentZone == 0) {
                playerWeapons = new ArrayList<>(player.getWeapons());
                for (Weapon w : player.getUnlockedWeapons()) {
                    player.addWeapon(w.id);
                }
            } else {
                if (playerWeapons != null) {
                    player.setWeapons(playerWeapons);
                    player.setCurrentWeapon();
                }
            }
            game.setPlaying(new Playing(
                    game,
                    player,
                    currentZone, currentSector
            ));
            setGameState(PLAYING);
        }

    }


    @Override
    public void mouseMoved(int x, int y) {
        for (NavigationButton b : buttons)
            b.setMouseOver(false);

        for (NavigationButton b : buttons)
            if (b.getBounds().contains(x, y)) {
                b.setMouseOver(true);
            }
    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseReleased(int x, int y) {

    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    public void update() {
    }

    public BufferedImage getImage(int id) {
        return images.get(id);
    }
}
