import java.util.ArrayList;
import java.util.Arrays;

public class Rotations {
  public static ArrayList<int[][][]> rotations = new ArrayList<int[][][]>();

  static int[][][] shapeA = new int[4][2][2];
  static int[][][] shapeB = new int[4][2][3]; // dimensions for A,B,C have been multipled by 2.
  static int[][][] shapeC = new int[3][3][3];
  static int[][][] shapeL = {
    { { 1 }, { 0 } },
    { { 1 }, { 0 } },
    { { 1 }, { 0 } },
    { { 1 }, { 1 } },
  };
  static int[][][] shapeP = {
    { { 2 }, { 2 } },
    { { 2 }, { 2 } },
    { { 2 }, { 0 } },
  };
  static int[][][] shapeT = {
    { { 3 }, { 3 }, { 3 } },
    { { 0 }, { 3 }, { 0 } },
    { { 0 }, { 3 }, { 0 } },
  };

  public static ArrayList<int[][][]> generateRotations(int[][][] shape) {
    ArrayList<int[][][]> list = new ArrayList<>();
    int depth = shape[0][0][0]; // intialize depth. Two for loops to calculate depth of shape
    // set the dimensions of the new rotations: (i.e. every combination of length,
    // width and height.)
    int[][][] rotateshape = new int[shape[0].length][shape.length][depth];
    int[][][] rotateshape2 = new int[shape[0].length][depth][shape.length];
    int[][][] rotateshape3 = new int[depth][shape.length][shape[0].length];
    int[][][] rotateshape4 = new int[depth][shape[0].length][shape.length];
    int[][][] rotateshape5 = new int[shape.length][depth][shape[0].length];

    for (int i = 0; i < shape.length; i++) { // looping through rows (height)
      for (int j = 0; j < shape[i].length; j++) { // looping through columns (length)
        for (int k = 0; k < shape[i][j].length; k++) { // looping through depth (width)
          rotateshape[j][i][k] = shape[i][j][k];
          rotateshape2[j][k][i] = shape[i][j][k];
          rotateshape3[k][i][j] = shape[i][j][k];
          rotateshape4[k][j][i] = shape[i][j][k];
          rotateshape5[i][k][j] = shape[i][j][k];
        }
      }
    } // add all rotations to the list
    list.add(shape); // original shape
    list.add(rotateshape);
    list.add(rotateshape2);
    list.add(rotateshape3);
    list.add(rotateshape4);
    list.add(rotateshape5);

    System.out.println(Arrays.deepToString(list.toArray()));

    System.out.println();
    System.out.println();

    deleteRepeatedRotations(list);

    return list;
  }

  public static ArrayList<int[][][]> deleteRepeatedRotations(
    ArrayList<int[][][]> shapeList
  ) {
    for (int i = 0; i < shapeList.size(); i++) {
      rotations = shapeList;
      for (int j = 0; j < shapeList.size(); j++) {
        if (i != j && rotations.get(i) == shapeList.get(j)) {
          shapeList.remove(j);
          deleteRepeatedRotations(shapeList);
        }
      }
    }
    System.out.println(Arrays.deepToString(rotations.toArray()));

    return shapeList;
  }

  // this is to test it. Delete it whenever
  public static void main(String[] args) {
    generateRotations(shapeC);
  }
}
