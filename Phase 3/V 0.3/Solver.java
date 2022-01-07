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
        Parcel p2 = new Parcel(shape1, 15);
        Parcel p1 = new Parcel(shape1, 10);
        Parcel p3 = new Parcel(shape1, 12);
        
        ArrayList<Parcel> parcels = new ArrayList<>();
        parcels.add(p1);
        parcels.add(p2);
        parcels.add(p3);
        Cargo c = new Cargo(map, parcels, count);
        
         c.printParcels();
         
    }

    public static Cargo recursiveFitter(Cargo cargo) {
        if (cargo.allParcelsUsed() || cargo.cargoFilled()) {
            return cargo;
        } else {
            ArrayList<Placement> possibleMoves = findPossiblePlacements(cargo);
            for (int i = 0; i < possibleMoves.size(); i++) {
                Cargo cargoCopy = cargo.copy();

                int parcelIndex = possibleMoves.get(i).parcelIndex;
                Position placePosition = possibleMoves.get(i).pos;
                cargoCopy.store(parcelIndex, placePosition);
                Cargo newCargo = recursiveFitter(cargoCopy);
                checkHighestValue(newCargo);
            }
        }
        return cargo;
    }

    private static ArrayList<Placement> findPossiblePlacements(Cargo cargo) {
        return new ArrayList<Placement>();
        //TODO: search
    }

    private static void checkHighestValue(Cargo cargo) {
        if (cargo.totalValue > highestValue) {
            highestValue = cargo.totalValue;
        }
    }
}
