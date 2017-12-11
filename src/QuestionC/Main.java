package QuestionC;


public class Main {
    public static void main(String[] args) {
        // C. Write a new version of your implementation using
        // floating synaptic weight (instead of 0 or 1 as binary weights)
        System.out.print(ANSI_GREEN + "\n\nC) Write a new version of your implementation using\n" +
                "floating synaptic weight (instead of 0 or 1 as binary weights)\n" + ANSI_RESET);
        Network nnet1 = new Network(3,3);
        double[] inputPattern  = {0.9, 0.6, 0.8};
        double[] outputPattern = {0.2, 0.9, 0.5};
        double[] testPattern1 =  {0.1, 0.6, 0.8};
        nnet1.train(inputPattern, outputPattern);
        nnet1.test(testPattern1);
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
