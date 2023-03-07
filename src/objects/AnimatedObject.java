package objects;

import inputs.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

import static helpz.Constants.Dimensions.BLOCK_SIZE;

public class AnimatedObject extends GameObject {

    public AnimatedObject(int x, int y,int id,int width,int height) {
        super(x, y, id,width,height);
    }

    public void drawRotated(Graphics g, BufferedImage image, int x, int y, int rx, int ry, double degrees){
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(x, y);
        g2d.rotate(Math.toRadians(degrees),rx,ry);
        g2d.drawImage(image,-rx,-ry,null);
        g2d.rotate(-Math.toRadians(degrees),rx,ry);
        g2d.translate(-(x), -(y));
    }

    public void drawBounds(Graphics g,Camera camera) {
        g.fillRect(x-camera.x,y-camera.y,getBounds().width,getBounds().height);
    }

    protected double calculateAngle(int x1, int y1, int x2, int y2) {
        int xDist = x1-x2;
        int yDist =y1-y2;

        float arcValue = (float)Math.atan(yDist / (float)xDist);
        float rotate = (float)Math.toDegrees(arcValue);
        if(xDist>=0)
            rotate+=180;

        return rotate;
    }

    public void snap(Block b) {
        double xdist;
        double ydist;
        if (this.y < b.y) {
            ydist = Math.abs(this.y + height - b.getY());
        } else {
            ydist = Math.abs(this.y - (b.getY() + BLOCK_SIZE));
        }
        if (this.x < b.getX()) {
            xdist = Math.abs(this.x + 30 - b.getX());
        } else {
            xdist = Math.abs(this.x - (b.getX() + BLOCK_SIZE));
        }
        if (xdist < ydist) {
            if(this.x<b.getX())
                x = b.getX()-30;
            else
                x=b.getX()+BLOCK_SIZE;
        } else {
            if(this.y<b.getY()) {
                y = b.getY()-height;
            } else{
                this.y=b.getY()+BLOCK_SIZE;
            }
        }
    }
}
