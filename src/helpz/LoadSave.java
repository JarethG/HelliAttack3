package helpz;

import scenes.Editing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

import static helpz.Constants.Dimensions.X_BLOCK_COUNT;
import static helpz.Constants.Dimensions.Y_BLOCK_COUNT;

public class LoadSave {

    public static int[][] loadLevel(String name) {
        InputStream io = ImageLoader.class.getClassLoader().getResourceAsStream("Levels/" + name + ".txt");
        if(io == null){
            return getDefaultLevel();
        }
            ArrayList<Integer> list = ReadFromFile(io);
            return Utilz.ArraylistTo2Dint(list, list.get(0), list.get(1));

    }

    private static ArrayList ReadFromFile(InputStream io) {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner sc = new Scanner(io);
        while (sc.hasNextLine()) {
            list.add(Integer.parseInt(sc.nextLine()));
        }
        sc.close();
        return list;
    }

    private static ArrayList ReadFromFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                list.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static int[][] getDefaultLevel() {
        SaveLevel("default",new int[Y_BLOCK_COUNT + 1 ][X_BLOCK_COUNT + 1]);
        return loadLevel("default");
    }

    private static void WriteToFile(File f, int[] idArr) {
        try {
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < idArr.length;i++)
                pw.println(idArr[i]);

            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void SaveLevel(String name, int[][] idArray){
        System.out.println("saving level " + name);
//        Utilz.printLevel(idArray);
        File levelFile = new File("res/Levels/" + name + ".txt");
        if (levelFile.exists()) {
           WriteToFile(levelFile,Utilz.Twoto1DArr(idArray));
        } else {
            CreateLevel(name,Utilz.Twoto1DArr(idArray));
            System.out.println("File: " + name + "does not exists");
        }
    }

    public static void CreateLevel(String name, int[] idArr) {
        File newLevel = new File("res/Levels/" + name + ".txt");

        if (newLevel.exists()) {
            System.out.println("File: " + name + "already exists");
        } else {
            try {
                newLevel.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            WriteToFile(newLevel, idArr);
        }
    }



}
