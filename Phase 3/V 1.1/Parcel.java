import java.util.ArrayList;

/**
 * All the data representing a parcel with its own rotation
 */
public class Parcel {
    /**
     * A 3D array containing the exact build of the parcel
     */
    private int[][][] shape;
    /**
     * Three consecutive numbers X,Y,Z (columns, rows, layers) (eg. 33,8,5)
     */
    public int[] dimensions;
    public float value; // The value of the parcel
    /**
     * The block ID (for differentiating rotations of the same parcel)
     */
    public int index;
    /**
     * The value to volume ratio of the parcel
     */
    public float valueRatio;
    private float volume;

    public Parcel(int[][][] shape, float value, int index) {
        this.index = index;
        this.shape = shape;
        this.value = value;
        int[] newDimensions = { shape[0][0].length, shape[0].length, shape.length };
        this.dimensions = newDimensions;

        volume = this.dimensions[0] * this.dimensions[1] * this.dimensions[2];
        this.valueRatio = value / volume;
    }

    // region Mutator methods
    /**
     * Rotates this parcel's shape
     * 
     * @param xRot
     * @param yRot
     * @param zRot
     */
    public void rotate(int xRot, int yRot, int zRot) {
        setShape(HelperMethods.createShapeRotation(shape, xRot, yRot, zRot));
    }

    public void setShape(int[][][] newShape) {
        shape = newShape;
        updateDimensions();
    }

    public void updateDimensions() {
        int[] newDimensions = { shape[0][0].length, shape[0].length, shape.length };
        this.dimensions = newDimensions;
    }
    // endregion

    // region Accessor methods
    public void print() {
        System.out.println("Printing parcel: ");
        System.out.println("Value: " + value);
        System.out.println("Volume: " + volume);
        System.out.println("Value ratio: " + valueRatio);
        System.out.print("Dimensions: ");
        printDimensions();
        HelperMethods.print(shape);
    }

    private void printDimensions() {
        System.out.print(dimensions[0] + "x");
        System.out.print(dimensions[1] + "x");
        System.out.print(dimensions[2]);
        System.out.println();
    }

    public float getVolume() {
        return volume;
    }

    /**
     * 
     * @param xAxis
     * @param yAxis
     * @param zAxis
     * @return a new parcel that is a rotated version of this one
     */
    public Parcel createRotation(int xAxis, int yAxis, int zAxis) {
        Parcel returnParcel = this.copy();
        returnParcel.rotate(xAxis, yAxis, zAxis);
        return returnParcel;
    }

    public ArrayList<Parcel> generateParcelRotations() {
        ArrayList<int[][][]> shapeRotationList = HelperMethods.generateRotations(this.getShape());
        ArrayList<Parcel> parcelList = new ArrayList<>();
        for (int[][][] shapeRotation : shapeRotationList) {
            Parcel newParcel = this.copy();
            newParcel.setShape(shapeRotation);
            parcelList.add(newParcel);
        }
        return parcelList;
    }

    public Parcel copy() {
        return new Parcel(HelperMethods.copy(shape), value, index);
    }

    public int[][][] getShape() {
        return shape;
    }

    public Block getAsBlock() {
        ArrayList<ParcelPlacement> placements = new ArrayList<ParcelPlacement>();
        Position pos = new Position(0, 0, 0);
        placements.add(new ParcelPlacement(this.copy(), pos));
        return new Block(getShape(), value, placements);
    }
    // endregion
}
