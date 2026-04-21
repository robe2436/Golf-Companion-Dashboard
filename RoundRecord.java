public class RoundRecord {
    private String courseName;
    private int par;
    private int score;
    private int putts;

    public RoundRecord(String courseName, int par, int score, int putts) {
        this.courseName = courseName;
        this.par = par;
        this.score = score;
        this.putts = putts;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getPar() {
        return par;
    }

    public int getScore() {
        return score;
    }

    public int getPutts() {
        return putts;
    }

    public String getRoundStatus() {
        if (score < par) {
            return "Under Par";
        } else if (score == par) {
            return "Even Par";
        } else {
            return "Over Par";
        }
    }
}