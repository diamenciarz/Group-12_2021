import java.util.ArrayList;

public class BotPentis {

    static UI ui = new UI();
    public static int xMapSize = 5;
    public static int yMapSize = 12;

    // Private variables
    private static int[][] currentShape;
    private static int[][] currentMapMatrix;
    private static int[][] lastShapeState;
    private static int currentShapeXPosition;
    private static int currentShapeYPosition;
    public static int score;

    private static ArrayList<int[][]> shapesQueue = new ArrayList<>();

    // Timer variables
    final static int START_DELAY = 1000;
    final static long DISPLAY_DELAY = 100;
    private static int gamesLeft = Integer.MAX_VALUE;
    private static boolean doDebugMessages = false;
    private static boolean displaySteps = true;


    // Bot
    public static BotListener botListener = new BotListener();

    /**
     * 
     * @return score from the game
     */
    public static double playGames(int amount, boolean setDisplaySteps) {
        displaySteps = setDisplaySteps;
        gamesLeft = amount;
        startBotGame();
        while (gamesLeft > 0) {
            takeNextShapeFromQueue();
            if (!doesNextShapeFitOnMap()) {
                resetGame();
            }
            makeMoves();
            //gamesLeft--;
        }
        //System.out.println("Game stopped");
        return score;
    }

    private static void startBotGame() {
        setupEmptyMap();
    }

    private static void makeMoves() {
        updateBotKeys();
        botListener.generateKeyPresses();
    }

    private static void resetGame() {
        setupEmptyMap();
        takeNextShapeFromQueue();
        resetScore();
        gamesLeft--;
    }

    private static void resetScore() {
        if (doDebugMessages) {
            // System.out.println("Score this game: " + score);
            System.out.println(score + ",");
        }
        AutomaticPlayer.totalScore += score;
        score = 0;
    }

    private static void setupEmptyMap() {
        currentMapMatrix = HelperMethods.getEmptyMatrix(xMapSize, yMapSize);
    }

    private static void takeNextShapeFromQueue() {
        checkShapeQueue();
        updateCurrentShape();
    }

    private static void checkShapeQueue() {
        if (shapesQueue.size() < 12) {
            add12ShapesToQueue();
        }
    }

    private static void updateCurrentShape() {
        currentShape = shapesQueue.get(0);
        shapesQueue.remove(0);
        resetCurrentShapePosition();
    }

    private static void updateBotKeys() {
        char[] chars = Bot.getMovementDirections(currentShape, currentMapMatrix);
        botListener.generateNewKeySequence(chars);
    }

    private static boolean doesNextShapeFitOnMap() {
        int[][] shapePlacedOnGrid = getCurrentShapePlacedOnGrid();

        if (!HelperMethods.areTwoFiguresOverlapping(currentMapMatrix, shapePlacedOnGrid)) {
            return true;
        }
        return false;
    }

    private static void add12ShapesToQueue() {
        shapesQueue.addAll(ShapeContainer.get12ShuffledShapes());
    }

    // Key pressed event methods -- called after each button press
    public static void pressedKey(char key) {
        doInput(key);
    }

    private static void saveLastShapeState() {
        lastShapeState = getCurrentShapePlacedOnGrid();
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
        if (input == ' ') {
            dropShapeOnMap();
        }
        displayMap();
        currentMapMatrix = HelperMethods.botDeleteRows(currentMapMatrix);
    }

    private static void dropShapeOnMap() {
        int yPositions = currentMapMatrix.length - currentShape.length;
        for (int i = 0; i < yPositions; i++) {
            if (HelperMethods.areTwoFiguresOverlapping(getCurrentShapePlacedOnGrid(), currentMapMatrix)) {
                return;
            }
            tryMoveCurrentShape(0, 1);
        }
    }

    private static int[][] getCurrentShapePlacedOnMap() {
        return HelperMethods.placeShapeOnMatrix(currentMapMatrix, currentShape, currentShapeXPosition,
                currentShapeYPosition);
    }

    private static int[][] getCurrentShapePlacedOnGrid() {
        return HelperMethods.placeShapeOnMatrix(GetEmptyMap(), currentShape, currentShapeXPosition,
                currentShapeYPosition);
    }

    private static void tryMoveCurrentShape(int deltaX, int deltaY) {
        saveLastShapeState();
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

        if(xPosition +rotatedShape[0].length >currentMapMatrix[0].length ) {
            xPosition--;
        } 

        if (!doesShapePositionOverlapBlocks(rotatedShape, xPosition, currentShapeYPosition)) {

            if (shapeOutOfBounds) {
                currentShapeXPosition -= deltaXToMove;
            }
            currentShape = rotatedShape;
        }
    }

    private static int getDeltaXPositionToMoveShapeInBounds(int[][] shape) {
        int shapeRightIndex = currentShapeXPosition + shape[0].length;
        int deltaPosition = shapeRightIndex - currentMapMatrix[0].length;

        return deltaPosition;
    }

    private static void handleCollisionWithBottomBorder() {
        if (doesShapePositionOverlapBlocks(currentShape)) {
            handleCollisionWithBlocks();
        } else
            placeShapeOnMap(currentShape, currentShapeXPosition, currentShapeYPosition);
    }

    private static void handleCollisionWithBlocks() {
        if (doesShapePositionOverlapBlocks(currentShape)) {
            // Place the last shape position on the map
            placeShapeOnMap(lastShapeState, 0, 0);
        }
    }

    private static boolean doesShapePositionOverlapBlocks(int[][] shape) {
        int[][] shapePlacedOnGrid = getCurrentShapePlacedOnGrid();
        if (HelperMethods.areTwoFiguresOverlapping(currentMapMatrix, shapePlacedOnGrid)) {
            return true;
        }
        return false;
    }

    private static boolean doesShapePositionOverlapBlocks(int[][] shape, int xPosition, int yPosition) {
        int[][] shapePlacedOnGrid = HelperMethods.placeShapeOnMatrix(GetEmptyMap(), currentShape, xPosition,
                yPosition);

        if (HelperMethods.areTwoFiguresOverlapping(currentMapMatrix, shapePlacedOnGrid)) {
            return true;
        }
        return false;
    }

    private static void placeShapeOnMap(int[][] shape, int xPosition, int yPosition) {
        // score++;
        currentMapMatrix = HelperMethods.placeShapeOnMatrix(currentMapMatrix, shape, xPosition, yPosition);
    }

    private static void displayMap() {
        if (displaySteps) {
            waitTime(DISPLAY_DELAY);
            ui.updateGrid(currentMapMatrix, score);
        }
    }

    private static void resetCurrentShapePosition() {
        currentShapeXPosition = 0;
        currentShapeYPosition = 0;
    }

    private static int[][] GetEmptyMap() {
        return HelperMethods.getEmptyMatrix(xMapSize, yMapSize);
    }
    private static void waitTime(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }
}