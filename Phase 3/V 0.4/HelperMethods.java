import java.util.ArrayList;

public class HelperMethods {

    // region Recursive methods
    /**
     *
     * @param x   column
     * @param y   row
     * @param z   layer
     * @param map
     * @return
     */
    public static int getHoleSize(Position pos, int[][][] map) {
        if (checkIfTileEmpty(map, pos)) {
            return recursiveHoleSizeCounter(pos, 0, HelperMethods.copy(map));
        } else {
            return 0;
        }
    }

    // Recursive empty space counter // Leave total = 0, when calling this method
    private static int recursiveHoleSizeCounter(Position pos, int total, int[][][] matrix) {
        // Initialization variables
        int foundEmpty = 1;
        // Modify map array and check break condition
        matrix[pos.z][pos.y][pos.x] = 1; // Mark its own tile as already checked
        // check right
        if (checkIfTileEmpty(matrix, new Position(pos, 1, 0, 0))) {
            foundEmpty += recursiveHoleSizeCounter(new Position(pos, 1, 0, 0), foundEmpty + total, matrix);
        }
        // check down
        if (checkIfTileEmpty(matrix, new Position(pos, 0, 1, 0))) {
            foundEmpty += recursiveHoleSizeCounter(new Position(pos, 0, 1, 0), foundEmpty + total, matrix);
        }
        // check left
        if (checkIfTileEmpty(matrix, new Position(pos, -1, 0, 0))) {
            foundEmpty += recursiveHoleSizeCounter(new Position(pos, -1, 0, 0), foundEmpty + total, matrix);
        }
        // check up
        if (checkIfTileEmpty(matrix, new Position(pos, 0, -1, 0))) {
            foundEmpty += recursiveHoleSizeCounter(new Position(pos, 0, -1, 0), foundEmpty + total, matrix);
        }
        // check forward
        if (checkIfTileEmpty(matrix, new Position(pos, 0, 0, 1))) {
            foundEmpty += recursiveHoleSizeCounter(new Position(pos, 0, 0, 1), foundEmpty + total, matrix);
        } // check back
        if (checkIfTileEmpty(matrix, new Position(pos, 0, 0, -1))) {
            foundEmpty += recursiveHoleSizeCounter(new Position(pos, 0, 0, -1), foundEmpty + total, matrix);
        }
        return foundEmpty;
    }

    /**
     *
     * @param arr
     * @param x   column
     * @param y   row
     * @param z   layer
     * @return true, if the position is outside the matrix or that position is filled with a non-zero
     */
    public static boolean checkIfTileEmpty(int[][][] arr, Position pos) {
        boolean zOutOfBounds = pos.z < 0 || pos.z >= arr.length;
        if (zOutOfBounds)
            return false;
        boolean yOutOfBounds = pos.y < 0 || pos.y >= arr[pos.z].length;
        if (yOutOfBounds)
            return false;
        boolean xOutOfBounds = pos.x < 0 || pos.x >= arr[pos.z][pos.y].length;
        if (xOutOfBounds)
            return false;
        boolean tileEmpty = arr[pos.z][pos.y][pos.x] == 0;
        if (tileEmpty)
            return true;
        else
            return false;
    }

    // endregion

    // region Heap sort
    public static void sort(ArrayList<Parcel> i) {
        int N = i.size();

        for (int k = N / 2; k > 0; k--)
            downheap(i, k, N);

        do {
            Parcel T = i.get(0);
            i.set(0, i.get(N - 1));
            i.set(N - 1, T);

            N = N - 1;
            downheap(i, 1, N);
        } while (N > 1);
    }

    private static void downheap(ArrayList<Parcel> i, int k, int N) {
        Parcel T = i.get(k - 1);

        while (k <= N / 2) {
            int j = k + k;
            if ((j < N) && (i.get(j - 1).value > i.get(j).value))
                j++;

            if (T.value <= i.get(j - 1).value)
                break;
            else {
                i.set(k - 1, i.get(j - 1));
                k = j;
            }
        }
        i.set(k - 1, T);
    }

    // endregion

    // region Copy
    public static ParcelPlacement[] copy(ParcelPlacement[] input) {
        ParcelPlacement[] returnArray = new ParcelPlacement[input.length];
        for (int i = 0; i < input.length; i++) {
            returnArray[i] = input[i];
        }
        return returnArray;
    }

