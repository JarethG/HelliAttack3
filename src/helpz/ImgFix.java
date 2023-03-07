package helpz;

import managers.LevelManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgFix {

    public static BufferedImage clip(BufferedImage b) {
        int w = b.getWidth();
        int h = b.getHeight();

        BufferedImage newImg = new BufferedImage(w-2,h-2,b.getType());
        Graphics2D g2d = newImg.createGraphics();
        g2d.drawImage(b,-1,-1,null);
        g2d.dispose();
        return newImg;
    }

   public static BufferedImage getRotatedImage(BufferedImage img,int rotationAngle) {
       int w = img.getWidth();
       int h = img.getHeight();

       BufferedImage newImg = new BufferedImage(w,h,img.getType());
       Graphics2D g2d = newImg.createGraphics();
       g2d.rotate(Math.toRadians(rotationAngle),w/2,h/2);
       g2d.drawImage(img,0,0,null);
       g2d.dispose();
       return newImg;
   }

   public static BufferedImage buildImage(BufferedImage[] imgs) {
       int w = imgs[0].getWidth();
       int h = imgs[0].getHeight();

       BufferedImage newImg = new BufferedImage(w,h,imgs[0].getType());
       Graphics2D g2d = newImg.createGraphics();

       for(BufferedImage img : imgs){
           g2d.drawImage(img,0,0,null);
       }
       g2d.dispose();
       return newImg;
   }

   public static BufferedImage getBuildRotateImage(BufferedImage[] imgs,int rotationAngle,int rotateIndex){
       int w = imgs[0].getWidth();
       int h = imgs[0].getHeight();

       BufferedImage newImg = new BufferedImage(w,h,imgs[0].getType());
       Graphics2D g2d = newImg.createGraphics();

       for(int i = 0; i < imgs.length; i++){
           if(rotateIndex == i)
               g2d.rotate(Math.toRadians(rotationAngle),w/2,h/2);
           g2d.drawImage(imgs[i],0,0,null);
           if(rotateIndex == i)
               g2d.rotate(Math.toRadians(-rotationAngle),w/2,h/2);
       }
       g2d.dispose();
       return newImg;
   }
    public static BufferedImage[] getBuildRotateAnimationImage(BufferedImage[] imgs,BufferedImage secondImage,int rotationAngle){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        BufferedImage[] arr = new BufferedImage[imgs.length];

        for(int i = 0; i < imgs.length;i++) {
            BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
            Graphics2D g2d = newImg.createGraphics();

            g2d.drawImage(imgs[i],0,0,null);
            g2d.rotate(Math.toRadians(rotationAngle),w/2,h/2);
            g2d.drawImage(secondImage,0,0,null);
            g2d.dispose();
            arr[i] = newImg;
        }


        return arr;
    }

    public static BufferedImage[] makeWalls(BufferedImage image) {

       BufferedImage[] walls = new BufferedImage[4];
       walls[0] = image;

        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage left = new BufferedImage(w,w,image.getType());

        Graphics2D g2d = left.createGraphics();
        g2d.rotate(Math.toRadians(90),0,h/2);
        g2d.drawImage(image,-8,-w+h/2,null);
        walls[1] = left;

        BufferedImage bottom = new BufferedImage(w,w,image.getType());

        g2d = bottom.createGraphics();
        g2d.rotate(Math.toRadians(180),w/2,h/2);
        g2d.drawImage(image,0,-w+h,null);
        walls[2] = bottom;

        BufferedImage right = new BufferedImage(w,w,image.getType());
        g2d = right.createGraphics();
        g2d.rotate(Math.toRadians(-90),0,h/2);
        g2d.drawImage(image,-34,4,null);
        walls[3] = right;



        g2d.dispose();
        return walls;
    }

    public static BufferedImage invertImage(BufferedImage bufferedImage) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage invert = new BufferedImage(w,h,bufferedImage.getType());
        Graphics2D g2d = invert.createGraphics();
        g2d.drawImage(bufferedImage, w, 0, -w, h, null);
//        g2d.drawImage(bufferedImage, w, h, -w, h, null);
        g2d.dispose();
        return invert;
    }

    public static void clip(LevelManager levelManager) {
        for(Integer i : levelManager.getBlocks().keySet()){
            try {
                BufferedImage newImage = ImgFix.clip(levelManager.getBlock(i));
                File outputfile = new File("./res/redefined/" +i+".png");
                ImageIO.write(newImage, "png", outputfile);
            } catch (IOException e) {

            }
        }
    }
}