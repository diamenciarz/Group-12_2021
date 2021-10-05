import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pentago {

    // Instances
    static Scanner scanner = new Scanner(System.in);
    static ShapeContainer shapeContainer = new ShapeContainer();

    // Startup settings variables
    public static boolean enableDebugMessages = false;
    public static boolean allowInputRepetition = true;
    public static int choices = 0;
    public static int xMapSize;
    public static int yMapSize;
    // Time counter
    static final long startTime = System.currentTimeMillis();

    // Internal variables
    public static int[][] arrayToCheck; // Dont modify this in any method
    public static int[][] currentMapArray;
    public static String[] inputBlocksArray;
    static ArrayList<int[][]> shapesToFit = new ArrayList<>();

    public static void main(String[] args) {
        StartProgram();
    }

    private static void StartProgram() {
        // Input methods
        AskForMapSize();
        PrintMatrixContentsInChatUsingLetters(currentMapArray);

        SaveInputToBlocksArray();
        if (!IsTheMapTooSmallForGivenShapes()) {

            FillshapesToFitArrayList();
            // No more inputs from now on
            scanner.close();

            int[][] solution = RecursiveSolution(currentMapArray, shapesToFit, 0);

            System.out.println("Solution trials: " + choices);
            PrintMatrixContentsInChatUsingLetters(solution);
            PrintTime();
        } else {
            System.out.println("The map is too small to fit the shapes");
        }
        
        // Graphic display
        new UI(solution[0].length, solution.length, 40, choices ).setState(solution);
    }

    private static void PrintTime() {
        final long endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime));
    }

    // ----------------- Powerful methods
    public static int[][] RecursiveSolution(int[][] currentMapState, ArrayList<int[][]> shapesToFitArray,
            int shapeIndex) {
        int shapesToFitAmount = shapesToFitArray.size() - 1;

        int[][] currentShapeToFit = shapesToFitArray.get(shapeIndex);
        int mapHeight = currentMapState.length;
        int mapLength = currentMapState[0].length;

        // Check all variations, before going to a new position
        ArrayList<int[][]> differentVariationsOfThisShape = GenerateDifferentVariationsOfThisShape(currentShapeToFit);
        int variationAmount = differentVariationsOfThisShape.size();
        for (int variations = 0; variations < variationAmount; variations++) {

            int[][] shapeVariation = differentVariationsOfThisShape.get(variations);
            // printMatrixContentsInChat(rotatedShapeToFit);

            int currentShapeHeight = shapeVariation.length;
            int currentShapeLength = shapeVariation[0].length;
            int xRepetitions = mapLength - currentShapeLength + 1;
            int yRepetitions = mapHeight - currentShapeHeight + 1;
            // Check all positions in range of the map
            for (int j = 0; j < yRepetitions; j++) {
                for (int k = 0; k < xRepetitions; k++) {

                    choices++;
                    int[][] emptyMap = ReturnEmptyMap(mapLength, mapHeight);
                    int[][] shapeOnEmptyGrid = PlaceShapeOnMap(emptyMap, shapeVariation, k, j);
                    if (!areTwoFiguresOverlapping(currentMapState, shapeOnEmptyGrid)) {

                        int[][] newMapState = PlaceShapeOnMap(currentMapState, shapeVariation, k, j);
                        if (AreAllHolesMultiplicationsOfFive(newMapState)) {

                            boolean hasFittedTheLastShape = shapesToFitAmount == shapeIndex;
                            if (hasFittedTheLastShape) {
                                return newMapState;
                            } else {
                                if (enableDebugMessages) {
                                    System.out.println("Shape: ");
                                    PrintMatrixContentsInChatUsingLetters(currentShapeToFit);
                                    System.out.println("Current state of the map: ");
                                    PrintMatrixContentsInChatUsingLetters(newMapState);
                                }
                                int[][] solution = RecursiveSolution(newMapState, shapesToFitArray, shapeIndex + 1);
                                if (!IsSolutionInvalid(solution)) {
                                    return solution;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Else, solution is invalid
        int invalidSolution[][] = { { -1, -1 } };
        return invalidSolution;
    }

    private static int[][] PlaceShapeOnMap(int mapArray[][], int shape[][], int xPosition, int yPosition) {
        int[][] mapStateArray = GetACopyOfThisArray(mapArray);

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

    private static int[][] GetACopyOfThisArray(int[][] arrayToCopy) {
        int[][] returnArray = new int[arrayToCopy.length][arrayToCopy[0].length];
        for (int i = 0; i < returnArray.length; i++) {
            for (int j = 0; j < returnArray[0].length; j++) {
                returnArray[i][j] = arrayToCopy[i][j];
            }
        }
        return returnArray;
    }

    private static int[][] ReturnEmptyMap(int xMapSize, int yMapSize) {
        return new int[yMapSize][xMapSize];
    }

    private static String AskForBlocksInput() {
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
    private static void FillshapesToFitArrayList() {
        for (String blockName : inputBlocksArray) {
            int[][] block = shapeContainer.GetShapeArray(blockName);
            shapesToFit.add(block);
        }
    }

    private static boolean IsSolutionInvalid(int[][] solution) {
        if (solution[0][0] == -1) {
            return true;
        } else {
            return false;
        }
    }

    private static ArrayList<int[][]> GenerateDifferentVariationsOfThisShape(int[][] shape) {
        int flippedShape[][] = FlipShapeVertically(shape);
        ArrayList<int[][]> shapeVariations = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int[][] newShape = RotateShapeRight(shape, i);
            shapeVariations.add(newShape);
        }
        for (int i = 0; i < 4; i++) {
            int[][] newShape = RotateShapeRight(flippedShape, i);
            ;
            shapeVariations.add(newShape);
        }

        shapeVariations = RemoveCopiedShapes(shapeVariations);
        return shapeVariations;
    }

    private static ArrayList<int[][]> RemoveCopiedShapes(ArrayList<int[][]> array) {
        ArrayList<int[][]> newArray = new ArrayList<>();
        newArray.add(array.get(0));

        for (int i = 0; i < array.size(); i++) {

            int[][] elementToAdd = array.get(i);
            if (!ArrayListContainsArray(newArray, elementToAdd)) {
                newArray.add(elementToAdd);
            }
        }
        return newArray;
    }

    private static boolean ArrayListContainsArray(ArrayList<int[][]> arrayList, int[][] arrayToCheck) {
        boolean newArrayContainsThisElement = false;
        for (int j = 0; j < arrayList.size(); j++) {

            newArrayContainsThisElement = AreTheseArraysTheSame(arrayList.get(j), arrayToCheck);
            if (newArrayContainsThisElement) {
                break;
            }

        }
        return newArrayContainsThisElement;
    }

    private static boolean AreTheseArraysTheSame(int[][] firstArray, int[][] secondArray) {
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

                boolean areSymbolsTheSame = (firstArray[i][j] == secondArray[i][j]);
                if (!areSymbolsTheSame) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean IsTheMapTooSmallForGivenShapes() {
        int combinedBlockArea = inputBlocksArray.length * 5;
        int mapArea = xMapSize * yMapSize;
        boolean areGivenShapesTooBig = combinedBlockArea > mapArea;
        ;
        if (areGivenShapesTooBig) {
            return true;
        }
        return false;
    }

    private static int[][] FlipShapeVertically(int shape[][]) {
        int[][] flippedShape = GetACopyOfThisArray(shape);

        int originalShapeHeight = shape.length;
        int originalShapeLength = shape[0].length;

        int flippedShapeLength = flippedShape[0].length;

        for (int i = 0; i < originalShapeHeight; i++) {
            for (int j = 0; j < originalShapeLength; j++) {
                flippedShape[i][flippedShapeLength - (j + 1)] = shape[i][j];
            }
        }
        return flippedShape;

    }

    private static int[][] RotateShapeRight(int shape[][], int times) {

        int[][] rotatedShape = shape;
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
            rotatedShape = RotateShapeRight(rotatedShape, times - 1);
            return rotatedShape;

        } else {
            return rotatedShape;
        }
    }

    // Checks, if two figures have some common tiles
    private static boolean areTwoFiguresOverlapping(int[][] firstArray, int[][] secondArray) {
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

    private static boolean AreAllHolesMultiplicationsOfFive(int[][] inputMatrix) {
        arrayToCheck = GetACopyOfThisArray(inputMatrix);
        boolean areAllHolesMultiplicationsOfFive = true;

        int yMapSize = arrayToCheck.length;
        for (int i = 0; i < yMapSize; i++) {

            int xMapSize = arrayToCheck[i].length;
            for (int j = 0; j < xMapSize; j++) {

                if (CheckIfTileEmpty(arrayToCheck, j, i)) {

                    int holeSize = LinkCheck(j, i, 0, yMapSize * xMapSize);
                    if (holeSize % 5 != 0) {
                        areAllHolesMultiplicationsOfFive = false;
                        break;
                    }
                }
            }
        }
        return areAllHolesMultiplicationsOfFive;
    }
    
    // Recursive empty space counter // Leave total = 0, when calling this method
    private static int LinkCheck(int xPosition, int yPosition, int total, int cutoffLength) {
        // Initialization variables
        int checkLimit = cutoffLength;
        int countNewTiles = 1;
        // Modify map array and check break condition
        arrayToCheck[yPosition][xPosition] = 1; // Mark its own tile as already checked
        if ((countNewTiles + total) >= checkLimit) {
            return countNewTiles;
        }

        if (enableDebugMessages) {
            PrintMatrixContentsInChat(arrayToCheck);
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
    private static boolean CheckIfTileEmpty(int[][] inputArray, int xPosition, int yPosition) {
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

    // ----------------- Methods asking for user input
    private static void SaveInputToBlocksArray() {
        String answer = AskForBlocksInput();
        int answerLength = answer.length();

        inputBlocksArray = new String[answerLength];

        for (int i = 0; i < answerLength; i++) {
            inputBlocksArray[i] = answer.substring(i, i + 1);
        }
    }

    private static void AskForMapSize() {
        System.out.println("Give me the x map size: ");
        xMapSize = scanner.nextInt();
        System.out.println("Give me the y map size: ");
        yMapSize = scanner.nextInt();
        scanner.nextLine();

        currentMapArray = ReturnEmptyMap(xMapSize, yMapSize);
    }
    // ----------------- Input Helper Methods

    // Checks if input is an element of {P,L,F,V,W,Y,I,T,Z,U,N,L}
    private static boolean IsInputCorrect(String inputString) {
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
                    System.out.println("Repetition is not currently allowed");
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    // For debuging
    private static void PrintMatrixContentsInChat(int[][] matrixToPrint) {
        for (int i = 0; i < matrixToPrint.length; i++) {
            for (int j = 0; j < matrixToPrint[i].length; j++) {
                System.out.print(matrixToPrint[i][j] + " ");
                ;
            }
            System.out.println("");
        }
    }

    private static void PrintMatrixContentsInChatUsingLetters(int[][] matrixToPrint) {

        String[][] stringArray = GetLettersForMatrix(matrixToPrint);

        for (int i = 0; i < stringArray.length; i++) {
            for (int j = 0; j < stringArray[i].length; j++) {
                System.out.print(stringArray[i][j] + " ");
                ;
            }
            System.out.println("");
        }
    }

    private static String[][] GetLettersForMatrix(int[][] matrixToPrint) {

        String[][] letterMatrix;
        if (IsThisMatrixRectangular(matrixToPrint)) {

            letterMatrix = new String[matrixToPrint.length][matrixToPrint[0].length];

            for (int i = 0; i < matrixToPrint.length; i++) {
                for (int j = 0; j < matrixToPrint[0].length; j++) {
                    letterMatrix[i][j] = TurnNumberToLetter(matrixToPrint[i][j]);
                }
            }

        } else {
            System.out.println("Output matrix is not rectangular - this should not be the case");
            return null;
        }
        return letterMatrix;
    }

    private static String TurnNumberToLetter(int number) {
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

    private static boolean IsThisMatrixRectangular(int[][] inputMatrix) {
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
    // ----------Original solution:
    // 12x5 PXFVWYTZUNLI Trials: 1.273.335 214.655 Time: 3852
    // 12x5 PIXFVZWYTLUN Trials: 16.040.305 Time: 10876
    // 12x5 TPIXLVZWYFUN Trials: 3.729.160 Time: 4267
    // 6x5 PPIPPI Trials: 101 Time: 2151 | PPPPII 817 Time: 2388

    // ----------Magic improvements:
    // 12x5 PXFVWYTZUNLI Trials: 214.655 Time: 3504
    // 12x5 PIXFVZWYTLUN Trials: 5.457.073 Time: 5716
    // 12x5 TPIXLVZWYFUN Trials: 997.256 Time: 10.000 - 3.300
    // 6x5 PPIPPI Trials: 101 Time: 2151 | PPPPII 445 Time: 3029

    // ----------------- Unused Methods
    private static ArrayList<ArrayList<Integer>> TurnArrayIntoList(int[][] array) {
        ArrayList<ArrayList<Integer>> arrayListList = new ArrayList<>();

        for (int j = 0; j < array.length; j++) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int k = 0; k < array.length; k++) {
                arrayList.add(array[j][k]);
            }
            arrayListList.add(arrayList);
        }
        return arrayListList;
    }

    private static ArrayList<ArrayList<ArrayList<Integer>>> TurnArrayIntoList(int[][][] array) {
        ArrayList<ArrayList<ArrayList<Integer>>> arrayListListList = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            ArrayList<ArrayList<Integer>> arrayListList = new ArrayList<>();
            for (int j = 0; j < array.length; j++) {
                ArrayList<Integer> arrayList = new ArrayList<>();
                for (int k = 0; k < array.length; k++) {
                    arrayList.add(array[i][j][k]);
                }
                arrayListList.add(arrayList);
            }
            arrayListListList.add(arrayListList);
        }
        return arrayListListList;
    }

    private static boolean WillThisShapeFitOnMap(int mapArray[][], int shape[][], int xPosition, int yPosition) {
        boolean willFit = true;

        int maxYPosition = shape.length + yPosition;
        if (maxYPosition > mapArray.length) {
            willFit = false;
        }
        int maxXPosition = shape[0].length + xPosition;
        if (maxXPosition > mapArray[0].length) {
            willFit = false;
        }
        return willFit;
    }

    private static boolean CheckMatrixForLinksOfLengthAtLeast(int[][] inputMatrix, int maxLinkLength) {
        arrayToCheck = GetACopyOfThisArray(inputMatrix);

        for (int i = 0; i < arrayToCheck.length; i++) {
            for (int j = 0; j < arrayToCheck[i].length; j++) {
                if (CheckIfTileEmpty(arrayToCheck, j, i)) {
                    if (LinkCheck(j, i, 0, maxLinkLength) >= maxLinkLength) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void PrintArrayListContentsInChat(ArrayList<ArrayList<Integer>> arrayListToPrint) {

        for (int i = 0; i < arrayListToPrint.size(); i++) {
            for (int j = 0; j < arrayListToPrint.get(i).size(); j++) {
                System.out.print(arrayListToPrint.get(i).get(j) + " ");
            }
            System.out.println("");
        }
    }

    private static void PrintArrayListArrayContentsInChat(ArrayList<int[][]> arrayListToPrint) {

        for (int i = 0; i < arrayListToPrint.size(); i++) {
            PrintMatrixContentsInChat(arrayListToPrint.get(i));
        }
    }
    // Checks, if there is a link on the map of this size or shorter
    private static boolean CheckMatrixForLinksOfLengthAtMost(int[][] inputMatrix, int maxLinkLength) {
        arrayToCheck = GetACopyOfThisArray(inputMatrix);

        int yMapSize = arrayToCheck.length;
        for (int i = 0; i < yMapSize; i++) {

            int xMapSize = arrayToCheck[i].length;
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
}
