import java.util.ArrayList;

public class BlockHolder {
    /**
     * A list containing all pre-assembled blocks
     */
    private static ArrayList<Block> blockList = new ArrayList<>();

    public static ArrayList<Block> getBlocks() {
        return blockList;
    }

    public static void main(String[] args) {
        int[] dim = { 5, 10, 12 };
        ParcelHolder.createParcels(false);
        blockList.addAll(createMultiplications(ParcelHolder.Aparcel().getAsBlock(), dim));

        HelperMethods.printB(blockList);
    }

    // region Mutator methods
    public static void setBlockList(ArrayList<Block> newList) {
        blockList = newList;
    }

    /**
     * This methods creates all of the predefined shapes, so it should always be
     * called before the program starts running.
     */
    public static void startup(int[] cargoDimensions, boolean useABCParcels) {
        ParcelHolder.createParcels(useABCParcels);
        addSingleParcelsToBlockList(useABCParcels);
        createBlocks(useABCParcels);
        fillListWithBlocks(useABCParcels);
        createScaledBlocks(cargoDimensions, useABCParcels);
    }
    // endregion

    // region Add blocks
    private static void addSingleParcelsToBlockList(boolean useABCParcels) {
        if (useABCParcels) {
            blockList.add(ParcelHolder.Lparcel().getAsBlock());
            blockList.add(ParcelHolder.Pparcel().getAsBlock());
            blockList.add(ParcelHolder.Tparcel().getAsBlock());
        } else {
            blockList.add(ParcelHolder.Aparcel().getAsBlock());
            blockList.add(ParcelHolder.Bparcel().getAsBlock());
            blockList.add(ParcelHolder.Cparcel().getAsBlock());
        }
    }

    private static void createBlocks(boolean useABCParcels) {
        if (useABCParcels) {
            C4 = createC4();
        } else {
            PP = createPP();
            LL = createLL();
            T5 = createT5();
            T10 = createT10();

            P2T = createP2T();
            P4T2 = createP4T2();
            P6T2 = createP6T2();
        }
    }

    private static void fillListWithBlocks(boolean useABCParcels) {
        ArrayList<Block> newArray = new ArrayList<>();
        if (useABCParcels) {

        } else {
            newArray.add(T5);
            newArray.add(T10);
            ParcelHolder.Lparcel().getAsBlock();
            ParcelHolder.Pparcel().getAsBlock();
            ParcelHolder.Tparcel().getAsBlock();
        }

        blockList = newArray;
    }

    private static void createScaledBlocks(int[] cargoDimensions, boolean useABCParcels) {
        if (useABCParcels) {
            blockList.addAll(createMultiplications(ParcelHolder.Aparcel().getAsBlock(), cargoDimensions));
            blockList.addAll(createMultiplications(ParcelHolder.Bparcel().getAsBlock(), cargoDimensions));
            blockList.addAll(createMultiplications(ParcelHolder.Cparcel().getAsBlock(), cargoDimensions));
        } else {
            blockList.addAll(createMultiplications(getLL(), cargoDimensions));
            blockList.addAll(createMultiplications(getPP(), cargoDimensions));
            blockList.addAll(createMultiplications(getP2T(), cargoDimensions));
            blockList.addAll(createMultiplications(getP4T2(), cargoDimensions));
            blockList.addAll(createMultiplications(getP6T2(), cargoDimensions));
        }
    }

