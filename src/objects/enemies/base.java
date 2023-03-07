package objects.enemies;

import inputs.Camera;
import objects.Player;

import static helpz.Constants.Dimensions.*;

public class base extends Enemy {

    int w = 100;
    int h = 60;
    int xdir = 1;
    int ydir = 0;
    int xSpeed, ySpeed = 0;
    boolean flash = false;
    int sequenceTimer = 100 + (int) Math.random() * 150;
    double xd, yd; // destination

    public base(int enemyType) {
        super(0, enemyType);
        xd = -150 + Math.random() * 300;
        yd = -200 + Math.random() * 50;
        setGunPosition(50,45);

    }

    @Override
    public void update(Player p, Camera camera) {
        if (this.sequenceTimer-- > 0) {
            if (this.sequenceTimer % 125 == 0) {
                this.xd = -150 + Math.random() * 300;
                this.yd = -200 + Math.random() * 50;
            }
            if (this.x > p.getX() + this.xd * SCALE) {
                this.xdir = -1;
            } else if (this.x < p.getX() + this.xd * SCALE - 30) {
                this.xdir = 1;
            } else {
                this.xdir = 0;
                this.xd = -150 + Math.random() * 300;
            }
            if (this.y > p.getY() + this.yd) {
                this.ydir = -1;
            } else if (this.y < p.getY() + this.yd - 30) {
                this.ydir = 1;
            } else {
                this.ydir = 0;
            }
        } else {
            if (this.ydir == 1) {
                this.ydir = (int) (-1 + Math.random() * 2);
            }
            if (this.y < camera.y - this.h * 1.5) {
                this.ydir = 0;
            }
            if (this.xdir == 0) {
                xdir = Math.random()>.5?1:-1;
            }
            if (this.x > camera.x + CAMERA_WIDTH + this.w || this.x < camera.x - this.w) {
                this.ydir = -1;
                if (this.x > -camera.x + CAMERA_WIDTH + this.w) {
                    this.xdir = -1;
                } else {
                    this.xdir = 1;
                }
                this.sequenceTimer = (int) (100 + Math.random() * 150);

                direction = Math.random()>.5?1:-1;
            }
        }

        if (this.xdir == 0) {
                xSpeed += xSpeed>0?-1:1;
        }
        this.xSpeed += this.xdir;
        this.ySpeed += this.ydir;
        limitSpeed();
        this.rotation = xSpeed;
        gunRotation = getGunRotation( p.getX() + p.getWidth()/2, p.getY() + p.getHeight()/2);
    }

    @Override
    public void move() {
        x += xSpeed;
        y += ySpeed;
        updateHitBox();

    }

    public void limitSpeed() {
        if (this.xSpeed > speed) {
            this.xSpeed = speed;
        }
        if (this.xSpeed < -speed) {
            this.xSpeed = -speed;
        }
        if (this.ydir == 0) {
            if (this.ySpeed > 0) {
                this.ySpeed = this.ySpeed - 1;
            }
            if (this.ySpeed < 0) {
                this.ySpeed = this.ySpeed + 1;
            }
        }
        if (this.ySpeed > 2) {
            this.ySpeed = 2;
        }
        if (this.ySpeed < -2) {
            this.ySpeed = -2;
        }
    }
}
