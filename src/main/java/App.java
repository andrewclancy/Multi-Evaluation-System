import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 22/03/16.
 */
public class App {

    public static void main(String [] args){

        Map<String, String> map = new HashMap<>();
        /**
         * This reads data from data.txt file in test-data folder and sorts Ref and Hyp
         * sentences into pairs (Formed into a Map)
         * **/
        try {
            map=getTestData();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        /**
         * Using the map achieved above get Evaluation analysis on them all accordingly
         **/
        for ( String key : map.keySet() ) {
           // System.out.println( key + "  =  " + map.get(key));
            WordSequenceAligner werEval = new WordSequenceAligner();
            Bleu bleu = new Bleu();
            String ref = key;
            String hyp =  map.get(key);
            String [] refArray = ref.split(" ");
            String [] hypArray = hyp.split(" ");
            WordSequenceAligner.Alignment a = werEval.align(refArray, hypArray);
            EvaluationWrapper e = new EvaluationWrapper(bleu.blueAlgorithm(ref,hyp), a.werScore(),ref,hyp);
            System.out.println(e.toString());

        }

    }

    /**
     * Get test data to test automatic Evaluations: Bleu, Nist, WER and Meteor
     * @return
     * @throws IOException
     */
    public static Map<String, String> getTestData() throws IOException {
        Map<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("/home/andrew/IdeaProjects/SMTProject/test-data/data"));
        try {
            String line = br.readLine();
            while (line != null) {
                list.add(line);
                line = br.readLine();

            }
        } finally {
            br.close();
        }
        for(int i =0; i < list.size(); i++){
            String ref = list.get(i);
            int j = i+1;
            String hyp = list.get(j);
            i++;
            map.put(ref,hyp);
        }
        //System.out.println(map);
        /*
        for ( String key : map.keySet() ) {
            System.out.println(key);
            System.out.println("----" + map.get(key));

        }*/
        return map;
    }
}


