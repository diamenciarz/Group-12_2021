import java.util.ArrayList;

public class ParcelHolder {

        private static int[][][] shapeA = { {
                        { 1, 1, 1, 1 },
                        { 1, 1, 1, 1 } },
                        {
                                        { 1, 1, 1, 1 },
                                        { 1, 1, 1, 1 } } };
        private static int[][][] shapeB = { {
                        { 2, 2, 2, 2 },
                        { 2, 2, 2, 2 },
                        { 2, 2, 2, 2 } },
                        {
                                        { 2, 2, 2, 2 },
                                        { 2, 2, 2, 2 },
                                        { 2, 2, 2, 2 } } };
        private static int[][][] shapeC = { {
                        { 3, 3, 3 },
                        { 3, 3, 3 },
                        { 3, 3, 3 } },
                        {
                                        { 3, 3, 3 },
                                        { 3, 3, 3 },
                                        { 3, 3, 3 } },
                        {
                                        { 3, 3, 3 },
                                        { 3, 3, 3 },
                                        { 3, 3, 3 } } };

        private static int[][][] shapeL = { {
                        { 1, 0 },
                        { 1, 0 },
                        { 1, 0 },
                        { 1, 1 } } };
        private static int[][][] shapeP = { {
                        { 2, 0 },
                        { 2, 2 },
                        { 2, 2 } } };
        private static int[][][] shapeT = { {
                        { 3, 3, 3 },
                        { 0, 3, 0 },
                        { 0, 3, 0 } } };

        private static int valueA = 3;
        private static int valueB = 4;
        private static int valueC = 5;

        private static int valueL = 3;
        private static int valueP = 4;
        private static int valueT = 5;

        public static Parcel Aparcel() {
                return new Parcel(shapeA, valueA, 0);
        }

        public static Parcel Bparcel() {
                return new Parcel(shapeB, valueB, 1);
        }

        public static Parcel Cparcel() {
                return new Parcel(shapeC, valueC, 2);
        }

        public static Parcel Lparcel() {
                return new Parcel(shapeL, valueL, 3);
        }

        public static Parcel Pparcel() {
                return new Parcel(shapeP, valueP, 4);
        }

        public static Parcel Tparcel() {
                return new Parcel(shapeT, valueT, 5);
        }

        private static ArrayList<Parcel> parcelList = new ArrayList<>();

        public static ArrayList<Parcel> getParcelList() {
                return HelperMethods.copyP(parcelList);
        }

        public static ArrayList<Parcel> Arotations = null;
        public static ArrayList<Parcel> Brotations = null;
        public static ArrayList<Parcel> Crotations = null;

        public static ArrayList<Parcel> Lrotations = null;
        public static ArrayList<Parcel> Protations = null;
        public static ArrayList<Parcel> Trotations = null;

        private static int[][][] emptyShape = { { { 0 } } };
        public static Parcel emptyParcel = null;

        public static int differentParcelCount = 6;

        public static void createParcels(boolean useABCParcels) {
                if (useABCParcels) {
                        Arotations = Aparcel().generateParcelRotations();
                        Brotations = Bparcel().generateParcelRotations();
                        Crotations = Cparcel().generateParcelRotations();
                } else {
                        Lrotations = Lparcel().generateParcelRotations();
                        Protations = Pparcel().generateParcelRotations();
                        Trotations = Tparcel().generateParcelRotations();
                }
                emptyParcel = new Parcel(emptyShape, 5, 0);

                fillParcelList();
        }

        private static void fillParcelList() {
                parcelList.add(Aparcel());
                parcelList.add(Bparcel());
                parcelList.add(Cparcel());

                parcelList.add(Lparcel());
                parcelList.add(Pparcel());
                parcelList.add(Tparcel());
        }
}
