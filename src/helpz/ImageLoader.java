package helpz;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static helpz.Constants.Dimensions.SCALE;

public class ImageLoader {


    public static HashMap<Integer, BufferedImage> loadJarImages(String pathName,int[] imageNames) {
        HashMap<Integer, BufferedImage> images = new HashMap<>();
        try {
        for(int i : imageNames){
            InputStream in = ImageLoader.class.getClassLoader().getResourceAsStream(pathName +"/" + i +".png");
            BufferedImage bi = ImageIO.read(in);
            if(SCALE!=1){
                bi = scale(bi);
            }
            images.put(i,bi);
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return images;
}
    public static BufferedImage scale(BufferedImage image) {
        BufferedImage scaled = new BufferedImage(image.getWidth()*SCALE,image.getHeight()*SCALE,image.getType());
        Graphics2D g2d = scaled.createGraphics();
        g2d.drawImage(image, 0, 0, image.getWidth()*SCALE, image.getHeight()*SCALE, null);
        g2d.dispose();
        return scaled;
    }

    public static HashMap<Integer,BufferedImage> loadImages(String pathName,int[] imageNames){
        return loadJarImages(pathName,imageNames);
//        HashMap<Integer, BufferedImage> images = new HashMap<>();
//        Path path = null;//path to directory
//        try {
//            path = Paths.get(ImageLoader.class.getClassLoader().getResource(pathName).toURI());
//            List<Path> paths = findByFileExtension(path, ".png");//list of paths to images in directory
//            for(Path p : paths) {
//                BufferedImage image = ImageIO.read(new File(p.toString()));
//                String imageName = removeExtension(p.getFileName().toString(), ".png");
//                System.out.println("image name : " + imageName);
//                images.put(Integer.parseInt(imageName), image);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return images;
    }

    public BufferedImage[] loadSprite(int size, int width, int height, String path) {

        BufferedImage[] sprite = new BufferedImage[size];

        try {
            BufferedImage spriteSheet = ImageIO.read(new File(path));
            for(int i = 0; i < size; i++) {
                sprite[i] = spriteSheet.getSubimage(i*width,0,width,height);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static List<Path> findByFileExtension(Path path, String fileExtension){

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path : " + path + " is not a directory path");
        }

        List<Path> result = null;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(Files::isRegularFile)   // is a file
                    .filter(p -> p.getFileName().toString().endsWith(fileExtension))
                    .collect(Collectors.toList());
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

    public static String removeExtension(String string, String extension){
        return string.substring(0,string.lastIndexOf(extension));
    }
}
