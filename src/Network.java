
import java.util.ArrayList;
import java.util.List;





public class Network {
    private boolean trained;
    private int synapseMatrix[][] = null;
    private int  activationVector[] = null;
    private double loadParameter;
    private int originalInputPattern[] = null;
    private List<int[]> patternsTrained;
    private List<Integer> hammingDistances;
    private int width, height;

    // constructor
    public Network(int w, int h) {
        width = w;
        height = h;
        trained = false;
        synapseMatrix = new int[height][width];
        activationVector = new int[width];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                synapseMatrix[i][j] = 0;
            }
        }
        patternsTrained = new ArrayList<>();
        hammingDistances = new ArrayList<>();
    }


    // this function calculates the sum of all the
    // inputs and the weights for each neuron
    private int[] integrator() {
        int integratorVector[] = new int[width];
        for (int i = 0; i < width; i++) {
            integratorVector[i] = 0;
            for (int j = 0; j < height; j++) {
                integratorVector[i] += synapseMatrix[j][i];
            }
        }
        return integratorVector;
    }


    // this function compares the output of the Integrator to the threshold
    public int[] activation(int[] integratorVector, int threshold) {
        int[] activationVector = new int[width];
        // loop through the integrator array and compare each to threshold value
        for (int i = 0; i < width; i++) {
            if(integratorVector[i] >= threshold)
                activationVector[i] = 1;
            else
                activationVector[i] = 0;
        }
        return activationVector;
    }


    // update the existing synaptic matrix with the condition
    // if the pre-synaptic cell and post-synaptic cell are both firing (1),
    // the synapse between them should be strengthened (0->1).
    private void updateSynapticMatrix(int[] inputPattern, int[] outputPattern) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(inputPattern[i] == 1 && outputPattern[j] == 1 && synapseMatrix[i][j] == 0)
                    synapseMatrix[i][j] = 1;
            }
        }
    }



    private int threshold(int[] inputPattern, int[] outputPattern) {
        // count no of 1s in input and output patterns
        int noOfOnesOutput = 0, noOfOnesInput = 0;
        for(int i=0; i<width; i++) {
            if(inputPattern[i] == 1)
                noOfOnesInput ++;
            if(outputPattern[i] == 1)
                noOfOnesOutput ++;
        }
        // compare them and return lesser
        if(noOfOnesInput > noOfOnesOutput){
            return  noOfOnesOutput;
        }
        return noOfOnesInput;
    }


    // report training results
    void trainReport() {
        System.out.print(Main.ANSI_YELLOW + "\nTRAINING REPORT\n" + Main.ANSI_RESET);
        // matrix
        System.out.print("Synapse Matrix:\n{\n");
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                System.out.printf(" %d", synapseMatrix[i][j]);
            }
            System.out.println();
        }
        System.out.print("}\n");
        // integrator vector
    int[] v = integrator();
        System.out.print("Integrator Vector: { ");
        for (int i = 0; i < width; ++i) {
            System.out.printf("%d ", v[i]);
        }

        System.out.print("} \n");
    }


    // train the network based on a input and output pattern
    void train(int[] input, int[] output) {
        if(!trained) {
            originalInputPattern = input;
            trained = true;
        }
        updateSynapticMatrix(input, output);
        addPatternTrained(input);
//    activationVector = Activation(Integrator(), Threshold(originalInputPattern, output));

    }


    // train with a list of patterns
    void train(List<int[]> inputPatterns, List<int[]> outputPatterns) {
        if(!trained) {
            originalInputPattern = inputPatterns.get(0);
            trained = true;
        }
        for (int i = 0; i < inputPatterns.size(); ++i) {
            updateSynapticMatrix(inputPatterns.get(i), outputPatterns.get(i));
            addPatternTrained(inputPatterns.get(i));
        }
    }


    // adds trained pattern to the list of patterns trained
    private void addPatternTrained(int[] inputPattern) {
        patternsTrained.add(inputPattern);
        hammingDistances.add(calculateHammingDistance(inputPattern));

        // re-calculate load parameter (patterns-stored / input-layer-size)
        loadParameter = (double) patternsTrained.size() / (double) height;

    }


    // calculates the hamming distance for an input against all existing
    // input training patterns existing in the network
    private int calculateHammingDistance(int[] inputPattern) {
        int distance = 0;
        for (int i = 0; i < width; ++i) {
            if(originalInputPattern[i] != inputPattern[i]) {
                distance ++;
            }
        }
        return distance;

    }


    // This function will test a new input pattern
    // against the pre existing synapse matrix
    void test(int[] input) {
        // create a sum vector that contains the sum of the
        // result of the multiplication of the input vector
        // with each column of the existing synapse matrix
        System.out.print(Main.ANSI_YELLOW + "\nTESTING REPORT\n" + Main.ANSI_RESET);
        // input pattern output
        String output = "";
        for (int i = 0; i < width; ++i) {
            output += input[i] + " ";
        }
        System.out.printf("\nInput Pattern: "+ output);
        // sum vector construction
        int[] sumVector = new int[width];
        for (int i = 0; i < width; i++) {
            int localSum = 0;
            for (int j = 0; j < height; j++) {
                localSum += input[j] * synapseMatrix[j][i];
            }
            sumVector[i] = localSum;
        }
        // find the index of the pattern with the min hamming distance
        // then use the index to find the pattern
        System.out.print("\nMin HD Pattern: { ");
        int minDistance = 0;
        int minDistanceIndex = 0;
        for (int i = 0; i < hammingDistances.size(); i++) {
            if(hammingDistances.get(i) <= minDistance) {
                minDistanceIndex = i;
            }
        }
        for (int j = 0; j < width; ++j) {
            System.out.printf("%d ", patternsTrained.get(minDistanceIndex)[j]);
        }
        System.out.print("}");
        // find threshold
        int threshold = threshold(originalInputPattern, input);
        // output for sum vector
        System.out.print("\nsumVector:      { ");
        for (int i = 0; i < width; i++) {
            System.out.printf("%d ", sumVector[i]);
        }
        System.out.print("}");
        // calculate output vector based on threshold and sumVector
        int[] outputVector = new int[width];
        for (int i = 0; i < width; i++) {
            if(sumVector[i] >= threshold) {
                outputVector[i] = 1;
            }else {
                outputVector[i] = 0;
            }
        }
        // output for final output vector
        System.out.print("\noutputVector:   { ");
        for (int i = 0; i < width; i++) {
            System.out.printf("%d ", outputVector[i]);
        }
        System.out.print("}\n");
        System.out.printf("\nThreshold: %d\n", threshold);
        System.out.print("Patterns Trained with Hamming Distances: \n");
        // patterns trained output
        for (int i = 0; i < patternsTrained.size(); ++i) {
            System.out.print("{ ");
            for (int j = 0; j < width; ++j) {
                System.out.printf("%d ", patternsTrained.get(i)[j]);
            }
            System.out.printf("} => HD: %d\n", hammingDistances.get(i));
        }
    }


    // load parameter - saturation
    double getLoadParameter () {
        return loadParameter;
    }


    // fraction of synapses that can be strengthened
    double getSynapsesFraction() {
        return 0.0;
    }
}
