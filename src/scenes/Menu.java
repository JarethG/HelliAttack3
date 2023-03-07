package scenes;

import helpz.ImageLoader;
import helpz.TOC;
import inputs.Camera;
import main.Game;
import main.GameStates;
import ui.Button;
import ui.NavigationButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static helpz.Constants.Dimensions.*;
import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods{

    private BufferedImage background;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private Random random;


    GameStates[] states = new GameStates[]{LEVELSELECT,EDIT,MENU,MENU,MENU,MENU};
    NavigationButton[] buttons = new NavigationButton[states.length];

    private Button play,edit,quit,start;

    private HashMap<Integer, BufferedImage> UI;

    public Menu(Game game) {
        super(game);
        loadImages();
        random = new Random();
        initButtons();
    }

    private void loadImages() {
        UI = ImageLoader.loadImages("UI", TOC.UI_IMAGES);
    }

    private void initButtons() {
        int w = 150;
        int h = BLOCK_SIZE;
        int x = CAMERA_WIDTH/2-w/2;
        int y = BLOCK_SIZE*2;
        int yOffset = BLOCK_SIZE;

        String[] names = new String[] {"start","edit","help","options","players","credits"};
        for( int i = 0; i < names.length; i++){
            buttons[i] = new NavigationButton(states[i],names[i],x,y + yOffset*i,w,h);
        }
    }

    @Override
    public void render(Graphics g) {
        drawBackground(g);
        drawButtons(g);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(UI.get(83),0,0, CAMERA_WIDTH,CAMERA_HEIGHT,null);
    }

    private void drawButtons(Graphics g) {
        for(NavigationButton b : buttons){
            b.draw(g);
        }
    }


    @Override
    public void mouseClicked(int x, int y) {
        for(NavigationButton b : buttons)
            if(b.getBounds().contains(x,y))
                b.onPress();
    }

    @Override
    public void mouseMoved(int x, int y) {
        for(NavigationButton b : buttons)
            b.setMouseOver(false);

        for(NavigationButton b : buttons)
            if(b.getBounds().contains(x,y))
            b.setMouseOver(true);

    }

    @Override
    public void mousePressed(int x, int y) {
        for(NavigationButton b : buttons)
           if(b.getBounds().contains(x,y))
               b.setMousePressed(true);
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    private void resetButtons() {
        for(NavigationButton b : buttons)
            b.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
