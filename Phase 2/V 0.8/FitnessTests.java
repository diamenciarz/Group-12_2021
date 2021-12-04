<<<<<<< HEAD
//import java.util.Arrays;

public class FitnessTests {

    public static void main(String[] args) {

        int [][] testMatrix = {{0,0,0,0,0},
                                {0,1,0,1,0},
                                {0,1,0,1,0},
                                {1,1,0,1,1},
                                {1,0,0,0,1},
                                {1,1,1,1,1},
                                {1,0,0,1,0},
                                {1,0,0,1,0}
        };

        // work mode 0: open roof score
        // work mode 1: enclosed area score
        // work mode 2: irregulatrity score

        System.out.println("Open roof score: " + roofMethod(testMatrix, 0));
        
        System.out.println("Enclosed area score: " + roofMethod(testMatrix, 1));

        System.out.println("Irregularity score: " + roofMethod(testMatrix, 2));

        // tower height
        System.out.println("Tower height: " + towerHeight(testMatrix));
        
    }

    /**
     * This method translates the gameboard to a "values matrix" where empty spaces are scored, 
     * and added up to give us some fitness information.
     * @param currentMapMatrix
     * @param workMode workMode 0: open roof score, workMode 1: enclosed area score, workMode 2: irregularity score
     * @return score
     */
    public static int roofMethod(int[][] currentMapMatrix, int workMode) {

        int underRoofScore = 0;
        int enclosedAreaScore = 0;
        int irregularityScore = 0;
        int finalScore = 0;

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

                // if there is an empty spot (i.e. 0), then we start checking that column, from that spot up
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
        // for(int[] i: valuesMatrix){ 
        //     System.out.println(Arrays.toString(i));
            
        // }

        // irregularity score
        irregularityScore = irregularity(towerHeight(valuesMatrix), valuesMatrix);

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
                            //System.out.print("Roof at: " + x + " " + y);

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
                                    //System.out.println(" This roof is open to the top");
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
                                } else if (col == currentMapMatrix[0].length-1 || currentMapMatrix[row][col+1] !=0){
                                    noWayRight = true;
                                }
                                    
                                // if above, left and right blocked, try moving down
                                if (noWayLeft && noWayUp && noWayRight) {
                                    if (row < currentMapMatrix.length-1) { 
                                        if (currentMapMatrix[row+1][col] == 0) {
                                            row++;

                                        } else {
                                            stillSearching = false;
                                            //System.out.println(" This roof is enclosed");
                                            // add up score
                                            enclosedAreaScore = scoreAdder(enclosedAreaScore, valuesMatrix, x, y);
                                            
                                            
                                        }
                                    } else {
                                        stillSearching = false;
                                        //System.out.println(" This roof is enclosed");
                                        // add up score
                                        enclosedAreaScore = scoreAdder(enclosedAreaScore, valuesMatrix, x, y);
                                    }
                                }
                            }

                            noWayUp = false;
                            noWayLeft =false;
                            noWayRight = false;
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

        // different work modes to return the score needed
        if (workMode == 0) {
            finalScore = underRoofScore;
        } 
        if (workMode == 1) {
            finalScore = enclosedAreaScore;
        } 
        if (workMode == 2) {
            finalScore = irregularityScore;
        } 

        return finalScore;
    }

    /**
     * This method adds up scores for enclosed areas or areas under an open roof.
     * @param theScore score from the values matrix is added up
     * @param valuesMatrix this matrix represents the game board in terms of scores of empty areas
     * @param x row coordinate
     * @param y column coordinate
     * @return score
     */
    private static int scoreAdder(int theScore, int[][] valuesMatrix, int x, int y) {
        
        for (int row = x+1 ;  ; row++ ) {
            
                theScore += valuesMatrix[row][y];
            
            if (valuesMatrix[row][y] == 1) {
                break;
            }
        }
        
        return theScore;
    }

    /**
     * This method returns the height of the peak.
     * @param matrix
     * @return int of tower height
     */
    public static int towerHeight(int [][] matrix) {
        int max = 0;
            for(int i = 0 ; i < matrix.length ; i++) { //for each row
                for(int j = 0; j < matrix[0].length ; j++) {   //for each column
               
                if(matrix[i][j] == 1) {
                    int row = i;
                    int height = matrix.length - row;  //all rows - the rows it went through to find shape
                    if(height > max) {
                    max = height;
                    
                    }
                }
            }
        }
        return max;
    }

    /**
     * This method adds up the score of the top surface of the game and returns that score.
     * @param towerHeight tower height from towerHeight method: the height of peak
     * @param matrix values matrix
     * @return int irregularity score
     */
    public static int irregularity(int towerHeight, int[][] matrix) {
        int score = 0;
        int towerRow = matrix.length - towerHeight+1;

        for (int col = 0 ; col < matrix[0].length ; col++) {
            for (int row = towerRow ; row < matrix.length ; row++) {
                if (matrix[row][col] == 0) {
                    break;
                } else {
                    score += matrix[row][col];
                }
            }
        }

        return score;
    }
