public class Parcel {
    /**
     * A 3D array containing the exact build of the parcel
     */
    public int[][][] shape;
    /**
     * Three consecutive numbers Z,Y,X (layers,rows,columns) (5,8,33)
     */
    public int[] dimensions;
    public float value; // The value of the parcel

    public Parcel(int[][][] shape, float value) {
        this.shape = shape;
        this.value = value;
        int[] newDimensions = { shape.length, shape[0].length, shape[0][0].length };
        this.dimensions = newDimensions;
    }
    public Parcel copy(){
        return new Parcel(HelperMethods.copy(shape), value);
    }
}
