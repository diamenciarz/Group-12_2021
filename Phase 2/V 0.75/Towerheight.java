public class Towerheight {
    public static void main(String[] args){
        
        int [][] amatrix = {    {0,0,0,0,0},
                                {0,0,0,0,0},
                                {0,0,0,0,0},
                                {0,0,0,0,0},
                                {0,1,0,0,0},
                                {1,1,0,0,0},
                                {1,0,0,1,0},
                                {1,0,0,1,0}
        };

        System.out.println("Towerheight is " + TowerHeight(amatrix));
    }

    /**
     * 
     * @param Matrix
     * @return int of tower height
     */
    public static int TowerHeight(int [][] Matrix) {
       int max = 0;
        for(int i = 0 ; i < Matrix.length ; i++) { //for each row
            for(int j = 0; j < Matrix[0].length ; j++) {   //for each column
              
                if(Matrix[i][j] == 1) {
                    int row = i;
                    int height = Matrix.length - row;  //all rows - the rows it went through to find shape
                    if(height > max) {
                    max = height;
                    
                    }
                }
            }
        }
        return max;
    }

        
}
