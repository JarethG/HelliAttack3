package managers;

import helpz.ImageLoader;
import helpz.ImgFix;
import helpz.TOC;
import objects.Weapon;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class WeaponManager {

    HashMap<Integer, BufferedImage> images = ImageLoader.loadImages("Player", TOC.PLAYER_IMAGES);

    HashMap<Integer, Weapon> weapons = new HashMap<>();
    HashMap<Integer, Weapon> ammoBoxes = new HashMap<>();

    public WeaponManager() {
        initWeapons();
        reverseWeapons();
    }

    private void reverseWeapons() {
        for(int i : weapons.keySet()){
            BufferedImage image = ImgFix.invertImage(images.get(i));
            images.put(i*-1,image);
        }
    }

    public BufferedImage get(int id) {
        return images.get(id);
    }

    public Weapon getWeapon(int id) {
        return weapons.get(id);
    }

    public Weapon getWeaponFromAmmoBox(int id) {
        return ammoBoxes.get(id);
    }

    public HashMap<Integer, Weapon> getWeapons() {
        return weapons;
    }

    public void initWeapons() {
        Weapon[] ws = new Weapon[]{bowAndArrow, grenade, pistol, machineGun, chainGun, shotgun, doubleShotgun, sniperRifle, grenadeLauncher, rpg, rocketLauncher, seekerLauncher, drunkenLauncher, guidedLauncher, flakCannon, shotgunRockets, flameThrower, sparkPlug, gooGun, bladerang, laserRifle, autoLaserRifle, shotgunLasers, railGun, anyTime, airStrike, taser, soundWave, aBombLauncher, blackHoleGenerator};
        for (Weapon w : ws) {
            weapons.put(w.getId(), w);
        }
        for (Weapon w : ws) {
            ammoBoxes.put(w.getAmmoBox(), w);
        }
    }

    public final Weapon pistol = new Weapon(33,3,5,6, 147, 325, 403,1000,7, 0.625,5);
    public final Weapon grenade = new Weapon(0, 0,12,23, 142, 327,401,25, 50, 0.625,5);
    public final Weapon bowAndArrow = new Weapon(26, 26,5,27, 195, 196,441,100, 30, 0.625,8);
    public final Weapon machineGun = new Weapon(44, 6,6,5, 149,  325,405,150, 10, 0.15625,5);
    public final Weapon chainGun = new Weapon(57,10,5,7, 151, 325, 411,1000,10, 0.0625,5);
    public final Weapon shotgun = new Weapon(44, 6,6,4, 153, 325,409,20, 15, 0.78125,5);
    public final Weapon doubleShotgun = new Weapon(45, 9,7,9, 158,  325,411,15, 10, 1.09375,5);
    public final Weapon sniperRifle = new Weapon(63, 8, 5,12,202,325,443,12, 75, .625,20);
    public final Weapon rocketLauncher = new Weapon(58, 8,16,18, 163,  335,413,11, 100, 1.25,3);
    public final Weapon grenadeLauncher = new Weapon(43,10,4,10, 174,  337,413,15, 75, .9375,8);
    public final Weapon rpg = new Weapon(55, 15,4,14, 176,  339,423,15, 85, .78125,1);
    public final Weapon seekerLauncher = new Weapon(58,11,16,21, 182, 329,429,7, 100, 1.5625,3);
    public final Weapon drunkenLauncher = new Weapon(63,8,18,20, 184, 335,431,6,50,1.875,2);
    public final Weapon guidedLauncher = new Weapon(58, 12, 16,21,187,333,433,9,100,1.5625,3);
    public final Weapon shotgunRockets = new Weapon(45,8,7,9, 169,345,417,7,25,1.25,3);
    public final Weapon railGun = new Weapon(56,12,8,10, 180, 343,427,6,200,2.34375,1);
    public final Weapon flakCannon = new Weapon(42, 9,4,10, 215,352,453,8,10,1.5625,16);
    public final Weapon flameThrower = new Weapon(55,6,14,4, 165,331,415,150,2,0.0625,5);
    public final Weapon bladerang = new Weapon(32, 10,4,11, 219,361,457,7,20,1.5625,15);
    public final Weapon sparkPlug = new Weapon(45,9,5,8, 189, 347,435,30,30,.46875,5);
    public final Weapon gooGun = new Weapon(36, 15,5,14, 217,359,455,25,50,.3125,5);
    public final Weapon laserRifle = new Weapon(55, 15, 5,12,204,350,445,25,30,0.5,15);
    public final Weapon autoLaserRifle = new Weapon(60, 15,5,14, 206, 350,447,25,20,.25,15);
    public final Weapon shotgunLasers = new Weapon(55, 15,5,16, 208, 350,449,12,20,.25,15);
    public final Weapon anyTime = new Weapon(62, 17,4,17, 221, 354,459,5,130,4.6875,15);
    public final Weapon airStrike = new Weapon(7, 15,5,4, 193, 367,439,4,50,1.25,0);
    public final Weapon taser = new Weapon(44, 10, 5,13,213, 356,451,2,5,6.25,10);
    public final Weapon soundWave = new Weapon(56,19,17,27, 223, 363,461,30,10,0.3125,7);
    public final Weapon aBombLauncher = new Weapon(57,16,12,28, 178, 341,425,3,500,9.375,1);
    public final Weapon blackHoleGenerator = new Weapon(80,20,29,33, 191, 325,437,1,0,18.75,0);


}
