import java.util.ArrayList;

public class HelperMethods {

    public static boolean prioritizeValue = false;

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
        }
        // check back
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
     * @return false, if the position is outside the matrix or that position is
     *         filled with a non-zero
     */
    public static boolean checkIfTileEmpty(int[][][] arr, Position pos) {
        if (isOutOfBounds(arr, pos)) {
            return false;
        }
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

    public static ArrayList<Block> copyB(ArrayList<Block> input) {
        ArrayList<Block> returnList = new ArrayList<Block>();
        for (Block block : input) {
            returnList.add(block.copy());
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

    public static void printI(ArrayList<int[][][]> input) {
        System.out.println("Count: " + input.size());
        for (int i = 0; i < input.size(); i++) {
            print(input.get(i));
        }
        System.out.println();
    }

    public static void printP(ArrayList<Parcel> input) {
        for (int i = 0; i < input.size(); i++) {
            input.get(i).print();
        }
        System.out.println();
    }

    public static void printB(ArrayList<Block> input) {
        for (int i = 0; i < input.size(); i++) {
            input.get(i).print();
        }
        System.out.println();
    }

    public static void printParcels(int[] parcelsCount) {
        System.out.println(getParcelArrayString(parcelsCount));
    }

    public static void printDimensions(int[] dimensions) {
        System.out.print(dimensions[0] + "x");
        System.out.print(dimensions[1] + "x");
        System.out.print(dimensions[2]);
        System.out.println();
    }

    // region Helper methods
    private static String getParcelArrayString(int[] parcelsCount) {
        String message = "";
        for (int i = 0; i < parcelsCount.length; i++) {
            message += getLetter(i);
            int parcelCount = parcelsCount[i];
            message += "x" + parcelCount + " ";
        }

        return message;
    }

    private static String getLetter(int index) {
        switch (index) {
            case 0: {
                return "A";
            }
            case 1: {
                return "B";
            }
            case 2: {
                return "C";
            }
            case 3: {
                return "L";
            }
            case 4: {
                return "P";
            }
            case 5: {
                return "T";
            }
            default:
                return "";
        }
    }
    // endregion
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

    // region Largest empty volume
    public static EmptySpace findLargestEmptySpace(int[][][] cargo) {
        boolean isEmpty = isShapeEmpty(cargo);
        if (isEmpty) {
            return createMaxVolumeEmptySpace(cargo);
        }
        EmptySpace firstCuboidFound = getFirstCuboid(cargo);
        boolean isEmptySpaceACuboid = firstCuboidFound.volume == getEmptySpacesCount(cargo);
        if (isEmptySpaceACuboid) {
            return firstCuboidFound;
        }
        EmptySpace emptySpace = new EmptySpace();
        for (int z = 0; z < cargo.length; z++) {
            for (int y = 0; y < cargo[0].length; y++) {
                for (int x = 0; x < cargo[0][0].length; x++) {
                    if (cargo[z][y][x] == 0) {
                        int[] dimensions = findLargestCuboidStartingAt(cargo, x, y, z);
                        int volume = dimensions[0] * dimensions[1] * dimensions[2];
                        if (volume > emptySpace.volume) {
                            emptySpace.volume = volume;
                            emptySpace.dimensions = dimensions;
                            emptySpace.pos = new Position(x, y, z);
                        }
                    }
                    int biggestAchievableVolume = (cargo.length - z) * (cargo[0].length - y)
                            * (cargo[0][0].length - x);
                    if (biggestAchievableVolume <= emptySpace.volume) {
                        break;
                    }
                }
                int biggestAchievableVolume = (cargo.length - z) * (cargo[0].length - y)
                        * cargo[0][0].length;
                if (biggestAchievableVolume <= emptySpace.volume) {
                    break;
                }
            }
            int biggestAchievableVolume = (cargo.length - z) * cargo[0].length * cargo[0][0].length;
            if (biggestAchievableVolume <= emptySpace.volume) {
                break;
            }
        }
        return emptySpace; // The biggest shape found
    }

    private static boolean isShapeEmpty(int[][][] shape) {
        for (int z = 0; z < shape.length; z++) {
            for (int y = 0; y < shape[0].length; y++) {
                for (int x = 0; x < shape[0][0].length; x++) {
                    if (shape[z][y][x] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static EmptySpace getFirstCuboid(int[][][] shape) {
        for (int z = 0; z < shape.length; z++) {
            for (int y = 0; y < shape[0].length; y++) {
                for (int x = 0; x < shape[0][0].length; x++) {
                    if (shape[z][y][x] == 0) {
                        return getLargestCuboidAtPosition(shape, new Position(x, y, z));
                    }
                }
            }
        }
        EmptySpace returnSpace = new EmptySpace();
        returnSpace.volume = 0;
        return returnSpace;
    }

    private static EmptySpace getLargestCuboidAtPosition(int[][][] shape, Position startPos) {
        for (int z = 0; z < shape.length; z++) {
            for (int y = 0; y < shape[0].length; y++) {
                for (int x = 0; x < shape[0][0].length; x++) {
                    if (shape[z][y][x] != 0) {
                        EmptySpace returnSpace = new EmptySpace();
                        returnSpace.volume = 0;
                        return returnSpace;
                    }
                }
            }
        }
        EmptySpace returnSpace = new EmptySpace(startPos);
        int[] newDim = { shape[0][0].length - startPos.x, shape[0].length - startPos.y, shape.length - startPos.z };
        returnSpace.dimensions = newDim;
        returnSpace.volume = newDim[0] * newDim[1] * newDim[2];
        return returnSpace;
    }

    private static EmptySpace createMaxVolumeEmptySpace(int[][][] shape) {
        EmptySpace totallyEmptySpace = new EmptySpace(new Position(0, 0, 0));
        int width = shape[0][0].length;
        int height = shape[0].length;
        int depth = shape.length;
        int[] dim = { width, height, depth };
        totallyEmptySpace.dimensions = dim;
        totallyEmptySpace.volume = width * height * depth;
        return totallyEmptySpace;
    }

    private static int[] findLargestCuboidStartingAt(int[][][] cargo, int x, int y, int z) {
        CuboidData data = new CuboidData();
        data.cargo = cargo;
        data.x = x;
        data.y = y;
        data.z = z;
        data.maxWidth = cargo[0][0].length - x;
        data.maxHeight = cargo[0].length - y;
        data.currentDepth = 1;
        return findLargestCuboidRecursively(data);
    }

    private static int[] findLargestCuboidRecursively(CuboidData data) {
        int[] largestDimensions = new int[3];
        int largestVolume = 0;
        int height = 1;
        for (int i = data.y; i < data.cargo[0].length && height <= data.maxHeight; i++) {
            boolean hitAnObstacle = !HelperMethods.checkIfTileEmpty(data.cargo, new Position(data.x, i, data.z));
            if (hitAnObstacle) {

                return largestDimensions;
            }
            int width = goRight(data.cargo[data.z], data.x, i);
            if (width < data.maxWidth) {
                data.maxWidth = width;
            }
            // The depth is always one higher than the z coordinate
            // Counts the current volume
            int[] cuboidDimensions = new int[3];
            int cuboidVolume = height * data.maxWidth * data.currentDepth;
            cuboidDimensions[0] = data.maxWidth;
            cuboidDimensions[1] = height;
            cuboidDimensions[2] = data.currentDepth;
            // Looks for possible volumes deeper into the cargo shape

            CuboidData newData = data.copy();
            newData.z = data.z + 1;
            newData.maxHeight = height;
            newData.currentDepth = data.currentDepth + 1;

            int[] deeperDimensions = findLargestCuboidRecursively(newData);
            int deeperVolume = deeperDimensions[0] * deeperDimensions[1] * deeperDimensions[2];
            if (cuboidVolume < deeperVolume) {
                cuboidVolume = deeperVolume;
                cuboidDimensions = deeperDimensions;
            }
            if (largestVolume < cuboidVolume) {
                largestVolume = cuboidVolume;
                largestDimensions = cuboidDimensions;
            }
            height++;
        }
        return largestDimensions;
    }

    public static int goRight(int[][] array, int x, int y) {
        int zeroCounter = 0;
        for (int i = x; i < array[0].length; i++) {
            if (array[y][i] == 0) {
                zeroCounter++;
            } else {
                return zeroCounter;
            }
        }
        return zeroCounter;
    }
    // endregion

    // region Expand volume
    /**
     * 
     * @param pos1 top left front corner of the first volume
     * @param pos2 bottom right back corner of the first volume
     * @param pos3 top left front corner of the second volume
     * @param pos4 bottom right back corner of the second volume
     * @return a volume big enough to contain all of those points at once
     */
    public static int[][][] getExpandedVolume(Position pos1, Position pos2, Position pos3, Position pos4) {
        int lowestX = lowerValue(pos1.x, pos3.x);
        int lowestY = lowerValue(pos1.y, pos3.y);
        int lowestZ = lowerValue(pos1.z, pos3.z);

        int highestX = higherValue(pos2.x, pos4.x);
        int highestY = higherValue(pos2.y, pos4.y);
        int highestZ = higherValue(pos2.z, pos4.z);

        return new int[highestZ - lowestZ + 1][highestY - lowestY + 1][highestX - lowestX + 1];
    }

    public static int higherValue(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    public static int lowerValue(int a, int b) {
        if (a > b) {
            return b;
        } else {
            return a;
        }
    }
    // endregion
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
                    if (isOutOfBounds(b, new Position(x, y, z))) {
                        return false;
                    }
                    if (a[z][y][x] != b[z][y][x]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean areEqual(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int x = 0; x < b.length; x++) {
            if (a[x] != b[x]) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param arr
     * @return true, if the position is outside the matrix or that position is
     *         filled with a zero
     */
    public static boolean isEmpty(int[][][] arr, Position pos) {
        if (isOutOfBounds(arr, pos)) {
            return true;
        }
        boolean tileEmpty = arr[pos.z][pos.y][pos.x] == 0;
        if (tileEmpty)
            return true;
        else
            return false;
    }

    private static boolean isOutOfBounds(int[][][] arr, Position pos) {
        boolean zOutOfBounds = pos.z < 0 || pos.z >= arr.length;
        if (zOutOfBounds)
            return true;
        boolean yOutOfBounds = pos.y < 0 || pos.y >= arr[pos.z].length;
        if (yOutOfBounds)
            return true;
        boolean xOutOfBounds = pos.x < 0 || pos.x >= arr[pos.z][pos.y].length;
        if (xOutOfBounds)
            return true;
        return false;
    }
    // endregion

    // region Rotation
    // region Generate Rotations
    public static ArrayList<int[][][]> generateRotations(int[][][] shape) {
        ArrayList<int[][][]> returnArrayList = new ArrayList<int[][][]>();
        int[][][] temporaryArray = HelperMethods.copy(shape);

        // rotate 2 times around Y axis
        for (int i = 0; i < 2; i++) {
            // rotate 4 times around X axis
            rotate4TimesAroundX(returnArrayList, temporaryArray);
            temporaryArray = rotateAroundY(temporaryArray);
            temporaryArray = rotateAroundY(temporaryArray);
        }
        temporaryArray = rotateAroundY(temporaryArray);
        // rotate 2 times around Y axis
        for (int i = 0; i < 2; i++) {
            // rotate 4 times around Z axis
            rotate4TimesAroundZ(returnArrayList, temporaryArray);
            temporaryArray = rotateAroundY(temporaryArray);
            temporaryArray = rotateAroundY(temporaryArray);
        }
        temporaryArray = rotateAroundX(temporaryArray);
        rotate4TimesAroundY(returnArrayList, temporaryArray);
        temporaryArray = rotateAroundX(temporaryArray);
        temporaryArray = rotateAroundX(temporaryArray);
        rotate4TimesAroundY(returnArrayList, temporaryArray);
        return deleteRepetitions(returnArrayList);
    }

    private static ArrayList<int[][][]> deleteRepetitions(ArrayList<int[][][]> shapeList) {
        ArrayList<int[][][]> returnArrayList = new ArrayList<int[][][]>();
        for (int[][][] shape : shapeList) {
            if (!HelperMethods.arrayListContains(returnArrayList, shape)) {
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

    private static void rotate4TimesAroundZ(ArrayList<int[][][]> arrayList, int[][][] shape) {
        for (int j = 0; j < 4; j++) {
            arrayList.add(shape);
            shape = rotateAroundZ(shape);
        }
    }
    // endregion

    // region Create Rotation
    public static int[][][] createShapeRotation(int[][][] shape, int x, int y, int z) {
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

    // region Rotate shape
    private static int[][][] rotateAroundX(int[][][] shape) {
        int depth = shape.length;
        int height = shape[0].length;
        int width = shape[0][0].length;
        int[][][] newshape = new int[height][depth][width];

        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    newshape[height - (y + 1)][z][x] = shape[z][y][x];
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
                    newshape[z][width - (x + 1)][y] = shape[z][y][x];
                }
            }
        }
        return newshape;
    }

    // endregion
    // endregion
    // endregion

    // region Find best fit
    public static Block getBestBlock(int[] emptyVolumeDimensions, Cargo cargo) {
        ArrayList<Block> allBlocks = BlockHolder.getBlocks();
        allBlocks = deleteWrongBlocks(allBlocks, cargo);
        BlockHolder.setBlockList(deleteWrongBlocks(allBlocks, cargo));
        ArrayList<Block> blocksThatFit = getBlocksThatFit(allBlocks, emptyVolumeDimensions);
        Block bestBlock = getTheHighestValueBlock(blocksThatFit);
        // Updates the block list to throw out all blocks that we don't have enough
        // parcels for permanently,
        // as the number of parcels only lowers
        return bestBlock;
    }
    // region Blocks with enough parcels

    /**
     * Deletes blocks with a volume higher than the empty space left and with not enough parcels to place them
     * @param blocks
     * @param cargo
     * @return
     */
    private static ArrayList<Block> deleteWrongBlocks(ArrayList<Block> blocks, Cargo cargo) {
        ArrayList<Block> returnList = new ArrayList<>();
        float totalEmptyVolume = getEmptySpacesCount(cargo.shape);

        for (Block block : blocks) {
            if (block.getVolume() == 1280) {
                //
                block.print();
            }
            if (hasLowEnoughVolume(block, totalEmptyVolume)) {
                boolean hasEnoughParcelsForThisBlock = hasEnoughParcelsForThisBlock(block, cargo);
                if (hasEnoughParcelsForThisBlock) {
                    returnList.add(block);
                }
            }
        }
        return returnList;
    }

    private static boolean hasEnoughParcelsForThisBlock(Block block, Cargo cargo) {
        int[] blockParcels = block.parcelsCount;
        int[] cargoParcels = cargo.parcelsLeftCount;
        return arrayFits(blockParcels, cargoParcels);
    }

    private static boolean hasLowEnoughVolume(Block block, float volume) {
        if (block.getVolume() <= volume) {
            return true;
        } else {
            return false;
        }
    }
    // endregion

    // region Blocks that fit
    private static ArrayList<Block> getBlocksThatFit(ArrayList<Block> blocks, int[] emptyVolumeDimensions) {
        ArrayList<Block> returnArrayList = new ArrayList<>();
        for (Block block : blocks) {
            returnArrayList.addAll(getBlocksThatFit(block, emptyVolumeDimensions));
        }
        return returnArrayList;
    }

    private static ArrayList<Block> getBlocksThatFit(Block block, int[] emptyVolumeDimensions) {
        ArrayList<Block> returnList = new ArrayList<>();
        boolean fitsNormally = arrayFits(block.dimensions, emptyVolumeDimensions);
        if (fitsNormally) {
            returnList.add(block);
        }
        int[] rotated2 = new int[3];
        rotated2[0] = block.dimensions[0];
        rotated2[1] = block.dimensions[2];
        rotated2[2] = block.dimensions[1];
        boolean fitsRotated2 = arrayFits(rotated2, emptyVolumeDimensions);
        if (fitsRotated2) {
            returnList.add(block.createRotation(1, 0, 0));
        }
        int[] rotated3 = new int[3];
        rotated3[0] = block.dimensions[1];
        rotated3[1] = block.dimensions[2];
        rotated3[2] = block.dimensions[0];
        boolean fitsRotated3 = arrayFits(rotated3, emptyVolumeDimensions);
        if (fitsRotated3) {
            returnList.add(block.createRotation(0, 1, 1));
        }
        int[] rotated4 = new int[3];
        rotated4[0] = block.dimensions[2];
        rotated4[1] = block.dimensions[1];
        rotated4[2] = block.dimensions[0];
        boolean fitsRotated4 = arrayFits(rotated4, emptyVolumeDimensions);
        if (fitsRotated4) {
            returnList.add(block.createRotation(0, 1, 0));
        }
        int[] rotated5 = new int[3];
        rotated5[0] = block.dimensions[1];
        rotated5[1] = block.dimensions[0];
        rotated5[2] = block.dimensions[2];
        boolean fitsRotated5 = arrayFits(rotated5, emptyVolumeDimensions);
        if (fitsRotated5) {
            returnList.add(block.createRotation(0, 0, 1));
        }
        int[] rotated6 = new int[3];
        rotated6[0] = block.dimensions[2];
        rotated6[1] = block.dimensions[0];
        rotated6[2] = block.dimensions[1];
        boolean fitsRotated6 = arrayFits(rotated6, emptyVolumeDimensions);
        if (fitsRotated6) {
            returnList.add(block.createRotation(1, 0, 1));
        }
        return returnList;
    }

    public static ArrayList<Block> getBlocksThatFit2(Block block, int[] emptyVolumeDimensions) {
        ArrayList<Block> returnList = new ArrayList<>();

        boolean fitsNormally = arrayFits(block.dimensions, emptyVolumeDimensions);
        if (fitsNormally) {
            returnList.add(block);
        }
        Block block2 = block.createRotation(1, 0, 0);
        boolean fitsRotated2 = arrayFits(block2.dimensions, emptyVolumeDimensions);
        if (fitsRotated2) {
            returnList.add(block2);

        }
        Block block3 = block.createRotation(0, 1, 1);
        boolean fitsRotated3 = arrayFits(block3.dimensions, emptyVolumeDimensions);
        if (fitsRotated3) {
            returnList.add(block3);

        }
        Block block4 = block.createRotation(0, 1, 0);
        boolean fitsRotated4 = arrayFits(block4.dimensions, emptyVolumeDimensions);
        if (fitsRotated4) {
            returnList.add(block4);

        }
        Block block5 = block.createRotation(0, 0, 1);
        boolean fitsRotated5 = arrayFits(block5.dimensions, emptyVolumeDimensions);
        if (fitsRotated5) {
            returnList.add(block5);

        }
        Block block6 = block.createRotation(1, 0, 1);
        boolean fitsRotated6 = arrayFits(block6.dimensions, emptyVolumeDimensions);
        if (fitsRotated6) {
            returnList.add(block6);

        }
        return returnList;
    }

    /**
     * This method checks, if every number in the smaller array is lower or equal
     * to the corresponding number at the same index of the bigger array
     * 
     * @param smaller
     * @param bigger
     * @return
     */
    private static boolean arrayFits(int[] smaller, int[] bigger) {
        for (int i = 0; i < bigger.length; i++) {
            boolean fits = smaller[i] <= bigger[i];
            if (!fits) {
                return false;
            }
        }
        return true;
    }
    // endregion

    // region Blocks that fit
    private static Block getTheHighestValueBlock(ArrayList<Block> blocks) {
        Block currentBestBlock = null;
        if (prioritizeValue) {
            for (int i = 0; i < blocks.size(); i++) {
                if (currentBestBlock == null) {
                    currentBestBlock = blocks.get(0);
                    continue;
                }
                if (blocks.get(i).value == currentBestBlock.value) {
                    if (blocks.get(i).valueRatio > currentBestBlock.valueRatio) {
                        currentBestBlock = blocks.get(i);
                    }
                    continue;
                }
                if (blocks.get(i).value > currentBestBlock.value) {
                    currentBestBlock = blocks.get(i);
                    continue;
                }
            }
            
        }else{
            for (int i = 0; i < blocks.size(); i++) {
                if (currentBestBlock == null) {
                    currentBestBlock = blocks.get(0);
                    continue;
                }
                if (blocks.get(i).valueRatio == currentBestBlock.valueRatio) {
                    if (blocks.get(i).value > currentBestBlock.value) {
                        currentBestBlock = blocks.get(i);
                    }
                    continue;
                }
                if (blocks.get(i).valueRatio > currentBestBlock.valueRatio) {
                    currentBestBlock = blocks.get(i);
                    continue;
                }
            }
        }
        return currentBestBlock;
    }
    // endregion
    // endregion

    // region Display

    public static void displayCargo(Cargo cargo/*, GUI gui*/) {
        for (ParcelPlacement placement : cargo.placements) {
            displayParcelPlacement(placement/*, gui*/);
        }
    }

    public static void displayBlock(Block block/*, GUI gui*/) {
        for (ParcelPlacement placement : block.placements) {
            displayParcelPlacement(placement/*, gui*/);
        }
    }

    private static void displayParcelPlacement(ParcelPlacement placement/*, GUI gui*/) {
        GUI.addToParcelList(placement.parcel.getShape(), placement.pos.z,placement.pos.y, placement.pos.x);
    }

    // endregion
}

class CuboidData {
    public int[][][] cargo;
    public int x;
    public int y;
    public int z;

    public int maxWidth;
    public int maxHeight;
    public int currentDepth;

    public CuboidData copy() {
        CuboidData copy = new CuboidData();
        copy.cargo = cargo;
        copy.x = x;
        copy.y = y;
        copy.z = z;
        copy.maxWidth = maxWidth;
        copy.maxHeight = maxHeight;
        copy.currentDepth = currentDepth;
        return copy;
    }
}