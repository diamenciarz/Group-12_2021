public class ParcelPlacement {
    public Parcel parcel;
    public Position pos;

    public ParcelPlacement(Parcel parcel, Position pos) {
        this.parcel = parcel;
        this.pos = pos;
    }

    public ParcelPlacement copy() {
        return new ParcelPlacement(parcel.copy(), pos.copy());
    }

    public static void main(String[] args) {
        int[][][] a = { { { 1, 0 }, { 1, 0 }, { 1, 0 }, { 1, 1 } } };
        System.out.println("Original matrix: ");
        HelperMethods.print(a);
        System.out.println("Rotations: ");
        HelperMethods.print(HelperMethods.generateRotations(a));
    }
}