import java.util.Arrays;

public class OpenRoofDetector {

    public static void main(String[] args) {

        int [][] testMatrix = {{0,0,0,0,0},
                                {0,1,1,1,1},
                                {0,0,0,0,1},
                                {1,1,1,0,1},
                                {1,0,0,0,1},
                                {1,0,1,1,1},
                                {1,0,0,1,0},
                                {1,0,0,1,0}
        };

        System.out.println("Final score: " + roofMethod(testMatrix));
        
    }

    public static int roofMethod(int[][] currentMapMatrix) {

        int underRoofScore = 0;
        boolean noWayUp = false;
        boolean noWayLeft = false;
        boolean noWayRight = false;
        boolean stillSearching = true;

        // create new matrix for score values
        int[][] valuesMatrix = new int[currentMapMatrix.length][currentMapMatrix[0].length];

        // to see the state of the game in the terminal
        // for(int[] i: currentMapMatrix){ 
        //     System.out.println(Arrays.toString(i));
        // }
        // System.out.println("\n");

        // check currentMapMatrix from bottom left corner for empty spots, row by row, up
        for (int i = currentMapMatrix.length-1 ; i >-1 ; i-- ) {
            for (int j = 0 ; j < currentMapMatrix[0].length ; j++) {

                // if there is an empty spot (i.e. 0), then we start check that column, from that spot up
                // if we get to a non-zero spot, then we abort
                if (currentMapMatrix[i][j] == 0) {

                    int x = i;
                    int y = j;
                    int valueScore = 1;

                    // checkes wether a 0 is in the game matrix. 
                    // if the valuesMatrix is also 0 at the same spot, then we check
                    while (currentMapMatrix[x][y] == 0 && valuesMatrix[x][y] == 0 ) {

                        if (currentMapMatrix[x][y] == 0) {
                            
                            // if there is an empty spot, and the above conditions are met, the valueMatrix is filled in
                            valuesMatrix[x][y] = valueScore;
                            valueScore++;
                            x--;
                        }
                        
                        // if we reach to the top of the matrix, break while loop
                        if (x<0) {
                            break;
                        }
                    }
                }
            }
        }

        //print valuesMatrix
        for(int[] i: valuesMatrix){ 
            System.out.println(Arrays.toString(i));
            
        }

        // now check for roof
        // checking the valuesMatrix, if there is a 1, then go up
        // if there is a 0, then that location is a roof
        for (int i = valuesMatrix.length-1 ; i >-1 ; i-- ) {

            for (int j = 0 ; j < valuesMatrix[0].length ; j++) {

                if (valuesMatrix[i][j] == 1) {
                    
                    // variables
                    int x = i;
                    int y = j;

                    while (valuesMatrix[i][j] == 1) {

                        // roof detector
                        if (valuesMatrix[x][y] == 0) {
                            System.out.println("Roof at: " + x + " " + y);

                            int row = x+1;
                            int col = y;

                            // check wether the roof opens up to top
                            while (stillSearching) {

                                // try moving up
                                if (row > 0 && noWayUp == false) { 
                                    if(currentMapMatrix[row-1][col] == 0) { // if above is open, move up
                                        noWayUp = false;
                                        noWayLeft = false;
                                        noWayRight = false;
                                        row--;
                                        
                                    } 
                                    else {
                                        noWayUp = true;
                                       
                                    }
                                } else if (row == 0) {
                                    noWayUp = true;
                                    System.out.println("This roof is open to the top");
                                    stillSearching = false;

                                    // add up score
                                    underRoofScore = scoreAdder(underRoofScore, valuesMatrix, x, y);
                                    //System.out.println("Current score: " + Pentis.underRoofScore);
                                }

                                // try moving left
                                if (col > 0 &&  noWayUp && !noWayLeft) { 
                                    if (currentMapMatrix[row][col-1] == 0) {
                                        noWayUp = false;
                                        col--;
                                    } else if (col == 0 || currentMapMatrix[row][col-1] != 0) {
                                    noWayLeft = true;
                                
                                    }
                                    
                                }
                                
                                // try moving right
                                if (col < currentMapMatrix[0].length-1 && currentMapMatrix[row][col+1] == 0 && noWayUp && noWayLeft) {
                                    noWayUp = false;
                                    col++;
                                } else if (col == currentMapMatrix[0].length-1) {
                                    noWayRight = true;
                                }
                                    
                                // if above, left and right blocked, try moving down
                                if (noWayLeft && noWayUp && noWayRight) {
                                    if (row < currentMapMatrix.length-1) { 
                                        if (currentMapMatrix[row+1][col] == 0) {
                                            row++;

                                        } else {
                                            stillSearching = false;
                                        }
                                    } else {
                                        stillSearching = false;
                                    }
                                }
                            }

                            noWayUp = false;
                            stillSearching = true;
                        }

                        // if we reach to a roof, break while loop
                        if (valuesMatrix[x][y] == 0) {
                            break;
                        }

                        // if roof isn't reached, decrement x value to go up the column
                        x--;

                        // if we reach to the top of the matrix, break while loop
                        if (x<0) {
                            break;
                        }
                    }
                }
            }
        }
        return underRoofScore;
    }


    private static int scoreAdder(int underRoofScore, int[][] valuesMatrix, int x, int y) {
        
        for (int row = x+1 ;  ; row++ ) {
            
                underRoofScore += valuesMatrix[row][y];
            
            if (valuesMatrix[row][y] == 1) {
                break;
            }
        }
        
        return underRoofScore;
    }
}