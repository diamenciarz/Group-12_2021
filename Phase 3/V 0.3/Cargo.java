import java.util.ArrayList;

public class Cargo {
    public static void main(String[] args) {
        int[][][] map = {
                { { 1, 1, 0, 0, 0 }, { 1, 0, 0, 1, 1 } },
                { { 1, 1, 0, 1, 1 }, { 0, 1, 0, 1, 0 } },
                { { 0, 0, 1, 1, 0 }, { 0, 0, 1, 1, 0 } } };
    }

    public int[][][] inventory;
    public ArrayList<Parcel> parcelsLeft;
    public int[] parcelsLeftCount;
    public float totalValue;

    public Cargo(int[][][] inventory, ArrayList<Parcel> parcels, int[] parcelCount) {
        this.inventory = inventory;
        this.parcelsLeftCount = parcelCount;
        this.parcelsLeft = parcels;
        sortParcels();
    }

    // region Mutator methods
    public void sortParcels() {
        HelperMethods.sort(parcelsLeft);
    }

    public void printParcels() {
        System.out.println("Printing values: ");
        for (Parcel parcel : parcelsLeft) {
            System.out.println(parcel.value);
        }
    }

    public void store(int parcelIndex, Position pos) {
        Parcel parcel = parcelsLeft.get(parcelIndex);
        boolean canPlaceParcel = parcelsLeftCount[parcelIndex] > 0 && willFit(parcel, pos);
        if (canPlaceParcel) {
            totalValue += parcel.value;
            parcelsLeftCount[parcelIndex] -= 1;
            placeParcel(parcel, pos);
        }
    }

    private void placeParcel(Parcel parcel, Position pos) {
        int[] dim = parcel.dimensions;

        for (int layer = 0; layer < dim[0]; layer++) {
            for (int row = 0; row < dim[1]; row++) {
                for (int col = 0; col < dim[2]; col++) {
                    boolean tileNotEmpty = parcel.shape[layer][row][col] != 0;
                    if (tileNotEmpty) {
                        inventory[layer + pos.z][row + pos.y][col + pos.x] = parcel.shape[layer][row][col];
                    }
                }
            }
        }
    }
    // endregion

    // region Accessor methods
    private boolean willFit(Parcel parcel, Position pos) {
        int[] dim = parcel.dimensions;
        for (int layer = 0; layer < dim[0]; layer++) {
            for (int row = 0; row < dim[1]; row++) {
                for (int col = 0; col < dim[2]; col++) {
                    if (isOverlapping(parcel.shape, new Position(pos, col, row, layer))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isOverlapping(int[][][] shape, Position pos) {
        boolean areTilesOverlapping = inventory[pos.z][pos.y][pos.x] != 0 && shape[pos.z][pos.y][pos.x] != 0;
        if (areTilesOverlapping) {
            return true;
        } else {
            return false;
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
        return new Cargo(HelperMethods.copy(inventory), HelperMethods.copy(parcelsLeft), parcelsLeftCount);
    }
    // endregion
}
