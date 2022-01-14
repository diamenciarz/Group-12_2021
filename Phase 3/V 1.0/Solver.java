import java.util.ArrayList;

public class Solver {

    public static float highestValue;
    public static boolean onlyDisplayTheResult = false;
    public static boolean doDebugMessages = false;

    // private static GUI gui = new GUI();

    // region Startup
    private static Cargo createCargo() {
        return new Cargo(getInventory(), getParcelCount());
    }

    private static int[][][] getInventory() {
        int[] dim = getInventoryDimensions();
        int[][][] emptyInventory = new int[dim[2]][dim[1]][dim[0]];

        return emptyInventory;
    }

    private static int[] getParcelCount() {
        int[] parcelCount = { 500, 200, 300, 500, 600, 300 };
        // int[] parcelCount = { 500, 200, 300, 40, 4, 252 };

        return parcelCount;
    }

    private static int[] getInventoryDimensions() {
        int[] dim = { 10, 20, 33 };
        return dim;
    }

    public static void solver( /* GUI gui */) {
        BlockHolder.startup(getInventoryDimensions(), false);
        System.out.println("Blocks generated: " + BlockHolder.getBlocks().size());

        Cargo cargo = createCargo();
        HelperMethods.print(cargo.parcelsCount);
        cargo.print();

        // int[] d = { 10, 9, 2 };
        // ArrayList<Block> ass= HelperMethods.getBlocksThatFit2(BlockHolder.getP6T2(), d);
        // HelperMethods.printB(ass);
        // Block bestBlock = HelperMethods.getBestBlock(d, cargo);
        // bestBlock.print();
        fillCargo(cargo);
    }

    // endregion
    public static void main(String[] args) {
        solver();
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

                System.out.println("Step: " + steps);
                System.out.println("Block parcels");
                HelperMethods.print(placement.block.parcelsCount);
                placeInCargo(cargo, placement);
                updateHighestValue(cargo);
                System.out.println("Cargo parcels");
                HelperMethods.print(cargo.parcelsCount);
                cargo.print();

                if (!onlyDisplayTheResult) {
                    // HelperMethods.displayCargo(cargo, gui);
                }
            }

            if (cargo.allParcelsUsed() || cargo.cargoFilled() || cargo.isValueMax()) {
                shouldStop = true;
            }
        }

        if (onlyDisplayTheResult) {
            // HelperMethods.displayCargo(cargo, gui);
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
        System.out.println("Empty space dimensions: ");
        HelperMethods.printDimensions(largestEmptySpace.dimensions);
        // Find the best block to fit in this empty volume
        Block bestBlock = HelperMethods.getBestBlock(largestEmptySpace.dimensions, cargo);
        BlockPlacement foundPlacement = new BlockPlacement(bestBlock, largestEmptySpace.pos);
        return foundPlacement;
    }
    // endregion
}