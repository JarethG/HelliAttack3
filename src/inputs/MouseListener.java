package inputs;

import main.Game;
import main.GameStates;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener, MouseWheelListener {

    private Game game;

    public MouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameStates.gameState) {
                case MENU:
                    game.getMenu().mouseClicked(e.getX(), e.getY());
                    break;
                case PLAYING:
                    game.getPlaying().mouseClicked(e.getX(), e.getY());
                    break;
                case LEVELSELECT:
                    game.getLevelSelect().mouseClicked(e.getX(), e.getY());
                    break;
                case EDIT:
                        game.getEditing().mouseClicked(e.getX(), e.getY());
                    break;
            }
        }  else if (e.getButton() == MouseEvent.BUTTON3)
            game.getEditing().rightClicked(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mousePressed(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mousePressed(e.getX(), e.getY());
                break;
            case LEVELSELECT:
                game.getLevelSelect().mousePressed(e.getX(), e.getY());
                break;
            case EDIT:
                game.getEditing().mousePressed(e.getX(), e.getY());
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseReleased(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseReleased(e.getX(), e.getY());
                break;
            case LEVELSELECT:
                game.getLevelSelect().mouseReleased(e.getX(), e.getY());
                break;
            case EDIT:
                game.getEditing().mouseReleased(e.getX(), e.getY());
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseDragged(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseDragged(e.getX(), e.getY());
                break;
            case EDIT:
                game.getEditing().mouseDragged(e.getX(), e.getY());
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseMoved(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseMoved(e.getX(), e.getY());
                break;
            case LEVELSELECT:
                game.getLevelSelect().mouseMoved(e.getX(), e.getY());
                break;
            case EDIT:
                game.getEditing().mouseMoved(e.getX(), e.getY());
                break;
        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                break;
            case PLAYING:
                game.getPlayer().scroll(e.getWheelRotation());
                break;
            case EDIT:
                game.getEditing().scroll(e.getWheelRotation());
                break;
        }
    }
}
