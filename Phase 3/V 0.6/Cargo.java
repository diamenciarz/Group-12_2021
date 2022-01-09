import java.util.ArrayList;

public class Cargo {

    /**
     * The 3D matrix showing all cubes of the container and what they are occupied
     * by. 0's are empty areas.
     */
    public int[][][] inventory;
    /**
     * The list of all parcels together with their positions in this container
     */
    public ArrayList<ParcelPlacement> placements;
    public ArrayList<Parcel> parcelsLeft;
    /**
     * The parcels are automatically sorted, so that those with the highest
     * value/volume ratio are on the left.
     */
    public int[] parcelsLeftCount;
    public float totalValue;
    public boolean stopProgram = false;

    public Cargo(int[][][] inventory, ArrayList<Parcel> parcels, int[] parcelCount) {
        this.inventory = inventory;
        this.parcelsLeftCount = parcelCount;
        this.parcelsLeft = parcels;
        sortParcels();
    }

    // region Mutator methods
    private void sortParcels() {
        HelperMethods.sort(parcelsLeft);
    }

    public void printParcels() {
        System.out.println("Printing values: ");
        for (Parcel parcel : parcelsLeft) {
            System.out.println(parcel.value);
        }
    }

    /**
     * Checks, if the block can in fact be successfully put in that place. 
     * This method could potentially be deleted in the future.
     * @param block
     * @param pos
     * @return true, if the parcel has been successfully put into place
     */
    public boolean tryStore(Block block, Position pos) {
        if (hasEnoughParcelsToPlaceBlock(block)) {
            if (willFit(block, pos)) {
                storeBlock(block, pos);
                return true;
            } else {
                System.out.println("Error - this block won't fit at that position");
                return false;
            }
        } else {
            System.out.println("Error - not enough parcels to fit that block");
            return false;
        }
    }

    /**
     * This method adds the parcels from the block into the cargo inventory, updates the total value and removes the 
     * number of blocks from the parcelsLeftCount array;
     * @param block
     * @param pos
     */
    private void storeBlock(Block block, Position pos){
        totalValue += block.value;
        addPlacementsToList(block.placements);
        removeParcelsFromList(block);
        placeBlock(block, pos);
    }

    private void placeBlock(Block block, Position pos) {
        int[] dim = block.dimensions;

        for (int layer = 0; layer < dim[0]; layer++) {
            for (int row = 0; row < dim[1]; row++) {
                for (int col = 0; col < dim[2]; col++) {
                    boolean tileNotEmpty = block.shape[layer][row][col] != 0;
                    if (tileNotEmpty) {
                        Position fillPosition = new Position(pos, col, row, layer);
                        int value = block.shape[layer][row][col];

                        fillInventorySpace(fillPosition, value);
                    }
                }
            }
        }
    }

    private void fillInventorySpace(Position pos, int number) {
        inventory[pos.z][pos.y][pos.x] = number;
    }

    public void stopProgram() {
        stopProgram = true;
    }
    
    public void addPlacementsToList(ArrayList<ParcelPlacement> placements){
        this.placements.addAll(placements);
    }
    // endregion

    // region Accessor methods
    private boolean willFit(Block block, Position pos) {
        int[] dim = block.dimensions;
        for (int layer = 0; layer < dim[0]; layer++) {
            for (int row = 0; row < dim[1]; row++) {
                for (int col = 0; col < dim[2]; col++) {
                    if (isOverlapping(block.shape, new Position(pos, col, row, layer))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 
     * @param shape
     * @param pos
     * @return true, if both the shape and the inventory have a block in that
     *         position
     */
    private boolean isOverlapping(int[][][] shape, Position pos) {
        boolean areTilesOverlapping = inventory[pos.z][pos.y][pos.x] != 0 && shape[pos.z][pos.y][pos.x] != 0;
        if (areTilesOverlapping) {
            return true;
        } else {
            return false;
        }
    }

    private boolean hasEnoughParcelsToPlaceBlock(Block block) {
        for (ParcelPlacement placement : block.placements) {
            boolean hasThisParcel = parcelsLeftCount[placement.parcel.index] > 0;
            if (!hasThisParcel) {
                return false;
            }
        }
        return true;
    }

    private void removeParcelsFromList(Block block) {
        for (ParcelPlacement placement : block.placements) {
            parcelsLeftCount[placement.parcel.index] -= 1;
        }
    }

    public boolean allParcelsUsed() {
        for (int count : parcelsLeftCount) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean cargoFilled() {
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {
                for (int k = 0; k < inventory[i][j].length; k++) {

                    if (inventory[i][j][k] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Cargo copy() {
        return new Cargo(HelperMethods.copy(inventory), HelperMethods.copyP(parcelsLeft), parcelsLeftCount);
    }

    public boolean isValueMax() {
        float emptyVolume = HelperMethods.getEmptySpacesCount(inventory);
        boolean couldAParcelFit = emptyVolume > getSmallestParcelSize();
        if (couldAParcelFit) {
            return false;
        } else {
            // This checks, if all the parcels with the highest value/volume ratio were used
            // first.
            // Then, we can say that the the maximum posible value has been reached
            boolean areTheLeastValuableParcelsLeft = HelperMethods.zerosOnlyAtTheFront(parcelsLeftCount);
            if (areTheLeastValuableParcelsLeft) {
                return true;
            } else {
                return false;
            }
        }
    }

    public float getSmallestParcelSize() {
        float smallestSize = Float.MAX_VALUE;
        for (int i = 0; i < parcelsLeftCount.length; i++) {
            float size = parcelsLeft.get(i).getVolume();
            if (size < smallestSize) {
                smallestSize = size;
            }
        }
        return smallestSize;
    }

    public static Parcel getMostValuableParcel(Cargo cargo) {
        for (int i = 0; i < cargo.parcelsLeftCount.length; i++) {
            if (cargo.parcelsLeftCount[0] > 0) {
                return cargo.parcelsLeft.get(i);
            }
        }
        return null;
    }
    // endregion
}
