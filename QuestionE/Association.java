package QuestionE;

import java.util.Arrays;

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
