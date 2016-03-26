import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 22/03/16.
 */
public class Bleu {
    List<String> ngramsOne = new ArrayList<>();
    List<String> ngramsTwo = new ArrayList<>();
    List<String> ngramsThree = new ArrayList<>();
    List<String> ngramsFour = new ArrayList<>();

    List<String> ngramsOneRef = new ArrayList<>();
    List<String> ngramsTwoRef = new ArrayList<>();
    List<String> ngramsThreeRef = new ArrayList<>();
    List<String> ngramsFourRef = new ArrayList<>();

    /**
     * Get ngram of string
     * @param n
     * @param str
     * @return
     */
    public List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++) {
            ngrams.add(concat(words, i, i + n));
        }

        return ngrams;
    }

    /**
     * concat
     * @param words
     * @param start
     * @param end
     * @return
     */
    public String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);

        return sb.toString();
    }

    /**
     * Get precision value
     * @param ngramsRef
     * @param ngramsMT
     * @return
     */
    public float precision(List<String> ngramsRef, List<String> ngramsMT) {
        int refCount = ngramsRef.size();
        int origionalCount = ngramsMT.size();
        int temp = origionalCount;
        ngramsMT.removeAll(ngramsRef);
        int count = ngramsMT.size();
        origionalCount = origionalCount - count;
        final float result = (float) origionalCount / (float) refCount;

        //System.out.println(origionalCount + "/" + refCount + " ---> " + result);
        return result;

    }

    public int getWordCount(String seq) {
        String[] wordList = null;
        for (int i = 0; i < seq.length(); i++) {
            wordList = seq.split(" ");
        }
        return wordList.length;
    }

    public double blueAlgorithm(String ref, String word) {
        float bp=0;
        int wordCount = getWordCount(word);
        int refWordCount = getWordCount(ref);
        if(wordCount <= refWordCount) {
            bp = (float) wordCount / (float) refWordCount;
        }else bp = 1;


        for (int n = 1; n <= 4; n++) {
            for (String ngram : ngrams(n, word)) {
                if(n == 1){
                    ngramsOneRef.add(ngram);
                }else if(n == 2){
                    ngramsTwoRef.add(ngram);
                }else if(n == 3){
                    ngramsThreeRef.add(ngram);
                }else if(n == 4){
                    ngramsFourRef.add(ngram);
                }

            }
        }

        /* ------------------------------------- */

        for (int n = 1; n <= 4; n++) {
            for (String ngrams : ngrams(n, ref)) {
                if(n == 1){
                    ngramsOne.add(ngrams);
                }else if(n == 2){
                    ngramsTwo.add(ngrams);
                }else if(n == 3){
                    ngramsThree.add(ngrams);
                }else if(n == 4){
                    ngramsFour.add(ngrams);
                }
            }

        }

        /* ------------------------------------------ */

        /*
        System.out.println("ngramsOne: " + ngramsOne);
        System.out.println("ngramsTwo: " + ngramsTwo);
        System.out.println("ngramsThree: " + ngramsThree);
        System.out.println("ngramsFour: " + ngramsFour);

        System.out.println();
        System.out.println("ngramsOneRef: " + ngramsOneRef);
        System.out.println("ngramsTwoRef: " + ngramsTwoRef);
        System.out.println("ngramsThreeRef: " + ngramsThreeRef);
        System.out.println("ngramsFourRef: " + ngramsFourRef);

        System.out.println();
        */

        final float n1= precision(ngramsOneRef, ngramsOne);
        final float n2=precision(ngramsTwoRef, ngramsTwo);
        final float n3=precision(ngramsThreeRef, ngramsThree);
        final float n4=precision(ngramsFourRef, ngramsFour);

        /*
        System.out.println("n1: " + n1);
        System.out.println("n2: " + n2);
        System.out.println("n3: " + n3);
        System.out.println("n4: " + n4);
        */

        //System.out.println("Brevity Penalty: " + wordCount +"/"+refWordCount + " ---> " +bp);

        double num = n1*n2*n3*n4;
        //System.out.println("Multiplication: " + num);
        final float power = (float)1/4;
        //System.out.println("Power: " + power);
        double sqrtNum = Math.pow(num, power);
        sqrtNum = sqrtNum * bp;
        //System.out.println("-------------------------------------------------------");
        //System.out.println("Final result: " + sqrtNum);
        //System.out.println("-------------------------------------------------------");
        return sqrtNum;
    }
}