    public static ArrayList<Block> createMultiplications(Block block, int[] cargoDimensions) {
        ArrayList<Block> newBlocks = new ArrayList<>();

        int cargoWidth = cargoDimensions[0];
        int cargoHeight = cargoDimensions[1];
        int cargoDepth = cargoDimensions[2];

        int blockWidth = block.dimensions[0];
        int blockHeight = block.dimensions[1];
        int blockDepth = block.dimensions[2];
        // X axis
        int xRepetitions = (cargoWidth / blockWidth) - 1;
        ArrayList<Block> horizontalBlocks = createXRepetitions(block, xRepetitions);
        // newBlocks.addAll(horizontalBlocks);
        // horizontalBlocks.get(horizontalBlocks.size() - 1).print();
        // Y axis
        int yRepetitions = (cargoHeight / blockHeight) - 1;
        ArrayList<Block> verticalBlocks = new ArrayList<>();
        for (Block block2 : horizontalBlocks) {
            verticalBlocks.addAll(createYRepetitions(block2, yRepetitions));
        }
        // newBlocks.addAll(verticalBlocks);
        // verticalBlocks.get(verticalBlocks.size() - 1).print();
        // Z axis
        int zRepetitions = countZRepetitions(blockDepth, cargoDepth) - 1;
        for (Block block2 : verticalBlocks) {
            ArrayList<Block> extendedBlocks = createZRepetitions(block2, zRepetitions);
            newBlocks.addAll(extendedBlocks);
        }
        // extendedBlocks.get(weirdBlocks.size() - 1).print();
        return newBlocks;
    }

    private static int countZRepetitions(int blockDepth, int cargoDepth) {
        int repetitions = 0;

        while (blockDepth <= cargoDepth) {
            repetitions++;
            blockDepth *= 2;
        }

        return repetitions;
    }

    private static ArrayList<Block> createXRepetitions(Block block, int repetitions) {
        ArrayList<Block> newBlocks = new ArrayList<>();
        if (block != null) {
            newBlocks.add(block.copy());
            Block newBlock = block.copy();
            for (int i = 0; i < repetitions; i++) {
                newBlock.mergeWith(new BlockPlacement(block.copy(), new Position(newBlock.dimensions[0], 0, 0)));
                newBlocks.add(newBlock.copy());
            }
        }
        return newBlocks;
    }

    private static ArrayList<Block> createYRepetitions(Block block, int repetitions) {
        ArrayList<Block> newBlocks = new ArrayList<>();
        if (block != null) {
            newBlocks.add(block.copy());
            Block newBlock = block.copy();
            for (int i = 0; i < repetitions; i++) {
                newBlock.mergeWith(new BlockPlacement(block.copy(), new Position(0, newBlock.dimensions[1], 0)));
                newBlocks.add(newBlock.copy());
            }
        }
        return newBlocks;
    }

    private static ArrayList<Block> createZRepetitions(Block block, int repetitions) {
        ArrayList<Block> newBlocks = new ArrayList<>();
        if (block != null) {
            newBlocks.add(block.copy());
            Block newBlock = block.copy();
            for (int i = 0; i < repetitions; i++) {
                newBlock.mergeWith(new BlockPlacement(newBlock, new Position(0, 0, newBlock.dimensions[2])));
                newBlocks.add(newBlock.copy());
            }
        }
        return newBlocks;
    }
    // endregion

    // region Block definitions

    // region LPT Parcels
    // region P2T
    public static Block getP2T() {
        return P2T;
    }

    private static Block P2T = null;

    private static Block createP2T() {
        ArrayList<ParcelPlacement> placements = new ArrayList<>();

        ParcelPlacement parcelPlacement = new ParcelPlacement(ParcelHolder.Tparcel(), new Position(1, 0, 0));
        placements.add(parcelPlacement);
        ParcelPlacement parcelPlacement2 = new ParcelPlacement(ParcelHolder.Pparcel(), new Position(0, 0, 0));
        placements.add(parcelPlacement2);
        ParcelPlacement parcelPlacement3 = new ParcelPlacement(ParcelHolder.Pparcel().createRotation(0, 2, 0),
                new Position(3, 0, 0));
        placements.add(parcelPlacement3);
        Block P2T = new Block(placements);

        return P2T;
    }
    // endregion

    // region PP
    public static Block getPP() {
        return PP;
    }

    private static Block PP = null;

    private static Block createPP() {
        ArrayList<ParcelPlacement> placements = new ArrayList<>();

        ParcelPlacement parcelPlacement = new ParcelPlacement(ParcelHolder.Pparcel().createRotation(0, 0, 2),
                new Position(0, 0, 0));
        placements.add(parcelPlacement);
        ParcelPlacement parcelPlacement2 = new ParcelPlacement(ParcelHolder.Pparcel(),
                new Position(0, 2, 0));
        placements.add(parcelPlacement2);
        Block PP = new Block(placements);

        return PP;
    }
    // endregion

