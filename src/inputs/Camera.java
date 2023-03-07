package inputs;

import objects.Block;
import objects.Player;

import java.awt.*;

import static helpz.Constants.Dimensions.*;

public class Camera {
    public int x,y;
    Block[][] level;

    public Camera(int x, int y,Block[][] level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public Point getBlocksInFocus(int playerX, int playerY) {
        int left = Math.max((playerX/BLOCK_SIZE)-X_BLOCK_COUNT/2,0);
        left = Math.min(left,level[0].length-X_BLOCK_COUNT -1);
        int Top = Math.max((playerY/BLOCK_SIZE)-Y_BLOCK_COUNT/2,0);
        Top = Math.min(Top, level.length-Y_BLOCK_COUNT - 1);

        return new Point(left,Top);
    }

    public void snap(Player player) {
        int minX = (int)Math.max(player.getX()-X_BLOCK_COUNT/2*BLOCK_SIZE,0);
        int minY = (int)Math.max(player.getY()-Y_BLOCK_COUNT/2*BLOCK_SIZE,0);
        this.x = Math.min(minX,(level[0].length-X_BLOCK_COUNT)*BLOCK_SIZE);
        this.y = Math.min(minY,(level.length-Y_BLOCK_COUNT)*BLOCK_SIZE);
    }
}
