import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class NeuralNetwork {

    static Random random = new Random();
    static float learningRate = 1;
    static float bias = 1;
    static float[] weights = new float[3];
    static ArrayList<float[]> allKnownSequences = new ArrayList<>();

    static boolean stopImmediately = false;
    static Scanner trainingFileScanner;
    static Scanner testingFileScanner;
    static Scanner chatScanner = new Scanner(System.in);
    static ArrayList<Integer> wrongIndexList = new ArrayList<>();

    static final int evolutionAmount = 1000;

    public static void main(String[] args) throws FileNotFoundException {

        setupStartingVariables();
        trainAlgorithm();

        useAlgorithm();
    }

    private static void setupStartingVariables() throws FileNotFoundException {
        //1 - 1000 evolutions
        //2 - 5000,000 evolutions
        //3 - 5000 evolutions
        File trainingFile = new File("C:\\Users\\staso\\OneDrive\\Pulpit\\Nauka\\Programowanie\\ComputerScience 1\\Project\\Phase 2\\Genetic_Algorithm\\Training_data.txt");
        File testingFile = new File("C:\\Users\\staso\\OneDrive\\Pulpit\\Nauka\\Programowanie\\ComputerScience 1\\Project\\Phase 2\\Genetic_Algorithm\\Testing_data.txt");
        trainingFileScanner = new Scanner(trainingFile);
        testingFileScanner = new Scanner(testingFile);

        fillWeights();
        System.out.println("Start");
        printWeights();

        // Supervised learning
        // Correct input and output sequences
        //
        addKnownSequences();
    }

    private static void trainAlgorithm() {
        for (int i = 0; i < evolutionAmount; i++) {
            trainSequence();
        }

        System.out.println("After training");
        printWeights();
    }

    private static void useAlgorithm() {
        printInputMessages();

        //runManualTests();
        runAutomaticTests();
        trainingFileScanner.close();
        chatScanner.close();
    }

    private static void fillWeights() {

        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextFloat();
        }
    }

    private static void addKnownSequences() {
        while (trainingFileScanner.hasNextLine()){

            String inputLine = trainingFileScanner.nextLine();
            System.out.println("Line: "+ inputLine);
            float[] inputs = parseInput(inputLine);
            addKnownSequence(inputs);
        }
    }

    private static void addKnownSequence(float[] sequence) {
        allKnownSequences.add(sequence);
    }

    private static void trainSequence() {
        for (int i = 0; i < allKnownSequences.size(); i++) {

            float[] trainingSequence = allKnownSequences.get(i);
            if (!isSequenceCorrect(trainingSequence)) {
                allKnownSequences.remove(i);
                i--;
            }else{
                trainAgent(trainingSequence);
            }
        }
    }

    private static void trainAgent(float[] inputs) {
        // Output in range <0,1>
        float output = countOutput(inputs);

        // Change output to 0 or 1
        if (output > 0) {
            output = 1;
        } else {
            output = 0;
        }

        float error = inputs[inputs.length - 1] - output;
        adjustWeights(inputs, error);
    }

    private static void adjustWeights(float[] inputs, float error) {
        for (int i = 0; i < weights.length - 1; i++) {
            weights[i] += error * learningRate * inputs[i];
        }
        weights[weights.length - 1] += error * learningRate * bias;
    }

    private static float countOutput(float[] inputsArray) {

        float output = 0;
        for (int i = 0; i < weights.length - 1; i++) {
            output += weights[i] * inputsArray[i];
        }

        // Last element is the bias
        output += weights[weights.length - 1] * bias;
        return output;
    }

    private static boolean isSequenceCorrect(float[] sequence) {
        boolean inputCorrect = !(sequence == null || (sequence.length != weights.length));
        if (!inputCorrect) {
            System.out.println("Input's length was wrong");
        }
        return inputCorrect;
    }
    
    private static boolean isInputCorrect(float[] inputsArray) {
        boolean inputCorrect = !(inputsArray == null || (inputsArray.length != weights.length - 1));
        if (!inputCorrect) {
            System.out.println("Input's length was wrong");
        }
        return inputCorrect;
    }

    private static void printWeights() {
        String message = "";
        for (int i = 0; i < weights.length; i++) {
            message += weights[i] + ", ";
        }
        System.out.println("Weights: " + message);
    }

    private static void runManualTests() {
        while (true) {
            String input = getInput();
            float[] parsedInput = parseInput(input);
            if (checkQuitProgram(input)) {
                return;
            }
            if (isInputCorrect(parsedInput)) {
                float output = getSolution(parsedInput);
                System.out.println("Solution: " + output);
            }
        }
    }

    private static void runAutomaticTests(){
        while (testingFileScanner.hasNextLine()){

            String input = testingFileScanner.nextLine();
            System.out.println("Line: "+ input);
            float[] parsedInput = parseInput(input);

            if (checkQuitProgram(input)) {
                return;
            }
            if (isInputCorrect(parsedInput)) {
                float output = getSolution(parsedInput);
                System.out.println("Solution: " + output);
            }
        }
    }

    private static String getInput() {
        String input = chatScanner.nextLine();
        return input;
    }

    private static boolean checkQuitProgram(String input) {
        if (input != null) {
            if (input.compareTo("e") == 0) {
                System.out.println("Leaving program...");
                return true;
            }
        } else {
            System.out.println("String was null");
        }
        return false;
    }

    private static float[] parseInput(String input) {
        if (input != null) {
            String[] splitSequence = input.split(",");
            float[] testSequence = new float[splitSequence.length];

            for (int i = 0; i < splitSequence.length; i++) {
                try {
                    testSequence[i] = Float.parseFloat(splitSequence[i]);

                } catch (Exception e) {
                    if (input.compareTo("e") != 0) {
                        System.out.println("the string does not contain a parsable float");
                    }
                }
            }
            return testSequence;

        } else {
            System.out.println("String was null");
            float[] e = new float[0];
            return e;
        }
    }

    private static float getSolution(float[] testSequence) {
        // Output in range <0,1>
        float output = countOutput(testSequence);

        // Return 0 or 1
        if (output > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    private static void printInputMessages() {
        System.out.println("Input some sequence:");
        String example = "";

        for (int i = 0; i < weights.length - 2; i++) {
            example += "<input>,";
        }
        example += "<input>";
        System.out.println("Example input: " + example);
    }
}
