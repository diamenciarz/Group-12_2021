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

    public ShapeContainer() {

    }

    // Shapes as arrays

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