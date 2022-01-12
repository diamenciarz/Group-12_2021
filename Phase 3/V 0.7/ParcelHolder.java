import java.util.ArrayList;

public class ParcelHolder {

    private static int[][][] shapeA = { {
            { 1, 1, 1, 1 },
            { 1, 1, 1, 1 } },
            {
                    { 1, 1, 1, 1 },
                    { 1, 1, 1, 1 } } };
    private static int[][][] shapeB = { {
            { 1, 1, 1, 1 },
            { 1, 1, 1, 1 },
            { 1, 1, 1, 1 } },
            {
                    { 1, 1, 1, 1 },
                    { 1, 1, 1, 1 },
                    { 1, 1, 1, 1 } } };
    private static int[][][] shapeC = { {
            { 1, 1, 1 },
            { 1, 1, 1 },
            { 1, 1, 1 } },
            {
                    { 1, 1, 1 },
                    { 1, 1, 1 },
                    { 1, 1, 1 } },
            {
                    { 1, 1, 1 },
                    { 1, 1, 1 },
                    { 1, 1, 1 } } };

    private static int[][][] shapeL = { {
            { 1, 0 },
            { 1, 0 },
            { 1, 0 },
            { 1, 1 } } };
    private static int[][][] shapeP = { {
            { 1, 0 },
            { 1, 1 },
            { 1, 1 } } };
    private static int[][][] shapeT = { {
            { 1, 1, 1 },
            { 0, 1, 0 },
            { 0, 1, 0 } } };

    private static int valueA = 3;
    private static int valueB = 4;
    private static int valueC = 5;

    private static int valueL = 5;
    private static int valueP = 5;
    private static int valueT = 5;

    public static Parcel Aparcel = new Parcel(shapeA, valueA, 0);
    public static Parcel Bparcel = new Parcel(shapeB, valueB, 1);
    public static Parcel Cparcel = new Parcel(shapeC, valueC, 2);

    public static Parcel Lparcel = new Parcel(shapeL, valueL, 3);
    public static Parcel Pparcel = new Parcel(shapeP, valueP, 4);
    public static Parcel Tparcel = new Parcel(shapeT, valueT, 5);

    public static ArrayList<Parcel> Arotations = HelperMethods.generateParcelRotations(Aparcel);
    public static ArrayList<Parcel> Brotations = HelperMethods.generateParcelRotations(Bparcel);
    public static ArrayList<Parcel> Crotations = HelperMethods.generateParcelRotations(Cparcel);

    public static ArrayList<Parcel> Lrotations = HelperMethods.generateParcelRotations(Lparcel);
    public static ArrayList<Parcel> Protations = HelperMethods.generateParcelRotations(Pparcel);
    public static ArrayList<Parcel> Trotations = HelperMethods.generateParcelRotations(Tparcel);

    private static int[][][] emptyShape = { { { 0 } } };
    public static Parcel emptyParcel = new Parcel(emptyShape, 5, 0);

    public static void main(String[] args) {
        HelperMethods.printP(Lrotations);

    }

    public static int differentParcelCount = 6;

    // region Rotations

    // region A
    private static int[][][] Arotation1 = { { { 1, 1, 1, 1 }, { 1, 1, 1, 1 } }, { { 1, 1, 1, 1 }, { 1, 1, 1, 1 } } };
    private static Parcel parcelA1 = new Parcel(Arotation1, valueA, 0);

    private static int[][][] Arotation2 = { { { 1, 1 }, { 1, 1 } }, { { 1, 1 }, { 1, 1 } }, { { 1, 1 }, { 1, 1 } },
            { { 1, 1 }, { 1, 1 } } };
    private static Parcel parcelA2 = new Parcel(Arotation2, valueA, 0);

