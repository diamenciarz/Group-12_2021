public class FindTheBiggestEmptyRectangle {

    public static void main(String[] args) {
        int[][][] tryarray = { 
              { { 1, 1 },
                { 1, 1 },
                { 0, 1 } },{
                { 0, 1 },
                { 0, 0 },
                { 0, 0 } },
              { { 0, 1 },
                { 0, 1 },
                { 0, 0 } },
              { { 1, 1 },
                { 0, 1 },
                { 0, 1 } } };

        HelperMethods.print(tryarray);
        System.out.println(findTheBiggestCuboid(tryarray));
        // System.out.println(findLargestCuboidRecursively(tryarray, 0, 0, 1, tryarray[0].length, tryarray.length));
        // System.out.println(findTheBiggestCuboid(tryarray));
        // System.out.println(searchForFreeRectangle(tryarray, 2, 2));
        // System.out.println(goRight(tryarray, 0, 2));
    }

    private static int[] findTheBiggestCuboid(int[][][] cargo) {
        int biggestCuboid = 0;
        int xPos = 0;
        int yPos = 0;
        for (int z = 0; z < cargo.length; z++) {
            for (int y = 0; y < cargo[0].length; y++) {
                for (int x = 0; x < cargo[0][0].length; x++) {
                    if (cargo[z][y][x] == 0) {
                        int volume = findLargestCuboidAtPosition(cargo, x, y, z);
                        if (volume > biggestCuboid) {
                            biggestCuboid = volume;
                        }
                    }
                    int biggestAchievableVolume = (cargo.length - (z + 1)) * (cargo[0].length - (y + 1))
                            * (cargo[0][0].length - (x + 1));
                    if (biggestAchievableVolume <= biggestCuboid) {
                        break;
                    }
                }
                int biggestAchievableVolume = (cargo.length - (z + 1)) * (cargo[0].length - (y + 1))
                        * cargo[0][0].length;
                if (biggestAchievableVolume <= biggestCuboid) {
                    break;
                }
            }
            int biggestAchievableVolume = (cargo.length - (z + 1)) * cargo[0].length * cargo[0][0].length;
            if (biggestAchievableVolume <= biggestCuboid) {
                break;
            }
        }
        return packData(biggestCuboid, xPos, yPos);
    }

    private static int[] packData(int biggestCuboid, int xPos, int yPos) {
        int[] data = new int[3]; // volume, x position, y position
        data[0] = biggestCuboid;
        data[1] = xPos;
        data[2] = yPos;
        return data;
    }

    private static int findLargestCuboidAtPosition(int[][][] cargo, int x, int y, int z) {
        return findLargestCuboidRecursively(cargo, x, y, z, cargo[0][0].length - (x + 1), cargo[0].length - (y + 1), 0);
    }

    public static int findLargestCuboidRecursively(int[][][] cargo, int x, int y, int z, int maxWidth, int maxHeight, int currentDepth) {
        int largestVolume = 0;
        int height = 0;
        for (int i = y; i < cargo.length; i++) {
            boolean hitAnObstacle = HelperMethods.checkIfTileEmpty(cargo, new Position(z, i, x));
            if (hitAnObstacle) {
                return largestVolume;
            }
            height++;
            currentDepth++;
            System.out.println("Y:" + i + " z: " + z);
            int width = goRight(cargo[z], x, i);
            if (maxWidth > width) {
                maxWidth = width;
            }
            // The depth is always one higher than the z coordinate
            //Counts the current volume
            int cuboidVolume = height * maxWidth * currentDepth;
            //Counts the highest achievable volume 
            int deeperVolume = findLargestCuboidRecursively(cargo, x, i, z + 1, maxWidth, maxHeight, currentDepth);
            if (cuboidVolume < deeperVolume) {
                cuboidVolume = deeperVolume;
            }
            if (largestVolume < cuboidVolume) {
                largestVolume = cuboidVolume;
            }
        }
        return largestVolume;
    }

    public static int goDown(int[][] array, int x, int y) {
        int zeroCounter = 0;
        for (int i = y; i < array.length; i++) {
            if (array[i][x] == 0) {
                zeroCounter++;
            } else {
                return zeroCounter;
            }
        }
        return zeroCounter;
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

    public static int searchForFreeCuboid(int[][][] cargo, int x, int y, int z) {
        int biggestCuboid = 0;

        int currentHeight = cargo[0].length;
        int currentWidth = cargo[0][0].length;
        int depth = 0;

        for (int i = z; i < cargo.length; i++) {
            if (cargo[i][y][x] != 0) {
                break;
            }
            depth++;
            int volume = 2; // getLargestCuboidVolume();
            if (volume > biggestCuboid) {
                biggestCuboid = volume;
            }
            // If the biggest volume that could be theoretically achieved, if the loop
            // continued, is lower than the current biggest cuboid, then

        }

        return biggestCuboid;
    }

    public static int[] searchForBiggestRectangle(int[][] cargo, int x, int y, int maxWidth, int maxHeight) {
        // max X, max Y, biggest rectangle width, biggest rectangle height
        int[] biggestRectangleDimensions = new int[4];
        int currentWidth = cargo[0].length;
        int height = 0;
        for (int i = y; i < cargo.length; i++) {
            if (cargo[i][x] != 0) {
                break;
            }
            height++;
            int width = goRight(cargo, x, i);
            if (currentWidth > width) {
                currentWidth = width;
            }
            int rectangleArea = height * currentWidth;
            if (rectangleArea > biggestRectangleDimensions[2]) {
                biggestRectangleDimensions[0] = width;
                biggestRectangleDimensions[1] = height;
                biggestRectangleDimensions[2] = rectangleArea;
            }
        }

        return biggestRectangleDimensions;
    }

}