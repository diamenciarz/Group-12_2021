import java.util.ArrayList;

import javax.swing.Timer;
import java.lang.Thread;

public class Pentis {

    static UI ui = new UI();
    public static int xMapSize = 5;
    public static int yMapSize = 12;
    public static int score;
    public static boolean isRowFull = false;
    public static ArrayList<Integer> highScores = new ArrayList<Integer>();
    public static int[] sortedHighscores = new int[5];

    // Private variables
    private static int[][] currentShape;
    private static int[][] currentMapMatrix;
    private static int[][] lastShapeState;
    private static int currentShapeXPosition;
    private static int currentShapeYPosition;
    private static char lastKeyPressed = 't';
    private static boolean setupStartingVariables = false;
    private static ArrayList<int[][]> shapesQueue = new ArrayList<>();

    // Timer variables
    public static TimeListener myListener = new TimeListener();
    public static Timer spaceTime = new Timer(10, myListener);
    public static Timer normalTime = new Timer(1000, myListener);
    final static int startDelay = 1000;

    public static void main(String[] args) throws InterruptedException {

        startProgram();
        // highScores.add(0);
    }

    // Startup methods
    private static void startProgram() throws InterruptedException {
        // Setup methods
        ResetMap();

        int[][] shapePlacedOnGrid = HelperMethods.placeShapeOnMatrix(GetEmptyMap(), currentShape, currentShapeXPosition,
                currentShapeYPosition);
        ui.updateGrid(shapePlacedOnGrid, score);
        // Enter Loop
        setupStartingVariables = true;

        // Timer start
        doSlowTimer();
        Thread.sleep(startDelay);
    }

    private static void ResetMap() {
        setupEmptyMap();
        takeNextShapeFromQueue();
        highScores.add(score);
        sortedHighscores = HelperMethods.highScoreOrdered(highScores);
        score = 0;
    }

    private static void setupEmptyMap() {
        currentMapMatrix = HelperMethods.getEmptyMatrix(xMapSize, yMapSize);
    }

    private static void takeNextShapeFromQueue() {
        doSlowTimer();

        if (shapesQueue.size() < 12) {
            add12ShapesToQueue();
        }
        currentShape = shapesQueue.get(0);
        shapesQueue.remove(0);

        resetCurrentShapePosition();

        if (!doesNextShapeFitOnMap()) {

            ResetMap();
        }

    }

    private static boolean doesNextShapeFitOnMap() {
        int[][] shapePlacedOnMap = HelperMethods.placeShapeOnMatrix(GetEmptyMap(), currentShape, currentShapeXPosition,
                currentShapeYPosition);
        if (!HelperMethods.areTwoFiguresOverlapping(currentMapMatrix, shapePlacedOnMap)) {
            return true;
        }
        return false;
    }

    private static void add12ShapesToQueue() {
        shapesQueue.addAll(ShapeContainer.get12ShuffledShapes());
    }

    // Key pressed event methods -- called after each button press
    public static void pressedKey(char key) {
        lastKeyPressed = key;

        if (setupStartingVariables) {
            saveLastShapeState();
        }
    }

    private static void saveLastShapeState() {
        lastShapeState = HelperMethods.placeShapeOnMatrix(GetEmptyMap(), currentShape, currentShapeXPosition,
                currentShapeYPosition);

        doInput(lastKeyPressed);
    }

    private static void doInput(char input) {

        // Checks grid border collisions
        if (input == 'a') {
            tryMoveCurrentShape(-1, 0);
        }
        if (input == 's') {
            resetTimer();
            tryMoveCurrentShape(0, 1);
        }
        if (input == 'd') {
            tryMoveCurrentShape(1, 0);
        }
        if (input == 'q') {
            tryRotateCurrentShape(3);
        }
        if (input == 'e') {
            tryRotateCurrentShape(1);
        }
        if (input == ' ') {
            doFastTimer();
        }
        if (input == 'o') {
        }

        displayShapeOnMap(currentShape, currentShapeXPosition, currentShapeYPosition);
    }

    private static void tryMoveCurrentShape(int deltaX, int deltaY) {
        // Check collision with borders
        boolean doesCurrentShapeCollideWithBorders = !HelperMethods.willThisShapeFitOnMap(currentMapMatrix,
                currentShape, currentShapeXPosition + deltaX, currentShapeYPosition + deltaY);

        if (!doesCurrentShapeCollideWithBorders) {
            currentShapeXPosition += deltaX;
            currentShapeYPosition += deltaY;

            boolean isMovingSideways = deltaX != 0;
            if (isMovingSideways) {
                checkSideCollision(deltaX, deltaY);
            } else {
                checkBottomCollision(deltaY);
            }
        }
        currentMapMatrix = HelperMethods.deleteRows(currentMapMatrix, true);

    }

