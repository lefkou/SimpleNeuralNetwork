import java.util.ArrayList;
import java.util.List;

public class QuestionD {
    public static void run() {
        /* Question D */
        // Write a new version of your implementation extending the
        // original ”pencil and paper” strategy for matching three inputs
        // simultaneously.
        Network netThreeInputs = new Network(6,6);
        // train pair 1
        int[] inputPattern1 = {1, 0, 0, 0, 1, 0};
        int[] outputPattern1 = {0, 1, 0, 1, 0, 1};
        // train pair 2
        int[] inputPattern2  = {1, 1, 0, 1, 0, 1};
        int[] outputPattern2 = {0, 0, 1, 1, 0, 0};
        // train pair 3
        int[] inputPattern3 = {1, 0, 0, 1, 1, 1};
        int[] outputPattern3 = {1, 0, 1, 1, 0, 1};
        // add to lists
        List<int[]> inputPatterns = new ArrayList<>();
        List<int[]> outputPatterns = new ArrayList<>();
        inputPatterns.add(inputPattern1);
        inputPatterns.add(inputPattern2);
        inputPatterns.add(inputPattern3);
        outputPatterns.add(outputPattern1);
        outputPatterns.add(outputPattern2);
        outputPatterns.add(outputPattern3);
        // training
        netThreeInputs.train(inputPatterns, outputPatterns);
        // test
        int[] test = {1, 0, 0, 0, 1, 0};
        netThreeInputs.trainReport();
        netThreeInputs.test(test);
    }
}
