import java.util.ArrayList;
import java.util.Arrays;

public class HelperMethods {

    private static boolean doDebugMessages = false;
    public static void main(String[] args) {
        // int[][] shape = { { 1, 1 }, { 1, 1 } };
        // int[][] mapArray = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 2, 0 }, { 0, 2,
        // 2, 0 } };
        // ArrayList<int[][]> list = generatePossibleMovesList(shape, mapArray);
        // System.out.println("Printing list...");
        // printArrayListUsingLetters(list);
    }

    // region Print
    public static void printArrayListUsingLetters(ArrayList<FutureMapState> list) {
        System.out.println("Array list: ");
        for (int i = 0; i < list.size(); i++) {
            printMatrixContentsInChatUsingLetters(list.get(i).mapState);
        }
    }

    public static void printMapStatesUsingLetters(ArrayList<FutureMapState> list) {
        System.out.println("Array list: ");
        for (int i = 0; i < list.size(); i++) {
            printMatrixContentsInChatUsingLetters(list.get(i).mapState);
        }
    }

    public static void printMatrixContentsInChatUsingLetters(int[][] matrixToPrint) {
        System.out.println("Printing new Matrix");
        String[][] stringArray = getLettersForMatrix(matrixToPrint);

        for (int i = 0; i < stringArray.length; i++) {
            for (int j = 0; j < stringArray[i].length; j++) {
                System.out.print(stringArray[i][j] + " ");
                ;
            }
            System.out.println("");
        }
    }

