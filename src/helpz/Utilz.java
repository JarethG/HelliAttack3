package helpz;

import objects.Block;

import java.util.ArrayList;
import java.util.Arrays;

public class Utilz {


    public static int[][] ArraylistTo2Dint(ArrayList<Integer> list, int ySize, int xSize) {
        int[][] newArr = new int[ySize][xSize];

        for(int j = 0; j < newArr.length;j++){
            for(int i = 0; i < newArr[j].length;i++){
                int index = j*xSize + i + 2;
                newArr[j][i] = list.get(index);
            }
        }
//        System.out.println(Arrays.deepToString(newArr));
        return newArr;
    }

    public static int[] Twoto1DArr(int[][]twoArr){
        int[] oneArr = new int[twoArr.length *twoArr[0].length + 2];
        oneArr[0] = twoArr.length;
        oneArr[1] = twoArr[0].length;

        for(int j = 0; j < twoArr.length;j++){
            for(int i = 0; i < twoArr[j].length;i++){
                int index = j*twoArr[j].length + i + 2;
                oneArr[index] = twoArr[j][i];
            }
        }
        return oneArr;
    }

    public static void printLevel(int[][] level) {
        for(int[] y : level){
            for(int x : y){
                if(x!= 0 )
                    System.out.print("x");
                else
                    System.out.print("0");
            }
            System.out.println();
        }
    }

}
