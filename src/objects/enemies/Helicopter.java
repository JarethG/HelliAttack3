package objects.enemies;

public class Helicopter extends base {

    public Helicopter() {
        super( 649);
        setStartHealth(300);
        this.speed = 7;
        setBounds(101, 60);
        setGunPosition(50,45);
    }
}
