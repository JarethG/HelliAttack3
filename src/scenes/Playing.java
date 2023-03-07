package scenes;

import helpz.ImageLoader;
import helpz.TOC;
import inputs.Camera;
import main.Game;
import managers.EnemyManager;
import managers.LevelManager;
import managers.ProjectileManager;
import objects.AmmoBox;
import objects.Block;
import objects.Player;
import objects.projectiles.Projectile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static helpz.Constants.Dimensions.*;
import static helpz.Constants.Dimensions.CAMERA_HEIGHT;
import static helpz.TOC.UNLOCK_ORDERED;
import static main.GameStates.*;

public class Playing  extends GameScene implements SceneMethods {
    private LevelManager levelManager;
    private ProjectileManager projectileManager;
    private EnemyManager enemyManager;
    private Camera camera;
    private boolean levelComplete = false;
    private int mouseX,mouseY;
    private Player player;
    private AmmoBox ammoBox;
    private HashMap<Integer, BufferedImage> UI;
    private boolean freeze = false;


    public Playing(Game game,Player player,int zone,int level) {
        super(game);
        this.player = player;
        player.heal();
        loadLevel(zone,level);
    }

    public void loadLevel(int zone, int sector) {
        levelManager = new LevelManager(zone,sector);
        camera = new Camera(0,0,levelManager.getLevel());
        projectileManager = new ProjectileManager(this);
        enemyManager = new EnemyManager(this,zone,sector);
        UI = ImageLoader.loadImages("UI", TOC.UI_IMAGES);

    }

    public void update() {
        if(!levelComplete) {
            player.setMouseAngle(mouseX,mouseY,camera);
            player.update();
            shooting();
            checkCollisions();
            camera.snap(player);
            if(freeze) return;
            projectileManager.update();
            enemyManager.update();
            updateAmmoBox();

        }
    }

    private void updateAmmoBox() {
        if(ammoBox!=null) {
            ammoBox.update(levelManager.getLevel());
            if(ammoBox.getBounds().intersects(player.getBounds())){
                player.pickUpWeapon(ammoBox.getId());
                ammoBox=null;
            }
        }

    }

    private void shooting() {
        if (player.getCooldown()==0) {
           if(player.isFiring()) {
               projectileManager.createNewProjectile(player.getCurrentWeapon(), player, camera);
               player.reload();
           }
        } else player.incrementCooldown();
    }


    @Override
    public void render(Graphics g) {

        g.drawImage(levelManager.getBackground(),0,0,null);
        levelManager.draw(g,camera,player);
        player.draw(g,camera);
        if(!levelComplete) {
        projectileManager.draw(g);
        enemyManager.draw(g);
        drawUI(g);
        drawAmmoBox(g);
        }else {
            drawLevelCompleteButton(g);
        }
    }

    private void drawAmmoBox(Graphics g) {
        if(ammoBox!=null){
            g.drawImage(projectileManager.getImage(ammoBox.getId()),ammoBox.getX()- camera.x,ammoBox.getY()- camera.y,null);
        }
    }

    private void drawUI(Graphics g) {
        g.drawString("enemies left " + enemyManager.getEnemies().size(),10,10);
        BufferedImage playerHead = UI.get(520);
        g.drawImage(playerHead,0,CAMERA_HEIGHT-playerHead.getHeight(),null);
        //healthBar
        BufferedImage health = UI.get(480);
        g.drawImage(health,playerHead.getWidth(),CAMERA_HEIGHT-health.getHeight(),null);
        double healthPercent = (double)player.getHealth()/player.maxHealth;
        if(healthPercent>0.1)
        g.drawImage(UI.get(499).getSubimage(0,0,(int)(health.getWidth()*healthPercent)-2,health.getHeight()-2),playerHead.getWidth(),CAMERA_HEIGHT-health.getHeight(),null);
        //reload
        BufferedImage reload = UI.get(472);
        int xpos = CAMERA_WIDTH-reload.getWidth();
        double reloadPercent = (double)(player.getCurrentWeapon().reload-player.getCooldown())/(player.getCurrentWeapon().reload);
        g.drawImage(reload,xpos,CAMERA_HEIGHT-reload.getHeight(),null);
        if(reloadPercent>0.1)
        g.drawImage(UI.get(489).getSubimage(0,0,reload.getWidth(),(int)(reload.getHeight()*reloadPercent)),xpos,CAMERA_HEIGHT-reload.getHeight(),null);
        //ammo
        BufferedImage ammo = UI.get(player.getCurrentWeapon().getAmmoBox());
        g.drawImage(ammo,xpos-=ammo.getWidth(),CAMERA_HEIGHT-ammo.getHeight(),null);
        //ammo left
        g.drawString(String.valueOf(player.getCurrentWeapon().clipSize),xpos-20*SCALE,CAMERA_HEIGHT-32);
        //timedistort
        g.drawImage(UI.get(493),xpos-=74*SCALE,CAMERA_HEIGHT-14*SCALE,null);
        //hyperjump
        g.drawImage(UI.get(474),xpos-74*SCALE,CAMERA_HEIGHT-14*SCALE,null);
        double jump = (double)(200-player.getHyperJumpTimer())/200;
        if(jump>0.1)
            g.drawImage(UI.get(491).getSubimage(0,0,(int)(74*SCALE*jump),14*SCALE),xpos-74*SCALE,CAMERA_HEIGHT-14*SCALE,null);
    }

