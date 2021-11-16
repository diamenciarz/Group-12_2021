import java.util.ArrayList;

public class deleteRowV2 {

    public static int scoreCounter;
    public static void main(String[] args) {
        int[][] input = {{1, 1, 1 ,1}, {3, 0, 2, 2}, {3, 3, 0, 0}, {4, 4, 4, 4}};
        printMatrix(deleteRows(input));
        
    }
    
    public static int[][] deleteRows(int[][] input) {
        ArrayList<Integer> saveRowIndexes = new ArrayList<Integer>();

       for (int index = 0; index < input.length; index++) {
           if(isRowFull(input, index) == false){
                   saveRowIndexes.add(index);
            

           }
       }
       int counter = 0;
        int[][] returnMatrix = new int[input.length][input[0].length];
        for (int index = saveRowIndexes.size()-1; index >= 0; index--) {
            

            pasteRow(returnMatrix.length - counter - 1, returnMatrix, input, saveRowIndexes.get(index));
            counter++;
            scoreCounter++;
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
}
