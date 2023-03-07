package objects.enemies;

import inputs.Camera;
import objects.Player;

import static helpz.Constants.Dimensions.CAMERA_WIDTH;

public class Drone extends base{
    public Drone() {
        super( 636);
        setStartHealth(50);
        setPos(0,800);
        setBounds(48,32);
        setGunPosition(30,23);
        xSpeed = 5;
    }

    public void update(Player player, Camera camera){
        if (direction > 0 && x >= camera.x + CAMERA_WIDTH) direction = -1;
        else if (direction < 0 && x < camera.x) direction = 1;
        xSpeed = 5*direction;
        if(player.getY()-y<200)ySpeed = -1;
        else if(player.getY()-y>300) ySpeed = 1;
        else ySpeed = 0;
    }
}
