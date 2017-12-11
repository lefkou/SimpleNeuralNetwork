package QuestionD;


public class Main {
    public static void main(String[] args) {
        // C. Write a new version of your implementation extending the original ”pencil and
        // paper” strategy for matching three inputs simultaneously.
        System.out.print(ANSI_GREEN + "\n\nD) " +
                "Write a new version of your implementation extending the original \n" +
                "”pencil and paper” strategy for matching three inputs simultaneously.\n" + ANSI_RESET);
        Network nnet1 = new Network(4,4);
        int[] inputPattern  = {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1};
        int[] outputPattern = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1};
        int[] testPattern1 = {1, 1, 0, 1};
        nnet1.train(inputPattern, outputPattern);
        nnet1.predict(testPattern1);
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
