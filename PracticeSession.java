public class PracticeSession {
    private String type;
    private int minutes;

    public PracticeSession(String type, int minutes) {
        this.type = type;
        this.minutes = minutes;
    }

    public String getType() {
        return type;
    }

    public int getMinutes() {
        return minutes;
    }
}