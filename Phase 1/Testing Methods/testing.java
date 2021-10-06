package project;
import java.io.*;
import java.util.Scanner;

public class testing {
    
    static Pentago pentago = new Pentago();
    private static void StartProgramT(){
        System.out.println("x");
         int x= scanner.nextInt();
        System.out.println("y");
         int y= scanner.nextInt();
            scanner.nextLine();
         String shapes =shapeInput();
         System.out.println(shapes);
       
         System.out.println("repetitions");

        int repetitions=scanner.nextInt();
        AskForMapSizeT(x, y);
        SaveInputToBlocksArrayT(shapes);
        FillshapesToFitArrayList();
        scanner.close();
        int[] SaveResults= new int[repetitions];
        for(int i=0;i<repetitions;i++){
        int[][] solution = RecursiveSolution(currentMapArray, shapesToFit, 0);
        //PrintMatrixContentsInChatUsingLetters(solution);
        System.out.println("Solution trials: " + choices);
        SaveResults[i]=choices;
        choices=0;
        }
        SaveResults(SaveResults);
    }
    private static void AskForMapSizeT(int x, int y) {
        int xSize= x;
        int ySize=y;

        

        currentMapArray = ReturnEmptyMap(xSize, ySize);

    }
    private static void SaveInputToBlocksArrayT(String shapes) {
        String answer = shapes;
        int answerLength = answer.length();

        inputBlocksArray = new String[answerLength];

        for (int i = 0; i < answerLength; i++) {
            inputBlocksArray[i] = answer.substring(i, i + 1);
        }
   
 }

 private static String RandomInputs(int amount){
    String shapes="";
    String test="";
    Random rand = new Random();
    
    while(shapes.length()<amount){
        int x= rand.nextInt(12);
        
            if(x==0){
                test= test+"P";
            }
            if(x==1){
                test= test+"X";
            }
            if(x==2){
                test= test+"F";
            }
            if(x==3){
                test= test+"V";
            }
            if(x==4){
                test= test+"W";
            }
            if(x==5){
                test= test+"Y";
            }
            if(x==6){
                test= test+"I";
            }
            if(x==7){
                test= test+"T";
            }
            if(x==8){
                test= test+"Z";
            }
            if(x==9){
                test= test+"U";
            }
            if(x==10){
                test= test+"N";
            }
            if(x==11){
                test= test+"L";
            }
            if(shapes.contains(test)){
                shapes=shapes.replace(test, "");
            }
            else{
                shapes= shapes+test;
                
            }
            test="";
        
        }
       
        return shapes;  
 }

private static String shapesT(){
    scanner.nextLine();
        System.out.println("shapes");
       String shapes= scanner.nextLine();
       return shapes;
}

private static String shapeInput(){
    String shapes="";
    System.out.println("Do you want random inputs? 1=yes 2=no");
    
    int answer=0;
    
         answer=scanner.nextInt();
    if(answer ==1){
        System.out.println("how many shapes?");
        int amount=scanner.nextInt();
         shapes=RandomInputs(amount);
    }
    if(answer==2){
          shapes=shapesT();
        }
        
    
        return shapes;
    }
    private static void SaveResults(int[] result){
        try{
            PrintStream output = new PrintStream(new File("C:\\Users\\UserX\\Desktop\\results\\output.txt"));
            System.setOut(output);
            for (int i = 0; i < result.length; i++) {
                output.print(result[i] + " ");
                
            }
        }
        catch(FileNotFoundException fx){
    
        }
    }
}