    // region LL
    public static Block getLL() {
        return LL;
    }

    private static Block LL = null;

    private static Block createLL() {
        ArrayList<ParcelPlacement> placements = new ArrayList<>();

        ParcelPlacement parcelPlacement = new ParcelPlacement(ParcelHolder.Lparcel().createRotation(0, 0, 2),
                new Position(0, 0, 0));
        placements.add(parcelPlacement);
        ParcelPlacement parcelPlacement2 = new ParcelPlacement(ParcelHolder.Lparcel(),
                new Position(0, 1, 0));
        placements.add(parcelPlacement2);
        Block LL = new Block(placements);

        return LL;
    }
    // endregion

    // region T5
    public static Block getT5() {
        return T5;
    }

    private static Block T5 = null;

    private static Block createT5() {
        ArrayList<ParcelPlacement> placements = new ArrayList<>();

        ParcelPlacement parcelPlacement = new ParcelPlacement(ParcelHolder.Tparcel(), new Position(0, 0, 0));
        placements.add(parcelPlacement);
        ParcelPlacement parcelPlacement2 = new ParcelPlacement(ParcelHolder.Tparcel(), new Position(0, 0, 1));
        placements.add(parcelPlacement2);
        ParcelPlacement parcelPlacement3 = new ParcelPlacement(ParcelHolder.Tparcel(), new Position(0, 0, 2));
        placements.add(parcelPlacement3);
        ParcelPlacement parcelPlacement4 = new ParcelPlacement(ParcelHolder.Tparcel(), new Position(0, 0, 3));
        placements.add(parcelPlacement4);
        ParcelPlacement parcelPlacement5 = new ParcelPlacement(ParcelHolder.Tparcel(), new Position(0, 0, 4));
        placements.add(parcelPlacement5);
        Block T5 = new Block(placements);

        return T5;
    }
    // endregion

    // region T10
    public static Block getT10() {
        return T10;
    }

    private static Block T10 = null;

    private static Block createT10() {
        Block T10 = T5.copy();
        T10.mergeWith(new BlockPlacement(T5.createRotation(0, 0, 2), new Position(1, 1, 0)));
        return T10;
    }
    // endregion

    // region P4T2
    public static Block getP4T2() {
        return P4T2;
    }

    private static Block P4T2 = null;

    private static Block createP4T2() {
        Block P4T2 = P2T.copy();
        P4T2.mergeWith(new BlockPlacement(P2T.copy(), new Position(0, 3, 0)));

        return P4T2;
    }
    // endregion

    // region P6T2
    public static Block getP6T2() {
        return P6T2;
    }

    private static Block P6T2 = null;

    private static Block createP6T2() {
        Block P6T2 = P4T2.copy();
        P6T2.mergeWith(new BlockPlacement(PP.createRotation(0, 0, 1), new Position(0, 6, 0)));

        return P6T2;
    }
    // endregion
    // endregion

    // region ABC Parcels
    // region C4
    public static Block getC4() {
        return C4;
    }

    private static Block C4 = null;

    private static Block createC4() {
        ArrayList<ParcelPlacement> placements = new ArrayList<>();

        ParcelPlacement parcelPlacement = new ParcelPlacement(ParcelHolder.Tparcel(), new Position(0, 0, 0));
        placements.add(parcelPlacement);
        ParcelPlacement parcelPlacement2 = new ParcelPlacement(ParcelHolder.Pparcel(), new Position(3, 0, 0));
        placements.add(parcelPlacement2);
        ParcelPlacement parcelPlacement3 = new ParcelPlacement(ParcelHolder.Pparcel(), new Position(0, 3, 0));
        placements.add(parcelPlacement3);
        ParcelPlacement parcelPlacement4 = new ParcelPlacement(ParcelHolder.Pparcel(), new Position(3, 3, 0));
        placements.add(parcelPlacement4);
        Block C4 = new Block(placements);

        return C4;
    }
    // endregion
    // endregion

    // endregion
}
