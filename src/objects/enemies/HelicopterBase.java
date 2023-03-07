package objects.enemies;

import inputs.Camera;
import objects.Player;

import java.awt.*;

import static helpz.Constants.Dimensions.CAMERA_WIDTH;
import static objects.enemies.SEQUENCES.TRACKING;


enum SEQUENCES {HOVER,TRACKING,DRONE}

public class HelicopterBase extends Enemy {

    protected int sequenceTimer;
    protected Point target;
    protected float xSpeed, ySpeed = 0;
    protected float acceleration = 1f;
    protected SEQUENCES sequence = TRACKING;

    public HelicopterBase(int helicopterType) {
        super(0, helicopterType);
        sequenceTimer = 0;
        setPos(100, 100);
        target = new Point(100, 100);
    }

    public void update(Player player, Camera camera) {
        if(atTarget())
            direction = direction>0?-1:1;

        setTarget(player, camera);
        updateDirection();
    }

    private boolean atTarget() {
       return Math.abs(target.x-x)<10;
    }

    private void setTarget(Player player, Camera camera) {
        switch(sequence){
            case TRACKING -> setTrackingPoint(player, camera);
            case DRONE -> setDronePoint(player,camera);
        }
    }

    public void move() {
        this.x += xSpeed;
        this.y += ySpeed;
        updateHitBox();
    }

    private void setTrackingPoint(Player player,Camera camera) {
        if (direction > 0)
            target = new Point(player.getX() - 300, player.getY() - 300);
        else
            target = new Point(player.getX() + 300, player.getY() - 300);
    }

    public void setDronePoint(Player player, Camera camera) {
        int ty = player.getY() - 300;
        int tx = camera.x-width;
        if (direction > 0 && x <= camera.x + CAMERA_WIDTH) tx += (CAMERA_WIDTH +width);
        else if (direction < 0 && x < camera.x-width) tx +=( CAMERA_WIDTH + width);
        target = new Point(tx,ty);
    }

    private void updateDirection() {
        double xDist = x - target.x;
        double yDist = y - target.y;
        float arcValue = (float) Math.atan(yDist / xDist);
        if (xDist > 0) {
            arcValue += Math.toRadians(180);
        }
        xSpeed += (float) (this.acceleration * Math.cos(arcValue));
        ySpeed += (float) (this.acceleration * Math.sin(arcValue));
        xSpeed = Math.max(Math.min(speed, xSpeed), -speed);
        ySpeed = Math.max(Math.min(speed, ySpeed), -speed);
    }


}
