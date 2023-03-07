package objects;

import helpz.ImageLoader;
import helpz.ImgFix;
import helpz.TOC;
import inputs.Camera;
import managers.WeaponManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static helpz.Constants.Dimensions.*;
import static helpz.Constants.effects.GRAVITY;
import static helpz.TOC.STARTING_UNLOCKS;

public class Player extends AnimatedObject {
    private BufferedImage[][] head;
    private ArrayList<Weapon> weapons;
    private BufferedImage[][] leg;
    private int speed = 4* SCALE;
    private boolean l, r, jump, lookLeft;
    private boolean onGround = false;
    private double dy = 0;
    private double runIndex = 0;
    private int weaponIndex = 0;
    boolean running;
    boolean crouch;
    boolean hyperJump = false;
    int hyperJumpTimer = 0;
    boolean doubleJump;

    private Weapon currentWeapon;
    private float aimAngle;
    private boolean firing = false;
    private int cooldown = 0;
    public int maxHealth = 20;
    private int health = 20;
    private WeaponManager weaponManager;
    private ArrayList<Weapon> unlockedWeapons;
    public int maxZone = 1;
    public int maxSector = 4;

    public Player(int x, int y) {
        super(x, y, 0, 30* SCALE, 50*SCALE);
        loadDefaultWeapons();
        loadParts();

    }

    private void loadDefaultWeapons() {
        weaponManager = new WeaponManager();
        weapons = new ArrayList<>();
        unlockedWeapons = new ArrayList<>();
        weapons.add(weaponManager.getWeapon(147));
        currentWeapon = weapons.get(0);
    }

    public void loadParts() {
        HashMap<Integer, BufferedImage> images = ImageLoader.loadImages("Player", TOC.PLAYER_IMAGES);
        int[] heads = new int[]{115};
        int[] legs = new int[]{106, 241, 243, 245, 247, 249, 251, 253, 255};
        head = new BufferedImage[2][heads.length];
        leg = new BufferedImage[2][legs.length];
        for (int i = 0; i < heads.length; i++) head[0][i] = images.get(heads[i]);
        for (int i = 0; i < legs.length; i++) leg[0][i] = images.get(legs[i]);
        for (int i = 0; i < heads.length; i++) head[1][i] = ImgFix.invertImage(images.get(heads[i]));
        for (int i = 0; i < legs.length; i++) leg[1][i] = ImgFix.invertImage(images.get(legs[i]));
    }

    public void draw(Graphics g, Camera camera) {
        int offset = crouch ? 15*SCALE : 0;
        int dir = lookLeft ? 1 : 0;
        g.drawImage(leg[dir][(int) runIndex], x - camera.x, y - camera.y - offset, null);
        g.drawImage(head[dir][0], x - camera.x, y - camera.y, null);
        if (lookLeft)
            drawGunLeft(g, camera);
        else drawGunRight(g, camera);
    }

    private void drawGunRight(Graphics g, Camera camera) {
        Graphics2D g2d = (Graphics2D) g;
        Point rPoint = currentWeapon.getRotationPoint();
        int l = currentWeapon.getX() - currentWeapon.getRotationPoint().x;
        g2d.translate(x - camera.x + 12 * SCALE - rPoint.x, y - camera.y + 24* SCALE - rPoint.getY());
        g2d.rotate(aimAngle, rPoint.x, rPoint.y);
        g2d.drawImage(weaponManager.get(currentWeapon.getId()), 0, 0, null);
//        g2d.drawOval(-l + rPoint.x, -l + rPoint.y, l * 2, l * 2);
        g2d.rotate(-aimAngle, rPoint.x, rPoint.y);
        g2d.translate(-(x - camera.x + 12* SCALE - rPoint.x), -(y - camera.y + 24* SCALE - rPoint.getY()));
    }

    private void drawGunLeft(Graphics g, Camera camera) {
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage image = weaponManager.get(-currentWeapon.getId());
        Point rPoint = new Point(image.getWidth() - currentWeapon.getRotationPoint().x, currentWeapon.getRotationPoint().y);
        g2d.translate(x - camera.x + 20* SCALE - rPoint.x, y - camera.y + 24* SCALE - rPoint.getY());
        g2d.rotate(aimAngle, rPoint.x, rPoint.y);
        g2d.drawImage(image, 0, 0, null);
        g2d.rotate(-aimAngle, rPoint.x, rPoint.y);
        g2d.translate(-(x - camera.x + 20* SCALE - rPoint.x), -(y - camera.y + 24* SCALE - rPoint.getY()));
    }

