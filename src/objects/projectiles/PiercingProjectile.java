package objects.projectiles;

import objects.enemies.Enemy;

public class PiercingProjectile extends DirectionalProjectile {

    double initXSpeed, initYSpeed;
    boolean railgunHit = false;
    public PiercingProjectile(int x, int y, double speed, int damage, double rotation, int projectileType) {
        super(x, y, speed, damage, rotation,projectileType,false);
        if(projectileType == 361){
            this.initXSpeed = xSpeed;
            this.initYSpeed = ySpeed;
        } else if(projectileType == 343) {
            this.initXSpeed = xSpeed;
            this.initYSpeed = ySpeed;
            this.life = 100;
            this.xSpeed = 0;
            this.ySpeed = 0;
        }
    }

    public void update(){
        if(projectileType == 361){
            xSpeed -= initXSpeed*0.03;
            ySpeed -= initYSpeed*0.03;}
        super.update();

    }

    @Override
    public void checkHit(Enemy e) {
         if(projectileType == 343 ) {
                 if (!railgunHit) {
                 for (int i = 0; i < 200; i+=2) {
                     if (e.getBounds().contains(x + i * initXSpeed, y + i * initYSpeed)) {
                         e.hurt(getDamage());
                         railgunHit = true;
                     }
                 }
                 railgunHit=true;
             }
         }else if (isHitting(e)) {
            e.hurt(getDamage());
        }
    }
}
