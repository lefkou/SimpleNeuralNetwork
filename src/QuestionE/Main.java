package QuestionE;

public class Main {

    public static void main(String[] args) {
        System.out.println(ANSI_GREEN + "EXERCISE E" + ANSI_RESET);


        System.out.print(ANSI_GREEN + "\n\n4) Teach a 36-synapse network:\n" + ANSI_RESET);
        Network nnet  = new Network(6,6);
        int[] inputPattern  = {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1};
        int[] outputPattern = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1};
        int[] testPattern1  = {1, 1, 0, 1};
        nnet.multiTrain(inputPattern, outputPattern);
        nnet.test(testPattern1);


//        System.out.println(Arrays.toString(randomBinaryVector(6)));
    }


    // colors for console output
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
