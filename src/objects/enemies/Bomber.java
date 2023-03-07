package objects.enemies;

import inputs.Camera;
import objects.Player;

import java.awt.*;

import static helpz.Constants.Dimensions.CAMERA_WIDTH;
import static helpz.Constants.Dimensions.SCALE;

public class Bomber extends base {
    //bombs do 2 damage
    public boolean bombs = false;
    public int bombtimer = 0;


    public Bomber() {
        super(315);
        setStartHealth(450);
        setBounds(101, 60);
        setGunPosition(80, 45);
        this.speed = 6 *SCALE;
    }

    public void update(Player p, Camera camera){
        this.bombtimer = this.bombtimer + 1;
        if(this.bombs)
        {
            if(this.bombtimer == 10)
            {
//                enemyShoot(this,this.body.bomb,-1,bombDrop,1,10,0,19,90);
//                sb.start("sAirstrike");
                this.setCooldownTimer(0);
                System.out.println("boom");
                this.bombtimer = 0;
            }
        }
        if(this.bombs) {
            if(this.y > p.getY() + this.yd) {
                this.ydir = -1;
            }
            else if(this.y < p.getY() + this.yd - 30) {
                this.ydir = 1;
            }
            else {
                this.ydir = 0;
            }
            if(this.xdir == 1 && this.x > camera.x + CAMERA_WIDTH + this.w || this.x < camera.x - this.w) {
                this.bombs = false;
                this.sequenceTimer = 0;
            }
        } else if (this.sequenceTimer-- > 0) {
            if (this.sequenceTimer % 125* SCALE == 0) {
                this.xd = -150* SCALE + Math.random() * 300* SCALE;
                this.yd = -200* SCALE + Math.random() * 50* SCALE;
            }
            if (this.x > p.getX() + this.xd * SCALE) {
                this.xdir = -1;
            } else if (this.x < p.getX() + this.xd * SCALE - 30) {
                this.xdir = 1;
            } else {
                this.xdir = 0;
                this.xd = -150* SCALE + Math.random() * 300* SCALE;
            }
            if (this.y > p.getY() + this.yd) {
                this.ydir = -1;
            } else if (this.y < p.getY() + this.yd - 30* SCALE) {
                this.ydir = 1;
            } else {
                this.ydir = 0;
            }
        } else {
            if (this.y < p.getY() + this.yd - 30* SCALE) {
                this.ydir = 1;
            }
            if (this.y < camera.y - this.h * 1.5) {
                if(this.bombtimer > 600) {
                    this.ydir = 1;
                } else {
                    this.ydir = 1;
                    this.sequenceTimer = (int) (100 + Math.random()*150);
                    direction = Math.random()>.5?1:-1;
                }
            }
            if(this.xdir == 0) direction = Math.random()>.5?1:-1;

            if (this.x > camera.x + CAMERA_WIDTH + this.w || this.x < camera.x - this.w) {
                this.ydir = -1;
                if (this.x > -camera.x + CAMERA_WIDTH + this.w) {
                    this.xdir = -1;
                } else {
                    this.xdir = 1;
                }
                if(this.bombtimer > 100 + Math.random()*150)
                {
                    this.bombs = true;
                    this.bombtimer = 0;
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
        if(bombs)
            gunRotation = 90;
        else
            gunRotation = getGunRotation( p.getX() + p.getWidth()/2, p.getY() + p.getHeight()/2);
    }

    public int getProjectileType() {
        return bombs?368 : 365;
    }



}