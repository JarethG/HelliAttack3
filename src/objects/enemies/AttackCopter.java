package objects.enemies;

public class AttackCopter extends base{
    //rockets do 3 damage
    public AttackCopter() {
        super( 1093);
        this.speed = 5;
        setStartHealth(600);
        setBounds(203,93);
        setGunPosition(170,80);
    }
}