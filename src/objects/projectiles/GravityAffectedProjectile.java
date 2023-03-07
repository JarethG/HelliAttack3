package objects.projectiles;

import inputs.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

import static helpz.Constants.effects.GRAVITY;

public class GravityAffectedProjectile extends Projectile{


    public GravityAffectedProjectile(int x, int y, double speed, int damage, double rotation,int projectileType) {
        super(x, y, speed, damage, rotation, projectileType,false);
        life = 200;
    }

    public void update() {
        super.update();
        ySpeed+=GRAVITY;
    }

    public void draw(Graphics g, BufferedImage image, Camera camera){
        Graphics2D g2d = (Graphics2D) g;
        int rx = width/2;
        int ry = width/2;
        g2d.translate(x- camera.x, y-camera.y);
        g2d.rotate(rotation,rx,ry);
        g.drawImage(image,  0,  0, null);
        g2d.rotate(-rotation,rx,ry);
        g2d.translate(-(x-camera.x ),-(y-camera.y));
    }
}
