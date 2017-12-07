public class QuestionC {
    public static void run() {

    /* Question C */
    // Write a new version of your implementation using ﬂoating synaptic
    // weight (instead of 0 or 1 as binary weights)
    System.out.printf("\n\nNetwork with floating Synaptic Weights");
    System.out.printf("\nTrain - 36 Synapses\n");
    Network floatNet = new Network(6,6);
    // Training 1
    int[] inputPattern1  = {1, 0, 0, 0, 1, 0};
    int[] outputPattern1 = {0, 1, 0, 1, 0, 1};
    floatNet.Train(inputPattern1, outputPattern1);
    // Training 2
    int[] inputPattern2  = {1, 1, 0, 1, 0, 1};
    int[] outputPattern2 = {0, 0, 1, 1, 0, 0};
    floatNet.Train(inputPattern2, outputPattern2);
    // Test
    int[] test = {1, 0, 0, 0, 1, 0};
    floatNet.TrainReport();
    floatNet.Test(test);
    }
}
