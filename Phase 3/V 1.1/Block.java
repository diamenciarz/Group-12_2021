import java.util.ArrayList;

/**
 * All the data representing a pack of parcels assembled together
 */
public class Block {
    /**
     * A 3D array containing the exact build of the block
     */
    public int[][][] shape = new int[1][1][1];
    /**
     * Three consecutive numbers X,Y,Z (columns, rows, layers) (eg. 33,8,5)
     */
    public int[] dimensions = new int[3];
    /**
     * The list of all parcels this block consists of
     */
    public ArrayList<ParcelPlacement> placements;
    public int[] parcelsCount = new int[ParcelHolder.differentParcelCount];
    /**
     * The value to volume ratio of the block
     */
    public float value; // The total value of the block
    /**
     * The ratio of total value to the volume of the cuboid that this shape takes up
     * including empty spaces.
     */
    public float valueRatio;
    private float volume;

    // region Constructors
    public Block(ArrayList<ParcelPlacement> placements) {
        this.placements = placements;
        updateShape();

        updateParcelsCount();
        updateDimensions();
        updateVolume();
        updateValueRatio();
    }

    public Block(int[][][] shape, ArrayList<ParcelPlacement> placements) {
        this.placements = placements;
        this.shape = shape;

        updateParcelsCount();
        updateDimensions();
        updateVolume();
        updateValueRatio();
    }

    public Block(int[][][] shape, float value, ArrayList<ParcelPlacement> placements) {
        this.value = value;
        this.placements = placements;
        this.shape = shape;

        updateParcelsCount();
        updateDimensions();
        updateVolume();
        updateValueRatio(value);
    }
    // endregion

    // region Mutator methods
    public void rotate(int xAxis, int yAxis, int zAxis) {
        shape = HelperMethods.createShapeRotation(shape, xAxis, yAxis, zAxis);
        for (ParcelPlacement placement : placements) {
            placement.rotate(xAxis, yAxis, zAxis, dimensions);
        }
        updateDimensions();
        // Call the rotation method
    }

    // region Update values
    private void updateShape() {
        shape = new int[1][1][1];
        for (ParcelPlacement placement : placements) {
            updatePlacement(placement);
        }
    }

    private void updatePlacement(ParcelPlacement placement) {
        addToInventory(placement.parcel, placement.pos);
    }

    private void updateParcelsCount() {
        for (ParcelPlacement placements : placements) {
            parcelsCount[placements.parcel.index]++;
        }
    }

    private void updateVolume() {
        volume = HelperMethods.getNonemptySpacesCount(shape);
    }

    private void updateValueRatio() {
        this.value = getTotalValue();
        int dimension = dimensions[0] * dimensions[1] * dimensions[2];
        this.valueRatio = value / dimension;
    }

    private void updateValueRatio(float value) {
        int dimension = dimensions[0] * dimensions[1] * dimensions[2];
        this.valueRatio = value / dimension;
    }

    public void updateDimensions() {
        int[] newDimensions = { shape[0][0].length, shape[0].length, shape.length };
        this.dimensions = newDimensions;
    }
    // endregion

    // region Merging
    /**
     * Checks, if the Block can in fact be successfully put at that position.
     * If so, then it adds it.
     * 
     * @param blockPlacement
     * @return true, if the placement was conducted successfully. Returns false, if
     *         the merge failed due to a collision
     */
    public boolean mergeWith(BlockPlacement blockPlacement) {
        Block myCopy = this.copy();
        for (ParcelPlacement placement : blockPlacement.block.placements) {
            ParcelPlacement movedPlacement = placement.copy();
            movedPlacement.pos = new Position(movedPlacement.pos, blockPlacement.pos.x, blockPlacement.pos.y,
                    blockPlacement.pos.z);
            if (!myCopy.mergeWith(movedPlacement)) {
                System.out.println("Error - this block won't fit at that position");
                return false;
            }
        }
        myCopy.updateDimensions();
        myCopy.updateVolume();
        myCopy.updateValueRatio(this.value);
        setTo(myCopy);
        return true;
    }

    /**
     * Checks, if the parcel can in fact be successfully put at that position.
     * If so, then it adds it.
     * 
     * @param placement
     * @return true, if the placement was conducted successfully. Returns false, if
     *         the merge failed due to a collision.
     */
    public boolean mergeWith(ParcelPlacement placement2) {
        ParcelPlacement placement = placement2.copy();
        boolean canAddParcel = !isShapeOverlapping(placement.parcel.getShape(), placement.pos);
        if (canAddParcel) {
            addParcel(placement);
            return true;
        } else {
            System.out.println("Error - this parcel won't fit at that position");
            return false;
        }
    }

    /**
     * Sets all variables of this block to the one given as input
     * 
     * @param block
     */
    public void setTo(Block block) {
        parcelsCount = block.parcelsCount;
        dimensions = block.dimensions;
        placements = block.placements;
        shape = block.shape;
        value = block.value;
        valueRatio = block.valueRatio;
        volume = block.volume;
    }

