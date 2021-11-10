import java.util.ArrayList;

public class Pentis {

    static ShapeContainer shapeContainer = new ShapeContainer();
    static UI ui = new UI();
    public static int xMapSize = 8;
    public static int yMapSize = 20;

    // Private variables
    private static int[][] currentShape;
    private static int[][] currentMapMatrix;
    private static int[][] lastShapeState;
    private static int currentShapeXPosition;
    private static int currentShapeYPosition;
    private static char lastKeyPressed = 't';
    private static boolean setupStartingVariables = false;
    public static int score;

    private static ArrayList<int[][]> shapesQueue = new ArrayList<>();

    public static void main(String[] args) {

        startProgram();
    }

    // Startup methods
    private static void startProgram() {
        // Setup methods
        ResetMap();

        int[][] shapePlacedOnGrid = HelperMethods.placeShapeOnMatrix(GetEmptyMap(), currentShape, currentShapeXPosition,
                currentShapeYPosition);
        ui.updateGrid(shapePlacedOnGrid);
        // Enter Loop
        setupStartingVariables = true;
    }

    private static void ResetMap() {
        System.out.println("reset map");
        setupEmptyMap();
        takeNextShapeFromQueue();
    }

    private static void setupEmptyMap() {
        currentMapMatrix = HelperMethods.getEmptyMatrix(xMapSize, yMapSize);
    }

    private static void takeNextShapeFromQueue() {
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
            tryMoveCurrentShape();
        }
    }

    private static void tryMoveCurrentShape() {
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
        if (input == 'o') {
        }
        currentMapMatrix = HelperMethods.deleteRows(currentMapMatrix);

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
        ui.updateGrid(displayMatrix);
    }

    private static void resetCurrentShapePosition() {
        currentShapeXPosition = 0;
        currentShapeYPosition = 0;
    }

    private static int[][] GetEmptyMap() {
        return HelperMethods.getEmptyMatrix(xMapSize, yMapSize);
    }
}
