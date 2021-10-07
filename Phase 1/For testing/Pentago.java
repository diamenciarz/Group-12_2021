import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pentago {

    // Instances
    static Scanner scanner = new Scanner(System.in);
    static ShapeContainer shapeContainer = new ShapeContainer();
    static UI ui = new UI();

    // Startup settings variables
    public static boolean enableDebugMessages = false;
    public static boolean allowInputRepetition = true;
    private static int solutionSteps = 0;
    public static int xMapSize;
    public static int yMapSize;
    private static long lastSavedTime;
    // Time counter
    static long startTime;

    // Internal variables
    private static int[][] temporaryHelperArray; // Dont modify this in any method
    private static int[][] currentMapArray;
    private static String[] inputBlocksArray;
    static ArrayList<int[][]> shapesToFit = new ArrayList<>();

    public static void main(String[] args) {

        // example input: 12,5, PIXFVZWYTLUN

        // Uncomment the method below for manual input
        //RunProgramManuallyOnce();
        
        // Use the method below in your own testing class instead, if you want to run it many times
        StartAutomaticTestWithTheseInputs(12, 5, "PIXVVZYYTLUN");
        System.out.println("Time: " + GetTime());
        System.out.println("Steps: " + GetSolutionStepAmount());
        StartAutomaticTestWithTheseInputs(5, 4, "uxui");
        System.out.println("Time: " + GetTime());
        System.out.println("Steps: " + GetSolutionStepAmount());
        StartAutomaticTestWithTheseInputs(12, 5, "ppppiiiipppp");
        System.out.println("Time: " + GetTime());
        System.out.println("Steps: " + GetSolutionStepAmount());
        
        /*
         * Use in this order in your own class: 
         * StartAutomaticTestWithTheseInputs(5, 12, "PIXVVZWYTLUN"); 
         * //to run the program once 
         * long time = GetTime(); 
         * //To save
         * the last time to a variable 
         * int steps = GetSolutionStepAmount(); 
         * // to get the amount of steps
         */

    }

    public static void StartAutomaticTestWithTheseInputs(int xSize, int ySize, String inputLetters) {
        currentMapArray = GetEmptyMap(xSize, ySize);
        xMapSize = xSize;
        yMapSize = ySize;

        String answer = inputLetters;
        int answerLength = answer.length();

        inputBlocksArray = new String[answerLength];

        for (int i = 0; i < answerLength; i++) {
            inputBlocksArray[i] = answer.substring(i, i + 1);
        }
        if (!IsTheMapTooSmallForGivenShapes()) {
            GetSolution();

        } else {
            System.out.println("The map is too small to fit the shapes");
        }
    }

    private static void RunProgramManuallyOnce() {
        // Input methods
        AskForDebugMode();
        GetUserInput();

        if (!IsTheMapTooSmallForGivenShapes()) {
            // Create the button here
            int[][] solution = GetSolution();
            DisplaySolution(solution);

        } else {
            System.out.println("The map is too small to fit the shapes");
        }
    }

    private static void AskForDebugMode() {
        System.out.println("Type 1 for debug mode or anything else for normal display ");
        String answerString = scanner.nextLine();
        if (answerString.compareTo("1") == 0) {
            enableDebugMessages = true;
        } else {
            enableDebugMessages = false;
        }
    }

    private static void GetUserInput() {
        AskForMapSize();
        PrintMatrixContentsInChatUsingLetters(currentMapArray);

        SaveInputToBlocksArray();
    }

    private static int[][] GetSolution() {
        FillshapesToFitArrayList();
        solutionSteps = 0;
        startTime = System.currentTimeMillis();
        
        // No more inputs from now on
        scanner.close();

        int[][] solution = NewRecursiveSolution(currentMapArray, shapesToFit);
        // int[][] solution = RecursiveSolution(currentMapArray, shapesToFit,0);

        System.out.println("Solution trials: " + solutionSteps);
        PrintMatrixContentsInChatUsingLetters(solution);

        CountTime();
        return solution;

    }

    private static void DisplaySolution(int[][] solutiontoDisplay) {

        // ui.UpdateTrials(GetSolutionStepAmount(), GetTime());
        ui.UpdateGrid(solutiontoDisplay);
    }

    public static int GetSolutionStepAmount() {
        return solutionSteps;
    }

    private static void CountTime() {
        long endTime = System.currentTimeMillis();

        lastSavedTime = endTime - startTime;
        System.out.println("Total execution time: " + lastSavedTime);
    }

    public static long GetTime() {
        return lastSavedTime;
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

            int currentShapeHeight = shapeVariation.length;
            int currentShapeLength = shapeVariation[0].length;

            ArrayList<int[]> nonFilledRowsAndColumns = GetAvailableRowAndColumnIndexes(currentMapState,
                    currentShapeLength, currentShapeHeight);
            // Check all positions in range of the map
            for (int y : nonFilledRowsAndColumns.get(0)) {
                for (int x : nonFilledRowsAndColumns.get(1)) {

                    solutionSteps++;
                    int[][] emptyMap = GetEmptyMap(mapLength, mapHeight);
                    int[][] shapeOnEmptyGrid = PlaceShapeOnMap(emptyMap, shapeVariation, x, y);
                    if (!areTwoFiguresOverlapping(currentMapState, shapeOnEmptyGrid)) {

                        int[][] newMapState = PlaceShapeOnMap(currentMapState, shapeVariation, x, y);

                        if (AreAllHolesMultiplicationsOfFive(newMapState)) {

                            if (enableDebugMessages) {
                                PrintMatrixContentsInChatUsingLetters(newMapState);
                            }
                            boolean hasFittedTheLastShape = shapesToFitAmount == shapeIndex;
                            if (hasFittedTheLastShape) {
                                return newMapState;
                            } else {

                                int[][] solution = RecursiveSolution(newMapState, shapesToFitArray, shapeIndex + 1);

                                if (enableDebugMessages) {

                                    System.out.println("Shape: ");
                                    PrintMatrixContentsInChatUsingLetters(currentShapeToFit);
                                    System.out.println("Current state of the map: ");
                                    PrintMatrixContentsInChatUsingLetters(newMapState);

                                }
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

    private static int[][] NewRecursiveSolution(int[][] currentMapState, ArrayList<int[][]> shapesLeftToFitArray) {
        ArrayList<int[][]> shapesLeftToFitArrayCopy = GetACopyOfThisArrayList(shapesLeftToFitArray);

        int[][] newMapState;
        int[][] currentMapStateCopy = GetACopyOfThisMatrix(currentMapState);
        // Check, if can use the faster algorithm, which doesn't work in every case
        ArrayList<int[][]> shapesLeftToFitForTheNextStep = GetACopyOfThisArrayList(shapesLeftToFitArrayCopy);
        int[][] mapWithFittedShapes = TryToFitShapesDirectlyToHoles(currentMapStateCopy, shapesLeftToFitForTheNextStep);
        if (mapWithFittedShapes != null) {
            // First algorithm
            newMapState = mapWithFittedShapes;

            solutionSteps++;
            boolean hasFittedTheLastShape = shapesLeftToFitForTheNextStep.isEmpty();
            if (hasFittedTheLastShape) {
                return newMapState;
            } else {
                int[][] solution = NewRecursiveSolution(newMapState, shapesLeftToFitForTheNextStep);

                if (!IsSolutionInvalid(solution)) {
                    return solution;
                }
            }
            // Second algorithm
        } else {
            int[][] currentShapeToFit = shapesLeftToFitArrayCopy.get(0);
            int mapHeight = currentMapStateCopy.length;
            int mapLength = currentMapStateCopy[0].length;

            ArrayList<int[][]> differentVariationsOfThisShape = GenerateDifferentVariationsOfThisShape(
                    currentShapeToFit);
            int variationAmount = differentVariationsOfThisShape.size();

            for (int variations = 0; variations < variationAmount; variations++) {
                int[][] shapeVariation = differentVariationsOfThisShape.get(variations);

                int currentShapeHeight = shapeVariation.length;
                int currentShapeLength = shapeVariation[0].length;

                ArrayList<int[]> nonFilledRowsAndColumns = GetAvailableRowAndColumnIndexes(currentMapStateCopy,
                        currentShapeLength, currentShapeHeight);
                for (int y : nonFilledRowsAndColumns.get(0)) {
                    for (int x : nonFilledRowsAndColumns.get(1)) {

                        int[][] emptyMap = GetEmptyMap(mapLength, mapHeight);
                        int[][] shapeOnEmptyGrid = PlaceShapeOnMap(emptyMap, shapeVariation, x, y);
                        if (!areTwoFiguresOverlapping(currentMapStateCopy, shapeOnEmptyGrid)) {
                            newMapState = PlaceShapeOnMap(currentMapStateCopy, shapeVariation, x, y);

                            if (AreAllHolesMultiplicationsOfFive(newMapState)) {

                                solutionSteps++;
                                if (enableDebugMessages) {
                                    System.out.println("Placed block normally");
                                    PrintMatrixContentsInChatUsingLetters(newMapState);
                                    ui.UpdateGrid(newMapState);
                                }
                                ArrayList<int[][]> shapesLeftToFitForTheSecondAlgorithm = GetACopyOfThisArrayList(
                                        shapesLeftToFitArrayCopy);
                                shapesLeftToFitForTheSecondAlgorithm.remove(0);
                                boolean hasFittedTheLastShape = shapesLeftToFitForTheSecondAlgorithm.isEmpty();
                                if (hasFittedTheLastShape) {
                                    return newMapState;
                                } else {
                                    int[][] solution = NewRecursiveSolution(newMapState,
                                            shapesLeftToFitForTheSecondAlgorithm);

                                    if (!IsSolutionInvalid(solution)) {
                                        return solution;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int invalidSolution[][] = { { -1 } };
        return invalidSolution;
    }

    // private static int[][] PruneConditions()
    private static ArrayList<int[][]> GetACopyOfThisArrayList(ArrayList<int[][]> arrayListToCopy) {
        ArrayList<int[][]> returnArrayList = new ArrayList<int[][]>();

        for (int[][] element : arrayListToCopy) {
            int[][] copiedElement = GetACopyOfThisMatrix(element);
            returnArrayList.add(copiedElement);
        }
        return returnArrayList;
    }

    private static int[][] TryToFitShapesDirectlyToHoles(int[][] currentMapState, ArrayList<int[][]> shapeArrayList) {
        int[][] holeShape = GetFirstHoleShapeOfSizeFive(currentMapState);
        if (holeShape != null) {
            for (int i = 0; i < shapeArrayList.size(); i++) {
                ArrayList<int[][]> currentShapeVariations = GenerateDifferentVariationsOfThisShape(
                        shapeArrayList.get(i));
                for (int[][] shapeVariation : currentShapeVariations) {

                    int[][] trimmedHoleShape = TrimShape(holeShape);
                    if (AreTheseArraysTheSame(shapeVariation, trimmedHoleShape)) {

                        int shapeNumber = GetShapeNumber(shapeVariation);
                        int[][] untrimmedShape = ChangeShapeNumbersTo(holeShape, shapeNumber);
                        int[][] returnMapState = PlaceShapeOnMap(currentMapState, untrimmedShape, 0, 0);
                        shapeArrayList.remove(i);

                        int[][] recursiveMapState = { { 1 } };
                        recursiveMapState = TryToFitShapesDirectlyToHoles(returnMapState, shapeArrayList);
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

    private static int GetShapeNumber(int[][] shape) {
        int[][] returnMatrix = GetACopyOfThisMatrix(shape);

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

    private static int[][] ChangeShapeNumbersTo(int[][] shape, int number) {
        int[][] returnMatrix = GetACopyOfThisMatrix(shape);

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

    private static int[][] PlaceShapeOnMap(int mapArray[][], int shape[][], int xPosition, int yPosition) {
        int[][] mapStateArray = GetACopyOfThisMatrix(mapArray);

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

    private static int[][] GetACopyOfThisMatrix(int[][] arrayToCopy) {
        int[][] returnArray = new int[arrayToCopy.length][arrayToCopy[0].length];
        for (int i = 0; i < returnArray.length; i++) {
            for (int j = 0; j < returnArray[0].length; j++) {
                returnArray[i][j] = arrayToCopy[i][j];
            }
        }
        return returnArray;
    }

    private static int[][] GetEmptyMap(int xMapSize, int yMapSize) {
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
        shapesToFit.clear();
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

                boolean areBothNotZero = ((firstArray[i][j] != 0) && (secondArray[i][j] != 0));
                boolean areBothZero = ((firstArray[i][j] == 0) && (secondArray[i][j] == 0));
                if (!(areBothNotZero || areBothZero)) {
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
        int[][] flippedShape = GetACopyOfThisMatrix(shape);

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
        temporaryHelperArray = GetACopyOfThisMatrix(inputMatrix);
        boolean areAllHolesMultiplicationsOfFive = true;

        int yMapSize = temporaryHelperArray.length;
        for (int i = 0; i < yMapSize; i++) {

            int xMapSize = temporaryHelperArray[i].length;
            for (int j = 0; j < xMapSize; j++) {

                if (CheckIfTileEmpty(temporaryHelperArray, j, i)) {

                    int holeSize = RecursiveHoleSizeCounter(j, i, 0, yMapSize * xMapSize);
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
    private static int RecursiveHoleSizeCounter(int xPosition, int yPosition, int total, int cutoffLength) {
        // Initialization variables
        int checkLimit = cutoffLength;
        int countNewTiles = 1;
        // Modify map array and check break condition
        temporaryHelperArray[yPosition][xPosition] = 1; // Mark its own tile as already checked
        if ((countNewTiles + total) >= checkLimit) {
            return countNewTiles;
        }
        // check right
        if (CheckIfTileEmpty(temporaryHelperArray, xPosition + 1, yPosition)) {
            countNewTiles += RecursiveHoleSizeCounter(xPosition + 1, yPosition, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }
        // check down
        if (CheckIfTileEmpty(temporaryHelperArray, xPosition, yPosition + 1)) {
            countNewTiles += RecursiveHoleSizeCounter(xPosition, yPosition + 1, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }
        // check left
        if (CheckIfTileEmpty(temporaryHelperArray, xPosition - 1, yPosition)) {
            countNewTiles += RecursiveHoleSizeCounter(xPosition - 1, yPosition, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }
        // check up
        if (CheckIfTileEmpty(temporaryHelperArray, xPosition, yPosition - 1)) {
            countNewTiles += RecursiveHoleSizeCounter(xPosition, yPosition - 1, countNewTiles + total, cutoffLength);

            if ((countNewTiles + total) >= checkLimit) {
                return countNewTiles;
            }
        }

        return countNewTiles;
    }

    private static ArrayList<Integer> TurnArrayIntoList(int[] array) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int k = 0; k < array.length; k++) {
            arrayList.add(array[k]);
        }
        return arrayList;
    }

    private static int[][] GetFirstHoleShapeOfSizeFive(int[][] currentStateOfTheMap) {
        int[][] currentStateOfTheMapCopy = GetACopyOfThisMatrix(currentStateOfTheMap);

        int xMapSize = currentStateOfTheMapCopy[0].length;
        int yMapSize = currentStateOfTheMapCopy.length;
        for (int i = 0; i < yMapSize; i++) {
            for (int j = 0; j < xMapSize; j++) {
                if (CheckIfTileEmpty(currentStateOfTheMapCopy, j, i)) {

                    temporaryHelperArray = GetACopyOfThisMatrix(currentStateOfTheMapCopy);
                    int currentHoleSize = RecursiveHoleSizeCounter(j, i, 0, yMapSize * xMapSize);
                    if (currentHoleSize == 5) {
                        int[][] emptyGrid = GetEmptyMap(xMapSize, yMapSize);
                        int[][] holeShape = RecursiveHoleShapeCounter(j, i, emptyGrid, currentStateOfTheMapCopy);
                        return holeShape;
                    }
                }
            }
        }
        return null;
    }

    private static int[][] RecursiveHoleShapeCounter(int xPosition, int yPosition, int[][] currentShapeSaved,
            int[][] currentStateOfTheMap) {
        // Modify map array and check break condition
        currentStateOfTheMap[yPosition][xPosition] = 1; // Mark its own tile as already checked
        currentShapeSaved[yPosition][xPosition] = 1;
        // check right
        if (CheckIfTileEmpty(currentStateOfTheMap, xPosition + 1, yPosition)) {
            RecursiveHoleShapeCounter(xPosition + 1, yPosition, currentShapeSaved, currentStateOfTheMap);
        }
        // check down
        if (CheckIfTileEmpty(currentStateOfTheMap, xPosition, yPosition - 1)) {
            RecursiveHoleShapeCounter(xPosition, yPosition - 1, currentShapeSaved, currentStateOfTheMap);
        }
        // Check left
        if (CheckIfTileEmpty(currentStateOfTheMap, xPosition - 1, yPosition)) {
            RecursiveHoleShapeCounter(xPosition - 1, yPosition, currentShapeSaved, currentStateOfTheMap);
        }
        // check up
        if (CheckIfTileEmpty(currentStateOfTheMap, xPosition, yPosition + 1)) {
            RecursiveHoleShapeCounter(xPosition, yPosition + 1, currentShapeSaved, currentStateOfTheMap);
        }

        return currentShapeSaved;
    }

    private static int[][] TrimShape(int[][] inputShape) {
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
        int minColumnIndex = GetMinimumValueFromArrayList(nonEmptyColumnIndexes);
        int maxColumnIndex = GetMaximumValueFromArrayList(nonEmptyColumnIndexes);
        int minRowIndex = GetMinimumValueFromArrayList(nonEmptyRowIndexes);
        int maxRowIndex = GetMaximumValueFromArrayList(nonEmptyRowIndexes);

        int xMapSize = maxColumnIndex - minColumnIndex + 1;
        int yMapSize = maxRowIndex - minRowIndex + 1;

        int[][] returnArray = GetEmptyMap(xMapSize, yMapSize);

        for (int x = 0; x < xMapSize; x++) {
            for (int y = 0; y < yMapSize; y++) {
                returnArray[y][x] = inputShape[y + minRowIndex][x + minColumnIndex];
            }
        }

        return returnArray;
    }

    private static ArrayList<int[]> GetAvailableRowAndColumnIndexes(int[][] inputMatrix, int shapeLength,
            int shapeHeight) {
        int ySize = inputMatrix.length;
        int xSize = inputMatrix[0].length;
        int[] nonFilledRowIndexes = CreateArrayWithINumberofIValues(ySize);
        int[] nonFilledColumnIndexes = CreateArrayWithINumberofIValues(xSize);

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

        nonFilledRowIndexes = RemoveValueFromArray(nonFilledRowIndexes, -1);
        nonFilledColumnIndexes = RemoveValueFromArray(nonFilledColumnIndexes, -1);
        // Check, how many rows and columns are left

        // Add the arrays to the return list
        ArrayList<int[]> returnArrayList = new ArrayList<>();
        returnArrayList.add(nonFilledRowIndexes);
        returnArrayList.add(nonFilledColumnIndexes);

        return returnArrayList;
    }

    private static int[] RemoveValueFromArray(int[] array, int value) {
        ArrayList<Integer> copiedList = TurnArrayIntoList(array);

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

    private static int[] CreateArrayWithINumberofIValues(int length) {
        int[] nonFilledRowIndexes = new int[length];

        for (int i = 0; i < length; i++) {
            nonFilledRowIndexes[i] = i;
        }
        return nonFilledRowIndexes;
    }

    private static int GetMinimumValueFromArrayList(ArrayList<Integer> arrayList) {
        int currentMinimum = arrayList.get(0);
        for (int i = 0; i < arrayList.size(); i++) {
            int currentArrayElement = arrayList.get(i);
            if (currentMinimum > currentArrayElement) {
                currentMinimum = currentArrayElement;
            }
        }
        return currentMinimum;
    }

    private static int GetMaximumValueFromArrayList(ArrayList<Integer> arrayList) {
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

        if (yMapSize >= xMapSize) {
            currentMapArray = GetEmptyMap(xMapSize, yMapSize);
        } else {
            currentMapArray = GetEmptyMap(yMapSize, xMapSize);
        }
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
        System.out.println("Printing new Matrix");
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

    private static int[][] GetFirstHoleShape(int[][] currentStateOfTheMap) {
        temporaryHelperArray = GetACopyOfThisMatrix(currentStateOfTheMap);

        for (int i = 0; i < temporaryHelperArray.length; i++) {
            for (int j = 0; j < temporaryHelperArray[i].length; j++) {
                if (CheckIfTileEmpty(temporaryHelperArray, j, i)) {

                    int[][] emptyGrid = GetEmptyMap(temporaryHelperArray[i].length, temporaryHelperArray.length);
                    return RecursiveHoleShapeCounter(j, i, emptyGrid, temporaryHelperArray);

                }
            }
        }
        return null;
    }

    private static void PrintArrayContentsInChat(int[] arrayToPrint) {
        for (int i = 0; i < arrayToPrint.length; i++) {
            System.out.print(arrayToPrint[i] + " ");
        }
        System.out.println("");
    }

    private static ArrayList<ArrayList<Integer>> TurnMatrixInto2List(int[][] array) {
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

    private static ArrayList<ArrayList<ArrayList<Integer>>> Turn3ArrayInto3List(int[][][] array) {
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

    private static int[] GetACopyOfThisArray(int[] arrayToCopy) {
        int[] returnArray = new int[arrayToCopy.length];
        for (int j = 0; j < returnArray.length; j++) {
            returnArray[j] = arrayToCopy[j];
        }
        return returnArray;
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
        temporaryHelperArray = GetACopyOfThisMatrix(inputMatrix);

        for (int i = 0; i < temporaryHelperArray.length; i++) {
            for (int j = 0; j < temporaryHelperArray[i].length; j++) {
                if (CheckIfTileEmpty(temporaryHelperArray, j, i)) {
                    if (RecursiveHoleSizeCounter(j, i, 0, maxLinkLength) >= maxLinkLength) {
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
        System.out.println("Printing arrayList: ");
        for (int i = 0; i < arrayListToPrint.size(); i++) {
            PrintMatrixContentsInChat(arrayListToPrint.get(i));
        }
    }

    // Checks, if there is a link on the map of this size or shorter
    private static boolean CheckMatrixForLinksOfLengthAtMost(int[][] inputMatrix, int maxLinkLength) {
        temporaryHelperArray = GetACopyOfThisMatrix(inputMatrix);

        int yMapSize = temporaryHelperArray.length;
        for (int i = 0; i < yMapSize; i++) {

            int xMapSize = temporaryHelperArray[i].length;
            for (int j = 0; j < xMapSize; j++) {

                if (CheckIfTileEmpty(temporaryHelperArray, j, i)) {

                    if (RecursiveHoleSizeCounter(j, i, 0, yMapSize * xMapSize) <= maxLinkLength) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}