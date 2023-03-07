package main;

import helpz.Constants;
import inputs.KeyboardListener;
import inputs.MouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    Game game;
    private Dimension size;

    private MouseListener mouseListener;
    private KeyboardListener keyboardListener;


    public GameScreen(Game game) {
        this.game = game;
        setPanelSize();
    }


    public void initInputs() {
        mouseListener = new MouseListener(game);
        keyboardListener = new KeyboardListener(game);

        addKeyListener(keyboardListener);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addMouseWheelListener(mouseListener);
        requestFocusInWindow();
        requestFocus();
    }

    private void setPanelSize() {
//        size = new Dimension(640,800);
        size = new Dimension(Constants.Dimensions.SCREEN_WIDTH,Constants.Dimensions.SCREEN_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.getRender().render(g);
    }

}
