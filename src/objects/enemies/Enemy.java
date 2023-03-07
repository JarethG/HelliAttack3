package objects.enemies;

import inputs.Camera;
import objects.AnimatedObject;
import objects.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static helpz.Constants.Dimensions.*;

public class Enemy extends AnimatedObject {

    protected Rectangle bounds;
    protected int health;
    protected int maxHealth;
    protected int enemyType;
    protected int direction;
    protected int speed;
    protected int gunX, gunY;
    protected boolean alive = true;
    protected int rotation = 0;
    protected double gunRotation = 0;
    private int cooldown = 50;
    private int cooldownTimer = 0;




    public Enemy(int id, int enemyType) {
        super(CAMERA_WIDTH,0,id,48,32);
        this.enemyType = enemyType;
        bounds = new Rectangle(x,y,32,32);
        direction = -1;
        this.speed = 3;
    }

    public void hurt(int damage){
        this.health-=damage;
        if(health<=0){
            alive = false;
        }
    }

    public void update(Player p, Camera camera){
        checkBoundary(camera);
    }

    public void draw(Graphics g,Camera camera, HashMap<Integer,BufferedImage>  images){
        BufferedImage image = images.get(getEnemyType()*getDirection());
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(x-camera.x, y - camera.y);
        g2d.rotate(Math.toRadians(getRotation()),0,0);
        g2d.drawImage(image,0,0,null);
        g2d.translate(gunX, gunY);
        g2d.rotate(Math.toRadians(gunRotation),6,8);

        g2d.drawImage(images.get(640),-6,-8,null);
        g2d.rotate(-Math.toRadians(gunRotation),6,8);
        g2d.translate(-gunX, -gunY);

        g2d.rotate(-Math.toRadians(getRotation()),0,0);
        g2d.translate(-(x-camera.x), -(y-camera.y));

    }

    public void checkBoundary(Camera camera) {
        if (x +width < 0 + camera.x) {
                direction=1;
            } else if (x >camera.x + CAMERA_WIDTH) {
               direction = -1;
            }
    }

    public void move() {
        this.x += speed * direction;
        updateHitBox();
    }

    protected void updateHitBox() {
        bounds.x =  x;
        bounds.y =  y;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    protected void setStartHealth(int health){
        this.health = health;
        this.maxHealth = health;
    }

    protected void setGunPosition(int x, int y){
        this.gunX = x*SCALE;
        this.gunY = y*SCALE;
    }

    protected void setBounds(int width, int height){
        this.bounds = new Rectangle(x,y,width*SCALE,height*SCALE);
        this.width = width*SCALE;
        this.height = height*SCALE;
    }

    public float getHealthBarFloat() {
        return health /(float)maxHealth;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getDirection() {
        return direction;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public boolean isAlive() {
        return alive;
    }

    public double getGunRotation(int px, int py) {
        return calculateAngle(x + gunX,y + gunY,px,py);
    }
    public double getGunRotation() {
        return gunRotation;
    }

    public int getGunX() {
        return gunX;
    }

    public int getGunY() {
        return gunY;
    }

    public int getRotation() {
        return rotation;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCooldownTimer() {
        return cooldownTimer;
    }

    public void setCooldownTimer(int cooldownTimer) {
        this.cooldownTimer = cooldownTimer;
    }

    public int getProjectileType() {
        return 365;
    }
}
