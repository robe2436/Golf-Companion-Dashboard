import java.util.ArrayList;

public class Player {
    private String name;
    private String favoriteCourse;
    private ArrayList<RoundRecord> rounds;
    private ArrayList<PracticeSession> practices;
    private String courseCondition;

    public Player() {
        name = "Not set";
        favoriteCourse = "Not set";
        rounds = new ArrayList<>();
        practices = new ArrayList<>();
        courseCondition = "Normal";
    }

    public void setName(String name) {
        if (name == null || name.equals("")) {
            this.name = "Not set";
        } else {
            this.name = name;
        }
    }

    public void setFavoriteCourse(String favoriteCourse) {
        if (favoriteCourse == null || favoriteCourse.equals("")) {
            this.favoriteCourse = "Not set";
        } else {
            this.favoriteCourse = favoriteCourse;
        }
    }

    public String getName() {
        return name;
    }

    public String getFavoriteCourse() {
        return favoriteCourse;
    }

    public void addRound(RoundRecord round) {
        rounds.add(round);
    }

    public void addPractice(PracticeSession practice) {
        practices.add(practice);
    }

    public int getRoundsPlayed() {
        return rounds.size();
    }

    public int getBestScore() {
        if (rounds.size() == 0) {
            return -1;
        }

        int best = rounds.get(0).getScore();

        for (int i = 1; i < rounds.size(); i++) {
            if (rounds.get(i).getScore() < best) {
                best = rounds.get(i).getScore();
            }
        }

        return best;
    }

    public int getTotalPracticeMinutes() {
        int total = 0;

        for (PracticeSession p : practices) {
            total += p.getMinutes();
        }

        return total;
    }

    public String getPracticeLevel() {
        int total = getTotalPracticeMinutes();

        if (total == 0) {
            return "No practice yet";
        } else if (total < 60) {
            return "Beginner";
        } else if (total < 180) {
            return "Improving";
        } else {
            return "Committed golfer";
        }
    }

    public String getClubSuggestion(int distance) {
        if (distance <= 100) {
            return "Wedge";
        } else if (distance <= 140) {
            return "9 Iron";
        } else if (distance <= 170) {
            return "7 Iron";
        } else if (distance <= 210) {
            return "Hybrid";
        } else {
            return "Driver";
        }
    }

    public void updateCondition() {
        String[] conditions = {"Sunny", "Windy", "Wet", "Ideal", "Cloudy"};
        int index = (int)(Math.random() * conditions.length);
        courseCondition = conditions[index];
    }

    public String getCourseCondition() {
        return courseCondition;
    }
}