    private static void checkSideCollision(int deltaX, int deltaY) {
        if (doesShapePositionOverlapBlocks(currentShape)) {
            currentShapeXPosition -= deltaX;
            currentShapeYPosition -= deltaY;
        }
    }

    private static void checkBottomCollision(int deltaY) {
        int shapeBottomIndex = currentShapeYPosition + currentShape.length + deltaY;
        boolean collidedWitBottomBorder = shapeBottomIndex > currentMapMatrix.length;
        if (collidedWitBottomBorder) {
            handleCollisionWithBottomBorder();
        } else {
            handleCollisionWithBlocks();
        }
    }

    private static void tryRotateCurrentShape(int rotation) {
        int[][] rotatedShape = HelperMethods.rotateShapeRight(currentShape, rotation);
        int deltaXToMove = getDeltaXPositionToMoveShapeInBounds(rotatedShape);

        boolean shapeOutOfBounds = deltaXToMove > 0;
        int xPosition = currentShapeXPosition;
        if (shapeOutOfBounds) {
            xPosition -= deltaXToMove;
        }
        if (!doesShapePositionOverlapBlocks(rotatedShape, xPosition, currentShapeYPosition)) {

            if (shapeOutOfBounds) {
                currentShapeXPosition -= deltaXToMove;
            }
            currentShape = rotatedShape;
            displayShapeOnMap(currentShape, currentShapeXPosition, currentShapeYPosition);
        }
    }

    private static int getDeltaXPositionToMoveShapeInBounds(int[][] shape) {
        int shapeRightIndex = currentShapeXPosition + shape[0].length;
        int deltaPosition = shapeRightIndex - currentMapMatrix[0].length;

        return deltaPosition;
    }

    private static void handleCollisionWithBlocks() {
        if (doesShapePositionOverlapBlocks(currentShape)) {
            // Place the last shape position on the map
            placeShapeOnMap(lastShapeState, 0, 0);
            takeNextShapeFromQueue();
        } else {
            // Display the moved shape. It is placed correctly
            displayShapeOnMap(currentShape, currentShapeXPosition, currentShapeYPosition);
        }
    }

    private static void handleCollisionWithBottomBorder() {
        if (doesShapePositionOverlapBlocks(currentShape)) {
            handleCollisionWithBlocks();
        } else {
            placeShapeOnMap(currentShape, currentShapeXPosition, currentShapeYPosition);
            takeNextShapeFromQueue();
        }
    }

    private static boolean doesShapePositionOverlapBlocks(int[][] shape) {
        int[][] shapePlacedOnGrid = HelperMethods.placeShapeOnMatrix(GetEmptyMap(), shape, currentShapeXPosition,
                currentShapeYPosition);

        if (HelperMethods.areTwoFiguresOverlapping(currentMapMatrix, shapePlacedOnGrid)) {
            return true;
        }
        return false;
    }

    private static boolean doesShapePositionOverlapBlocks(int[][] shape, int xPosition, int yPosition) {
        int[][] shapePlacedOnGrid = HelperMethods.placeShapeOnMatrix(GetEmptyMap(), shape, xPosition, yPosition);

        if (HelperMethods.areTwoFiguresOverlapping(currentMapMatrix, shapePlacedOnGrid)) {
            return true;
        }
        return false;
    }

    private static void placeShapeOnMap(int[][] shape, int xPosition, int yPosition) {
        currentMapMatrix = HelperMethods.placeShapeOnMatrix(currentMapMatrix, shape, xPosition, yPosition);
    }

    private static void displayShapeOnMap(int[][] shape, int xPosition, int yPosition) {
        int[][] displayMatrix = HelperMethods.placeShapeOnMatrix(currentMapMatrix, shape, xPosition, yPosition);
        ui.updateGrid(displayMatrix, score);
    }

    private static void resetCurrentShapePosition() {
        currentShapeXPosition = 0;
        currentShapeYPosition = 0;
    }

    private static int[][] GetEmptyMap() {
        return HelperMethods.getEmptyMatrix(xMapSize, yMapSize);
    }

    public static void doFastTimer() {
        spaceTime.start();
        normalTime.stop();
    }

    public static void doSlowTimer() {
        spaceTime.stop();
        normalTime.start();
    }

    public static void resetTimer() {
        normalTime.restart();
    }
}
