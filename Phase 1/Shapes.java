
public class Shapes {

    public Shapes() {

    }

    // Shapes as arrays
    private static int[][] arrayP = { { 1, 1 }, { 1, 1 }, { 1, 0 } };
    private static int[][] arrayX = { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 1, 0 } };
    private static int[][] arrayF = { { 0, 1, 1 }, { 1, 1, 0 }, { 0, 1, 0 } };
    private static int[][] arrayV = { { 1, 0, 0 }, { 1, 0, 0 }, { 1, 1, 1 } };
    private static int[][] arrayW = { { 1, 0, 0 }, { 1, 1, 0 }, { 0, 1, 1 } };
    private static int[][] arrayY = { { 0, 1 }, { 1, 1 }, { 0, 1 }, { 0, 1 } };
    private static int[][] arrayI = { { 1 }, { 1 }, { 1 }, { 1 }, { 1 } };
    private static int[][] arrayT = { { 1, 1, 1 }, { 0, 1, 0 }, { 0, 1, 0 } };
    private static int[][] arrayZ = { { 1, 1, 0 }, { 0, 1, 0 }, { 0, 1, 1 } };
    private static int[][] arrayU = { { 1, 0, 1 }, { 1, 1, 1 } };
    private static int[][] arrayN = { { 1, 1, 0, 0 }, { 0, 1, 1, 1 } };
    private static int[][] arrayL = { { 0, 0, 0, 1 }, { 1, 1, 1, 1 } };

    // private static String[] blocksArray;

    public int[][] GetShapeArray(String inputShape) {
        if (inputShape.toLowerCase() == "p") {
            return arrayP;
        }
        if (inputShape.toLowerCase() == "x") {
            return arrayX;
        }
        if (inputShape.toLowerCase() == "f") {
            return arrayF;
        }
        if (inputShape.toLowerCase() == "v") {
            return arrayV;
        }
        if (inputShape.toLowerCase() == "w") {
            return arrayW;
        }
        if (inputShape.toLowerCase() == "y") {
            return arrayY;
        }
        if (inputShape.toLowerCase() == "i") {
            return arrayI;
        }
        if (inputShape.toLowerCase() == "t") {
            return arrayT;
        }
        if (inputShape.toLowerCase() == "z") {
            return arrayZ;
        }
        if (inputShape.toLowerCase() == "u") {
            return arrayU;
        }
        if (inputShape.toLowerCase() == "n") {
            return arrayN;
        }
        if (inputShape.toLowerCase() == "l") {
            return arrayL;
        }
        return null;
    }
}