    public static void printMatrixContentsInChat(float[][] matrixToPrint) {
        System.out.println("Printing new Matrix");
        for (int i = 0; i < matrixToPrint.length; i++) {
            for (int j = 0; j < matrixToPrint[i].length; j++) {
                System.out.print(matrixToPrint[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void printArrayContentsInChat(double[] matrixToPrint) {
        System.out.println("Printing new Matrix");
        for (int i = 0; i < matrixToPrint.length; i++) {
            System.out.print(matrixToPrint[i] + " ");
        }
        System.out.println("");
    }

    // endregion

    // region Generate Possible Moves
    public static ArrayList<FutureMapState> generatePossibleMovesList(int[][] shape, int[][] mapArray, double[] weights) {
        ArrayList<FutureMapState> returnArrayList = new ArrayList<>();
        for (int rotations = 0; rotations < 4; rotations++) {

            int[][] rotatedShape = rotateShapeRight(shape, rotations);
            int xPositions = mapArray[0].length - rotatedShape[0].length;
            for (int x = 0; x <= xPositions; x++) {
                FutureMapState fMState = new FutureMapState();
                fMState.deltaXPosition = x;
                fMState.rotateTimes = rotations;
                int[][] shapeDroppedOnMap = getShapeDroppedOnMap(rotatedShape, x, mapArray);
                if (shapeDroppedOnMap != null) {
                    fMState.setMapState(shapeDroppedOnMap, weights);
                    returnArrayList.add(fMState);
                }
            }
        }
        return getWithoutRepeatedElements(returnArrayList);
    }

    /**
     * Returns the map with a shape placed on it, as if it the player pressed space.
     * 
     * @param shape
     * @param mapArray
     * @param xPosition - the position of the top left corner of the shape
     * @return
     */
    public static int[][] getShapeDroppedOnMap(int[][] shape, int xPosition, int[][] mapArray) {
        int yPositions = mapArray.length - shape.length;
        int[][] previousShapeOnMap = null;
        for (int i = 0; i <= yPositions; i++) {
            int[][] emptyMap = getEmptyMatrix(mapArray[0].length, mapArray.length);
            int[][] shapeOnEmptyGrid = placeShapeOnMatrix(emptyMap, shape, xPosition, i);

            if (!areTwoFiguresOverlapping(shapeOnEmptyGrid, mapArray)) {
                int[][] shapeOnMap = placeShapeOnMatrix(mapArray, shape, xPosition, i);
                previousShapeOnMap = shapeOnMap;
            } else {
                return previousShapeOnMap;
            }
        }
        return previousShapeOnMap;
    }

    public static int getMovesToDropOnMap(int[][] shape, int xPosition, int[][] mapArray) {
        int yPositions = mapArray.length - shape.length;
        for (int i = 0; i <= yPositions; i++) {
            int[][] emptyMap = getEmptyMatrix(mapArray[0].length, mapArray.length);
            int[][] shapeOnEmptyGrid = placeShapeOnMatrix(emptyMap, shape, xPosition, i);

            if (areTwoFiguresOverlapping(shapeOnEmptyGrid, mapArray)) {
                return i;
            }
        }
        return yPositions;
    }

    public static ArrayList<FutureMapState> getWithoutRepeatedElements(ArrayList<FutureMapState> mapStates) {
        ArrayList<FutureMapState> returnArrayList = new ArrayList<>();
        for (int i = mapStates.size() - 1; i >= 0; i--) {
            if (!ArrayListContainsMatrix(returnArrayList, mapStates.get(i).mapState)) {
                returnArrayList.add(mapStates.get(i));
            }
        }
        return returnArrayList;
    }
    // endregion

    // region Accessor Methods
    public static int[][] getACopyOfThisMatrix(int[][] matrixToCopy) {
        int[][] returnArray = new int[matrixToCopy.length][matrixToCopy[0].length];
        for (int i = 0; i < returnArray.length; i++) {
            for (int j = 0; j < returnArray[0].length; j++) {
                returnArray[i][j] = matrixToCopy[i][j];
            }
        }
        return returnArray;
    }

    public static double[] getACopyOfThisArray(double[] arrayToCopy) {
        double[] returnArray = new double[arrayToCopy.length];
            for (int j = 0; j < returnArray.length; j++) {
                returnArray[j] = arrayToCopy[j];
            }
        return returnArray;
    }


    public static int[][] getEmptyMatrix(int xMapSize, int yMapSize) {
        return new int[yMapSize][xMapSize];
    }

    private static String[][] getLettersForMatrix(int[][] matrixToPrint) {

        String[][] letterMatrix;
        if (isThisMatrixRectangular(matrixToPrint)) {

            letterMatrix = new String[matrixToPrint.length][matrixToPrint[0].length];

            for (int i = 0; i < matrixToPrint.length; i++) {
                for (int j = 0; j < matrixToPrint[0].length; j++) {
                    letterMatrix[i][j] = turnNumberToLetter(matrixToPrint[i][j]);
                }
            }

        } else {
            System.out.println("Output matrix is not rectangular - this should not be the case");
            return null;
        }
        return letterMatrix;
    }

    private static String turnNumberToLetter(int number) {
        if (number == 1) {
            return "P";
        }
        if (number == 2) {
            return "X";
        }
        if (number == 3) {
            return "F";
        }
        if (number == 4) {
            return "V";
        }
        if (number == 5) {
            return "W";
        }
        if (number == 6) {
            return "Y";
        }
        if (number == 7) {
            return "I";
        }
        if (number == 8) {
            return "T";
        }
        if (number == 9) {
            return "Z";
        }
        if (number == 10) {
            return "U";
        }
        if (number == 11) {
            return "N";
        }
        if (number == 12) {
            return "L";
        }
        // System.out.println("This letter output should not be null");
        return "0";
    }
    // endregion

    // region Modify Matrixes
    public static int[][] placeShapeOnMatrix(int mapArray[][], int shape[][], int xPosition, int yPosition) {
        int[][] mapStateArray = HelperMethods.getACopyOfThisMatrix(mapArray);

        int shapeHeight = shape.length;
        int shapeLength = shape[0].length;

        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeLength; j++) {
                if (shape[i][j] != 0) {
                    mapStateArray[i + yPosition][j + xPosition] = shape[i][j];
                }
            }
        }

        return mapStateArray;
    }
    public static int[] highScoreOrdered(ArrayList<Integer> list) {
        int[] sortedArray = new int[list.size()];

        for (int i = 0; i < list.size() ; i++) {
            sortedArray[i] = (int) list.get(i);
        }

        Arrays.sort(sortedArray);
        System.out.println(Arrays.toString(sortedArray));

        int[] returnArray = {0,0,0,0,0};

        for (int i = 0 ; i < 5 ; i++) {
            if (i < sortedArray.length) {
                returnArray[i] = sortedArray[sortedArray.length-i-1];
            }            
        }

        System.out.println(Arrays.toString(returnArray));
            
        return returnArray;
    }

    public static int[][] rotateShapeRight(int shape[][], int times) {

        int[][] rotatedShape = getACopyOfThisMatrix(shape);
        if (times > 0) {
            int originalShapeHeight = shape.length;
            int originalShapeLength = shape[0].length;

            rotatedShape = new int[originalShapeLength][originalShapeHeight];

            int rotatedShapeLength = rotatedShape[0].length;

            for (int i = 0; i < originalShapeHeight; i++) {
                for (int j = 0; j < originalShapeLength; j++) {
                    rotatedShape[j][rotatedShapeLength - (i + 1)] = shape[i][j];
                }
            }
            rotatedShape = rotateShapeRight(rotatedShape, times - 1);
            return rotatedShape;

        } else {
            return rotatedShape;
        }
    }
    // endregion

    // region Map Checks
    public static boolean isThisMatrixRectangular(int[][] inputMatrix) {
        int horizontalMatrixSize = inputMatrix[0].length;
        // Let's give this matrix some benefit of a doubt
        boolean isMatrixRectangular = true;

        for (int i = 0; i < inputMatrix.length; i++) {
            if (inputMatrix[i].length != horizontalMatrixSize) {
                isMatrixRectangular = false;
            }

        }

        return isMatrixRectangular;
    }

    public static boolean areTwoFiguresOverlapping(int[][] firstArray, int[][] secondArray) {
        // Limit search to not go out of the smaller array
        int ySize = firstArray.length;
        if (secondArray.length < ySize) {
            ySize = secondArray.length;
        }

        int xSize = firstArray[0].length;
        if (secondArray[0].length < xSize) {
            xSize = secondArray[0].length;
        }

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {

                boolean areOverlapping = (firstArray[i][j] != 0 && secondArray[i][j] != 0);
                if (areOverlapping) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean willThisShapeFitOnMap(int mapArray[][], int shape[][], int xPosition, int yPosition) {
        boolean willFit = true;

        int maxYPosition = shape.length + yPosition;
        boolean isYInBounds = (maxYPosition > mapArray.length) || yPosition < 0;
        if (isYInBounds) {
            willFit = false;
        }
        int maxXPosition = shape[0].length + xPosition;
        boolean isXInBounds = (maxXPosition > mapArray[0].length) || xPosition < 0;
        if (isXInBounds) {
            willFit = false;
        }
        return willFit;
    }

    private static boolean ArrayListContainsMatrix(ArrayList<FutureMapState> arrayList, int[][] arrayToCheck) {
        boolean newArrayContainsThisElement = false;
        for (int j = 0; j < arrayList.size(); j++) {

            newArrayContainsThisElement = AreTheseMatrixesTheSame(arrayList.get(j).mapState, arrayToCheck);
            if (newArrayContainsThisElement) {
                break;
            }

        }
        return newArrayContainsThisElement;
    }

    private static boolean AreTheseMatrixesTheSame(int[][] firstArray, int[][] secondArray) {
        // Limit search to not go out of the smaller array
        if (secondArray.length != firstArray.length) {
            return false;
        }
        if (secondArray[0].length != firstArray[0].length) {
            return false;
        }

        int ySize = firstArray.length;
        int xSize = firstArray[0].length;
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {

                boolean areBothNotZero = ((firstArray[i][j] != 0) && (secondArray[i][j] != 0));
                boolean areBothZero = ((firstArray[i][j] == 0) && (secondArray[i][j] == 0));
                if (!(areBothNotZero || areBothZero)) {
                    return false;
                }
            }
        }
        return true;
    }
    // endregion

    // region Delete Rows
    public static int[][] deleteRows(int[][] input, boolean modifyScore) {
        int rowDeletedAmount = 0;
        ArrayList<Integer> saveRowIndexes = new ArrayList<Integer>();

        for (int index = 0; index < input.length; index++) {
            if (isRowFull(input, index) == false) {
                saveRowIndexes.add(index);
            } else {
                rowDeletedAmount++;
            }
        }
        int counter = 0;
        int[][] returnMatrix = new int[input.length][input[0].length];
        for (int index = saveRowIndexes.size() - 1; index >= 0; index--) {

            pasteRow(returnMatrix.length - counter - 1, returnMatrix, input, saveRowIndexes.get(index));
            counter++;
        }
        if (modifyScore) {
            Pentis.score += rowDeletedAmount;
        }
        return returnMatrix;
    }

    public static int[][] botDeleteRows(int[][] input) {
        int rowDeletedAmount = 0;
        ArrayList<Integer> saveRowIndexes = new ArrayList<Integer>();

        for (int index = 0; index < input.length; index++) {
            if (isRowFull(input, index) == false) {
                saveRowIndexes.add(index);
            } else {
                rowDeletedAmount++;
            }
        }
        int counter = 0;
        int[][] returnMatrix = new int[input.length][input[0].length];
        for (int index = saveRowIndexes.size() - 1; index >= 0; index--) {

            pasteRow(returnMatrix.length - counter - 1, returnMatrix, input, saveRowIndexes.get(index));
            counter++;
        }
        BotPentis.score += rowDeletedAmount;
        if (doDebugMessages) {
            System.out.println("Score increased by: " + rowDeletedAmount);
        }
        return returnMatrix;
    }
    /**
     * 
     * @param input
     * @return returns the number of rows deleted
     */
    public static int getDeletedRows(int[][] input) {
        int rowDeletedAmount = 0;
        ArrayList<Integer> saveRowIndexes = new ArrayList<Integer>();

        for (int index = 0; index < input.length; index++) {
            if (isRowFull(input, index) == false) {
                saveRowIndexes.add(index);
            } else {
                rowDeletedAmount++;
            }
        }
        int counter = 0;
        int[][] returnMatrix = new int[input.length][input[0].length];
        for (int index = saveRowIndexes.size() - 1; index >= 0; index--) {

            pasteRow(returnMatrix.length - counter - 1, returnMatrix, input, saveRowIndexes.get(index));
            counter++;
        }
        return rowDeletedAmount;
    }

    private static void pasteRow(int returnIndex, int[][] returnMatrix, int[][] inputMatrix, int inputIndex) {
        for (int i = 0; i < inputMatrix[0].length; i++) {

            returnMatrix[returnIndex][i] = inputMatrix[inputIndex][i];
        }
    }

    private static boolean isRowFull(int[][] input, int index) {
        for (int i = 0; i < input[0].length; i++) {
            if (input[index][i] == 0) {
                return false;
            }
        }
        return true;
    }
    // endregion

}
