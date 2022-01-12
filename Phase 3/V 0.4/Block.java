import java.util.ArrayList;

public class Block {
    /**
     * A 3D array containing the exact build of the block
     */
    public int[][][] shape;
    /**
     * Three consecutive numbers Z,Y,X (layers,rows,columns) (eg. 5,8,33)
     */
    public int[] dimensions;
    public float value; // The total value of the block
    /**
     * The list of all parcels this block consists of
     */
    public ArrayList<ParcelPlacement> placements;
    /**
     * The value to volume ratio of the block
     */
    public float valueRatio;
    private float volume;

    public Block(int[][][] shape, ArrayList<ParcelPlacement> placements) {
        this.shape = shape;
        this.placements = placements;

        updateDimensions();
        UpdateValueRatio();
    }

    public Block(int[][][] shape, float value, ArrayList<ParcelPlacement> placements) {
        this.shape = shape;
        this.value = value;
        this.placements = placements;

        updateDimensions();
        UpdateValueRatio(value);
    }

    // region Mutator methods
    private void UpdateValueRatio() {
        this.value = countTotalValue();
        volume = HelperMethods.getNonemptySpacesCount(shape);

        this.valueRatio = volume / value;
    }

    private void UpdateValueRatio(float value) {
        volume = HelperMethods.getNonemptySpacesCount(shape);
        this.valueRatio = volume / value;
    }

    private void updateDimensions() {
        int[] newDimensions = { shape.length, shape[0].length, shape[0][0].length };
        this.dimensions = newDimensions;
    }

    public void rotate(int xAxis, int yAxis, int zAxis) {
        // Call the rotation method
    }

    // region Merging
    /**
     * Checks, if the parcel can in fact be successfully put in that place.
     * 
     * @param placement
     */
    public void mergeWith(ParcelPlacement placement) {
        boolean canAddParcel = !isShapeOverlapping(placement.parcel.shape, placement.pos);
        if (canAddParcel) {
            addParcel(placement.parcel, placement.pos);
        } else {
            System.out.println("Error - this block won't fit at that position");
        }
    }

    private void addParcel(Parcel parcel, Position pos) {
        this.value += parcel.value;
        addToInventory(parcel, pos);
        UpdateValueRatio(this.value);
    }

    private void addToInventory(Parcel parcel, Position pos) {
        int[] dim = parcel.dimensions;
        expandInventory(dim, pos);

        for (int layer = 0; layer < dim[0]; layer++) {
            for (int row = 0; row < dim[1]; row++) {
                for (int col = 0; col < dim[2]; col++) {
                    boolean tileNotEmpty = parcel.shape[layer][row][col] != 0;
                    if (tileNotEmpty) {
                        Position fillPosition = new Position(pos, col, row, layer);
                        int value = parcel.shape[layer][row][col];

                        fillInventorySpace(fillPosition, value);
                    }
                }
            }
        }
    }

    private void fillInventorySpace(Position pos, int number) {
        shape[pos.z][pos.y][pos.x] = number;
    }

    /**
     * Expands the inventory to fit the additional dimensions
     * 
     * @param dimensions
     * @param pos
     */
    private void expandInventory(int[] dimensions, Position pos) {
        int xLength = dimensions[2] + pos.x;
        int yLength = dimensions[1] + pos.y;
        int zLength = dimensions[0] + pos.z;
        boolean xFits = xLength <= this.dimensions[2];
        boolean yFits = yLength <= this.dimensions[1];
        boolean zFits = zLength <= this.dimensions[0];
        boolean needsToExpand = !(xFits && yFits && zFits);

        if (needsToExpand) {
            int[][][] newInventory = new int[zLength][yLength][zLength];
            copyInventoryContents(newInventory, pos);
            shape = newInventory;
            updateDimensions();
        }
    }

    public void copyInventoryContents(int[][][] newInventory, Position pos) {
        try {
            for (int layer = pos.z; layer < newInventory.length; layer++) {
                for (int row = pos.y; row < newInventory[0].length; row++) {
                    for (int col = pos.x; col < newInventory[0][0].length; col++) {
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

    // region Accessor methods
    public Block copy() {
        return new Block(HelperMethods.copy(shape), value, HelperMethods.copy(placements));
    }

    public float getVolume() {
        return volume;
    }

    private float countTotalValue() {
        float counter = 0;
        for (ParcelPlacement placement : placements) {
            counter += placement.parcel.value;
        }

        return counter;
    }

    private boolean isShapeOverlapping(int[][][] shape, Position pos) {
        int depth = shape.length;
        int height = shape[0].length;
        int width = shape[0][0].length;

        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    Position deltaPosition = new Position(x - pos.x, y - pos.y, z - pos.z);
                    if (!HelperMethods.checkIfTileEmpty(shape, deltaPosition)) {

                        Position shapePosition = new Position(x, y, z);
                        if (!HelperMethods.checkIfTileEmpty(this.shape, shapePosition)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void print() {
        HelperMethods.print(shape);
        System.out.println("Value: " + value);
        System.out.println("Volume: " + volume);
        System.out.println("Value ratio: " + valueRatio);
        System.out.print("Dimensions: ");
        HelperMethods.print(dimensions);
    }
    // endregion
}