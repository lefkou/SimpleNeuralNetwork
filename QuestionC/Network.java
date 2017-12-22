package QuestionC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Network {

    public class Association {

        double[] inputPattern;
        double[] outputPattern;

        Association(double[] inputPattern, double[] outputPattern) {
            this.inputPattern = inputPattern;
            this.outputPattern = outputPattern;
        }

        @Override
        public String toString() {
            return "Association { \n" +
                    " InputPattern:  " + Arrays.toString(inputPattern) +
                    ",\n OutputPattern: " + Arrays.toString(outputPattern) +
                    "\n}";
        }
    }


    private int width;
    private int height;
    private double[] synapticMatrix;
    private List<Association> associations;
    private double[] sumVector;
    private double[] outputVector;
    private double maxLoadParameter;


    // constructor
    public Network(int w, int h) {
        this.width = w;
        this.height = h;
        this.synapticMatrix = new double[w*h];
        this.associations = new ArrayList<>();
        this.sumVector = new double[w];
        this.outputVector = new double[w];
    }


    // update the matrix with the created from a training pair
    void updateSynapticMatrix(double[] inputPattern, double[] outputPattern) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.synapticMatrix[i*width+j] = inputPattern[i] * outputPattern[j] / (width+height);
            }
        }
    }



    // save pair of input and output pattern
    void addAssociation(double[] inputPattern, double[] outputPattern) {
        this.associations.add(new Association(inputPattern, outputPattern));
    }


    // show associations (pairs of input and output patterns) saved
    void printAssociations() {
        System.out.println();
        this.associations.forEach(System.out::println);
    }


    // training
    void train(double[] inputPattern, double[] outputPattern) {
        updateSynapticMatrix(inputPattern, outputPattern);
        addAssociation(inputPattern, outputPattern);
    }


    void scaleMatrix() {
        double max = 0, min = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(max < this.synapticMatrix[i*width+j]) {
                    max = this.synapticMatrix[i*width+j];
                }
                if(min > this.synapticMatrix[i*width+j]) {
                    min = this.synapticMatrix[i*width+j];
                }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.synapticMatrix[i*width+j] = (this.synapticMatrix[i*width+j] - min) / (max - min);
            }
        }

    }
    void createSummaryVector(double[] input) {
        for (int i = 0; i < width; i++) {
            double sum = 0;
            for (int j = 0; j < height; j++) {
                sum += this.synapticMatrix[(j*width+i)];
            }
            sumVector[i] = sum;
        }
    }


    private double sigmoid(double num) {
        return 1 / (1 + Math.pow(Math.E, - num));
    }


    private void createOutputVector(double[] input) {
        for (int i = 0; i < sumVector.length; i++) {
            outputVector[i] = sigmoid(sumVector[i] / width);
        }
    }


    // testing
    double[] test(double[] input) {

        // create a vector with the sum of each column of the matrix * input pattern
        scaleMatrix();
        createSummaryVector(input);
        createOutputVector(input);
//        printAssociations();
        printSynapticMatrix();
        System.out.print("Test Pattern: ");
        printPattern(input);
        printSumVector();
        printOutputVector();

        return outputVector;
    }


    int indexWithMinHammingDistance(double[] input) {

        int minHd = input.length;
        int minHDIndex = 0;
        for (int i = 0; i < associations.size(); i++) {
            Association association = associations.get(i);
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

    public double getLoadParameter() {
        return (double) associations.size() / (double) width;
    }

    public double getFractionOfSynapses() {
        // number of synapses that can be strengthened
        int counter = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(this.synapticMatrix[i*width+j] != 1) {
                    counter ++;
                }
            }
        }
        return (double)(height*width) / counter;
    }


    public void saturate() {
        // train while output is correct
        boolean found;
        do {
            this.train(randomBinaryVector(this.width), randomBinaryVector(this.width));
            found = isPatternCorrect(this.test(randomBinaryVector(this.width)));
            maxLoadParameter = getLoadParameter();
        } while(found);
        System.out.println("Fraction of synapses 11: " + getFractionOfSynapses());
        System.out.println("Max Load parameter 11: " + maxLoadParameter);
    }


    /* * * * * * * * * * * */
    /*       HELPERS       */
    /* * * * * * * * * * * */


    boolean matchPatterns(double[] patternA, double[] patternB) {
        for (int i = 0; i < patternA.length; i++) {
            if(patternA[i] != patternB[i]) return false;
        }
        return true;
    }

    boolean isPatternCorrect (double[] pattern) {
        for (Association association : associations) {
            if (matchPatterns(pattern, association.outputPattern)) {
                return true;
            }
        }
        return false;
    }


    double[] randomBinaryVector(int len) {
        double[] arr = new double[len];
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
                System.out.printf(" %.2f", this.synapticMatrix[i*width+j]);
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

    void printPattern(double[] pattern) {
        System.out.print("[");
        for (int i = 0; i < pattern.length; i++) {
            System.out.printf(" %.2f", pattern[i]);
        }
        System.out.println(" ]");
    }
}
