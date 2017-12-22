package QuestionB;

public class Main {

    public static void main(String[] args) {
        System.out.println(ANSI_GREEN + "EXERCISE B" + ANSI_RESET);

        // 1. Teach a 16-synapse network to pair an input
        // pattern with an output pattern.
        System.out.print(ANSI_GREEN + "\n\n1) Teach a 16-synapse network " +
                "to pair an input \npattern with an output pattern.\n" + ANSI_RESET);
        Network nnet1 = new Network(4,4);
        int[] inputPattern  = {1, 0, 0, 1};
        int[] outputPattern = {0, 0, 1, 1};
        int[] testPattern1 = {1, 1, 0, 1};
        nnet1.train(inputPattern, outputPattern);
        nnet1.test(testPattern1);

        // 2. Test the trained network with an incomplete
        // version of the input pattern.
        System.out.print(ANSI_GREEN + "\n\n2) Testing with incomplete pattern:\n" + ANSI_RESET);
        int[] incompletePattern = {1, 0, 0, 0};
        nnet1.test(incompletePattern);

        // 3. Test the trained network with a noisy pattern.
        System.out.print(ANSI_GREEN + "\n\n3) Testing with noisy pattern:\n" + ANSI_RESET);
        int[] noisyPattern = {1, 0, 1, 1};
        nnet1.test(noisyPattern);

        //  4. Teach a 36-synapse network
        System.out.print(ANSI_GREEN + "\n\n4) Teach a 36-synapse network:\n" + ANSI_RESET);
        Network nnet = new Network(6,6);
        int[] inputPattern1  = {1, 0, 1, 0, 1, 0};
        int[] outputPattern1 = {0, 1, 0, 1, 0, 1};
        int[] inputPattern2  = {1, 1, 0, 1, 0, 0};
        int[] outputPattern2 = {0, 0, 1, 1, 0, 0};
        int[] testPattern    = {1, 1, 1, 0, 1, 0};
        nnet.train(inputPattern1, outputPattern1);
        nnet.train(inputPattern2, outputPattern2);
        nnet.test(testPattern);
        nnet.printSynapticMatrix();

        // 5. Test the network with a distorted version of one of the
        // two training patterns
        System.out.print(ANSI_GREEN + "\n\n5) Testing with distorted pattern:\n" + ANSI_RESET);
        int[] distortedPattern = {1, 0, 1, 0, 1, 1};
        nnet.test(distortedPattern);

        // 6. Fraction of the synapses and parameter.
        System.out.println(ANSI_GREEN + "\n\n6) Fraction of the synapses and parameter:" + ANSI_RESET);
        System.out.println("Layer Saturation Info:");
        System.out.println("Load Parameter (α): " + ANSI_CYAN + nnet1.getLoadParameter() + ANSI_RESET);
        System.out.println("Fraction of Synapses: " + ANSI_CYAN + nnet1.getFractionOfSynapses() + ANSI_RESET);

        // 7. Calculate the maximum load parameter for the network
        System.out.println(ANSI_GREEN + "\n\n7) Calculate the maximum load parameter for the network" + ANSI_RESET);
//        nnet1.saturate();
//        System.out.println("Max Load Parameter (α): " + ANSI_CYAN + nnet.getLoadParameter() + ANSI_RESET);
        nnet1.saturate();
        nnet.saturate();
//        System.out.println("Max Fraction of Synapses: " + ANSI_CYAN + nnet.getFractionOfSynapses() + ANSI_RESET);

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