    // region Helper methods
    private void addParcel(ParcelPlacement placement) {
        this.value += placement.parcel.value;
        addToInventory(placement.parcel, placement.pos);
        parcelsCount[placement.parcel.index]++;
        placements.add(placement);
    }

    private void addToInventory(Parcel parcel, Position pos) {
        Position position = pos.copy();
        int[] dim = parcel.dimensions;
        expandInventory(dim, position);
        setNegativeValuesToZero(position);
        for (int layer = 0; layer < dim[2]; layer++) {
            for (int row = 0; row < dim[1]; row++) {
                for (int col = 0; col < dim[0]; col++) {
                    boolean tileNotEmpty = parcel.getShape()[layer][row][col] != 0;
                    if (tileNotEmpty) {
                        Position fillPosition = new Position(position, col, row, layer);
                        int value = parcel.getShape()[layer][row][col];

                        setInventorySpot(fillPosition, value);
                    }
                }
            }
        }
    }

    private void setInventorySpot(Position pos, int number) {
        shape[pos.z][pos.y][pos.x] = number;
    }

    /**
     * Expands the inventory to fit the additional dimensions. Changes all negative
     * coordinates of the position to zero's
     * 
     * @param dimensions
     * @param pos
     */
    private void expandInventory(int[] dimensions, Position pos) {
        Position position = pos.copy();
        Position pos1 = new Position(0, 0, 0);
        Position pos2 = new Position(shape[0][0].length - 1, shape[0].length - 1, shape.length - 1);
        Position pos3 = position.copy();
        Position pos4 = new Position(position, dimensions[0] - 1, dimensions[1] - 1, dimensions[2] - 1);

        int[][][] newInventory = HelperMethods.getExpandedVolume(pos1, pos2, pos3, pos4);
        int[] newDimensions = { newInventory[0][0].length, newInventory[0].length, newInventory.length };
        translatePosition(position);

        boolean needsToExpand = !HelperMethods.areEqual(newDimensions, this.dimensions);
        if (needsToExpand) {
            copyInventoryContents(newInventory, position);
            shape = newInventory;
        }
    }

    private void translatePosition(Position pos) {
        // if negative - set to positive,
        // if positive - set to zero
        if (pos.x >= 0) {
            pos.x = 0;
        } else {
            pos.x *= -1;
        }
        if (pos.y >= 0) {
            pos.y = 0;
        } else {
            pos.y *= -1;
        }
        if (pos.z >= 0) {
            pos.z = 0;
        } else {
            pos.z *= -1;
        }
    }

    private void setNegativeValuesToZero(Position pos) {
        pos.x = HelperMethods.higherValue(0, pos.x);
        pos.y = HelperMethods.higherValue(0, pos.y);
        pos.z = HelperMethods.higherValue(0, pos.z);
    }

    private void copyInventoryContents(int[][][] newInventory, Position pos) {
        try {
            for (int layer = 0; layer < shape.length; layer++) {
                for (int row = 0; row < shape[0].length; row++) {
                    for (int col = 0; col < shape[0][0].length; col++) {
                        newInventory[layer + pos.z][row + pos.y][col + pos.x] = shape[layer][row][col];

                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error - tried to copy inventory contents outside of boundaries of the matrix");
        }
    }
    // endregion
    // endregion
    // endregion

    // region Accessor methods
    public Block copy() {
        return new Block(HelperMethods.copy(shape), value, HelperMethods.copy(placements));
    }

    public float getVolume() {
        return volume;
    }

    private float getTotalValue() {
        float counter = 0;
        for (ParcelPlacement placement : placements) {
            counter += placement.parcel.value;
        }
        return counter;
    }

    /**
     * @param xAxis
     * @param yAxis
     * @param zAxis
     * @return returns a new block that is a rotated version of this block
     */
    public Block createRotation(int xAxis, int yAxis, int zAxis) {
        Block copy = this.copy();
        copy.rotate(xAxis, yAxis, zAxis);
        return copy;
    }

    public void print() {
        HelperMethods.print(shape);
        System.out.println("Value: " + value);
        System.out.println("Volume: " + volume);
        System.out.println("Value ratio: " + valueRatio);
        System.out.print("Dimensions: ");
        HelperMethods.printDimensions(dimensions);
        System.out.print("Parcels used: ");
        HelperMethods.printParcels(parcelsCount);
    }

    // region Helper methods
    private boolean isShapeOverlapping(int[][][] shape, Position pos) {
        int depth = shape.length;
        int height = shape[0].length;
        int width = shape[0][0].length;

        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    Position deltaPosition = new Position(x - pos.x, y - pos.y, z - pos.z);
                    if (!HelperMethods.isEmpty(shape, deltaPosition)) {

                        Position shapePosition = new Position(x, y, z);
                        if (!HelperMethods.isEmpty(this.shape, shapePosition)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    // endregion
    // endregion
}