    public static ArrayList<ParcelPlacement> copy(ArrayList<ParcelPlacement> input) {
        ArrayList<ParcelPlacement> returnList = new ArrayList<ParcelPlacement>();
        for (ParcelPlacement placement : input) {
            returnList.add(placement.copy());
        }
        return returnList;
    }

    public static Parcel[] copy(Parcel[] input) {
        Parcel[] returnArray = new Parcel[input.length];
        for (int i = 0; i < input.length; i++) {
            returnArray[i] = input[i];
        }
        return returnArray;
    }

    public static ArrayList<Parcel> copyP(ArrayList<Parcel> input) {
        ArrayList<Parcel> returnList = new ArrayList<Parcel>();
        for (Parcel parcel : input) {
            returnList.add(parcel.copy());
        }
        return returnList;
    }

    public static int[][][] copy(int[][][] input) {
        int[][][] copy = new int[input.length][input[0].length][input[0][0].length];

        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[i].length; j++) {
                for (int k = 0; k < copy[i][j].length; k++) {
                    copy[i][j][k] = input[i][j][k];
                }
            }
        }
        return copy;
    }

    public static int[][] copy(int[][] input) {
        int[][] copy = new int[input.length][input[0].length];

        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[i].length; j++) {
                copy[i][j] = input[i][j];
            }
        }
        return copy;
    }

    public static int[] copy(int[] input) {
        int[] copy = new int[input.length];

        for (int i = 0; i < copy.length; i++) {
            copy[i] = input[i];
        }
        return copy;
    }

    // endregion

    // region Print
    public static void print(int[] input) {
        for (int tile : input) {
            System.out.print(tile);
        }
        System.out.println();
    }

    public static void print(int[][] input) {
        for (int[] row : input) {
            print(row);
        }
    }

    public static void print(int[][][] input) {
        System.out.println("Printing:");
        for (int i = 0; i < input.length; i++) {
            System.out.println("Layer: " + i);
            print(input[i]);
        }
        System.out.println();
    }

    public static void print(ArrayList<int[][][]> input) {
        for (int i = 0; i < input.size(); i++) {
            print(input.get(i));
        }
        System.out.println();
    }
    // endregion

    // region Volume
    public static float getEmptySpacesCount(int[][][] input) {
        float counter = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                for (int k = 0; k < input[i][j].length; k++) {

                    if (input[i][j][k] == 0) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public static float getNonemptySpacesCount(int[][][] input) {
        float counter = 0;
        for (int z = 0; z < input.length; z++) {
            for (int y = 0; y < input[z].length; y++) {
                for (int x = 0; x < input[z][y].length; x++) {

                    if (input[z][y][x] != 0) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }
    // endregion

    // region Array properties
    public static boolean zerosOnlyAtTheFront(int[] arr) {
        boolean onlyAtTheFront = true;
        boolean frontEnded = false;
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
            if (!frontEnded && arr[i] != 0) {
                frontEnded = true;
            } else {
                if (frontEnded && arr[i] == 0) {
                    onlyAtTheFront = false;
                }
            }
        }
        return onlyAtTheFront;
    }

    public static boolean arrayListContains(ArrayList<int[][][]> input, int[][][] shape) {
        for (int[][][] s : input) {
            if (HelperMethods.areEqual(s, shape)) {
                return true;
            }
        }
        return false;
    }

    public static boolean areEqual(int[][][] a, int[][][] b) {
        int depth = a.length;
        int height = a[0].length;
        int width = a[0][0].length;
        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (a[z][y][x] != b[z][y][x]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    /**
     *
     * @param arr
     * @param x   column
     * @param y   row
     * @param z   layer
     * @return false, if the position is outside the matrix or that position is filled with a zero
     */
    public static boolean isEmpty(int[][][] arr, Position pos) {
        boolean zOutOfBounds = pos.z < 0 || pos.z >= arr.length;
        if (zOutOfBounds)
            return true;
        boolean yOutOfBounds = pos.y < 0 || pos.y >= arr[pos.z].length;
        if (yOutOfBounds)
            return true;
        boolean xOutOfBounds = pos.x < 0 || pos.x >= arr[pos.z][pos.y].length;
        if (xOutOfBounds)
            return true;
        boolean tileEmpty = arr[pos.z][pos.y][pos.x] == 0;
        if (tileEmpty)
            return true;
        else
            return false;
    }
    // endregion

    // region Casting
    public static Block turnParcelIntoBlock(Parcel parcel) {
        ArrayList<ParcelPlacement> placements = new ArrayList<ParcelPlacement>();
        Position pos = new Position(0, 0, 0);
        placements.add(new ParcelPlacement(parcel, pos));
        return new Block(parcel.shape, parcel.value, placements);
    }
    // endregion

    // region Rotation
    /**
     * Rotates the shape 90 degrees clockwise around each axis the specified number
     * of times.
     * 
     * @param shape
     * @param x
     * @param y
     * @param z
     * @return The rotated shape as a new 3D matrix
     */
    public static ArrayList<int[][][]> generateRotations(int[][][] shape) {
        ArrayList<int[][][]> returnArrayList = new ArrayList<int[][][]>();
        int[][][] temporaryArray = HelperMethods.copy(shape);

        // rotate 4 times around Y axis
        for (int i = 0; i < 4; i++) {
            // rotate 4 times around X axis
            rotate4TimesAroundX(returnArrayList, temporaryArray);
            temporaryArray = rotateAroundY(temporaryArray);
        }
        temporaryArray = rotateAroundZ(temporaryArray);
        rotate4TimesAroundY(returnArrayList, temporaryArray);
        temporaryArray = rotateAroundZ(temporaryArray);
        temporaryArray = rotateAroundZ(temporaryArray);
        rotate4TimesAroundY(returnArrayList, temporaryArray);

        return deleteRepetitions(returnArrayList);
    }

    private static ArrayList<int[][][]> deleteRepetitions(ArrayList<int[][][]> shapeList) {
        ArrayList<int[][][]> returnArrayList = new ArrayList<int[][][]>();
        for (int[][][] shape : shapeList) {
            if (HelperMethods.arrayListContains(returnArrayList, shape)) {
                returnArrayList.add(shape);
            }
        }
        return returnArrayList;
    }

    private static void rotate4TimesAroundX(ArrayList<int[][][]> arrayList, int[][][] shape) {
        for (int j = 0; j < 4; j++) {
            arrayList.add(shape);
            shape = rotateAroundX(shape);
        }
    }

    private static void rotate4TimesAroundY(ArrayList<int[][][]> arrayList, int[][][] shape) {
        for (int j = 0; j < 4; j++) {
            arrayList.add(shape);
            shape = rotateAroundY(shape);
        }
    }

    public static int[][][] rotateShape(int[][][] shape, int x, int y, int z) {
        int[][][] returnArray = HelperMethods.copy(shape);
        for (int i = 0; i < (x % 4); i++) {
            returnArray = rotateAroundX(returnArray);
        }
        for (int i = 0; i < (y % 4); i++) {
            returnArray = rotateAroundY(returnArray);
        }
        for (int i = 0; i < (z % 4); i++) {
            returnArray = rotateAroundZ(returnArray);
        }
        return returnArray;
    }

    private static int[][][] rotateAroundX(int[][][] shape) {
        int depth = shape.length;
        int height = shape[0].length;
        int width = shape[0][0].length;
        int[][][] newshape = new int[height][depth][width];

        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    newshape[y][depth - (z + 1)][x] = shape[z][y][x];
                }
            }
        }
        return newshape;
    }

    private static int[][][] rotateAroundY(int[][][] shape) {
        int depth = shape.length;
        int height = shape[0].length;
        int width = shape[0][0].length;
        int[][][] newshape = new int[width][height][depth];

        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    newshape[x][y][depth - (z + 1)] = shape[z][y][x];
                }
            }
        }
        return newshape;
    }

    private static int[][][] rotateAroundZ(int[][][] shape) {
        int depth = shape.length;
        int height = shape[0].length;
        int width = shape[0][0].length;
        int[][][] newshape = new int[depth][width][height];

        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    newshape[z][x][height - (y + 1)] = shape[z][y][x];
                }
            }
        }
        return newshape;
    }
    // endregion
}
