package QuestionF;

import java.util.*;


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
    private HashMap<Integer, ArrayList<Integer>> synapticMatrix;
    public List<Association> associations;
    private int[] sumVector;
    private int[] outputVector;
    public float maxLoadParameter;


    // constructor
    public Network(int w, int h) {
        this.width = w;
        this.height = h;
        this.synapticMatrix = new HashMap<>();
        this.associations = new ArrayList<>();
        this.sumVector = new int[w];
        this.outputVector = new int[w];
    }


    // update the matrix with the created from a training pair
    void updateSynapticMatrix(int[] inputPattern, int[] outputPattern) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(inputPattern[i]*outputPattern[j] == 1) {
                    if(synapticMatrix.containsKey(i)){
                        if(!synapticMatrix.get(i).contains(j)) {
                            synapticMatrix.get(i).add(j);
                        }
                    } else {
                        synapticMatrix.put(i, new ArrayList<>());
                        synapticMatrix.get(i).add(j);
                    }
                }

            }
        }
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
    void train(int[] inputPattern, int[] outputPattern) {
        updateSynapticMatrix(inputPattern, outputPattern);
        addAssociation(inputPattern, outputPattern);
    }


    void createSummaryVector(int[] input) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
               if(synapticMatrix.containsKey(i) && synapticMatrix.get(i).contains(j)) {
                   sumVector[j] += input[i];
//                   System.out.println(synapticMatrix.get(i).get(j));
               }
            }
        }
    }


    private int calcThreshold(int[] input) {
        // count no of 1s in input and output patterns
        int inputCount = 0, outputCount = 0;
        for(int i=0; i < width; i++) {
            if(input[i] == 1)
                inputCount ++;
            if(associations.get(0).inputPattern[i] == 1)
                outputCount ++;
        }
        // compare them and return lesser
        if(inputCount <= outputCount){
            return  inputCount;
        }
        return outputCount;
    }


    private void createOutputVector(int[] input) {

        int threshold = calcThreshold(associations.
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
        printSumVector();
        printOutputVector();

        return outputVector;
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

    void printSynapticMatrix() {
        System.out.print("\n\nSynaptic Matrix {\n");
        for (Map.Entry<Integer, ArrayList<Integer>> entry : synapticMatrix.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();
            System.out.println(key + ", " + value);
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