=======
//import java.util.Arrays;

public class FitnessTests {

    public static void main(String[] args) {

        // int [][] testMatrix = {{0,0,0,0,0},
        // {0,1,0,1,0},
        // {0,1,0,1,0},
        // {1,1,0,1,1},
        // {1,0,0,0,1},
        // {1,1,1,1,1},
        // {1,0,0,1,0},
        // {1,0,0,1,0}
        // };

        // // work mode 0: open roof score
        // // work mode 1: enclosed area score
        // // work mode 2: irregulatrity score

        // System.out.println("Open roof score: " + roofMethod(testMatrix, 0));

        // System.out.println("Enclosed area score: " + roofMethod(testMatrix, 1));

        // System.out.println("Irregularity score: " + roofMethod(testMatrix, 2));

        // // tower height
        // System.out.println("Tower height: " + towerHeight(testMatrix));

    }

    /**
     * This method translates the gameboard to a "values matrix" where empty spaces
     * are scored,
     * and added up to give us some fitness information.
     * 
     * @param currentMapMatrix
     * @param workMode         workMode 0: open roof score, workMode 1: enclosed
     *                         area score, workMode 2: irregularity score
     * @return score
     */
    public static int roofMethod(int[][] currentMapMatrix, int workMode) {

        int underRoofScore = 0;
        int enclosedAreaScore = 0;
        int irregularityScore = 0;
        int finalScore = 0;

        boolean noWayUp = false;
        boolean noWayLeft = false;
        boolean noWayRight = false;
        boolean stillSearching = true;

        // create new matrix for score values
        int[][] valuesMatrix = new int[currentMapMatrix.length][currentMapMatrix[0].length];

        // to see the state of the game in the terminal
        // for(int[] i: currentMapMatrix){
        // System.out.println(Arrays.toString(i));
        // }
        // System.out.println("\n");

        // check currentMapMatrix from bottom left corner for empty spots, row by row,
        // up
        for (int i = currentMapMatrix.length - 1; i > -1; i--) {
            for (int j = 0; j < currentMapMatrix[0].length; j++) {

                // if there is an empty spot (i.e. 0), then we start checking that column, from
                // that spot up
                // if we get to a non-zero spot, then we abort
                if (currentMapMatrix[i][j] == 0) {

                    int x = i;
                    int y = j;
                    int valueScore = 1;

                    // checkes wether a 0 is in the game matrix.
                    // if the valuesMatrix is also 0 at the same spot, then we check
                    while (currentMapMatrix[x][y] == 0 && valuesMatrix[x][y] == 0) {

                        if (currentMapMatrix[x][y] == 0) {

                            // if there is an empty spot, and the above conditions are met, the valueMatrix
                            // is filled in
                            valuesMatrix[x][y] = valueScore;
                            valueScore++;
                            x--;
                        }

                        // if we reach to the top of the matrix, break while loop
                        if (x < 0) {
                            break;
                        }
                    }
                }
            }
        }

        // irregularity score
        irregularityScore = irregularity(towerHeight(valuesMatrix), valuesMatrix);

        // now check for roof
        // checking the valuesMatrix, if there is a 1, then go up
        // if there is a 0, then that location is a roof
        for (int i = valuesMatrix.length - 1; i > -1; i--) {

            for (int j = 0; j < valuesMatrix[0].length; j++) {

                if (valuesMatrix[i][j] == 1) {

                    // variables
                    int x = i;
                    int y = j;

                    while (valuesMatrix[i][j] == 1) {

                        // roof detector
                        if (valuesMatrix[x][y] == 0) {
                            // System.out.print("Roof at: " + x + " " + y);

                            int row = x + 1;
                            int col = y;

                            // check wether the roof opens up to top
                            while (stillSearching) {

                                // try moving up
                                if (row > 0 && noWayUp == false) {
                                    if (currentMapMatrix[row - 1][col] == 0) { // if above is open, move up
                                        noWayUp = false;
                                        noWayLeft = false;
                                        noWayRight = false;
                                        row--;

                                    } else {
                                        noWayUp = true;

                                    }
                                } else if (row == 0) {
                                    noWayUp = true;
                                    // System.out.println(" This roof is open to the top");
                                    stillSearching = false;

                                    // add up score
                                    underRoofScore = scoreAdder(underRoofScore, valuesMatrix, x, y);
                                    // System.out.println("Current score: " + Pentis.underRoofScore);
                                }
                                // try moving left
                                if (col > 0 && noWayUp && !noWayLeft) {
                                    if (currentMapMatrix[row][col - 1] == 0) {
                                        noWayUp = false;
                                        col--;
                                    } else if (col == 0 || currentMapMatrix[row][col - 1] != 0) {
                                        noWayLeft = true;

                                    }

                                }

                                // try moving right
                                if (col < currentMapMatrix[0].length - 1 && currentMapMatrix[row][col + 1] == 0
                                        && noWayUp && noWayLeft) {
                                    noWayUp = false;
                                    col++;
                                } else if (col == currentMapMatrix[0].length - 1
                                        || currentMapMatrix[row][col + 1] != 0) {
                                    noWayRight = true;
                                }

                                // if above, left and right blocked, try moving down
                                if (noWayLeft && noWayUp && noWayRight) {
                                    if (row < currentMapMatrix.length - 1) {
                                        if (currentMapMatrix[row + 1][col] == 0) {
                                            row++;

                                        } else {
                                            stillSearching = false;
                                            // System.out.println(" This roof is enclosed");
                                            // add up score
                                            enclosedAreaScore = scoreAdder(enclosedAreaScore, valuesMatrix, x, y);

                                        }
                                    } else {
                                        stillSearching = false;
                                        // System.out.println(" This roof is enclosed");
                                        // add up score
                                        enclosedAreaScore = scoreAdder(enclosedAreaScore, valuesMatrix, x, y);
                                    }
                                }
                            }

                            noWayUp = false;
                            noWayLeft = false;
                            noWayRight = false;
                            stillSearching = true;
                        }

                        // if we reach to a roof, break while loop
                        if (valuesMatrix[x][y] == 0) {
                            break;
                        }

                        // if roof isn't reached, decrement x value to go up the column
                        x--;

                        // if we reach to the top of the matrix, break while loop
                        if (x < 0) {
                            break;
                        }
                    }
                }
            }
        }

        // different work modes to return the score needed
        if (workMode == 0) {
            finalScore = underRoofScore;
        }
        if (workMode == 1) {
            finalScore = enclosedAreaScore;
        }
        if (workMode == 2) {
            finalScore = irregularityScore;
        }

        return finalScore;
    }

