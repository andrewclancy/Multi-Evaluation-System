
/**
 * Created by andrew on 25/03/16.
 */
public class EvaluationWrapper{
    private double bleuScore;
    private float werScore;
    private String reference;
    private String hypothesis;

    public EvaluationWrapper(double bleu, float wer, String ref, String hyp) {
        this.bleuScore = bleu;
        this.werScore = wer;
        this.reference=ref;
        this.hypothesis=hyp;
    }

    @Override
    public String toString() {
        StringBuilder eval = new StringBuilder();
        eval.append("--------------------------------------------------------"+"\n");
        eval.append("Reference:  | " + reference+"\n");
        eval.append("Hypothesis: | " + hypothesis +"\n");

        eval.append("   ----------------------------"+"\n");
        eval.append("   |BLEU:    | " + bleuScore +"\n");
        eval.append("   |WER:     | " + werScore +"\n");
        eval.append("   |NIST:    | " + "N/A" +"\n");
        eval.append("   |METEOR:  | " + "N/A"  +"\n");
        eval.append("   ----------------------------"+"\n");

        return eval.toString();
    }

}
