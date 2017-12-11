package QuestionE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Network {

    public class Association {

        int[] inputPattern;
        int[] outputPattern;

        Association(int[] inputPattern, int[] outputPattern) {
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


    public int width;
    private int height;
    public static List<Association> associations;
    public float maxLoadParameter;
    List<Layer> layers;


    // constructor
    public Network(int w, int h) {
        this.width = w;
        this.height = h;
        this.associations = new ArrayList<>();
        this.layers = new ArrayList<>();
    }


    // save pair of input and output pattern
    void addAssociation(int[] inputPattern, int[] outputPattern) {
        this.associations.add(new Association(inputPattern, outputPattern));
    }

    // show associations (pairs of input and output patterns) saved
    void printAssociations() {
        System.out.println();
        this.associations.forEach(System.out::println);

    }


    // training
    int[] train(int[] inputPattern, int[] outputPattern) {
        addAssociation(inputPattern, outputPattern);
        Layer startLayer = new Layer(width, height);
        startLayer.train(inputPattern, outputPattern);
        this.layers.add(startLayer);
        return startLayer.predict(inputPattern);
    }


    // triple training in one go
    void multiTrain(int[] inputPatternTriple, int[] outputPattern) {

        int[] inTemp  = new int[width];

        for (int j = 0; j < inputPatternTriple.length; j+=width) {
            for (int i = 0; i < width; i++) {
                inTemp[i] = inputPatternTriple[i+j];
            }
            train(inTemp, outputPattern);
            System.out.println("Input = " + Arrays.toString(inTemp) +
                    ", Output = " + Arrays.toString(outputPattern));
        }

    }

    int[] test(int[] input) {
        int[] test = this.layers.
    }

    int indexWithMinHammingDistance(int[] input) {

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

    public float getLoadParameter() {
        return (float) associations.size() / (float) width;
    }

//    public float getFractionOfSynapses() {
//        // number of synapses that can be strengthened
//        int counter = 0;
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                if(this.synapticMatrix[i*width+j] != 1) {
//                    counter ++;
//                }
//            }
//        }
//        return (float)(height*width) / counter;
//    }


//    public void saturate() {
//        // train while output is correct
//        boolean found = false;
//        do {
//            this.train(randomBinaryVector(this.width), randomBinaryVector(this.width));
//            found = isPatternCorrect(this.test(randomBinaryVector(this.width)));
//            maxLoadParameter = getLoadParameter();
//
//
//        } while(found);
////        System.out.println("Fraction of synapses 11: " + getFractionOfSynapses());
////        System.out.println("Max Load parameter 11: " + maxLoadParameter);
//    }


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
        for (Association association : associations) {
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
//
//    void printSynapticMatrix() {
//        System.out.print("\n\nSynaptic Matrix {");
//        for (int i = 0; i < width; i++) {
//            System.out.println();
//            for (int j = 0; j < height; j++) {
//                System.out.print(" " + this.synapticMatrix[i*width+j]);
//            }
//        }
//        System.out.println("\n}");
//    }
//
//    void printSumVector() {
//        System.out.print("Sum Vector: ");
//        printPattern(sumVector);
//    }
//
//    void printOutputVector() {
//        System.out.print("Output Vector: ");
//        printPattern(outputVector);
//    }
//
//    void printPattern(int[] pattern) {
//        System.out.print("[");
//        for (int i = 0; i < pattern.length; i++) {
//            System.out.print(" " + pattern[i]);
//        }
//        System.out.println(" ]");
//    }
}
