import java.util.ArrayList;

public class Solver {

    public static float highestValue;

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
        int[] parcelCount = { 0, 0, 0, 100, 100, 100 };

        return parcelCount;
    }

    private static int[] getInventoryDimensions() {
        int[] dim = { 5, 8, 33 };
        return dim;
    }

    // endregion
    public static void main(String[] args) {
        BlockHolder.startup(getInventoryDimensions(), true);
        System.out.println(BlockHolder.getBlocks().size());
        int[] volume = { 5, 10, 20 };
        Block bestBlock = HelperMethods.getBestBlock(volume, createCargo());
        bestBlock.print();
    }

    public static Cargo recursiveFitter(Cargo cargo) {
        if (cargo.allParcelsUsed() || cargo.cargoFilled() || cargo.isValueMax()) {
            cargo.stopProgram();
            return cargo;
        }
        BlockPlacement placement = findPlacement(cargo);
        if (placement == null) {
            cargo.stopProgram();
            return cargo;
        } else {
            placeInCargo(cargo, placement);
            updateHighestValue(cargo);
            Cargo bestFittedCargo = recursiveFitter(cargo);
            if (bestFittedCargo.stopProgram) {
                return bestFittedCargo;
            }
        }
        return cargo;
    }

    // region Mutator methods
    private static void updateHighestValue(Cargo cargo) {
        if (cargo.totalValue > highestValue) {
            highestValue = cargo.totalValue;

            // update display();
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
