import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GolfDashboardApp extends Application {

    private Player player = new Player();

    private Label bottomStatus = new Label("Welcome to Golf Companion Dashboard");

    private Label homeNameLabel = new Label("Player: Not set");
    private Label homeFavoriteCourseLabel = new Label("Favorite Course: Not set");
    private Label homeRoundsLabel = new Label("Rounds Played: 0");
    private Label homeBestScoreLabel = new Label("Best Score: N/A");
    private Label homePracticeLabel = new Label("Practice Minutes: 0");
    private Label homeConditionLabel = new Label("Course Condition: Normal");

    private Label scoreSummaryLabel = new Label("No round recorded yet.");
    private Label practiceSummaryLabel = new Label("No practice logged yet.");
    private Label clubResultLabel = new Label("Enter a distance to get a club suggestion.");
    private Label profileSummaryLabel = new Label("Profile not updated yet.");

    private VBox centerPane = new VBox();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(15));
        topBox.setStyle("-fx-background-color: lightgreen;");
        Label title = new Label("Golf Companion Dashboard");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        topBox.getChildren().add(title);

        VBox leftBox = new VBox(10);
        leftBox.setPadding(new Insets(15));
        leftBox.setStyle("-fx-background-color: beige;");

        Button homeButton = new Button("Home");
        Button profileButton = new Button("Profile");
        Button roundButton = new Button("New Round");
        Button practiceButton = new Button("Practice Log");
        Button clubButton = new Button("Club Guide");
        Button conditionButton = new Button("Update Condition");
        Button resetButton = new Button("Reset Stats");
        Button exitButton = new Button("Exit");

        homeButton.setMaxWidth(Double.MAX_VALUE);
        profileButton.setMaxWidth(Double.MAX_VALUE);
        roundButton.setMaxWidth(Double.MAX_VALUE);
        practiceButton.setMaxWidth(Double.MAX_VALUE);
        clubButton.setMaxWidth(Double.MAX_VALUE);
        conditionButton.setMaxWidth(Double.MAX_VALUE);
        resetButton.setMaxWidth(Double.MAX_VALUE);
        exitButton.setMaxWidth(Double.MAX_VALUE);

        leftBox.getChildren().addAll(
                homeButton, profileButton, roundButton, practiceButton,
                clubButton, conditionButton, resetButton, exitButton
        );

        centerPane.setPadding(new Insets(20));
        centerPane.setSpacing(10);

        HBox bottomBox = new HBox();
        bottomBox.setPadding(new Insets(10));
        bottomBox.setStyle("-fx-background-color: lightgray;");
        bottomBox.getChildren().add(bottomStatus);

        homeButton.setOnAction(e -> showHomePane());
        profileButton.setOnAction(e -> showProfilePane());
        roundButton.setOnAction(e -> showRoundPane());
        practiceButton.setOnAction(e -> showPracticePane());
        clubButton.setOnAction(e -> showClubPane());

        conditionButton.setOnAction(e -> {
            player.updateCondition();
            updateHomeLabels();
            bottomStatus.setText("Course condition updated.");
            showHomePane();
        });

        resetButton.setOnAction(e -> {
            player = new Player();
            updateHomeLabels();
            scoreSummaryLabel.setText("No round recorded yet.");
            practiceSummaryLabel.setText("No practice logged yet.");
            clubResultLabel.setText("Enter a distance to get a club suggestion.");
            profileSummaryLabel.setText("Profile not updated yet.");
            bottomStatus.setText("All stats were reset.");
            showHomePane();
        });

        exitButton.setOnAction(e -> primaryStage.close());

        root.setTop(topBox);
        root.setLeft(leftBox);
        root.setCenter(centerPane);
        root.setBottom(bottomBox);

        updateHomeLabels();
        showHomePane();

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Golf Companion Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showHomePane() {
        updateHomeLabels();

        centerPane.getChildren().clear();

        Label heading = new Label("Home");
        heading.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        centerPane.getChildren().addAll(
                heading,
                homeNameLabel,
                homeFavoriteCourseLabel,
                homeRoundsLabel,
                homeBestScoreLabel,
                homePracticeLabel,
                homeConditionLabel
        );

        bottomStatus.setText("Home page opened.");
    }

    private void showProfilePane() {
        centerPane.getChildren().clear();

        Label heading = new Label("Profile");
        heading.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Player Name:");
        TextField nameField = new TextField();
        nameField.setText(player.getName());

        Label courseLabel = new Label("Favorite Course:");
        TextField courseField = new TextField();
        courseField.setText(player.getFavoriteCourse());

        Button saveButton = new Button("Save Profile");

        saveButton.setOnAction(e -> {
            player.setName(nameField.getText());
            player.setFavoriteCourse(courseField.getText());

            profileSummaryLabel.setText(
                    "Profile Saved - Name: " + player.getName()
                    + ", Favorite Course: " + player.getFavoriteCourse()
            );

            updateHomeLabels();
            bottomStatus.setText("Profile updated.");
        });

        centerPane.getChildren().addAll(
                heading,
                nameLabel,
                nameField,
                courseLabel,
                courseField,
                saveButton,
                profileSummaryLabel
        );
    }

    private void showRoundPane() {
        centerPane.getChildren().clear();

        Label heading = new Label("New Round");
        heading.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label courseLabel = new Label("Course Name:");
        TextField courseField = new TextField();

        Label parLabel = new Label("Course Par:");
        TextField parField = new TextField();

        Label scoreLabel = new Label("Your Score:");
        TextField scoreField = new TextField();

        Label puttsLabel = new Label("Putts:");
        TextField puttsField = new TextField();

        Button saveRoundButton = new Button("Save Round");

        saveRoundButton.setOnAction(e -> {
            try {
                String courseName = courseField.getText();
                int par = Integer.parseInt(parField.getText());
                int score = Integer.parseInt(scoreField.getText());
                int putts = Integer.parseInt(puttsField.getText());

                RoundRecord round = new RoundRecord(courseName, par, score, putts);
                player.addRound(round);

                String status = round.getRoundStatus();

                scoreSummaryLabel.setText(
                        "Round Saved - Course: " + courseName
                        + ", Score: " + score
                        + ", Par: " + par
                        + ", Putts: " + putts
                        + ", Status: " + status
                );

                updateHomeLabels();
                bottomStatus.setText("Round saved.");
            } catch (Exception ex) {
                scoreSummaryLabel.setText("Please enter valid numbers for par, score, and putts.");
            }
        });

        centerPane.getChildren().addAll(
                heading,
                courseLabel,
                courseField,
                parLabel,
                parField,
                scoreLabel,
                scoreField,
                puttsLabel,
                puttsField,
                saveRoundButton,
                scoreSummaryLabel
        );
    }

    private void showPracticePane() {
        centerPane.getChildren().clear();

        Label heading = new Label("Practice Log");
        heading.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label typeLabel = new Label("Practice Type:");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Driving", "Putting", "Chipping");
        typeBox.setValue("Driving");

        Label minutesLabel = new Label("Minutes:");
        TextField minutesField = new TextField();

        Button savePracticeButton = new Button("Save Practice");

        savePracticeButton.setOnAction(e -> {
            try {
                String type = typeBox.getValue();
                int minutes = Integer.parseInt(minutesField.getText());

                PracticeSession session = new PracticeSession(type, minutes);
                player.addPractice(session);

                practiceSummaryLabel.setText(
                        "Practice Saved - Type: " + type
                        + ", Minutes: " + minutes
                        + ", Level: " + player.getPracticeLevel()
                );

                updateHomeLabels();
                bottomStatus.setText("Practice session saved.");
            } catch (Exception ex) {
                practiceSummaryLabel.setText("Please enter a valid number of minutes.");
            }
        });

        centerPane.getChildren().addAll(
                heading,
                typeLabel,
                typeBox,
                minutesLabel,
                minutesField,
                savePracticeButton,
                practiceSummaryLabel
        );
    }

    private void showClubPane() {
        centerPane.getChildren().clear();

        Label heading = new Label("Club Guide");
        heading.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label distanceLabel = new Label("Distance in yards:");
        TextField distanceField = new TextField();

        Button suggestButton = new Button("Suggest Club");

        suggestButton.setOnAction(e -> {
            try {
                int distance = Integer.parseInt(distanceField.getText());
                String club = player.getClubSuggestion(distance);

                clubResultLabel.setText(
                        "For " + distance + " yards, suggested club: " + club
                );

                bottomStatus.setText("Club suggestion updated.");
            } catch (Exception ex) {
                clubResultLabel.setText("Please enter a valid number for distance.");
            }
        });

        centerPane.getChildren().addAll(
                heading,
                distanceLabel,
                distanceField,
                suggestButton,
                clubResultLabel
        );
    }

    private void updateHomeLabels() {
        homeNameLabel.setText("Player: " + player.getName());
        homeFavoriteCourseLabel.setText("Favorite Course: " + player.getFavoriteCourse());
        homeRoundsLabel.setText("Rounds Played: " + player.getRoundsPlayed());

        if (player.getBestScore() == -1) {
            homeBestScoreLabel.setText("Best Score: N/A");
        } else {
            homeBestScoreLabel.setText("Best Score: " + player.getBestScore());
        }

        homePracticeLabel.setText("Practice Minutes: " + player.getTotalPracticeMinutes());
        homeConditionLabel.setText("Course Condition: " + player.getCourseCondition());
    }

    public static void main(String[] args) {
        launch(args);
    }
}