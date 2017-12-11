package QuestionE;

import java.util.*;

public class Network {


    public int width;
    private int height;
    public static List<Association> associations;
    public float maxLoadParameter;
    public Layer l1, l2;


    // constructor
    public Network(int w, int h) {
        this.width = w;
        this.height = h;
        associations = new ArrayList<>();
        this.l1 = new Layer(width, height);
        this.l2 = new Layer(width, height);
    }


//    // save pair of input and output pattern
    void addAssociation(int[] inputPattern, int[] outputPattern) {
        associations.add(new Association(inputPattern, outputPattern));
    }

    // show associations (pairs of input and output patterns) saved
    void printAssociations() {
        System.out.println();
        associations.forEach(System.out::println);

    }


    // training
    void train(int[] inputPattern, int[] outputPattern) {
        addAssociation(inputPattern, outputPattern);
        this.l1.train(inputPattern, outputPattern);
        int[] l1output = l1.predict(inputPattern);
        this.l2.train(l1output, outputPattern);
        System.out.println("Layer 1 input vector:  " + Arrays.toString(inputPattern));
        System.out.println("Layer 1 output vector: " + Arrays.toString(l1output));
        l1.printSynapticMatrix();
        System.out.println("Layer 2 input vector:  " + Arrays.toString(l1output));



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

    // testing with test pattern
    int[] test(int[] input) {
        int[] l2output = this.l2.predict(input);
        System.out.println("Layer 2 output vector:  " + Arrays.toString(l2output));
        l2.printSynapticMatrix();
        return l2output;

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
