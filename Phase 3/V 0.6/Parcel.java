public class Parcel {
    /**
     * A 3D array containing the exact build of the parcel
     */
    public int[][][] shape;
    /**
     * Three consecutive numbers Z,Y,X (layers,rows,columns) (eg. 5,8,33)
     */
    public int[] dimensions;
    public float value; // The value of the parcel
    public int index;
    /**
     * The value to volume ratio of the parcel
     */
    public float valueRatio;
    private float volume;

    public Parcel(int[][][] shape, float value, int index) {
        this.shape = shape;
        this.value = value;
        int[] newDimensions = { shape.length, shape[0].length, shape[0][0].length };
        this.dimensions = newDimensions;

        volume = HelperMethods.getNonemptySpacesCount(shape);
        this.valueRatio = volume/value;
        
    }

    public void updateDimensions() {
        int[] newDimensions = { shape.length, shape[0].length, shape[0][0].length };
        this.dimensions = newDimensions;
    }
    
    public Parcel copy(){
        return new Parcel(HelperMethods.copy(shape), value, index);
    }
    public void rotate(int xAxis, int yAxis, int zAxis){
        //Call the rotation method
    }
    public float getVolume(){
        return volume;
    }
    public void print(){
        HelperMethods.print(shape);
        System.out.println("Value: " + value);
        System.out.println("Volume: " + volume);
        System.out.println("Value ratio: " + valueRatio);
        System.out.print("Dimensions: ");
        HelperMethods.print(dimensions);
    }
}