    /**
     * This method adds up scores for enclosed areas or areas under an open roof.
     * 
     * @param theScore     score from the values matrix is added up
     * @param valuesMatrix this matrix represents the game board in terms of scores
     *                     of empty areas
     * @param x            row coordinate
     * @param y            column coordinate
     * @return score
     */
    private static int scoreAdder(int theScore, int[][] valuesMatrix, int x, int y) {

        for (int row = x + 1;; row++) {

            theScore += valuesMatrix[row][y];

            if (valuesMatrix[row][y] == 1) {
                break;
            }
        }

        return theScore;
    }

    /**
     * This method returns the height of the peak.
     * 
     * @param matrix
     * @return int of tower height
     */
    public static int towerHeight(int[][] matrix) {
        for (int y = 0; y < matrix.length; y++) { // for each row
            for (int x = 0; x < matrix[0].length; x++) { // for each column

                if (matrix[y][x] != 0) {
                    return matrix.length - y; // all rows - the rows it went through to find shape
                }
            }
        }
        return 0;
    }

    /**
     * This method adds up the score of the top surface of the game and returns that
     * score.
     * 
     * @param towerHeight tower height from towerHeight method: the height of peak
     * @param matrix      values matrix
     * @return int irregularity score
     */
    private static int irregularity(int towerHeight, int[][] matrix) {
        int score = 0;
        int towerRow = matrix.length - towerHeight + 1;

        for (int col = 0; col < matrix[0].length; col++) {
            for (int row = towerRow; row < matrix.length; row++) {
                if (matrix[row][col] == 0) {
                    break;
                } else {
                    score += matrix[row][col];
                }
            }
        }

        return score;
    }
>>>>>>> d158b094497fd2b9a6a8ebd45edec8b27ff7387d
}