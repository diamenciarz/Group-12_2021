import java.util.ArrayList;

public class BlockHolder {
    /**
     * A list containing all pre-assembled blocks
     */
    public static ArrayList<Block> blockList = getBlocks();

    private static ArrayList<Block> getBlocks() {
        ArrayList<Block> returnArray = new ArrayList<>();
        return returnArray;
    }

    public static void main(String[] args) {
        P6T2.print();
        HelperMethods.print(P6T2.parcelsCount);
    }
    // region Block definitions

    // region PTP
    public static Block getPTP(){
        return PTP;
    }
    private static Block PTP = createPTP();
    private static Block createPTP(){
        ArrayList<ParcelPlacement> placements = new ArrayList<>();
        
        ParcelPlacement parcelPlacement = new ParcelPlacement(ParcelHolder.Tparcel, new Position(1, 0, 0));
        placements.add(parcelPlacement);
        ParcelPlacement parcelPlacement2 = new ParcelPlacement(ParcelHolder.Pparcel, new Position(0, 0, 0));
        placements.add(parcelPlacement2);
        ParcelPlacement parcelPlacement3 = new ParcelPlacement(HelperMethods.createParcelRotation(ParcelHolder.Pparcel, 0, 2, 0), new Position(3, 0, 0));
        placements.add(parcelPlacement3);
        Block PTP = new Block(placements);
        
        return PTP;
    }
    // endregion
    
    // region PP
    public static Block getPP(){
        return PP;
    }
    private static Block PP = createPP();
    private static Block createPP(){
        ArrayList<ParcelPlacement> placements = new ArrayList<>();
        
        ParcelPlacement parcelPlacement = new ParcelPlacement(ParcelHolder.Pparcel, new Position(0, 0, 0));
        placements.add(parcelPlacement);
        ParcelPlacement parcelPlacement2 = new ParcelPlacement(HelperMethods.createParcelRotation(ParcelHolder.Pparcel, 0, 0, 2), new Position(0, -2, 0));
        placements.add(parcelPlacement2);
        Block PTP = new Block(placements);
        
        return PTP;
    }
    // endregion
    
    // region LL
    public static Block getLL(){
        return LL;
    }
    private static Block LL = createLL();
    private static Block createLL(){
        ArrayList<ParcelPlacement> placements = new ArrayList<>();
        
        ParcelPlacement parcelPlacement = new ParcelPlacement(ParcelHolder.Lparcel, new Position(0, 0, 0));
        placements.add(parcelPlacement);
        ParcelPlacement parcelPlacement2 = new ParcelPlacement(HelperMethods.createParcelRotation(ParcelHolder.Lparcel, 0, 0, 2), new Position(0, -3, 0));
        placements.add(parcelPlacement2);
        Block PTP = new Block(placements);
        
        return PTP;
    }
    // endregion
    
    // region LL
    public static Block getP6T2(){
        return P6T2;
    }
    private static Block P6T2 = createP6T2();
    private static Block createP6T2(){
        Block P6T2 = PP.copy();
        P6T2.mergeWith(new BlockPlacement(PTP, new Position(2, 0, 0)));
        P6T2.mergeWith(new BlockPlacement(PTP, new Position(2, 3, 0)));

        return P6T2;
    }
    // endregion

    // endregion
}
