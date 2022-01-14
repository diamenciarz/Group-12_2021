import java.util.ArrayList;

public class Solver {

    public static float highestValue;
    public static boolean onlyDisplayTheResult = false;

    // private static GUI gui = new GUI();

    // region Startup
    private static Cargo createCargo() {
        return new Cargo(getInventory(), getParcelCount());
    }

    private static int[][][] getInventory() {
        int[] dim = getInventoryDimensions();
        int[][][] emptyInventory = new int[dim[0]][dim[1]][dim[2]];

        return emptyInventory;
    }

    private static int[] getParcelCount() {
        int[] parcelCount = { 0, 0, 0, 600, 600, 6 };

        return parcelCount;
    }

    private static int[] getInventoryDimensions() {
        int[] dim = { 5, 12, 33 };
        return dim;
    }

    // endregion
    public static void main(String[] args) {
        BlockHolder.startup(getInventoryDimensions(), false);
        System.out.println("Blocks generated: " + BlockHolder.getBlocks().size());

        Cargo cargo = createCargo();
        // cargo.print();
        // Block bestBlock = HelperMethods.getBestBlock(cargo.dimensions, cargo);
        // bestBlock.print();
        // fillCargo(cargo);
    }

    private static Cargo fillCargo(Cargo cargo) {
        boolean shouldStop = false;
        int steps = 0;
        while (!shouldStop) {
            BlockPlacement placement = findPlacement(cargo);

            if (placement.block == null) {
                shouldStop = true;
            } else {
                steps++;
                System.out.println(steps);
                placement.block.print();
                placeInCargo(cargo, placement);
                updateHighestValue(cargo);
                if (!onlyDisplayTheResult) {
                    HelperMethods.displayCargo(cargo);
                }
            }

            if (cargo.allParcelsUsed() || cargo.cargoFilled() || cargo.isValueMax()) {
                shouldStop = true;
            }
        }

        if (onlyDisplayTheResult) {
            HelperMethods.displayCargo(cargo);
        }
        return cargo;
    }

    // region Mutator methods
    private static void updateHighestValue(Cargo cargo) {
        if (cargo.totalValue > highestValue) {
            highestValue = cargo.totalValue;
        }
    }

    private static boolean placeInCargo(Cargo cargo, BlockPlacement placement) {
        // Place a parcel at position
        Block block = placement.block;
        Position placePosition = placement.pos;
        // Modify the new cargo copy
        return cargo.tryStore(block, placePosition);
    }
    // endregion

    // region Accessor methods
    /**
     * 
     * @param cargo
     * @param placement
     * @return true, if the block was successfully placed at that position
     */

    private static BlockPlacement findPlacement(Cargo cargo) {
        // Look at the current state of the cargo and find the largest empty cuboid
        EmptySpace largestEmptySpace = HelperMethods.findLargestEmptySpace(cargo.shape);
        // Find the best block to fit in this empty volume
        Block bestBlock = HelperMethods.getBestBlock(largestEmptySpace.dimensions, cargo);
        BlockPlacement foundPlacement = new BlockPlacement(bestBlock, largestEmptySpace.pos);
        return foundPlacement;
    }
    // endregion
}