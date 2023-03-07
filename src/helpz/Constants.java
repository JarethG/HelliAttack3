package helpz;

import java.awt.*;

import static helpz.Constants.Dimensions.SCALE;

public class Constants {

    public static class Dimensions{
        public static final int SCALE = 2;
        public static final int Y_BLOCK_COUNT = 10;//15
        public static final int X_BLOCK_COUNT = 14;//20
        public static final int BLOCK_SIZE = 32 * SCALE;
        public static final int CAMERA_WIDTH = X_BLOCK_COUNT*BLOCK_SIZE;;
        public static final int CAMERA_HEIGHT = Y_BLOCK_COUNT*BLOCK_SIZE;
        public static final int SCREEN_WIDTH = CAMERA_WIDTH;
        public static final int SCREEN_HEIGHT = CAMERA_HEIGHT;
    };

    public static class names{
        public static String LEVEL_NAME = "3.4 ";
    }

    public static class effects{
        public static final double GRAVITY = 0.25*SCALE;
    }
}
