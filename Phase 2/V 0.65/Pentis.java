import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pentis {

    // Instances
    static Scanner scanner = new Scanner(System.in);
    static ShapeContainer shapeContainer = new ShapeContainer();
    static UI ui = new UI();

    // Startup settings variables
    public static boolean enableDebugMessages = false;
    public static boolean allowInputRepetition = true;
    public static int xMapSize = 5;
    public static int yMapSize = 12;
    private static long lastSavedTime;

    // Time counter
    static final long startTime = System.currentTimeMillis();

    // Internal variables
    private static int[][] temporaryHelperArray; // Dont modify this in any method
    private static int[][] currentMapMatrix;
    private static String[] inputBlocksArray;
    private static ArrayList<int[][]> shapesToFit = new ArrayList<>();
    private static ArrayList<int[][]> shapesQueue = new ArrayList<>();

    private static int[][] currentShape;
    private static int[][] lastShapeState;
    private static int currentShapeXPosition;
    private static int currentShapeYPosition;
    private static char lastKeyPressed = 't';
    private static boolean setupStartingVariables = false;

    public static void pressedKey(char key) {
        lastKeyPressed = key;

        if (setupStartingVariables) {
            tryMoveCurrentShape();
        }
    }

    public static void main(String[] args) {

        startProgram();
    }

    private static void startProgram() {
        // Setup methods
        setupEmptyMap();
        add12ShapesToQueue();

        takeNextShapeFromQueue();
        int[][] shapePlacedOnGrid = placeShapeOnMap(getEmptyMap(xMapSize, yMapSize), currentShape,
                currentShapeXPosition, currentShapeYPosition);
        ui.updateGrid(shapePlacedOnGrid);
        // Enter Loop
        setupStartingVariables = true;
    }

    private static void tryMoveCurrentShape() {
        lastShapeState = placeShapeOnMap(getEmptyMap(xMapSize, yMapSize), currentShape, currentShapeXPosition,
                currentShapeYPosition);

        System.out.println("Pressed:" + lastKeyPressed);
        doInput(lastKeyPressed);
    }

    private static void doInput(char input) {
        if (input == 'a') {
            tryMoveCurrentShape(-1, 0);
        }
        if (input == 's') {
            tryMoveCurrentShape(0, 1);
        }
        if (input == 'd') {
            tryMoveCurrentShape(1, 0);
        }
        if (input == 'q') {
            currentShape = tryRotateCurrentShape(3);
        }
        if (input == 'e') {
            currentShape = tryRotateCurrentShape(1);
        }
        if (input == 'o') {
        }

        doStep();
    }

    private static void doStep() {
        int[][] shapePlacedOnGrid = placeShapeOnMap(getEmptyMap(xMapSize, yMapSize), currentShape,
                currentShapeXPosition, currentShapeYPosition);
        if (areTwoFiguresOverlapping(currentMapMatrix, shapePlacedOnGrid)) {
            currentMapMatrix = placeShapeOnMap(currentMapMatrix, lastShapeState, 0, 0);
            takeNextShapeFromQueue();
        } else {
            addShapeOnMap(currentShape);
        }

        if (shapesQueue.size() < 12) {
            add12ShapesToQueue();
        }
    }

    private static void addShapeOnMap(int[][] shape) {
        int[][] displayMatrix = placeShapeOnMap(currentMapMatrix, shape, currentShapeXPosition, currentShapeYPosition);
        printMatrixContentsInChatUsingLetters(displayMatrix);
        ui.updateGrid(displayMatrix);
    }

    private static String getActionInputFromConsole() {
        boolean isInputCorrect = false;
        String playerInput = null;
        while (!isInputCorrect) {
            playerInput = scanner.next();
            isInputCorrect = playerInput.equals("a") || playerInput.equals("s") || playerInput.equals("d")
                    || playerInput.equals("q") || playerInput.equals("e") || playerInput.equals("o");
        }
        return playerInput;
    }

    private static void tryMoveCurrentShape(int deltaX, int deltaY) {
        if (willThisShapeFitOnMap(currentMapMatrix, currentShape, currentShapeXPosition + deltaX,
                currentShapeYPosition + deltaY)) {
            currentShapeXPosition += deltaX;
            currentShapeYPosition += deltaY;
        }
        int shapeBottomIndex = currentShapeYPosition + currentShape.length + deltaY;
        if (shapeBottomIndex >= currentMapMatrix.length) {
            addShapeOnMap(currentShape);
        }
    }

    /**
     * If the rotated shape cannot fit onto the map, the non-rotated shape is
     * returned
     */
    private static int[][] tryRotateCurrentShape(int rotation) {
        int[][] rotatedShape = rotateShapeRight(currentShape, rotation);
        boolean isRotatedShapeInBounds = willThisShapeFitOnMap(currentMapMatrix, rotatedShape, currentShapeXPosition,
                currentShapeYPosition);

        if (isRotatedShapeInBounds) {
            return rotatedShape;
        }
        return currentShape;
    }

    private static void setupEmptyMap() {
        currentMapMatrix = getEmptyMap(5, 12);
    }

    private static void add12ShapesToQueue() {
        shapesQueue.addAll(ShapeContainer.get12ShuffledShapes());
    }

    private static void takeNextShapeFromQueue() {
        currentShape = shapesQueue.get(0);
        shapesQueue.remove(0);

        resetCurrentShapePosition();
    }

    private static void resetCurrentShapePosition() {
        currentShapeXPosition = 0;
        currentShapeYPosition = 0;
    }

    public static int[][] deleteRows(int[][] input) {
        ArrayList<Integer> saveRowIndexes = new ArrayList<Integer>();

        for (int index = 0; index < input.length; index++) {
            if (isRowFull(input, index) == false) {
                saveRowIndexes.add(index);

            }
        }
        int counter = 0;
        int[][] returnMatrix = new int[input.length][input[0].length];
        for (int index = saveRowIndexes.size() - 1; index >= 0; index--) {

            pasteRow(returnMatrix.length - counter - 1, returnMatrix, input, saveRowIndexes.get(index));
            counter++;
        }
        return returnMatrix;
    }

    private static void pasteRow(int returnIndex, int[][] returnMatrix, int[][] inputMatrix, int inputIndex) {
        for (int i = 0; i < inputMatrix[0].length; i++) {

            returnMatrix[returnIndex][i] = inputMatrix[inputIndex][i];
        }
    }

    public static boolean isRowFull(int[][] input, int index) {
        for (int i = 0; i < input[0].length; i++) {
            if (input[index][i] == 0) {
                return false;
            }
        }
        return true;
    }

    private static void countTime() {
        final long endTime = System.currentTimeMillis();

        lastSavedTime = endTime - startTime;
        System.out.println("Total execution time: " + lastSavedTime);
    }

    public long GetTime() {
        return lastSavedTime;
    }

    // ----------------- Powerful methods

    // private static int[][] PruneConditions()
    private static ArrayList<int[][]> getACopyOfThisArrayList(ArrayList<int[][]> arrayListToCopy) {
        ArrayList<int[][]> returnArrayList = new ArrayList<int[][]>();

        for (int[][] element : arrayListToCopy) {
            int[][] copiedElement = getACopyOfThisMatrix(element);
            returnArrayList.add(copiedElement);
        }
        return returnArrayList;
    }

    private static int[][] tryToFitShapesDirectlyToHoles(int[][] currentMapState, ArrayList<int[][]> shapeArrayList) {
        int[][] holeShape = getFirstHoleShapeOfSizeFive(currentMapState);
        if (holeShape != null) {
            for (int i = 0; i < shapeArrayList.size(); i++) {
                ArrayList<int[][]> currentShapeVariations = generateDifferentVariationsOfThisShape(
                        shapeArrayList.get(i));
                for (int[][] shapeVariation : currentShapeVariations) {

                    int[][] trimmedHoleShape = trimShape(holeShape);
                    if (areTheseArraysTheSame(shapeVariation, trimmedHoleShape)) {

                        int shapeNumber = getShapeNumber(shapeVariation);
                        int[][] untrimmedShape = changeShapeNumbersTo(holeShape, shapeNumber);
                        int[][] returnMapState = placeShapeOnMap(currentMapState, untrimmedShape, 0, 0);
                        shapeArrayList.remove(i);

                        int[][] recursiveMapState = { { 1 } };
                        recursiveMapState = tryToFitShapesDirectlyToHoles(returnMapState, shapeArrayList);
                        if (recursiveMapState != null) {
                            returnMapState = recursiveMapState;
                        }
                        return returnMapState;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the value of the first non-zero element of the matrix starting from
     * top left
     */
    private static int getShapeNumber(int[][] shape) {
        int[][] returnMatrix = getACopyOfThisMatrix(shape);

        int shapeHeight = shape.length;
        int shapeLength = shape[0].length;

        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeLength; j++) {
                if (shape[i][j] != 0) {
                    return returnMatrix[i][j];
                }
            }
        }
        return 0;
    }

    private static int[][] changeShapeNumbersTo(int[][] shape, int number) {
        int[][] returnMatrix = getACopyOfThisMatrix(shape);

        int shapeHeight = shape.length;
        int shapeLength = shape[0].length;

        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeLength; j++) {
                if (shape[i][j] != 0) {
                    returnMatrix[i][j] = number;
                }
            }
        }

        return returnMatrix;
    }

    private static int[][] placeShapeOnMap(int mapArray[][], int shape[][], int xPosition, int yPosition) {
        int[][] mapStateArray = getACopyOfThisMatrix(mapArray);

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

    private static int[][] getACopyOfThisMatrix(int[][] arrayToCopy) {
        int[][] returnArray = new int[arrayToCopy.length][arrayToCopy[0].length];
        for (int i = 0; i < returnArray.length; i++) {
            for (int j = 0; j < returnArray[0].length; j++) {
                returnArray[i][j] = arrayToCopy[i][j];
            }
        }
        return returnArray;
    }

    private static int[][] getEmptyMap(int xMapSize, int yMapSize) {
        return new int[yMapSize][xMapSize];
    }

    private static String askForBlocksInput() {
        String inputString = "";
        boolean isInputCorrect = false;
        while (!isInputCorrect) {
            inputString = scanner.nextLine();

            if (isInputCorrect(inputString.toLowerCase())) {
                isInputCorrect = true;
            } else {
                System.out.println("Input incorrect");
            }
        }
        return inputString.toUpperCase();
    }

    private static boolean isSolutionInvalid(int[][] solution) {
        if (solution[0][0] == -1) {
            return true;
        } else {
            return false;
        }
    }

    private static ArrayList<int[][]> generateDifferentVariationsOfThisShape(int[][] shape) {
        int flippedShape[][] = flipShapeVertically(shape);
        ArrayList<int[][]> shapeVariations = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int[][] newShape = rotateShapeRight(shape, i);
            shapeVariations.add(newShape);
        }
        for (int i = 0; i < 4; i++) {
            int[][] newShape = rotateShapeRight(flippedShape, i);
            ;
            shapeVariations.add(newShape);
        }

        shapeVariations = removeCopiedShapes(shapeVariations);
        return shapeVariations;
    }

    private static ArrayList<int[][]> removeCopiedShapes(ArrayList<int[][]> array) {
        ArrayList<int[][]> newArray = new ArrayList<>();
        newArray.add(array.get(0));

        for (int i = 0; i < array.size(); i++) {

            int[][] elementToAdd = array.get(i);
            if (!arrayListContainsArray(newArray, elementToAdd)) {
                newArray.add(elementToAdd);
            }
        }
        return newArray;
    }

    private static boolean arrayListContainsArray(ArrayList<int[][]> arrayList, int[][] arrayToCheck) {
        boolean newArrayContainsThisElement = false;
        for (int j = 0; j < arrayList.size(); j++) {

            newArrayContainsThisElement = areTheseArraysTheSame(arrayList.get(j), arrayToCheck);
            if (newArrayContainsThisElement) {
                break;
            }

        }
        return newArrayContainsThisElement;
    }

    private static boolean areTheseArraysTheSame(int[][] firstArray, int[][] secondArray) {
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

    private static int[][] flipShapeVertically(int shape[][]) {
        int[][] flippedShape = getACopyOfThisMatrix(shape);

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

    private static int[][] rotateShapeRight(int shape[][], int times) {

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

    private static boolean areAllHolesMultiplicationsOfFive(int[][] inputMatrix) {
        temporaryHelperArray = getACopyOfThisMatrix(inputMatrix);
        boolean areAllHolesMultiplicationsOfFive = true;

        int yMapSize = temporaryHelperArray.length;
        for (int i = 0; i < yMapSize; i++) {

            int xMapSize = temporaryHelperArray[i].length;
            for (int j = 0; j < xMapSize; j++) {

                if (checkIfTileEmpty(temporaryHelperArray, j, i)) {

                    int holeSize = recursiveHoleSizeCounter(j, i, 0, yMapSize * xMapSize);
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
    private static int recursiveHoleSizeCounter(int xPosition, int yPosition, int total, int cutoffLength) {
        // Initialization variables
        int checkLimit = cutoffLength;
        int countNewTiles = 1;
        // Modify map array and check break condition
        temporaryHelperArray[yPosition][xPosition] = 1; // Mark its own tile as already checked
        if ((countNewTiles + total) >= checkLimit) {
            return countNewTiles;
        }
        // check right
        if (checkIfTileEmpty(temporaryHelperArray, xPosition + 1, yPosition)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition + 1, yPosition, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }
        // check down
        if (checkIfTileEmpty(temporaryHelperArray, xPosition, yPosition + 1)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition, yPosition + 1, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }
        // check left
        if (checkIfTileEmpty(temporaryHelperArray, xPosition - 1, yPosition)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition - 1, yPosition, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }
        // check up
        if (checkIfTileEmpty(temporaryHelperArray, xPosition, yPosition - 1)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition, yPosition - 1, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }

        return countNewTiles;
    }

    private static ArrayList<Integer> turnArrayIntoList(int[] array) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int k = 0; k < array.length; k++) {
            arrayList.add(array[k]);
        }
        return arrayList;
    }

    private static int[][] getFirstHoleShapeOfSizeFive(int[][] currentStateOfTheMap) {
        int[][] currentStateOfTheMapCopy = getACopyOfThisMatrix(currentStateOfTheMap);

        int xMapSize = currentStateOfTheMapCopy[0].length;
        int yMapSize = currentStateOfTheMapCopy.length;
        for (int i = 0; i < yMapSize; i++) {
            for (int j = 0; j < xMapSize; j++) {
                if (checkIfTileEmpty(currentStateOfTheMapCopy, j, i)) {

                    temporaryHelperArray = getACopyOfThisMatrix(currentStateOfTheMapCopy);
                    int currentHoleSize = recursiveHoleSizeCounter(j, i, 0, yMapSize * xMapSize);
                    if (currentHoleSize == 5) {
                        int[][] emptyGrid = getEmptyMap(xMapSize, yMapSize);
                        int[][] holeShape = recursiveHoleShapeCounter(j, i, emptyGrid, currentStateOfTheMapCopy);
                        return holeShape;
                    }
                }
            }
        }
        return null;
    }

    private static int[][] recursiveHoleShapeCounter(int xPosition, int yPosition, int[][] currentShapeSaved,
            int[][] currentStateOfTheMap) {
        // Modify map array and check break condition
        currentStateOfTheMap[yPosition][xPosition] = 1; // Mark its own tile as already checked
        currentShapeSaved[yPosition][xPosition] = 1;
        // check right
        if (checkIfTileEmpty(currentStateOfTheMap, xPosition + 1, yPosition)) {
            recursiveHoleShapeCounter(xPosition + 1, yPosition, currentShapeSaved, currentStateOfTheMap);
        }
        // check down
        if (checkIfTileEmpty(currentStateOfTheMap, xPosition, yPosition - 1)) {
            recursiveHoleShapeCounter(xPosition, yPosition - 1, currentShapeSaved, currentStateOfTheMap);
        }
        // Check left
        if (checkIfTileEmpty(currentStateOfTheMap, xPosition - 1, yPosition)) {
            recursiveHoleShapeCounter(xPosition - 1, yPosition, currentShapeSaved, currentStateOfTheMap);
        }
        // check up
        if (checkIfTileEmpty(currentStateOfTheMap, xPosition, yPosition + 1)) {
            recursiveHoleShapeCounter(xPosition, yPosition + 1, currentShapeSaved, currentStateOfTheMap);
        }

        return currentShapeSaved;
    }

    private static int[][] trimShape(int[][] inputShape) {
        int ySize = inputShape.length;
        int xSize = inputShape[0].length;

        ArrayList<Integer> nonEmptyRowIndexes = new ArrayList<>();
        ArrayList<Integer> nonEmptyColumnIndexes = new ArrayList<>();
        // Find all rows and columns that are not empty and save them to a list
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (inputShape[y][x] == 1) {

                    nonEmptyColumnIndexes.add(x);
                    nonEmptyRowIndexes.add(y);
                }
            }
        }
        int minColumnIndex = getMinimumValueFromArrayList(nonEmptyColumnIndexes);
        int maxColumnIndex = getMaximumValueFromArrayList(nonEmptyColumnIndexes);
        int minRowIndex = getMinimumValueFromArrayList(nonEmptyRowIndexes);
        int maxRowIndex = getMaximumValueFromArrayList(nonEmptyRowIndexes);

        int xMapSize = maxColumnIndex - minColumnIndex + 1;
        int yMapSize = maxRowIndex - minRowIndex + 1;

        int[][] returnArray = getEmptyMap(xMapSize, yMapSize);

        for (int x = 0; x < xMapSize; x++) {
            for (int y = 0; y < yMapSize; y++) {
                returnArray[y][x] = inputShape[y + minRowIndex][x + minColumnIndex];
            }
        }

        return returnArray;
    }

    private static ArrayList<int[]> getAvailableRowAndColumnIndexes(int[][] inputMatrix, int shapeLength,
            int shapeHeight) {
        int ySize = inputMatrix.length;
        int xSize = inputMatrix[0].length;
        int[] nonFilledRowIndexes = createArrayWithINumberofIValues(ySize);
        int[] nonFilledColumnIndexes = createArrayWithINumberofIValues(xSize);

        // Find all rows that are filled and remove (2k-1) of them from the list
        for (int y = ySize - 1; y >= 0; y--) {
            boolean isRowFilled = true;
            for (int x = xSize - 1; x >= 0; x--) {
                if (inputMatrix[y][x] == 0) {
                    isRowFilled = false;
                }
            }
            if (isRowFilled) {
                for (int i = ((-shapeHeight) + 1); i < 1; i++) {
                    boolean isIndexInBounds = ((y + i) >= 0) && (y + i) < ySize;
                    if (isIndexInBounds) {
                        nonFilledRowIndexes[y + i] = -1;
                    }
                }
            }
        }
        // Find all columns that are filled and remove (2k-1) of them from the list
        for (int x = xSize - 1; x >= 0; x--) {
            boolean isColumnFilled = true;
            for (int y = ySize - 1; y >= 0; y--) {
                if (inputMatrix[y][x] == 0) {
                    isColumnFilled = false;
                }
            }
            if (isColumnFilled) {
                for (int i = ((-shapeLength) + 1); i < 1; i++) {
                    boolean isIndexInBounds = ((x + i) >= 0) && ((x + i) < xSize);
                    if (isIndexInBounds) {
                        nonFilledColumnIndexes[x + i] = -1;
                    }
                }
            }
        }
        // Remove last indexes from the map, as the shapes have some size of their own
        for (int i = 0; i < shapeLength; i++) {
            int indexToRemove = xSize - i;
            boolean isIndexInBounds = (indexToRemove >= 0) && (indexToRemove < xSize);
            if (isIndexInBounds) {
                nonFilledColumnIndexes[indexToRemove] = -1;
            }

        }
        for (int i = 0; i < shapeHeight; i++) {
            int indexToRemove = ySize - i;
            boolean isIndexInBounds = (indexToRemove >= 0) && (indexToRemove < ySize);
            if (isIndexInBounds) {
                nonFilledRowIndexes[indexToRemove] = -1;
            }

        }

        nonFilledRowIndexes = removeValueFromArray(nonFilledRowIndexes, -1);
        nonFilledColumnIndexes = removeValueFromArray(nonFilledColumnIndexes, -1);
        // Check, how many rows and columns are left

        // Add the arrays to the return list
        ArrayList<int[]> returnArrayList = new ArrayList<>();
        returnArrayList.add(nonFilledRowIndexes);
        returnArrayList.add(nonFilledColumnIndexes);

        return returnArrayList;
    }

    private static int[] removeValueFromArray(int[] array, int value) {
        ArrayList<Integer> copiedList = turnArrayIntoList(array);

        for (int i = array.length - 1; i >= 0; i--) {
            if (copiedList.get(i) == value) {
                copiedList.remove(i);
            }
        }

        array = new int[copiedList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = copiedList.get(i);

        }
        return array;
    }

    private static int[] createArrayWithINumberofIValues(int length) {
        int[] nonFilledRowIndexes = new int[length];

        for (int i = 0; i < length; i++) {
            nonFilledRowIndexes[i] = i;
        }
        return nonFilledRowIndexes;
    }

    private static int getMinimumValueFromArrayList(ArrayList<Integer> arrayList) {
        int currentMinimum = arrayList.get(0);
        for (int i = 0; i < arrayList.size(); i++) {
            int currentArrayElement = arrayList.get(i);
            if (currentMinimum > currentArrayElement) {
                currentMinimum = currentArrayElement;
            }
        }
        return currentMinimum;
    }

    private static int getMaximumValueFromArrayList(ArrayList<Integer> arrayList) {
        int currentMaximum = arrayList.get(0);
        for (int i = 0; i < arrayList.size(); i++) {
            int currentArrayElement = arrayList.get(i);
            if (currentMaximum < currentArrayElement) {
                currentMaximum = currentArrayElement;
            }
        }
        return currentMaximum;
    }

    // Basic check
    private static boolean checkIfTileEmpty(int[][] inputArray, int xPosition, int yPosition) {
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
    private static void saveInputToBlocksArray() {
        String answer = askForBlocksInput();
        int answerLength = answer.length();

        inputBlocksArray = new String[answerLength];

        for (int i = 0; i < answerLength; i++) {
            inputBlocksArray[i] = answer.substring(i, i + 1);
        }
    }

    // ----------------- Input Helper Methods

    // Checks if input is an element of {P,L,F,V,W,Y,I,T,Z,U,N,L}
    private static boolean isInputCorrect(String inputString) {
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
    private static void printMatrixContentsInChat(int[][] matrixToPrint) {
        for (int i = 0; i < matrixToPrint.length; i++) {
            for (int j = 0; j < matrixToPrint[i].length; j++) {
                System.out.print(matrixToPrint[i][j] + " ");
                ;
            }
            System.out.println("");
        }
    }

    private static void printMatrixContentsInChatUsingLetters(int[][] matrixToPrint) {
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

    private static boolean isThisMatrixRectangular(int[][] inputMatrix) {
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
    // 12x5 PIXFVZWYTLUN Trials: 5.457.073 Time: 5716 ||| 3398557 ||| 3104004
    // 12x5 TPIXLVZWYFUN Trials: 997.256 Time: 10.000 - 3.300
    // 6x5 PPIPPI Trials: 101 Time: 2151 | PPPPII 445 Time: 3029
    // 6x5 uuxiii Trials: 1141 |

    // ----------Complex pruning:
    // 12x5 PXFVWYTZUNLI Trials: 142.409 Time: 3504
    // 12x5 PIXFVZWYTLUN Trials: 3.104.004 Time: 5716
    // 12x5 TPIXLVZWYFUN Trials: 633.112 Time: 10.000 - 3.300
    // 6x5 PPIPPI Trials: 101 Time: 2151 | PPPPII 445 Time: 3029
    // 6x5 uuxiii Trials: 1141 |

    // ----------------- Unused Methods

    private static int[][] getFirstHoleShape(int[][] currentStateOfTheMap) {
        temporaryHelperArray = getACopyOfThisMatrix(currentStateOfTheMap);

        for (int i = 0; i < temporaryHelperArray.length; i++) {
            for (int j = 0; j < temporaryHelperArray[i].length; j++) {
                if (checkIfTileEmpty(temporaryHelperArray, j, i)) {

                    int[][] emptyGrid = getEmptyMap(temporaryHelperArray[i].length, temporaryHelperArray.length);
                    return recursiveHoleShapeCounter(j, i, emptyGrid, temporaryHelperArray);

                }
            }
        }
        return null;
    }

    private static void printArrayContentsInChat(int[] arrayToPrint) {
        for (int i = 0; i < arrayToPrint.length; i++) {
            System.out.print(arrayToPrint[i] + " ");
        }
        System.out.println("");
    }

    private static ArrayList<ArrayList<Integer>> turnMatrixInto2List(int[][] array) {
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

    private static ArrayList<ArrayList<ArrayList<Integer>>> turn3ArrayInto3List(int[][][] array) {
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

    private static int[] getACopyOfThisArray(int[] arrayToCopy) {
        int[] returnArray = new int[arrayToCopy.length];
        for (int j = 0; j < returnArray.length; j++) {
            returnArray[j] = arrayToCopy[j];
        }
        return returnArray;
    }

    private static boolean willThisShapeFitOnMap(int mapArray[][], int shape[][], int xPosition, int yPosition) {
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

    private static boolean checkMatrixForLinksOfLengthAtLeast(int[][] inputMatrix, int maxLinkLength) {
        temporaryHelperArray = getACopyOfThisMatrix(inputMatrix);

        for (int i = 0; i < temporaryHelperArray.length; i++) {
            for (int j = 0; j < temporaryHelperArray[i].length; j++) {
                if (checkIfTileEmpty(temporaryHelperArray, j, i)) {
                    if (recursiveHoleSizeCounter(j, i, 0, maxLinkLength) >= maxLinkLength) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void printArrayListContentsInChat(ArrayList<ArrayList<Integer>> arrayListToPrint) {

        for (int i = 0; i < arrayListToPrint.size(); i++) {
            for (int j = 0; j < arrayListToPrint.get(i).size(); j++) {
                System.out.print(arrayListToPrint.get(i).get(j) + " ");
            }
            System.out.println("");
        }
    }

    private static void printArrayListArrayContentsInChat(ArrayList<int[][]> arrayListToPrint) {
        System.out.println("Printing arrayList: ");
        for (int i = 0; i < arrayListToPrint.size(); i++) {
            printMatrixContentsInChat(arrayListToPrint.get(i));
        }
    }

    // Checks, if there is a link on the map of this size or shorter
    private static boolean checkMatrixForLinksOfLengthAtMost(int[][] inputMatrix, int maxLinkLength) {
        temporaryHelperArray = getACopyOfThisMatrix(inputMatrix);

        int yMapSize = temporaryHelperArray.length;
        for (int i = 0; i < yMapSize; i++) {

            int xMapSize = temporaryHelperArray[i].length;
            for (int j = 0; j < xMapSize; j++) {

                if (checkIfTileEmpty(temporaryHelperArray, j, i)) {

                    if (recursiveHoleSizeCounter(j, i, 0, yMapSize * xMapSize) <= maxLinkLength) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
