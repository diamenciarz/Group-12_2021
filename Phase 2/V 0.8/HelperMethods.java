import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

public class HelperMethods {

    //public static boolean isRFull;

    public static int[][] getACopyOfThisMatrix(int[][] arrayToCopy) {
        int[][] returnArray = new int[arrayToCopy.length][arrayToCopy[0].length];
        for (int i = 0; i < returnArray.length; i++) {
            for (int j = 0; j < returnArray[0].length; j++) {
                returnArray[i][j] = arrayToCopy[i][j];
            }
        }
        return returnArray;
    }
    public static int[][] getEmptyMatrix(int xMapSize, int yMapSize) {
        return new int[yMapSize][xMapSize];
    }
    public static int[][] rotateShapeRight(int shape[][], int times) {

        int[][] rotatedShape = getACopyOfThisMatrix(shape);
        if (times > 0) {
            int originalShapeHeight = shape.length;
            int originalShapeLength = shape[0].length;

            rotatedShape = new int[originalShapeLength][originalShapeHeight];

            int rotatedShapeLength = rotatedShape[0].length;

            for (int i = 0; i < originalShapeHeight; i++) {
                for (int j = 0; j < originalShapeLength; j++) {
                    rotatedShape[j][rotatedShapeLength - (i + 1)] = shape[i][j];
                }
            }
            rotatedShape = rotateShapeRight(rotatedShape, times - 1);
            return rotatedShape;

        } else {
            return rotatedShape;
        }
    }
    public static boolean willThisShapeFitOnMap(int mapArray[][], int shape[][], int xPosition, int yPosition) {
        boolean willFit = true;

        int maxYPosition = shape.length + yPosition;
        boolean isYInBounds = (maxYPosition > mapArray.length) || yPosition < 0;
        if (isYInBounds) {
            willFit = false;
        }
        int maxXPosition = shape[0].length + xPosition;
        boolean isXInBounds = (maxXPosition > mapArray[0].length) || xPosition < 0;
        if (isXInBounds) {
            willFit = false;
        }
        return willFit;
    }
    public static void printMatrixContentsInChatUsingLetters(int[][] matrixToPrint) {
        System.out.println("Printing new Matrix");
        String[][] stringArray = getLettersForMatrix(matrixToPrint);

        for (int i = 0; i < stringArray.length; i++) {
            for (int j = 0; j < stringArray[i].length; j++) {
                System.out.print(stringArray[i][j] + " ");
                ;
            }
            System.out.println("");
        }
    }

    private static String[][] getLettersForMatrix(int[][] matrixToPrint) {

        String[][] letterMatrix;
        if (isThisMatrixRectangular(matrixToPrint)) {

            letterMatrix = new String[matrixToPrint.length][matrixToPrint[0].length];

            for (int i = 0; i < matrixToPrint.length; i++) {
                for (int j = 0; j < matrixToPrint[0].length; j++) {
                    letterMatrix[i][j] = turnNumberToLetter(matrixToPrint[i][j]);
                }
            }

        } else {
            System.out.println("Output matrix is not rectangular - this should not be the case");
            return null;
        }
        return letterMatrix;
    }

    private static String turnNumberToLetter(int number) {
        if (number == 1) {
            return "P";
        }
        if (number == 2) {
            return "X";
        }
        if (number == 3) {
            return "F";
        }
        if (number == 4) {
            return "V";
        }
        if (number == 5) {
            return "W";
        }
        if (number == 6) {
            return "Y";
        }
        if (number == 7) {
            return "I";
        }
        if (number == 8) {
            return "T";
        }
        if (number == 9) {
            return "Z";
        }
        if (number == 10) {
            return "U";
        }
        if (number == 11) {
            return "N";
        }
        if (number == 12) {
            return "L";
        }
        // System.out.println("This letter output should not be null");
        return "0";
    }
    public static boolean isThisMatrixRectangular(int[][] inputMatrix) {
        int horizontalMatrixSize = inputMatrix[0].length;
        // Let's give this matrix some benefit of a doubt
        boolean isMatrixRectangular = true;

        for (int i = 0; i < inputMatrix.length; i++) {
            if (inputMatrix[i].length != horizontalMatrixSize) {
                isMatrixRectangular = false;
            }

        }

        return isMatrixRectangular;
    }
    public static boolean areTwoFiguresOverlapping(int[][] firstArray, int[][] secondArray) {
        // Limit search to not go out of the smaller array
        int ySize = firstArray.length;
        if (secondArray.length < ySize) {
            ySize = secondArray.length;
        }

        int xSize = firstArray[0].length;
        if (secondArray[0].length < xSize) {
            xSize = secondArray[0].length;
        }

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {

                boolean areOverlapping = (firstArray[i][j] != 0 && secondArray[i][j] != 0);
                if (areOverlapping) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int[][] placeShapeOnMatrix(int mapArray[][], int shape[][], int xPosition, int yPosition) {
        int[][] mapStateArray = HelperMethods.getACopyOfThisMatrix(mapArray);

        int shapeHeight = shape.length;
        int shapeLength = shape[0].length;

        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeLength; j++) {
                if (shape[i][j] != 0) {
                    mapStateArray[i + yPosition][j + xPosition] = shape[i][j];
                }
            }
        }

        


        
        return mapStateArray;
    }

    public static int[][] deleteRows(int[][] input) {
        ArrayList<Integer> saveRowIndexes = new ArrayList<Integer>();

        for (int index = 0; index < input.length; index++) {
            if(isRowFull(input, index) == false){
                saveRowIndexes.add(index);
                

           } else {
               Pentis.score++;
           }
        }
        int counter = 0;
        int[][] returnMatrix = new int[input.length][input[0].length];
        for (int index = saveRowIndexes.size()-1; index >= 0; index--) {
            

            pasteRow(returnMatrix.length - counter - 1, returnMatrix, input, saveRowIndexes.get(index));
            counter++;

        }
        return returnMatrix;
        
    }

    public static void pasteRow(int returnIndex, int[][] returnMatrix, int[][] inputMatrix, int inputIndex) {
        for (int i = 0; i < inputMatrix[0].length; i++) {
            

            returnMatrix[returnIndex][i] = inputMatrix[inputIndex][i];
        }
    }

    public static boolean isRowFull(int[][] input, int index) {
        for (int i = 0; i < input[0].length; i++) {
            if(input[index][i] == 0){
                return false;
            } 
            
        } 
        return true;
    }

    public static void printMatrix(int[][] inputMatrix){
        for (int i = 0; i < inputMatrix.length; i++) {
            for (int j = 0; j < inputMatrix[0].length; j++) {
                System.out.print(inputMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // takes the highscores arraylist and orders it and saves it to a 5 length array to be displayed by UI
    public static int[] highScoreOrdered(ArrayList<Integer> list) {
        int[] sortedArray = new int[list.size()];

        for (int i = 0; i < list.size() ; i++) {
            sortedArray[i] = (int) list.get(i);
        }

        Arrays.sort(sortedArray);
        System.out.println(Arrays.toString(sortedArray));

        int[] returnArray = {0,0,0,0,0};

        for (int i = 0 ; i < 5 ; i++) {
            if (i < sortedArray.length) {
                returnArray[i] = sortedArray[sortedArray.length-i-1];
            }            
        }

        System.out.println(Arrays.toString(returnArray));
            
        return returnArray;
    }

    // public static String returnSortedHighscores(int[] highscores) {

    //     String str = "";
    //     for (int i = 0 ; i < 5 ; i++) {
    //         int score = highscores[highscores.length-i];
    //         str += "Rank 1: " + score + "\n";
            
    //     }
    //     return str;
    // }
}
