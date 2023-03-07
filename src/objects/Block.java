package objects;

import java.awt.*;

import static helpz.Constants.Dimensions.BLOCK_SIZE;

public class Block {
    private final Rectangle bounds;
    int id;
    int x;
    int y;

    public Block(int id,int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x,y,BLOCK_SIZE,BLOCK_SIZE);
    }

    public int getId() {
        return id;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
