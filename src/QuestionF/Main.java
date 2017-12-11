package QuestionF;

public class Main {

    public static void main(String[] args) {
        System.out.println(ANSI_GREEN + "EXERCISE F" + ANSI_RESET);

        System.out.print(ANSI_GREEN + "\n\nTesting:\n" + ANSI_RESET);
        Network nnet = new Network(6,6);
        int[] inputPattern1  = {1, 0, 1, 0, 1, 0};
        int[] outputPattern1 = {0, 1, 0, 1, 0, 1};
        int[] inputPattern2  = {1, 1, 0, 1, 0, 0};
        int[] outputPattern2 = {0, 0, 1, 1, 0, 0};
        int[] testPattern    = {1, 1, 1, 0, 1, 0};
        nnet.train(inputPattern1, outputPattern1);
//        nnet.train(inputPattern2, outputPattern2);
        nnet.printSynapticMatrix();
        nnet.predict(testPattern);


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
