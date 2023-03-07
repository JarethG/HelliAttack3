package managers;

import helpz.ImageLoader;
import helpz.ImgFix;
import helpz.TOC;
import inputs.Camera;
import objects.enemies.Enemy;
import objects.Player;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static helpz.Constants.Dimensions.CAMERA_HEIGHT;
import static helpz.Constants.Dimensions.CAMERA_WIDTH;

public class EnemyManager {

    private Playing playing;
    HashMap<Integer, BufferedImage> images;
    private ArrayList<Enemy>enemies;
    private Enemy dead;

    private int gunId = 640;
    private int enemyHeight;

    public EnemyManager(Playing playing,int zone,int sector) {
        loadImages();
        enemies=getLevelEnemies(zone,sector);
        this.playing = playing;
        enemyHeight = playing.getWorldHeight()-300;
        if(enemies.size()!=0)
        enemies.get(0).setPos(CAMERA_WIDTH,500);
    }

    public ArrayList<Enemy> getLevelEnemies(int zone, int level) {
        return TOC.getLevelEnemies(zone,level);
    }

    private void loadImages() {
        images = ImageLoader.loadImages("Enemies",TOC.ENEMY_IMAGES);
        Integer [] keys = images.keySet().toArray(Integer[]::new);

        for(int i : keys){
            images.put(-i, ImgFix.invertImage(images.get(i)));
        }
    }

    public void update() {
        Enemy e = getCurrentEnemy();
        if(e==null)return;
        Camera camera = playing.getCamera();
        if(e.isAlive()) {
            if(e.getCooldownTimer()>0){
                e.setCooldownTimer(e.getCooldownTimer()-1);
            } else {
                e.setCooldownTimer(e.getCooldown());
                playing.getProjectileManager().createNewEnemyProjectile(e,playing.getPlayer());
            }
            e.update(playing.getPlayer(),camera);
            e.move();
        } else {
            dead = enemies.remove(0);
            if(enemies.size()==0){
                playing.setLevelComplete(true);
            } else {
                enemies.get(0).setPos(playing.getCamera().x-100, enemyHeight);
            }
        }
        if(dead!=null) {
            if (dead.getY() < CAMERA_HEIGHT + playing.getCamera().y) {
                dead.move();
                dead.setPos(dead.getX(), dead.getY() + 10);
            } else {
                dead = null;
            }
        }
    }

    public void draw(Graphics g) {
        if(enemies.size()==0)return;
        Enemy e = enemies.get(0);
        Camera camera = playing.getCamera();
        g.setColor(Color.red);
        g.fillRect(e.getX() - camera.x,e.getY()- camera.y,(int)(e.getHealthBarFloat()*40),3);
        if(!e.isAlive()){
            drawSmoke(g);
        } else {
            e.draw(g,camera,images);

        }
    }

    private void drawSmoke(Graphics g) {
        Enemy e = enemies.get(0);
        Camera camera = playing.getCamera();
        g.drawImage(playing.getProjectileManager().images.get(331), e.getX() - camera.x,e.getY()- camera.y,null);
    }



    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Enemy getCurrentEnemy() {
        if(enemies.size()!=0){
            return enemies.get(0);
        }
        else return null;
    }
}
