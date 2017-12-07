public class QuestionB {
    public static void run (){
        System.out.println("Running... ");


        // 1. Teach a 16-synapse network to pair an input pattern with an output pattern.
        System.out.print("\n\nQuestion B. 1) ");
        System.out.print("Train - 16 synapses\n");
        Network nnet = new Network(4,4);
        int[] inputPattern  = {1, 0, 1, 1};
        int[] outputPattern = {0, 0, 1, 1};
        nnet.Train(inputPattern, outputPattern);
        nnet.TrainReport();

        // 2. Test the trained network with an incomplete version of the input pattern.
        System.out.print("\n\nQuestion B. 2) ");
        System.out.print("Testing with incomplete pattern:\n");
        int[] incompletePattern = {0, 0, 1, 0};
        nnet.Test(incompletePattern);

        // 3. Test the trained network with a noisy pattern.
        System.out.printf("\n\nQuestion B. 3) ");
        System.out.printf("Testing with noisy pattern:\n");
        int[] noisyPattern = {1, 1, 1, 1};
        nnet.Test(noisyPattern);

        // 4. Teach a 36-synapse network
        System.out.printf("\n\nQuestion B. 4) ");
        System.out.printf("Training with 36 synapses:\n");
        System.out.printf("\n\nTrain - 36 Synapses\n");

        Network net2 = new Network(6,6);
        // train 1
        int[] inputPattern1  = {1, 0, 0, 0, 1, 0};
        int[] outputPattern1 = {0, 1, 0, 1, 0, 1};
        net2.Train(inputPattern1, outputPattern1);
        // train 2
        int[] inputPattern2  = {1, 1, 0, 1, 0, 1};
        int[] outputPattern2 = {0, 0, 1, 1, 0, 0};
        net2.Train(inputPattern2, outputPattern2);
        // test
        int[] test = {1, 0, 0, 0, 1, 0};
        net2.TrainReport();
        net2.Test(test);

        // 5. Test the network with a distorted version of one of the two training patterns
        System.out.printf("\n\nQuestion B. 5) ");
        System.out.printf("Testing with distorted pattern:\n");
        int[] distortedPattern = {1, 0, 1, 0, 1, 1};
        net2.Test(distortedPattern);

        // 6. Fraction of the synapses and parameter.
        System.out.printf("Network Saturation Info:\n");
        // load parameter
        System.out.printf("Load Parameter (α): %2f\n", net2.GetLoadParameter());

        // fraction of synapses
        System.out.printf("Fraction of Synapses: \n");
        // 7. Calculate the maximum load parameter for the network
        System.out.printf("Max Load Parameter: \n");
        // to get the max load parameter test how many patterns a network
        // can contain before it saturates - meaning starts making mistakes
    }
}
