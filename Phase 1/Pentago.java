import java.util.Scanner; // Import the Scanner class
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pentago {

    // Instances
    static Scanner scanner = new Scanner(System.in);

    // Startup settings variables
    public static boolean enableDebugMessages = false;
    public static boolean allowInputRepetition = false;
    public static int blockLength = 2;

    // Internal variables
    public static int[][] arrayToCheck; // Dont modify this in any method
    public static int[][] currentMapArray;
    public static String[] inputBlocksArray;

    public static void main(String[] args) {

        int[][] shape = { { 0, 1 }, { 0, 5 }, { 7, 5 } };
        int[][] second = { { 0, 0, 0, 0 }, { 5, 5, 5, 0 }, { 0, 0, 5, 5 }, { 0, 5, 0, 0 } };

        int[][] arrayX = { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 1, 0 } };
        int[][] arrayU = { { 1, 0, 1 }, { 1, 1, 1 } };

        int[][][] shapesToFit = { arrayX, arrayU, arrayX };

        // arrayToCheck = second;
        int xPosition = 0;
        int yPosition = 0;
        GenerateMap(5, 3);
        // AskForMapSize();

        int[][] solution = IterateSolution(currentMapArray, shapesToFit, 0);
        printMatrixContentsInChat(solution);

        scanner.close();
    }
    // ----------------- Methods asking for user input

    public static void SaveInputToBlocksArray() {
        String answer = AskForBlocksInput();
        int answerLength = answer.length();

        inputBlocksArray = new String[answerLength];

        for (int i = 0; i < answerLength; i++) {
            inputBlocksArray[i] = answer.substring(i, i + 1);
        }
    }

    public static void AskForMapSize() {
        int xSize;
        int ySize;

        System.out.println("Give me the x map size: ");
        xSize = scanner.nextInt();
        System.out.println("Give me the y map size: ");
        ySize = scanner.nextInt();
        scanner.nextLine();

        GenerateMap(xSize, ySize);
    }
    // ----------------- Input Helper Methods

    // Checks if input is an element of {P,L,F,V,W,Y,I,T,Z,U,N,L}
    public static boolean IsInputCorrect(String inputString) {
        // Create character arrayList
        List<String> c = Arrays.asList("p", "x", "f", "v", "w", "y", "i", "t", "z", "u", "n", "l");
        ArrayList<String> noRepetitionArray = new ArrayList<String>();
        noRepetitionArray.addAll(c);

        for (int i = 0; i < inputString.length(); i++) {

            int charValue = (int) inputString.charAt(i);
            String charAtIndex = inputString.substring(i, i + 1);
            if (charValue >= 97 && charValue <= 122) {
                if (charValue == 102 || charValue == 105 || charValue == 108 || charValue == 110 || charValue == 112
                        || (charValue >= 116 & charValue <= 122)) {
                    if (!allowInputRepetition) {
                        if (noRepetitionArray.contains(charAtIndex)) {
                            noRepetitionArray.remove(charAtIndex);
                        } else {
                            return false;
                        }
                        // Ok then, check other characters
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    // ----------------- Powerful methods
    public static int[][] IterateSolution(int[][] currentMapState, int[][][] shapesToFitArray, int shapeIndex) {
        int shapesToFitAmount = shapesToFitArray.length;

        for (int i = 0; i < shapesToFitAmount; i++) {

            int[][] currentShapeToFit = shapesToFitArray[shapeIndex];
            int currentShapeHeight = currentShapeToFit.length;
            int currentShapeLength = currentShapeToFit[0].length;
            int mapHeight = currentMapState.length;
            int mapLength = currentMapState[0].length;
            int xRepetitions = mapHeight - currentShapeHeight + 1;
            int yRepetitions = mapLength - currentShapeLength + 1;
            for (int j = 0; j < yRepetitions; j++) {

                for (int k = 0; k < xRepetitions; k++) {
                    for (int rotations = 1; rotations <= 4; rotations++) {

                        int[][] rotatedShapeToFit = RotateShapeRight(currentShapeToFit, rotations);
                        if (WillThisShapeFitOnMap(rotatedShapeToFit, i, j)) {

                            int[][] newMapState = PlaceShapeOnMap(rotatedShapeToFit, i, j);
                            boolean hasFoundHoles = CheckMatrixForLinksOfLengthAtMost(newMapState, 4);
                            if (!hasFoundHoles) {

                                int[][] solution = IterateSolution(newMapState, shapesToFitArray, shapeIndex + 1);
                                if (!IsSolutionInvalid(solution)) {

                                    currentMapState = newMapState;
                                }
                            }
                        }
                    }
                }
            }
        }
        return currentMapState;
    }

    public static int[][] PlaceShapeOnMap(int shape[][], int xPosition, int yPosition) {
        int[][] mapStateArray = GetACopyOfThisArray(currentMapArray);

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

    public static int[][] GetACopyOfThisArray(int[][] arrayToCopy) {
        int[][] returnArray = new int[arrayToCopy.length][arrayToCopy[0].length];
        for (int i = 0; i < returnArray.length; i++) {
            for (int j = 0; j < returnArray[0].length; j++) {
                returnArray[i][j] = arrayToCopy[i][j];
            }
        }
        return returnArray;
    }

    public static void GenerateMap(int xMapSize, int yMapSize) {
        currentMapArray = new int[yMapSize][xMapSize];
    }

    public static String AskForBlocksInput() {
        String inputString = "";
        boolean isInputCorrect = false;
        while (!isInputCorrect) {
            inputString = scanner.nextLine();

            if (IsInputCorrect(inputString.toLowerCase())) {
                isInputCorrect = true;
            } else {
                System.out.println("Input incorrect");
            }
        }
        return inputString.toUpperCase();
    }

    // ----------------- Helper Methods
    public static boolean IsSolutionInvalid(int[][] solution) {
        if (allowInputRepetition) {
            solution[0][0] = -1;
            return true;
        } else {
            return false;
        }
    }

    public static int[][] RotateShapeRight(int shape[][], int times) {
        int originalShapeHeight = shape.length;
        int originalShapeLength = shape[0].length;

        int[][] rotatedShape = new int[originalShapeLength][originalShapeHeight];

        int rotatedShapeHeight = rotatedShape.length;
        int rotatedShapeLength = rotatedShape[0].length;

        for (int i = 0; i < originalShapeHeight; i++) {
            for (int j = 0; j < originalShapeLength; j++) {
                rotatedShape[j][rotatedShapeLength - (i + 1)] = shape[i][j];
            }
        }
        times--;
        if (times != 0) {
            rotatedShape = RotateShapeRight(rotatedShape, times);
        }
        return rotatedShape;
    }

    public static boolean WillThisShapeFitOnMap(int shape[][], int xPosition, int yPosition) {
        boolean willFit = true;

        int MaxYPosition = shape.length + yPosition;
        if (MaxYPosition > currentMapArray.length) {
            willFit = false;
        }
        int MaxXPosition = shape[0].length + xPosition;
        if (MaxXPosition > currentMapArray[0].length) {
            willFit = false;
        }
        return willFit;
    }

    // Checks, if two figures have some common tiles
    public static boolean areTwoFiguresOverlapping(int[][] firstArray, int[][] secondArray) {
        // Limit search to not go out of the smaller array
        int ySize = firstArray.length;
        if (secondArray.length < ySize) {
            ySize = secondArray[0].length;
        }

        int xSize = firstArray[0].length;
        if (secondArray[0].length < xSize) {
            xSize = secondArray[0].length;
        }

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {

                boolean areOverlapping = (firstArray[i][j] != 0 && secondArray[i][j] != 0);
                if (areOverlapping) {
                    System.out.println("Are overlapping at: " + j + "x" + i);
                    System.out.println("First equals: " + firstArray[i][j]);
                    System.out.println("Second equals: " + secondArray[i][j]);
                    return true;
                }
            }
        }
        return false;
    }

    // Checks, if there is a link on the map of this size or shorter
    public static boolean CheckMatrixForLinksOfLengthAtMost(int[][] inputMatrix, int maxLinkLength) {
        arrayToCheck = inputMatrix;

        int yMapSize = inputMatrix.length;
        for (int i = 0; i < yMapSize; i++) {

            int xMapSize = inputMatrix[i].length;
            for (int j = 0; j < xMapSize; j++) {
                if (CheckIfTileEmpty(arrayToCheck, j, i)) {
                    if (LinkCheck(j, i, 0, yMapSize * xMapSize) <= maxLinkLength) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Checks, if there is a link on the map of this size or longer
    public static boolean CheckMatrixForLinksOfLengthAtLeast(int[][] inputMatrix, int maxLinkLength) {
        arrayToCheck = inputMatrix;

        for (int i = 0; i < inputMatrix.length; i++) {
            for (int j = 0; j < inputMatrix[i].length; j++) {
                if (CheckIfTileEmpty(arrayToCheck, j, i)) {
                    if (LinkCheck(j, i, 0, maxLinkLength) >= maxLinkLength) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Recursive empty space counter // Leave total = 0, when calling this method
    public static int LinkCheck(int xPosition, int yPosition, int total, int cutoffLength) {
        // Initialization variables
        int checkLimit = cutoffLength;
        int countNewTiles = 1;
        // Modify map array and check break condition
        arrayToCheck[yPosition][xPosition] = 1; // Mark its own tile as already checked
        if ((countNewTiles + total) >= checkLimit) {
            return countNewTiles;
        }

        if (enableDebugMessages) {
            printMatrixContentsInChat(arrayToCheck);
        }

        // check right
        if (CheckIfTileEmpty(arrayToCheck, xPosition + 1, yPosition)) {
            countNewTiles += LinkCheck(xPosition + 1, yPosition, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }
        // check down
        if (CheckIfTileEmpty(arrayToCheck, xPosition, yPosition + 1)) {
            countNewTiles += LinkCheck(xPosition, yPosition + 1, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }
        // check left
        if (CheckIfTileEmpty(arrayToCheck, xPosition - 1, yPosition)) {
            countNewTiles += LinkCheck(xPosition - 1, yPosition, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }
        // check up
        if (CheckIfTileEmpty(arrayToCheck, xPosition, yPosition - 1)) {
            countNewTiles += LinkCheck(xPosition, yPosition - 1, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }

        return countNewTiles;
    }

    // Basic check
    public static boolean CheckIfTileEmpty(int[][] inputArray, int xPosition, int yPosition) {
        boolean yOutOfBounds = yPosition >= inputArray.length || yPosition < 0;
        if (yOutOfBounds) {
            return false;
        }
        boolean xOutOfBounds = xPosition >= inputArray[yPosition].length || xPosition < 0;
        if (xOutOfBounds) {
            return false;
        }
        boolean tileEmpty = inputArray[yPosition][xPosition] == 0;
        if (!tileEmpty) {
            // System.out.println("Tile not empty");

            return false;
        }

        else {
            // System.out.println("Tile empty");
            return true;
        }
    }

    // For debuging
    public static void printMatrixContentsInChat(int[][] matrixToPrint) {
        System.out.println("Printing a new matrix");

        for (int i = 0; i < matrixToPrint.length; i++) {
            for (int j = 0; j < matrixToPrint[i].length; j++) {
                System.out.print(matrixToPrint[i][j] + " ");
                ;
            }
            System.out.println("");
        }
    }

    // Unused
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
}