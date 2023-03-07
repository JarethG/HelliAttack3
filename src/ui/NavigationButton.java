package ui;

import main.GameStates;

import java.awt.*;

import static main.GameStates.LEVELSELECT;
import static main.GameStates.setGameState;

public class NavigationButton extends Button{

    private GameStates gameState;

    public NavigationButton(GameStates gameState,String name, int x, int y, int width, int height) {
        super(name, x, y, width, height);
        this.gameState = gameState;
    }

    public void draw(Graphics g) {

        drawText(g);

    }

    private void drawText(Graphics g) {
        g.setFont(new Font("Arial",Font.PLAIN,24));
        if (mouseOver)
            g.setColor(Color.GRAY);
        else
            g.setColor(new Color(200, 200, 200));
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 2);
    }

    public void onPress() {
        setGameState(gameState);
    }
}
