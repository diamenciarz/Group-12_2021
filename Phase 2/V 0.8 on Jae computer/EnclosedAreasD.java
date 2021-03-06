public class EnclosedAreasD {

    private boolean doDebugMessages = false;

    public static void main(String[] args) {
        EnclosedAreasD e = new EnclosedAreasD();
        int[][] map = {
                { 0, 0, 0, 0, 0 },
                { 0, 2, 0, 0, 0 },
                { 0, 2, 2, 0, 0 },
                { 0, 0, 2, 0, 0 },
                { 2, 0, 2, 0, 0 },
        };
        System.out.println(e.countConnectionsBetweenBlocks(map));
    }

    // region Recursive methods

    public int countEnclosedAreas(int[][] mapMatrix) {
        int[][] temporaryHelperArray = HelperMethods.getACopyOfThisMatrix(mapMatrix);
        int enclosedAreas = 0;
        int yMapSize = temporaryHelperArray.length;
        for (int i = 0; i < yMapSize; i++) {
            int xMapSize = temporaryHelperArray[i].length;
            for (int j = 0; j < xMapSize; j++) {
                if (checkIfCanGo(temporaryHelperArray, j, i)) {
                    int holeSize = recursiveHoleSizeCounter(j, i, 0, temporaryHelperArray);
                    if (i != 0)
                        enclosedAreas += holeSize;
                }
            }
        }
        return enclosedAreas;
    }

    private int recursiveHoleSizeCounter(int xPosition, int yPosition, int total, int[][] mapState) {
        // Initialization variables
        int checkLimit = mapState.length * mapState[0].length;
        int countNewTiles = 1;
        // Modify map array and check break condition
        mapState[yPosition][xPosition] = 1; // Mark its own tile as already checked
        if ((countNewTiles + total) >= checkLimit)
            return countNewTiles;
        // check right
        if (checkIfCanGo(mapState, xPosition + 1, yPosition)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition + 1, yPosition, countNewTiles + total, mapState);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        // check down
        if (checkIfCanGo(mapState, xPosition, yPosition + 1)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition, yPosition + 1, countNewTiles + total, mapState);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        // check left
        if (checkIfCanGo(mapState, xPosition - 1, yPosition)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition - 1, yPosition, countNewTiles + total, mapState);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        // check up
        if (checkIfCanGo(mapState, xPosition, yPosition - 1)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition, yPosition - 1, countNewTiles + total, mapState);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        return countNewTiles;
    }

    // region Helper methods

    private boolean checkIfCanGo(int[][] inputArray, int xPosition, int yPosition) {
        if (isOutOfBounds(inputArray, xPosition, yPosition)) {
            return false;
        }
        return checkIfTileEmpty(inputArray, xPosition, yPosition);
    }

    private boolean checkIfTileEmpty(int[][] inputArray, int xPosition, int yPosition) {
        boolean tileEmpty = inputArray[yPosition][xPosition] == 0;
        if (tileEmpty)
            return true;
        else
            return false;
    }

    private boolean isOutOfBounds(int[][] inputArray, int xPosition, int yPosition) {
        boolean yOutOfBounds = yPosition >= inputArray.length || yPosition < 0;
        if (yOutOfBounds)
            return true;
        boolean xOutOfBounds = xPosition >= inputArray[yPosition].length || xPosition < 0;
        if (xOutOfBounds)
            return true;
        return false;
    }

    private boolean isTouchingItself(int[][] inputArray, int xPosition, int yPosition) {
        boolean tileEmpty = inputArray[yPosition][xPosition] == 12;
        if (tileEmpty)
            return true;
        else
            return false;
    }

    public int countRoofs(int[][] mapMatrix) {
        int[][] temporaryHelperArray = HelperMethods.getACopyOfThisMatrix(mapMatrix);
        int roofPoints = 0;
        int xMapSize = temporaryHelperArray[0].length;
        for (int j = 0; j < xMapSize; j++) {
            if (checkIfCanGo(temporaryHelperArray, j, 0)) {
                int counted = recursiveRoofCounter(j, 0, 0, temporaryHelperArray, false);
                roofPoints += counted;
            }
        }
        return roofPoints;
    }

    private int recursiveRoofCounter(int xPosition, int yPosition, int total, int[][] mapState,
            boolean movedHorizontally) {
        // Initialization variables
        // Modify map array and check break condition
        mapState[yPosition][xPosition] = 12; // Mark its own tile as already checked
        int checkLimit = mapState.length * mapState[0].length;
        int countNewTiles = 0;
        if (doDebugMessages) {
            HelperMethods.printMatrixContentsInChatUsingLetters(mapState);
        }
        if ((countNewTiles + total) >= checkLimit)
            return countNewTiles;
        if (movedHorizontally) {
            int roof = checkRoofHeight(mapState, xPosition, yPosition);
            int floor = checkFloorDepth(mapState, xPosition, yPosition);
            if (roof > 0 && floor > 0) {
                countNewTiles++;
            }
            if (roof > 0) {
                countNewTiles += roof - 1;
                if (floor > 0) {
                    countNewTiles += floor - 1;
                }
            }
        }
        // check right
        if (checkIfCanGo(mapState, xPosition + 1, yPosition)) {
            countNewTiles += recursiveRoofCounter(xPosition + 1, yPosition, countNewTiles + total, mapState, true);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        // check down
        if (checkIfCanGo(mapState, xPosition, yPosition + 1)) {
            countNewTiles += recursiveRoofCounter(xPosition, yPosition + 1, countNewTiles + total, mapState, false);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        // check left
        if (checkIfCanGo(mapState, xPosition - 1, yPosition)) {
            countNewTiles += recursiveRoofCounter(xPosition - 1, yPosition, countNewTiles + total, mapState, true);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        // check up
        if (checkIfCanGo(mapState, xPosition, yPosition - 1)) {
            countNewTiles += recursiveRoofCounter(xPosition, yPosition - 1, countNewTiles + total, mapState, false);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        if (doDebugMessages) {
            System.out.println("Count: " + countNewTiles);
        }
        return countNewTiles;
    }

    private int checkRoofHeight(int[][] mapState, int xPosition, int yPosition) {
        if (!(isOutOfBounds(mapState, xPosition, yPosition - 1)
                || isTouchingItself(mapState, xPosition, yPosition - 1))) {
            if (checkIfTileEmpty(mapState, xPosition, yPosition - 1)) {
                int returnedValue = checkRoofHeight(mapState, xPosition, yPosition - 1);
                if (returnedValue == 0) {
                    return 0;
                } else {
                    return returnedValue + 1;
                }
            } else {
                // If reached roof - return 1
                return 1;
            }
        } else {
            // Went outside boundsg - return 0
            return 0;
        }
    }

    private int checkFloorDepth(int[][] mapState, int xPosition, int yPosition) {
        if (!(isOutOfBounds(mapState, xPosition, yPosition + 1)
                || isTouchingItself(mapState, xPosition, yPosition + 1))) {
            if (checkIfTileEmpty(mapState, xPosition, yPosition + 1)) {
                int returnedValue = checkFloorDepth(mapState, xPosition, yPosition + 1);
                if (returnedValue == 0) {
                    return 0;
                } else {
                    return returnedValue + 1;
                }
            } else {
                // If reached floor - return 1
                return 1;
            }
        } else {
            // Went outside bounds - return 1
            return 1;
        }
    }

    // endregion

    // endregion

    // region Check if connected
    public int countBlocksNextToWalls(int[][] mapMatrix) {
        int mapHeight = mapMatrix.length;
        int mapLength = mapMatrix[0].length;
        int points = 0;
        for (int i = 0; i < mapHeight; i++) {
            if (mapMatrix[i][0] != 0) {
                points++;
            }
        }
        for (int i = 0; i < mapHeight; i++) {
            if (mapMatrix[i][mapLength - 1] != 0) {
                points++;
            }
        }
        return points;
    }

    public int countConnectedBlocks(int[][] mapMatrix) {
        int mapHeight = mapMatrix.length;
        int mapLength = mapMatrix[0].length;
        int points = 0;
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapLength; x++) {
                if (isConnected(mapMatrix, x, y)) {
                    points++;
                }
            }
        }
        return points;
    }

    private boolean isConnected(int[][] inputArray, int xPosition, int yPosition) {
        // Check right
        if (checkIfIsTouching(inputArray, xPosition + 1, yPosition)) {
            return true;
        }
        // Check left
        if (checkIfIsTouching(inputArray, xPosition - 1, yPosition)) {
            return true;
        }
        // Check top
        if (checkIfIsTouching(inputArray, xPosition, yPosition + 1)) {
            return true;
        }
        // Check bottom
        if (checkIfIsTouching(inputArray, xPosition, yPosition - 1)) {
            return true;
        }
        return false;
    }
    // endregion

    // region Count connections
    public int countConnectionsBetweenBlocks(int[][] mapMatrix) {
        int mapHeight = mapMatrix.length;
        int mapLength = mapMatrix[0].length;
        int points = 0;
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapLength; x++) {
                points += checkConnections(mapMatrix, x, y);
            }
        }
        return points;
    }

    private int checkConnections(int[][] inputArray, int xPosition, int yPosition) {
        int connections = 0;
        // Check right
        if (checkIfIsTouching(inputArray, xPosition + 1, yPosition)) {
            connections++;
        }
        // Check left
        if (checkIfIsTouching(inputArray, xPosition - 1, yPosition)) {
            connections++;
        }
        // Check top
        if (checkIfIsTouching(inputArray, xPosition, yPosition + 1)) {
            connections++;
        }
        // Check bottom
        if (checkIfIsTouching(inputArray, xPosition, yPosition - 1)) {
            connections++;
        }
        return connections;
    }

    // endregion

    // region Helper methods
    private boolean checkIfIsTouching(int[][] inputArray, int xPosition, int yPosition) {
        if (isOutOfBounds(inputArray, xPosition, yPosition)) {
            return false;
        }
        return !checkIfTileEmpty(inputArray, xPosition, yPosition);
    }
    // endregion

}
