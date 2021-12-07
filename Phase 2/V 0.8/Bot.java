<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Bot {
    // Object variables
    static File fileWeights = new File(
            "C:\\Users\\staso\\OneDrive\\Pulpit\\Nauka\\Programowanie\\ComputerScience 1\\Project\\Phase 2\\Game\\Weights.txt");
    static Scanner weightsFileScanner;
    static Random random = new Random();

    // Numerical variables
    static double[] weights = new double[5];
    static boolean doDebugMessages = false;
    private static boolean useFile = false;

    public static void main(String[] args) {
        if (useFile) {
            updateWeights();
        }
        int[][] shape = { { 1, 1 }, { 1, 1 } };
        int[][] mapArray = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 2, 0 }, { 0, 2, 2, 0 } };

        System.out.println("Movement directions: " + getMovementDirections(shape, mapArray).toString());
    }

    // region Startup Methods
    private static void updateWeights() {
        try {
            weightsFileScanner = new Scanner(fileWeights);
        } catch (Exception e) {
        }
        weights = readWeights();
        if (doDebugMessages) {
        }
    }

    private static double[] readWeights() {
        String input = weightsFileScanner.nextLine();

        if (doDebugMessages) {
            System.out.println("Line: " + input);
        }
        double[] parsedInput = parseInput(input);

        if (isInputCorrect(parsedInput)) {
            return parsedInput;
        } else {
            System.err.println("Input incorrect");
            return new double[1];
        }
    }

    // endregion

    public static void setWeights(double[] newWeights) {
        weights = newWeights;
    }

    // region Get Directions
    public static char[] getMovementDirections(int[][] shape, int[][] mapArray) {
        FutureMapState bestMove = getBestMove(shape, mapArray);
        char[] returnSequence = addCharsToSequence(bestMove);
        return returnSequence;
    }

    private static FutureMapState getBestMove(int[][] shape, int[][] mapArray) {
        ArrayList<FutureMapState> possibleMoves = HelperMethods.generatePossibleMovesList(shape, mapArray, weights);

        int bestMoveIndex = getBestMoveIndex(possibleMoves);
        return possibleMoves.get(bestMoveIndex);
    }

    private static char[] addCharsToSequence(FutureMapState move) {
        int rotations = move.rotateTimes;
        char[] returnArray = new char[move.deltaXPosition + rotations + 1];

        for (int i = 0; i < rotations; i++) {
            returnArray[i] = 'e';
        }
        for (int i = rotations; i < move.deltaXPosition + rotations; i++) {
            returnArray[i] = 'd';
        }
        returnArray[returnArray.length - 1] = ' ';
        return returnArray;
    }

    /**
     * The lower, the better
     * 
     * @param possibleMoves
     * @return
     */
    private static int getBestMoveIndex(ArrayList<FutureMapState> possibleMoves) {
        int bestMoveIndex = 0;
        double bestScore = Double.MAX_VALUE;
        for (int i = 0; i < possibleMoves.size(); i++) {
            double currentScore = possibleMoves.get(i).getMoveError();
            if (doDebugMessages) {
                System.out.println("Score: " + currentScore);
            }
            if (currentScore < bestScore) {
                bestScore = currentScore;
                bestMoveIndex = i;
            }
        }
        if (doDebugMessages) {
            System.out.println("Best score (the lowest): " + bestScore + " index: " + bestMoveIndex);
        }
        return bestMoveIndex;
    }
    // endregion

    // region Check Input Correctness
    private static boolean isInputCorrect(double[] inputsArray) {
        boolean isInputIncorrect = inputsArray == null || (inputsArray.length != weights.length);
        if (isInputIncorrect) {
            System.out.println("Input's length was wrong");
        }
        return !isInputIncorrect;
    }

    private static double[] parseInput(String input) {
        if (input != null) {
            String[] splitSequence = input.split(",");
            double[] testSequence = new double[splitSequence.length];

            for (int i = 0; i < splitSequence.length; i++) {
                try {
                    testSequence[i] = Float.parseFloat(splitSequence[i]);

                } catch (Exception e) {

                }
            }
            return testSequence;

        } else {
            System.out.println("String was null");
            double[] e = new double[0];
            return e;
        }
    }
    // endregion
}
=======
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Bot {
    // Object variables
    static File fileWeights = new File(
            "C:\\Users\\staso\\OneDrive\\Pulpit\\Nauka\\Programowanie\\ComputerScience 1\\Project\\Phase 2\\Game\\Weights.txt");
    static Scanner weightsFileScanner;
    static Random random = new Random();

    // Numerical variables
    static double[] weights = new double[8];
    static boolean doDebugMessages = false;
    private static boolean useFile = false;

    public static void main(String[] args) {
        if (useFile) {
            updateWeights();
        }
        int[][] shape = { { 1, 1 }, { 1, 1 } };
        int[][] mapArray = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 2, 0 }, { 0, 2, 2, 0 } };

        System.out.println("Movement directions: " + getMovementDirections(shape, mapArray).toString());
    }

    // region Startup Methods
    private static void updateWeights() {
        try {
            weightsFileScanner = new Scanner(fileWeights);
        } catch (Exception e) {
        }
        weights = readWeights();
        if (doDebugMessages) {
        }
    }

    private static double[] readWeights() {
        String input = weightsFileScanner.nextLine();

        if (doDebugMessages) {
            System.out.println("Line: " + input);
        }
        double[] parsedInput = parseInput(input);

        if (isInputCorrect(parsedInput)) {
            return parsedInput;
        } else {
            System.err.println("Input incorrect");
            return new double[1];
        }
    }

    // endregion

    public static void setWeights(double[] newWeights) {
        weights = newWeights;
    }

    // region Get Directions
    public static char[] getMovementDirections(int[][] shape, int[][] mapArray) {
        FutureMapState bestMove = getBestMove(shape, mapArray);
        char[] returnSequence = addCharsToSequence(bestMove);
        return returnSequence;
    }

    private static FutureMapState getBestMove(int[][] shape, int[][] mapArray) {
        ArrayList<FutureMapState> possibleMoves = HelperMethods.generatePossibleMovesList(shape, mapArray, weights);

        int bestMoveIndex = getBestMoveIndex(possibleMoves);
        return possibleMoves.get(bestMoveIndex);
    }

    private static char[] addCharsToSequence(FutureMapState move) {
        int rotations = move.rotateTimes;
        char[] returnArray = new char[move.deltaXPosition + rotations + 1];

        for (int i = 0; i < rotations; i++) {
            returnArray[i] = 'e';
        }
        for (int i = rotations; i < move.deltaXPosition + rotations; i++) {
            returnArray[i] = 'd';
        }
        returnArray[returnArray.length - 1] = ' ';
        return returnArray;
    }

    /**
     * The lower, the better
     * 
     * @param possibleMoves
     * @return
     */
    private static int getBestMoveIndex(ArrayList<FutureMapState> possibleMoves) {
        int bestMoveIndex = 0;
        double bestScore = Double.MAX_VALUE;
        for (int i = 0; i < possibleMoves.size(); i++) {
            double currentScore = possibleMoves.get(i).getMoveError();
            if (doDebugMessages) {
                System.out.println("Score: " + currentScore);
            }
            if (currentScore < bestScore) {
                bestScore = currentScore;
                bestMoveIndex = i;
            }
        }
        if (doDebugMessages) {
            System.out.println("Best score (the lowest): " + bestScore + " index: " + bestMoveIndex);
        }
        return bestMoveIndex;
    }
    // endregion

    // region Check Input Correctness
    private static boolean isInputCorrect(double[] inputsArray) {
        boolean isInputIncorrect = inputsArray == null || (inputsArray.length != weights.length);
        if (isInputIncorrect) {
            System.out.println("Input's length was wrong");
        }
        return !isInputIncorrect;
    }

    private static double[] parseInput(String input) {
        if (input != null) {
            String[] splitSequence = input.split(",");
            double[] testSequence = new double[splitSequence.length];

            for (int i = 0; i < splitSequence.length; i++) {
                try {
                    testSequence[i] = Float.parseFloat(splitSequence[i]);

                } catch (Exception e) {

                }
            }
            return testSequence;

        } else {
            System.out.println("String was null");
            double[] e = new double[0];
            return e;
        }
    }
    // endregion
}
>>>>>>> 8801ab492abbdd3ad941c90274520c131974e560
