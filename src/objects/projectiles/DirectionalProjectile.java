package objects.projectiles;

import inputs.Camera;
import objects.GameObject;
import objects.enemies.Enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DirectionalProjectile extends Projectile{

    private double speed;

    public DirectionalProjectile(int x, int y, double speed, int damage, double rotation, int projectileType,boolean explosive) {
        super(x, y, speed, damage, rotation,  projectileType,explosive);
        this.speed = speed;
        this.life = 300;
    }

    public boolean isHitting(GameObject o){
        if(projectileType == 329)//seeking
        updateDirection(o.getX() + o.getBounds().width/2,o.getY() +o.getBounds().height/2);
            return super.isHitting(o);
    }

    public void updateDirection(int ex, int ey) {
        double xDist = (x - ex);
        double yDist = (y-ey);

        float arcValue = (float)Math.atan(yDist / xDist);
        if(xDist>0) {
            arcValue+=Math.toRadians(180);
        }
        this.rotation = arcValue;

        xSpeed = (float) (this.speed * Math.cos(arcValue));
        ySpeed = (float) (speed * Math.sin(arcValue));
    }

    public void setSpeed(int speed, double rotation) {
        xSpeed = (float) (speed * Math.cos(rotation));
        ySpeed = (float) (speed * Math.sin(rotation));
    }

    public void draw(Graphics g, BufferedImage image, Camera camera){
        Graphics2D g2d = (Graphics2D) g;
        int ry = image.getHeight()/2;
        g2d.translate(x- camera.x, y-camera.y-ry);
        g2d.rotate(rotation,0,ry);
        g.drawImage(image,  0,  0, null);
        g2d.rotate(-rotation,0,ry);
        g2d.translate(-(x-camera.x ),-(y-camera.y-ry));
        g.setColor(Color.red);

    }
}
