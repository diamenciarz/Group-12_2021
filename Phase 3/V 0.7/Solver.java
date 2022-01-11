import java.util.ArrayList;

public class Solver {

    public static float highestValue;

    public static void main(String[] args) {
        int[][][] map = {
                { { 1, 1, 0, 0, 0 }, { 1, 0, 0, 1, 1 } },
                { { 1, 1, 0, 1, 1 }, { 0, 1, 0, 1, 0 } },
                { { 0, 0, 1, 1, 0 }, { 0, 0, 1, 1, 0 } },
        };

        int[] count = { 4, 66, 100 };
        int[][][] shape1 = { { { 1, 1 }, { 1, 0 } }, { { 1, 1 }, { 0, 1 } },
                { { 0, 0 }, { 0, 0 } } };
        Parcel p2 = new Parcel(shape1, 15, 1);
        Parcel p1 = new Parcel(shape1, 10, 2);
        Parcel p3 = new Parcel(shape1, 12, 3);

        ArrayList<Parcel> parcels = new ArrayList<>();
        parcels.add(p1);
        parcels.add(p2);
        parcels.add(p3);
        Cargo cargo = new Cargo(map, parcels, count);

        cargo.printParcels();

    }

    public static Cargo recursiveFitter(Cargo cargo) {
        if (cargo.allParcelsUsed()) {
            cargo.stopProgram();
            return cargo;
        }
        if (cargo.cargoFilled()) {
            return cargo;
        } else {
            BlockPlacement placement = findPlacement(cargo);
            while (placement != null) {
                Cargo newCargo = placeInCargo(cargo, placement);

                Cargo bestFittedCargo = recursiveFitter(newCargo);
                checkHighestValue(bestFittedCargo);

                if (bestFittedCargo.stopProgram) {
                    return bestFittedCargo;
                }

                placement = findPlacement(cargo);
            }
        }
        return cargo;
    }

    // region Mutator methods
    private static void checkHighestValue(Cargo cargo) {
        if (cargo.totalValue > highestValue) {
            highestValue = cargo.totalValue;
            // update display();
        }
    }
    // endregion

    // region Accessor methods
    private static Cargo placeInCargo(Cargo cargo, BlockPlacement placement) {
        Cargo cargoCopy = cargo.copy();
        // Place a parcel at position
        Block block = placement.block;
        Position placePosition = placement.pos;
        // Modify the new cargo copy
        cargoCopy.tryStore(block, placePosition);
        return cargoCopy;
    }

    private static BlockPlacement findPlacement(Cargo cargo) {
        EmptySpace largestEmptySpace = HelperMethods.findLargestEmptySpace(cargo.shape);
        // Find the best block to fit in this empty volume
        Block bestBlock = HelperMethods.getBestBlock(largestEmptySpace.dimensions, BlockHolder.blockList);
        BlockPlacement foundPlacement = new BlockPlacement(bestBlock, largestEmptySpace.pos);
        return foundPlacement;
    }
    // endregion
}