    private static int[][][] Arotation3 = { { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } },
            { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } } };
    private static Parcel parcelA3 = new Parcel(Arotation3, valueA, 0);
    // endregion

    // region B
    private static int[][][] Brotation1 = { { { 1, 1, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } },
            { { 1, 1, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } } };
    private static Parcel parcelB1 = new Parcel(Brotation1, valueB, 1);

    private static int[][][] Brotation2 = { { { 1, 1, 1, 1 }, { 1, 1, 1, 1 } }, { { 1, 1, 1, 1 }, { 1, 1, 1, 1 } },
            { { 1, 1, 1, 1 }, { 1, 1, 1, 1 } } };
    private static Parcel parcelB2 = new Parcel(Brotation2, valueB, 1);

    private static int[][][] Brotation3 = { { { 1, 1 }, { 1, 1 }, { 1, 1 } },
            { { 1, 1 }, { 1, 1 }, { 1, 1 } }, { { 1, 1 }, { 1, 1 }, { 1, 1 } },
            { { 1, 1 }, { 1, 1 }, { 1, 1 } } };
    private static Parcel parcelB3 = new Parcel(Brotation3, valueB, 1);

    private static int[][][] Brotation4 = { { { 1, 1, 1 }, { 1, 1, 1 } }, { { 1, 1, 1 }, { 1, 1, 1 } },
            { { 1, 1, 1 }, { 1, 1, 1 } }, { { 1, 1, 1 }, { 1, 1, 1 } } };
    private static Parcel parcelB4 = new Parcel(Brotation4, valueB, 1);

    private static int[][][] Brotation5 = { { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } },
            { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } }, { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } } };
    private static Parcel parcelB5 = new Parcel(Brotation5, valueB, 1);

    private static int[][][] Brotation6 = { { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } },
            { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } },
            { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } } };
    private static Parcel parcelB6 = new Parcel(Brotation6, valueB, 1);
    // endregion

    // region C
    private static int[][][] Crotation1 = { { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } },
            { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } } };
    private static Parcel parcelC1 = new Parcel(Crotation1, valueC, 2);
    // endregion

    // region L
    private static int[][][] Lrotation1 = { { { 1, 0 }, { 1, 0 }, { 1, 0 }, { 1, 1 } } };
    private static Parcel parcelL1 = new Parcel(Lrotation1, valueL, 3);

    private static int[][][] Lrotation2 = { { { 1, 0 } }, { { 1, 0 } }, { { 1, 0 } }, { { 1, 1 } } };
    private static Parcel parcelL2 = new Parcel(Lrotation2, valueL, 3);

    private static int[][][] Lrotation3 = { { { 1, 1 }, { 1, 0 }, { 1, 0 }, { 1, 0 } } };
    private static Parcel parcelL3 = new Parcel(Lrotation3, valueL, 3);

    private static int[][][] Lrotation4 = { { { 1, 1 } }, { { 1, 0 } }, { { 1, 0 } }, { { 1, 0 } } };
    private static Parcel parcelL4 = new Parcel(Lrotation4, valueL, 3);

    private static int[][][] Lrotation5 = { { { 0, 1 }, { 0, 1 }, { 0, 1 }, { 1, 1 } } };
    private static Parcel parcelL5 = new Parcel(Lrotation5, valueL, 3);

    private static int[][][] Lrotation6 = { { { 0, 1 } }, { { 0, 1 } }, { { 0, 1 } }, { { 1, 1 } } };
    private static Parcel parcelL6 = new Parcel(Lrotation6, valueL, 3);

    private static int[][][] Lrotation7 = { { { 1, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 } } };
    private static Parcel parcelL7 = new Parcel(Lrotation7, valueL, 3);

    private static int[][][] Lrotation8 = { { { 1, 1 } }, { { 0, 1 } }, { { 0, 1 } }, { { 0, 1 } } };
    private static Parcel parcelL8 = new Parcel(Lrotation8, valueL, 3);

    private static int[][][] Lrotation9 = { { { 1 }, { 1 }, { 1 }, { 1 } }, { { 0 }, { 0 }, { 0 }, { 1 } } };
    private static Parcel parcelL9 = new Parcel(Lrotation9, valueL, 3);

    private static int[][][] Lrotation10 = { { { 1, 1, 1, 1 } }, { { 1, 0, 0, 0 } } };
    private static Parcel parcelL10 = new Parcel(Lrotation10, valueL, 3);

    private static int[][][] Lrotation11 = { { { 1 }, { 1 }, { 1 }, { 1 } }, { { 1 }, { 0 }, { 0 }, { 0 } } };
    private static Parcel parcelL11 = new Parcel(Lrotation11, valueL, 3);

    private static int[][][] Lrotation12 = { { { 1, 1, 1, 1 } }, { { 0, 0, 0, 1 } } };
    private static Parcel parcelL12 = new Parcel(Lrotation12, valueL, 3);

    private static int[][][] Lrotation13 = { { { 0 }, { 0 }, { 0 }, { 1 } }, { { 1 }, { 1 }, { 1 }, { 1 } } };
    private static Parcel parcelL13 = new Parcel(Lrotation13, valueL, 3);

    private static int[][][] Lrotation14 = { { { 1, 0, 0, 0 } }, { { 1, 1, 1, 1 } } };
    private static Parcel parcelL14 = new Parcel(Lrotation14, valueL, 3);

    private static int[][][] Lrotation15 = { { { 1 }, { 0 }, { 0 }, { 0 } }, { { 1 }, { 1 }, { 1 }, { 1 } } };
    private static Parcel parcelL15 = new Parcel(Lrotation15, valueL, 3);

    private static int[][][] Lrotation16 = { { { 0, 0, 0, 1 } }, { { 1, 1, 1, 1 } } };
    private static Parcel parcelL16 = new Parcel(Lrotation16, valueL, 3);

    private static int[][][] Lrotation17 = { { { 0 }, { 1 } }, { { 0 }, { 1 } }, { { 0 }, { 1 } }, { { 1 }, { 1 } } };
    private static Parcel parcelL17 = new Parcel(Lrotation17, valueL, 3);

    private static int[][][] Lrotation18 = { { { 1, 0, 0, 0 }, { 1, 1, 1, 1 } } };
    private static Parcel parcelL18 = new Parcel(Lrotation18, valueL, 3);

    private static int[][][] Lrotation19 = { { { 1 }, { 1 } }, { { 0 }, { 1 } }, { { 0 }, { 1 } }, { { 0 }, { 1 } } };
    private static Parcel parcelL19 = new Parcel(Lrotation19, valueL, 3);

    private static int[][][] Lrotation20 = { { { 0, 0, 0, 1 }, { 1, 1, 1, 1 } } };
    private static Parcel parcelL20 = new Parcel(Lrotation20, valueL, 3);

    private static int[][][] Lrotation21 = { { { 1 }, { 1 } }, { { 1 }, { 0 } }, { { 1 }, { 0 } }, { { 1 }, { 0 } } };
    private static Parcel parcelL21 = new Parcel(Lrotation21, valueL, 3);

    private static int[][][] Lrotation22 = { { { 1, 1, 1, 1 }, { 0, 0, 0, 1 } } };
    private static Parcel parcelL22 = new Parcel(Lrotation22, valueL, 3);

    private static int[][][] Lrotation23 = { { { 1 }, { 0 } }, { { 1 }, { 0 } }, { { 1 }, { 0 } }, { { 1 }, { 1 } } };
    private static Parcel parcelL23 = new Parcel(Lrotation23, valueL, 3);

    private static int[][][] Lrotation24 = { { { 1, 1, 1, 1 }, { 1, 0, 0, 0 } } };
    private static Parcel parcelL24 = new Parcel(Lrotation24, valueL, 3);
    // endregion
    // Incorrect rotations below
    // region P
    private static int[][][] Protation1 = { { { 1, 0 }, { 1, 0 }, { 1, 0 }, { 1, 1 } } };
    private static Parcel parcelP1 = new Parcel(Protation1, valueP, 3);

    private static int[][][] Protation2 = { { { 1, 0 } }, { { 1, 0 } }, { { 1, 0 } }, { { 1, 1 } } };
    private static Parcel parcelP2 = new Parcel(Protation2, valueP, 3);

    private static int[][][] Protation3 = { { { 1, 1 }, { 1, 0 }, { 1, 0 }, { 1, 0 } } };
    private static Parcel parcelP3 = new Parcel(Protation3, valueP, 3);

    private static int[][][] Protation4 = { { { 1, 1 } }, { { 1, 0 } }, { { 1, 0 } }, { { 1, 0 } } };
    private static Parcel parcelP4 = new Parcel(Protation4, valueP, 3);

    private static int[][][] Protation5 = { { { 0, 1 }, { 0, 1 }, { 0, 1 }, { 1, 1 } } };
    private static Parcel parcelP5 = new Parcel(Protation5, valueP, 3);

    private static int[][][] Protation6 = { { { 0, 1 } }, { { 0, 1 } }, { { 0, 1 } }, { { 1, 1 } } };
    private static Parcel parcelP6 = new Parcel(Protation6, valueP, 3);

    private static int[][][] Protation7 = { { { 1, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 } } };
    private static Parcel parcelP7 = new Parcel(Protation7, valueP, 3);

    private static int[][][] Protation8 = { { { 1, 1 } }, { { 0, 1 } }, { { 0, 1 } }, { { 0, 1 } } };
    private static Parcel parcelP8 = new Parcel(Protation8, valueP, 3);

    private static int[][][] Protation9 = { { { 1 }, { 1 }, { 1 }, { 1 } }, { { 0 }, { 0 }, { 0 }, { 1 } } };
    private static Parcel parcelP9 = new Parcel(Protation9, valueP, 3);

    private static int[][][] Protation10 = { { { 1, 1, 1, 1 } }, { { 1, 0, 0, 0 } } };
    private static Parcel parcelP10 = new Parcel(Protation10, valueP, 3);

    private static int[][][] Protation11 = { { { 1 }, { 1 }, { 1 }, { 1 } }, { { 1 }, { 0 }, { 0 }, { 0 } } };
    private static Parcel parcelP11 = new Parcel(Protation11, valueP, 3);

    private static int[][][] Protation12 = { { { 1, 1, 1, 1 } }, { { 0, 0, 0, 1 } } };
    private static Parcel parcelP12 = new Parcel(Protation12, valueP, 3);

    private static int[][][] Protation13 = { { { 0 }, { 0 }, { 0 }, { 1 } }, { { 1 }, { 1 }, { 1 }, { 1 } } };
    private static Parcel parcelP13 = new Parcel(Protation13, valueP, 3);

    private static int[][][] Protation14 = { { { 1, 0, 0, 0 } }, { { 1, 1, 1, 1 } } };
    private static Parcel parcelP14 = new Parcel(Protation14, valueP, 3);

    private static int[][][] Protation15 = { { { 1 }, { 0 }, { 0 }, { 0 } }, { { 1 }, { 1 }, { 1 }, { 1 } } };
    private static Parcel parcelP15 = new Parcel(Protation15, valueP, 3);

    private static int[][][] Protation16 = { { { 0, 0, 0, 1 } }, { { 1, 1, 1, 1 } } };
    private static Parcel parcelP16 = new Parcel(Protation16, valueP, 3);

    private static int[][][] Protation17 = { { { 0 }, { 1 } }, { { 0 }, { 1 } }, { { 0 }, { 1 } }, { { 1 }, { 1 } } };
    private static Parcel parcelP17 = new Parcel(Protation17, valueP, 3);

    private static int[][][] Protation18 = { { { 1, 0, 0, 0 }, { 1, 1, 1, 1 } } };
    private static Parcel parcelP18 = new Parcel(Protation18, valueP, 3);

    private static int[][][] Protation19 = { { { 1 }, { 1 } }, { { 0 }, { 1 } }, { { 0 }, { 1 } }, { { 0 }, { 1 } } };
    private static Parcel parcelP19 = new Parcel(Protation19, valueP, 3);

    private static int[][][] Protation20 = { { { 0, 0, 0, 1 }, { 1, 1, 1, 1 } } };
    private static Parcel parcelP20 = new Parcel(Protation20, valueP, 3);

    private static int[][][] Protation21 = { { { 1 }, { 1 } }, { { 1 }, { 0 } }, { { 1 }, { 0 } }, { { 1 }, { 0 } } };
    private static Parcel parcelP21 = new Parcel(Protation21, valueP, 3);

    private static int[][][] Protation22 = { { { 1, 1, 1, 1 }, { 0, 0, 0, 1 } } };
    private static Parcel parcelP22 = new Parcel(Protation22, valueP, 3);

    private static int[][][] Protation23 = { { { 1 }, { 0 } }, { { 1 }, { 0 } }, { { 1 }, { 0 } }, { { 1 }, { 1 } } };
    private static Parcel parcelP23 = new Parcel(Protation23, valueP, 3);

    private static int[][][] Protation24 = { { { 1, 1, 1, 1 }, { 1, 0, 0, 0 } } };
    private static Parcel parcelP24 = new Parcel(Protation24, valueP, 3);
    // endregion

    // endregion
}
