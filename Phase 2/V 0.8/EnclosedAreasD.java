public class EnclosedAreasD {

    public static void main(String[] args) {
        EnclosedAreasD e = new EnclosedAreasD();
        int[][] map = { { 0, 0, 0, 0, 0 },
                { 0, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 1 },
                { 1, 1, 1, 0, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 0, 0, 1, 0 },
                { 1, 0, 0, 1, 0 }
        };
        System.out.println(e.countEnclosedAreas(map));
    }

    private int countEnclosedAreas(int[][] mapMatrix) {
        int[][] temporaryHelperArray = mapMatrix;
        int enclosedAreas = 0;
        int yMapSize = temporaryHelperArray.length;
        for (int i = 0; i < yMapSize; i++) {

            int xMapSize = temporaryHelperArray[i].length;
            for (int j = 0; j < xMapSize; j++) {

                if (checkIfTileEmpty(temporaryHelperArray, j, i)) {

                    int holeSize = recursiveHoleSizeCounter(j, i, 0, temporaryHelperArray);
                    if (i != 0)
                        enclosedAreas += holeSize;
                }
            }
        }
        return enclosedAreas;
    }

    private int recursiveHoleSizeCounter(int xPosition, int yPosition, int total,
            int[][] mapState) {
        // Initialization variables
        int checkLimit = mapState.length * mapState[0].length;
        int countNewTiles = 1;
        // Modify map array and check break condition
        mapState[yPosition][xPosition] = 1; // Mark its own tile as already checked
        if ((countNewTiles + total) >= checkLimit)
            return countNewTiles;
        // check right
        if (checkIfTileEmpty(mapState, xPosition + 1, yPosition)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition + 1, yPosition, countNewTiles + total,
                    mapState);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        // check down
        if (checkIfTileEmpty(mapState, xPosition, yPosition + 1)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition, yPosition + 1, countNewTiles + total,
                    mapState);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        // check left
        if (checkIfTileEmpty(mapState, xPosition - 1, yPosition)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition - 1, yPosition, countNewTiles + total,
                    mapState);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        // check up
        if (checkIfTileEmpty(mapState, xPosition, yPosition - 1)) {
            countNewTiles += recursiveHoleSizeCounter(xPosition, yPosition - 1, countNewTiles + total,
                    mapState);
            if ((countNewTiles + total) >= checkLimit)
                return countNewTiles;
        }
        return countNewTiles;
    }

    private boolean checkIfTileEmpty(int[][] inputArray, int xPosition, int yPosition) {
        boolean yOutOfBounds = yPosition >= inputArray.length || yPosition < 0;
        if (yOutOfBounds)
            return false;
        boolean xOutOfBounds = xPosition >= inputArray[yPosition].length || xPosition < 0;
        if (xOutOfBounds)
            return false;
        boolean tileEmpty = inputArray[yPosition][xPosition] == 0;
        if (!tileEmpty)
            return false;
        else
            return true;
    }
}
