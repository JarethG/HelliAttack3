package objects;

import java.awt.*;

import static helpz.Constants.Dimensions.SCALE;

public class Weapon {

    public int x,y,id;
    public Point rotationPoint;
    public int ammoType;
    public int ammoBox;
    public int damage;
    public int reload;
    public boolean empty;
    public int clipSize;
    public int ammo;
    public int bulletSpeed;
    public double muzzleArc;

    public Weapon(int muzzlex, int muzzley,int rx, int ry,int id,int ammoType,int ammoBox,int clipSize,int damage,double reload,int bulletSpeed) {
        this.x = muzzlex *SCALE;
        this.y = muzzley*SCALE;
        rotationPoint = new Point(rx*SCALE,ry*SCALE);
        this.id = id;
        this.ammoType=ammoType;
        this.ammoBox = ammoBox;
        this.clipSize=clipSize;
        this.ammo = clipSize;
        this.damage = damage;
        this.reload = (int)(60*reload);

        int xDist = x- rotationPoint.x;
        int yDist = y- rotationPoint.y;
        this.muzzleArc = (float)Math.atan(yDist / (float)xDist);

        this.bulletSpeed = bulletSpeed*SCALE;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getRotationPoint() {
        return rotationPoint;
    }

    public int getAmmoType() {
        return ammoType;
    }

    public int getDamage() {
        return  damage;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean b){
        this.empty = empty;
    }

    public int getReload() {
        return reload;
    }

    public int getAmmoBox() {
        return ammoBox;
    }

    public void removeAmmo() {
        ammo-=1;
        if (ammo<=0){
            empty=true;
        }
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public double getMuzzleArc(){
        return muzzleArc;
    }
}
