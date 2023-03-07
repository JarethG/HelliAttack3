package managers;

import helpz.ImageLoader;
import helpz.TOC;
import inputs.Camera;
import objects.*;
import objects.enemies.Enemy;
import objects.projectiles.*;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static helpz.Constants.Dimensions.*;

public class ProjectileManager {


    Playing playing;
    HashMap<Integer, BufferedImage> images;
    private ArrayList<Projectile> playerProjectiles = new ArrayList<>();
    private ArrayList<Projectile> enemyProjectiles = new ArrayList<>();

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        images = ImageLoader.loadImages("Ammo", TOC.AMMO_IMAGES);
        images.putAll(ImageLoader.loadImages("explosions", TOC.EXPLOSION_IMAGES));
    }

    public void update() {
        if (playing.isLevelComplete()) {
            return;
        }
        Player player = playing.getPlayer();
        Enemy e = playing.getEnemyManager().getCurrentEnemy();
        if (e == null) {
            for (Projectile p : playerProjectiles.stream().toList()) {
                p.update();
            }
        } else {
            for (Projectile p : playerProjectiles.stream().toList()) {
                p.update();
                p.checkHit(e);
            }
            if (!e.isAlive() && playing.getEnemyManager().getEnemies().size() % 2 == 1) {
                ArrayList<Weapon> w = player.getUnlockedWeapons();
                int ran = (int) (Math.random() * w.size());
                playing.setAmmoBox(new AmmoBox(e.getX(), e.getY(), w.get(ran).ammoBox, 33, 32));
            }
            for (Projectile p : enemyProjectiles.stream().toList()) {
                p.update();
                if (p.isHitting(player)) {
                    p.setActive(false);
                    player.hurt(p.getDamage());
                }
            }
        }
        ArrayList<Projectile> newExplosions = new ArrayList<>();
        playerProjectiles.stream()
                .filter(p -> !p.isActive())
                .forEach(p -> {
                    if (p.isExplosive()) {
                        newExplosions.add(
                                new Explosion(p.getX(), p.getY(), p.getDamage())
                        );
                    }
                });
        playerProjectiles.addAll(newExplosions);
        newExplosions.clear();
        enemyProjectiles.stream()
                .filter(p -> !p.isActive())
                .forEach(p -> {
                    if (p.isExplosive()) {
                        newExplosions.add(
                                new Explosion(p.getX(), p.getY(), p.getDamage())
                        );
                    }
                });
        enemyProjectiles.addAll(newExplosions);

        playerProjectiles.removeIf(p -> !p.isActive());
        enemyProjectiles.removeIf(p -> !p.isActive());
    }

    public void draw(Graphics g) {
        List<Projectile> plist = playerProjectiles.stream().toList();
        List<Projectile> elist = enemyProjectiles.stream().toList();
        for (Projectile p : plist) {
            p.draw(g, images.get(p.getProjectileType()), playing.getCamera());
        }
        for (Projectile p : elist) {
            p.draw(g, images.get(p.getProjectileType()), playing.getCamera());
        }
    }

    public void createNewProjectile(Weapon w, Player p, Camera c) {
        double angle = p.getAimAngle();
        int l = w.getX() - w.getRotationPoint().x;

        int x, y;
        if (!p.isLookLeft()) {
            x = (int) (p.getX() + 12 * SCALE + l * Math.cos(angle + w.getMuzzleArc()));
            y = (int) (p.getY() + 24 * SCALE + l * Math.sin(angle + w.getMuzzleArc()));
        } else {
            x = (int) (p.getX() + 20 * SCALE + l * Math.cos(angle - w.getMuzzleArc()));
            y = (int) (p.getY() + 24 * SCALE + l * Math.sin(angle - w.getMuzzleArc()));
        }


        if (w.id == 215) {//flak
            for (int i = 0; i < 20; i++) {
                double dir = Math.random() * 0.5;
                double s = Math.random() * w.getBulletSpeed();
                playerProjectiles.add(new GravityAffectedProjectile(x, y,
                        s, w.getDamage(), (float) (angle - 0.25 + dir), w.ammoType));
            }
            return;
        }
        if (w.id == 193) {//airStrike
            for (int i = 0; i <= 20; i += 2) {
                playerProjectiles.add(new GravityAffectedProjectile(x - SCREEN_WIDTH / 2 + i * BLOCK_SIZE, c.y - i * BLOCK_SIZE, w.getBulletSpeed(), w.getDamage(), (float) (-Math.PI / 2), w.getAmmoType()));
            }
            return;
        }
        switch (w.id) {
            case 153 -> generateArc(angle, Math.toRadians(30), 5, x, y, w.getBulletSpeed(), w, false);//shotgun
            case 158 -> generateArc(angle, Math.toRadians(40), 9, x, y, w.getBulletSpeed(), w, false);//double shotgun
            case 184, 169 -> generateArc(angle, Math.toRadians(30), 3, x, y, w.getBulletSpeed(), w, true);//drunken
            case 208 -> generateArc(angle, Math.toRadians(30), 3, x, y, w.getBulletSpeed(), w, false);//drunken
            case 202 ->
                    playerProjectiles.add(new Projectile(x, y, w.getBulletSpeed(), w.getDamage(), angle, w.ammoType, false));
            case 176, 163, 182, 187, 178 ->
                    playerProjectiles.add(new DirectionalProjectile(x, y, w.getBulletSpeed(), w.getDamage(), angle, w.ammoType, true));
            case 206, 204 ->
                    playerProjectiles.add(new DirectionalProjectile(x, y, w.getBulletSpeed(), w.getDamage(), angle, w.ammoType, false));
            case 142, 195, 174, 217 ->
                    playerProjectiles.add(new GravityAffectedProjectile(x, y, w.getBulletSpeed(), w.getDamage(), angle, w.ammoType));
            case 165, 219, 180, 221, 223 ->
                    playerProjectiles.add(new PiercingProjectile(x, y, w.getBulletSpeed(), w.getDamage(), angle, w.ammoType));
            default ->
                    playerProjectiles.add(new Projectile(x, y, w.getBulletSpeed(), w.getDamage(), angle, w.getAmmoType(), false));
        }
    }

    public void generateArc(double rotation, double arc, int count, int x, int y, double speed, Weapon weapon, boolean explosive) {
        double start = rotation - arc / 2;
        for (int i = 0; i < count; i++) {
            playerProjectiles.add(new DirectionalProjectile(x, y, speed, weapon.getDamage(), start, weapon.getAmmoType(), explosive));
            start += arc / count;
        }
    }

    public void createNewEnemyProjectile(Enemy e, Player p) {
        int speed = 5;
        double arcValue = Math.toRadians(e.getGunRotation());
        switch (e.getProjectileType()) {
            case 365 -> enemyProjectiles.add(new DirectionalProjectile(e.getX() + e.getGunX(), e.getY() + e.getGunY(),
                    speed, 1, arcValue, e.getProjectileType(), false));
            case 368 -> enemyProjectiles.add(new DirectionalProjectile(e.getX() + e.getGunX(), e.getY() + e.getGunY(),
                    speed, 1, arcValue, e.getProjectileType(), true));
        }
    }

    public BufferedImage getImage(int id) {
        return images.get(id);
    }

    public ArrayList<Projectile> getPlayerProjectiles() {
        return playerProjectiles;
    }

    public ArrayList<Projectile> getEnemyProjectiles() {
        return enemyProjectiles;
    }
}