    private void drawLevelCompleteButton(Graphics g) {
        BufferedImage bi = levelManager.getUI().get(1646);
        int x = CAMERA_WIDTH/2-bi.getWidth()/2;
        g.drawImage(bi,x,200,null);
        g.drawImage(game.getLevelSelect().getImage(1648),x,200,null);
    }

    private void checkCollisions() {
       levelManager.checkCollisions(player,camera);
        ArrayList<Block> blocks = levelManager.getBlocksInFocus(player,camera);
        for(Block b : blocks){
            for(Projectile p : projectileManager.getEnemyProjectiles()){
                if(p.hitsBlock(b))
                    p.setActive(false);

            }
            for(Projectile p : projectileManager.getPlayerProjectiles()){
                if(b.getBounds().intersects(p.getBounds())){
                    if(p.hitsBlock(b))
                        p.setActive(false);
                }
            }
        }
    }


    @Override
    public void mouseClicked(int x, int y) {
        if(levelComplete){
            setGameState(LEVELSELECT);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        this.mouseX=x;
        this.mouseY=y;

    }

    @Override
    public void mousePressed(int x, int y) {
        player.setFiring(true);
    }

    @Override
    public void mouseReleased(int x, int y) {
        player.setFiring(false);
    }

    @Override
    public void mouseDragged(int x, int y) {
        this.mouseX=x;
        this.mouseY=y;
    }

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_P:setGameState(MENU);
                break;
                case KeyEvent.VK_W:player.jump();
                break;
            case KeyEvent.VK_A:player.moveLeft(true);
                break;
            case KeyEvent.VK_S:player.crouch(true);
                break;
            case KeyEvent.VK_D:player.moveRight(true);
                break;
            case KeyEvent.VK_SPACE:player.setHyperJump(true);
                break;
            case KeyEvent.VK_E:player.swapWeapon(1);break;
            case KeyEvent.VK_Q:player.swapWeapon(-1);break;
            case KeyEvent.VK_SHIFT:freeze=true;break;
            case KeyEvent.VK_K:hax();break;

        }
    }

    private void hax() {
        for(int i : UNLOCK_ORDERED){
            game.getPlayer().addWeapon(i);
        }
    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                break;
            case KeyEvent.VK_A:player.moveLeft(false);
                break;
            case KeyEvent.VK_S:player.crouch(false);
                break;
            case KeyEvent.VK_D:player.moveRight(false);
                break;
            case KeyEvent.VK_SPACE:player.setHyperJump(false);
            case KeyEvent.VK_SHIFT:freeze=false;

        }
    }
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public Camera getCamera() {
        return camera;
    }

    public boolean isLevelComplete() {
        return levelComplete;
    }

    public void setLevelComplete(boolean b) {
        levelComplete=b;
    }

    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }
    public void setLevel(Block[][] level) {
//        this.level = level;
    }

    public Player getPlayer() {
        return player;
    }

    public void setAmmoBox(AmmoBox ammoBox) {
        this.ammoBox = ammoBox;
    }

    public int getWorldHeight() {
        return levelManager.getLevel().length*BLOCK_SIZE;
    }
}
