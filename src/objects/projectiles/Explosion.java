package objects.projectiles;

import objects.Block;
import objects.enemies.Enemy;

public class Explosion extends Projectile{
    int[] explosions = new int[]{373,375,377,379,381,383,385,387,389,391,393,395};
    int frame = 0;
    int frameTimer = 0;
    public Explosion(int x, int y, int damage) {
        super(x, y, 0, damage, 0, 371, false);
    }

    public void update(){
        if(frame==explosions.length-1){
            setActive(false);
        } else {
               frame++;

        }
        projectileType = explosions[frame];
    }

    @Override
    public void checkHit(Enemy e) {
        if (isHitting(e)) {
            e.hurt(getDamage());
        }
    }

    @Override
    public boolean hitsBlock(Block b) {
        return false;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }
}
