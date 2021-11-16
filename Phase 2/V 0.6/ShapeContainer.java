import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShapeContainer {
    private static int[][] arrayP = { { 1, 1 }, { 1, 1 }, { 1, 0 } };
    private static int[][] arrayX = { { 0, 2, 0 }, { 2, 2, 2 }, { 0, 2, 0 } };
    private static int[][] arrayF = { { 0, 3, 3 }, { 3, 3, 0 }, { 0, 3, 0 } };
    private static int[][] arrayV = { { 4, 0, 0 }, { 4, 0, 0 }, { 4, 4, 4 } };
    private static int[][] arrayW = { { 5, 0, 0 }, { 5, 5, 0 }, { 0, 5, 5 } };
    private static int[][] arrayY = { { 0, 6 }, { 6, 6 }, { 0, 6 }, { 0, 6 } };
    private static int[][] arrayI = { { 7 }, { 7 }, { 7 }, { 7 }, { 7 } };
    private static int[][] arrayT = { { 8, 8, 8 }, { 0, 8, 0 }, { 0, 8, 0 } };
    private static int[][] arrayZ = { { 9, 9, 0 }, { 0, 9, 0 }, { 0, 9, 9 } };
    private static int[][] arrayU = { { 10, 0, 10 }, { 10, 10, 10 } };
    private static int[][] arrayN = { { 11, 11, 0, 0 }, { 0, 11, 11, 11 } };
    private static int[][] arrayL = { { 0, 0, 0, 12 }, { 12, 12, 12, 12 } };

    private static int[][][] shapeArray = {arrayF,arrayI,arrayL,arrayN,arrayP,arrayT,arrayU,arrayV,arrayW,arrayX,arrayY,arrayZ};

    public ShapeContainer() {

    }

    // Shapes as arrays

    // private static String[] blocksArray;

    public static int[][] getShapeArray(int index) {
        if(index <= 11){
            return shapeArray[index];
        }
        else{
            System.out.println("Shape index out of range");
            return null;
        }
    }
    /**
     * Returns a shuffled sequence of shapes from 0 to 11 contained in an array list
     * @return twelveShapes
     */
    public static ArrayList<int[][]> get12ShuffledShapes() {
        // 1 to 12 shuffle in list
        Integer[] queueOfShapes = {0,1,2,3,4,5,6,7,8,9,10,11};
        List<Integer> shapesList = Arrays.asList(queueOfShapes);
        Collections.shuffle(shapesList);

        // creating an array list to store the twelve shapes
        ArrayList<int[][]> twelveShapes = new ArrayList<int[][]>();

        // ShapeContainer object
        ShapeContainer shape = new ShapeContainer();

        // adding each shape to the array list
        for (int i = 0 ; i < 12 ; i++) {
            twelveShapes.add(getShapeArray(shapesList.get(i)));
        }

        return twelveShapes;
    }  
}