import java.util.ArrayList;
import javax.swing.Timer; 
import java.lang.Thread;

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
    static int currentShapeYPosition;
    private static char lastKeyPressed = 't';
    private static boolean setupStartingVariables = false;
    public static int score;

    private static ArrayList<int[][]> shapesQueue = new ArrayList<>();

    // Timer variables
    public static TimeListener myListener = new TimeListener();
    public static Timer spaceTime = new Timer(10, myListener);
    public static Timer normalTime = new Timer(1000, myListener);
    final int startDelay = 1000;
    static boolean spaceBar = false;
     
    public static void main(String[] args) throws InterruptedException {

        startProgram();
    }

    // Startup methods
    private static void startProgram() throws InterruptedException {
        // Setup methods
        ResetMap();
        
        int[][] shapePlacedOnGrid = HelperMethods.placeShapeOnMatrix(GetEmptyMap(), currentShape, currentShapeXPosition,
                currentShapeYPosition);
        ui.updateGrid(shapePlacedOnGrid);
        // Enter Loop
        setupStartingVariables = true;

        // Timer start
        Thread.sleep(1000);
        normalTime.start();;
        
    }

    private static void ResetMap() {
        System.out.println("reset map");
        setupEmptyMap();
        takeNextShapeFromQueue();
    }

    // after space bar pressed and piece reaches bottom
    private static void backToNormalTime() {
        spaceTime.stop();;
        normalTime.start();
    }

    private static void setupEmptyMap() {
        currentMapMatrix = HelperMethods.getEmptyMatrix(xMapSize, yMapSize);
    }

    private static void takeNextShapeFromQueue() {
        // after space bar pressed and piece reaches bottom
        backToNormalTime();

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
        // if (input == ' ') {
            
        //     //normalTime.stop();
        //     //spaceTime.start();
        //     //pressedKey('s');
        // }
        currentMapMatrix = HelperMethods.deleteRows(currentMapMatrix);

        displayShapeOnMap(currentShape, currentShapeXPosition, currentShapeYPosition);
    }

    static void tryMoveCurrentShape(int deltaX, int deltaY) {
        boolean doesCurrentShapeCollideWithBorders = !HelperMethods.willThisShapeFitOnMap(currentMapMatrix,
                currentShape, currentShapeXPosition + deltaX, currentShapeYPosition + deltaY);
        if (!doesCurrentShapeCollideWithBorders) {
            currentShapeXPosition += deltaX;
            currentShapeYPosition += deltaY;

            int shapeBottomIndex = currentShapeYPosition + currentShape.length + deltaY;
            if (shapeBottomIndex > currentMapMatrix.length) {

                if (doesCurrentShapePositionOverlapBlocks()) {
                    handleCollisionWithBlocks();

                }else{

                    placeShapeOnMap(currentShape, currentShapeXPosition, currentShapeYPosition);
                    takeNextShapeFromQueue();
                
                }

            } else {
                handleCollisionWithBlocks();

            }
        }
    }

    private static void displayShapeOnMap(int[][] shape, int xPosition, int yPosition) {
        int[][] displayMatrix = HelperMethods.placeShapeOnMatrix(currentMapMatrix, shape, xPosition, yPosition);
        ui.updateGrid(displayMatrix);
    }

    private static void tryRotateCurrentShape(int rotation) {
        int[][] rotatedShape = HelperMethods.rotateShapeRight(currentShape, rotation);
        boolean isRotatedShapeInBounds = HelperMethods.willThisShapeFitOnMap(currentMapMatrix, rotatedShape,
                currentShapeXPosition, currentShapeYPosition);

        if (isRotatedShapeInBounds) {
            currentShape = rotatedShape;
        }
        handleCollisionWithBlocks();
    }

    private static void handleCollisionWithBlocks() {
        if (doesCurrentShapePositionOverlapBlocks()) {
            placeShapeOnMap(lastShapeState, 0, 0);
            takeNextShapeFromQueue();

        } else {
            displayShapeOnMap(currentShape, currentShapeXPosition, currentShapeYPosition);
        }
    }

    private static boolean doesCurrentShapePositionOverlapBlocks() {
        int[][] shapePlacedOnGrid = HelperMethods.placeShapeOnMatrix(GetEmptyMap(), currentShape, currentShapeXPosition,
                currentShapeYPosition);

        if (HelperMethods.areTwoFiguresOverlapping(currentMapMatrix, shapePlacedOnGrid)) {
            return true;
        }
        return false;
    }

    private static void placeShapeOnMap(int[][] shape, int xPosition, int yPosition) {
        currentMapMatrix = HelperMethods.placeShapeOnMatrix(currentMapMatrix, shape, xPosition, yPosition);
    }

    private static void resetCurrentShapePosition() {
        currentShapeXPosition = 0;
        currentShapeYPosition = 0;
    }

    private static int[][] GetEmptyMap() {
        return HelperMethods.getEmptyMatrix(xMapSize, yMapSize);
    }
   
    
}



