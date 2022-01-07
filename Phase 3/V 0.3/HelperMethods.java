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
     * @return
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
    public static ArrayList<Parcel> copy(ArrayList<Parcel> input) {
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
}
