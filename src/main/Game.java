package main;

import helpz.LoadSave;
import managers.LevelManager;
import objects.Player;
import scenes.Editing;
import scenes.LevelSelect;
import scenes.Menu;
import scenes.Playing;

import javax.swing.*;

public class Game extends JFrame implements Runnable {


    private GameScreen gameScreen;
    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;


    //Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private LevelSelect levelSelect;
    private Editing editing;
    private LevelManager levelManager;
    private Player player;

    public Game() {
        initClasses();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setResizable(false);
        add(gameScreen);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initClasses() {
//        tileManager = new TileManager();
        levelManager = new LevelManager(0,0);
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        player = new Player(100,100);
        playing = new Playing(this,player,0,0);
        levelSelect = new LevelSelect(this);
        editing = new Editing(this);

    }







    private void start() {
        gameThread = new Thread(this) {
        };
        gameThread.start();
    }


    private void updateGame() {
        switch(GameStates.gameState){
            case MENU:
                break;
            case PLAYING:
                playing.update();
                break;
            case LEVELSELECT:
                levelSelect.update();
                break;
            case EDIT:
                editing.update();
                break;
        }
    }

    public static void main(String[] args) {

        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int updates = 0;
        int frames = 0;

        long now;

        while (true) {

            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }

            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    //Getters and setters
    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Editing getEditing() {
        return editing;
    }

    public LevelSelect getLevelSelect() {return levelSelect;}
    public void setPlaying(Playing playing){
        this.playing = playing;
    }

    public Player getPlayer() {
        return player;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
}
