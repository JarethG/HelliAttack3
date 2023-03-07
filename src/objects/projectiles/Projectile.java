package objects.projectiles;

import inputs.Camera;
import objects.AnimatedObject;
import objects.Block;
import objects.GameObject;
import objects.enemies.Enemy;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Projectile extends AnimatedObject {
    protected int projectileType,damage;
    protected float trueX,trueY,xSpeed, ySpeed;
    protected double rotation;
    protected int life;
    protected boolean active = true;
    protected double speed;
    private boolean explosive;

    public Projectile(int x, int y, double speed, int damage,double rotation,int projectileType, boolean explosive) {
        super(x,y,0,10,10);
        trueX = x;
        trueY = y;
        this.projectileType = projectileType;
        this.speed = speed;
        xSpeed = (float) (speed * Math.cos(rotation));
        ySpeed = (float) (speed * Math.sin(rotation));
        this.damage = damage;
        this.rotation = rotation;
        life = 100;
        this.explosive = explosive;
    }
    public Projectile(int x, int y, int damage,int projectileType) {
        super(x,y,0,10,10);
        trueX = x;
        trueY = y;
        this.projectileType = projectileType;
        this.damage = damage;
        life = 100;
    }

    public void move(){
        trueX+=xSpeed;
        trueY+=ySpeed;
        x = (int)trueX;
        y =(int)trueY;
        if(life>=0)
            life--;
        else
            active=false;
    }

    public void update(){
        if(isActive()){
            move();
        }
        if(projectileType == 339){//rpg
            xSpeed*=1.1;
            ySpeed*=1.1;
        }
    }

    public void draw(Graphics g, BufferedImage image, Camera camera){
        g.drawImage(image,  x - camera.x-image.getWidth()/2,  y - camera.y- image.getHeight()/2, null);
    }

    public boolean isHitting(GameObject o) {
            return o.getBounds().contains(x,y);
    }


    public int getProjectileType() {
        return projectileType;
    }

    public boolean isActive() {
        return active;
    }

    public int getDamage() {
        return damage;
    }

    public void setActive(boolean active) {this.active = active;}

    public void setVelocity(double rotation,double speed) {
        xSpeed = (float) (speed * Math.cos(rotation));
        ySpeed = (float) (speed * Math.sin(rotation));
        this.rotation = rotation;
    }

    public void updatedirection(double rotation) {
        xSpeed = (float) (this.speed * Math.cos(rotation));
        ySpeed = (float) (speed * Math.sin(rotation));
    }

    public void checkHit(Enemy e) {
        if (isHitting(e)) {
            setActive(false);
            e.hurt(getDamage());
        }
    }

    public boolean isExplosive() {
        return explosive;
    }

    public boolean hitsBlock(Block b) {
        return b.getBounds().intersects(getBounds());
    }
}
