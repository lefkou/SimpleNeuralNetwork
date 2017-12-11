package QuestionE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Layer {

    public int[] getOutputVector() {
        return outputVector;
    }


    public int width;
    private int height;
    private int[] synapticMatrix;
    private int[] sumVector;
    private int[] outputVector;
    public float maxLoadParameter;


    // constructor
    public Layer(int w, int h) {
        this.width = w;
        this.height = h;
        this.synapticMatrix = new int[w*h];
        this.sumVector = new int[w];
        this.outputVector = new int[w];
    }


    // update the matrix with the created from a training pair
    void updateSynapticMatrix(int[] inputPattern, int[] outputPattern) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(inputPattern[i] == 1 &&  outputPattern[j] == 1 && this.synapticMatrix[i*width+j] == 0) {
                    this.synapticMatrix[i*width+j] = 1;
                }
            }
        }
    }

    // training
    void train(int[] inputPattern, int[] outputPattern) {
        updateSynapticMatrix(inputPattern, outputPattern);
    }


    // triple training in one go
    void trainTriple(int[] inputPatternTriple, int[] outputPatternTriple) {

        int[] inTemp  = new int[width];
        int[] outTemp = new int[width];

        for (int j = 0; j < inputPatternTriple.length; j+=width) {

            for (int i = 0; i < width; i++) {
                inTemp[i] = inputPatternTriple[i+j];
                outTemp[i] = outputPatternTriple[i+j];
            }
            train(inTemp, outTemp);
            System.out.println("Input = " + Arrays.toString(inTemp) +
                    ", Output = " + Arrays.toString(outTemp));
        }

    }

    void createSummaryVector(int[] input) {
        for (int i = 0; i < width; i++) {
            int sum = 0;
            for (int j = 0; j < height; j++) {
                sum += input[j] * this.synapticMatrix[(j*width+i)];
            }
            sumVector[i] = sum;
        }
    }


    private int calcThreshold(int[] input) {
        // count no of 1s in input and output patterns
        int inputCount = 0, outputCount = 0;
        for(int i=0; i < width; i++) {
            if(input[i] == 1)
                inputCount ++;
            if(Network.associations.get(0).inputPattern[i] == 1)
                outputCount ++;
        }
        // compare them and return lesser
        if(inputCount <= outputCount){
            return  inputCount;
        }
        return outputCount;
    }


    private void createOutputVector(int[] input) {

        int threshold = calcThreshold(Network.associations.
                get(indexWithMinHammingDistance(input)).
                outputPattern);
        for (int i = 0; i < sumVector.length; i++) {
            if (sumVector[i] >= threshold) {
                outputVector[i] = 1;
            }
        }
    }

    // testing
    int[] predict(int[] input) {

        // create a vector with the sum of each column of the matrix * input pattern
        createSummaryVector(input);
        createOutputVector(input);
//        printAssociations();
//        printSynapticMatrix();
        System.out.print("Test Pattern: ");
        printPattern(input);
//        printSumVector();
        printOutputVector();

        return outputVector;
    }


    int indexWithMinHammingDistance(int[] input) {

        int minHd = input.length;
        int minHDIndex = 0;
        for (int i = 0; i < Network.associations.size(); i++) {
            Network.Association association = Network.associations.get(i);
            int sum = 0;
            for (int j = 0; j < input.length; j++) {
                if (input[j] != association.inputPattern[j]) {
                    sum++;
                }
            }
            if (sum < minHd) {
                minHd = sum;
                minHDIndex = i;
            }

        }

        return minHDIndex;
    }

    public float getLoadParameter() {
        return (float) Network.associations.size() / (float) width;
    }

    public float getFractionOfSynapses() {
        // number of synapses that can be strengthened
        int counter = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(this.synapticMatrix[i*width+j] != 1) {
                    counter ++;
                }
            }
        }
        return (float)(height*width) / counter;
    }


    public void saturate() {
        // train while output is correct
        boolean found = false;
        do {
            this.train(randomBinaryVector(this.width), randomBinaryVector(this.width));
            found = isPatternCorrect(this.predict(randomBinaryVector(this.width)));
            maxLoadParameter = getLoadParameter();


        } while(found);
//        System.out.println("Fraction of synapses 11: " + getFractionOfSynapses());
//        System.out.println("Max Load parameter 11: " + maxLoadParameter);
    }


    /* * * * * * * * * * * */
    /*       HELPERS       */
    /* * * * * * * * * * * */


    boolean matchPatterns(int[] patternA, int[] patternB) {
        for (int i = 0; i < patternA.length; i++) {
            if(patternA[i] != patternB[i]) return false;
        }
        return true;
    }

    boolean isPatternCorrect (int[] pattern) {
        for (Network.Association association : Network.associations) {
            if (matchPatterns(pattern, association.outputPattern)) {
                return true;
            }
        }
        return false;
    }


    int[] randomBinaryVector(int len) {
        int[] arr = new int[len];
        Random r = new Random();
        for(int i=0 ; i<arr.length ; i++){
            boolean bool = r.nextBoolean();
            arr[i] = (bool) ? 1 : 0;
        }
        return arr;
    }
    /* * * * * * * * * * * */
    /*       OUTPUTS       */
    /* * * * * * * * * * * */

    void printSynapticMatrix() {
        System.out.print("\n\nSynaptic Matrix {");
        for (int i = 0; i < width; i++) {
            System.out.println();
            for (int j = 0; j < height; j++) {
                System.out.print(" " + this.synapticMatrix[i*width+j]);
            }
        }
        System.out.println("\n}");
    }

    void printSumVector() {
        System.out.print("Sum Vector: ");
        printPattern(sumVector);
    }

    void printOutputVector() {
        System.out.print("Output Vector: ");
        printPattern(outputVector);
    }

    void printPattern(int[] pattern) {
        System.out.print("[");
        for (int i = 0; i < pattern.length; i++) {
            System.out.print(" " + pattern[i]);
        }
        System.out.println(" ]");
    }
}