    public void update() {

        if (jump) {
            if (onGround) {
                if (hyperJump) {
                    if (hyperJumpTimer == 0) {
                        //jump
                        hyperJumpTimer = 200;
                        dy = -12* SCALE;
                        onGround = false;
                    }
                } else {
                    dy = -6* SCALE;
                    onGround = false;
                }
                doubleJump = true;
            } else if (doubleJump) {
                dy = -6* SCALE;
                doubleJump = false;
            }
            jump = false;
        }


        if (running) {
            runIndex += 0.3;
            if (runIndex >= leg[0].length) {
                runIndex = 1;
            }
        }

        if (l) {
            x -= speed;
            running = true;
        } else if (r) {
            x += speed;
            running = true;
        } else {
            running = false;
            runIndex = 0;
        }

        y += dy;
        dy += GRAVITY;

        if (hyperJumpTimer > 0) {
            hyperJumpTimer--;
        }

    }

    public void moveLeft(boolean l) {
        this.l = l;
    }

    public void moveRight(boolean r) {
        this.r = r;
    }

    public void jump() {
        jump = true;
    }

    public void crouch(boolean b) {
        this.height = b ? 30* SCALE : 50* SCALE;
        this.crouch = b;
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
            xdist = Math.abs(this.x + width - b.getX());
        } else {
            xdist = Math.abs(this.x - (b.getX() + BLOCK_SIZE));
        }
        if (xdist < ydist) {
            if (this.x < b.getX())
                x = b.getX() - width;
            else
                x = b.getX() + BLOCK_SIZE;
        } else {
            if (this.y < b.getY()) {
                onGround = true;
                dy = 0;
                y = b.getY() - height;
            } else {
                dy = 0;
                this.y = b.getY() + BLOCK_SIZE;
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }


    public void setHyperJump(boolean hyperJump) {
        this.hyperJump = hyperJump;
    }

    public void swapWeapon(int i) {
        weaponIndex += i;
        if (weaponIndex >= weapons.size()) {
            weaponIndex = 0;
        } else if (weaponIndex < 0) {
            weaponIndex = weapons.size() - 1;
        }
        currentWeapon = weapons.get(weaponIndex);
        cooldown = 0;
    }

    public void scroll(int i) {
        swapWeapon(i);
    }


    public void setMouseAngle(int mouseX, int mouseY, Camera camera) {

        double xDist = (x + 12* SCALE - camera.x - mouseX);
        double yDist = (y + 24* SCALE - camera.y - mouseY);

        float rotate = (float) Math.atan(yDist / xDist);
//        if(xDist<0 && lookLeft){
//            lookLeft=false;
//            x+=12* SCALE;
//        } else if (xDist>=0 &&!lookLeft) {
//            lookLeft = true;
//            x-=12* SCALE;
//        }
        lookLeft = xDist >= 0;
        this.aimAngle = rotate;
    }

    public double getAimAngle() {
        if (lookLeft)
            return (aimAngle + Math.PI);
        return aimAngle;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public boolean isFiring() {
        return firing;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    public int getCooldown() {
        return cooldown;
    }


    public int getHealth() {
        return health;
    }

    public void hurt(int damage) {
        this.health -= damage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void reload() {
        cooldown = currentWeapon.getReload();
        currentWeapon.removeAmmo();
        if (currentWeapon.isEmpty()) {
            weapons.remove(weaponIndex);
            swapWeapon(1);
            firing = false;
        }

    }

    public void incrementCooldown() {
        cooldown--;
    }

    public void pickUpWeapon(int id) {
        Weapon weapon = weaponManager.getWeaponFromAmmoBox(id);
        if (weapons.contains(weapon)) {
            int index = weapons.indexOf(weapon);
            weapons.get(index).ammo += weapon.clipSize;
        } else {
            weapon.ammo = weapon.clipSize;
            weapons.add(weapon);
        }
    }

    public void addWeaponUnlock(int[] arr) {
        unlockedWeapons = new ArrayList<>();
        for (int i : arr) {
            unlockedWeapons.add(weaponManager.getWeapon(i));
        }
    }

    public void addWeapon(int id) {
        Weapon weapon = weaponManager.getWeapon(id);
        if (!weapons.contains(weapon)) {
            weapons.add(weapon);
        }
    }

    public ArrayList<Weapon> getUnlockedWeapons() {
        return unlockedWeapons;
    }

    public void setWeapons(ArrayList<Weapon> arr) {
        weapons = arr;
    }

    public int getMaxZone() {
        return maxZone;
    }

    public int getMaxSector() {
        return maxSector;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public int getHyperJumpTimer() {
        return hyperJumpTimer;
    }

    public void heal() {
        this.health = maxHealth;
    }

    public void setCurrentWeapon() {
        currentWeapon = weapons.get(0);
    }

    public boolean isLookLeft() {
        return lookLeft;
    }
}


