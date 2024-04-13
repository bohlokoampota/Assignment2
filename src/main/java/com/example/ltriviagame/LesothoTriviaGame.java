package com.example.ltriviagame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LesothoTriviaGame extends Application {
    private Stage primaryStage;
    private BorderPane mainLayout;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LesothoTrivia.fxml"));
        Parent root = loader.load();

        TriviaController controller = loader.getController();

        primaryStage.setTitle("Lesotho Trivia Game");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        initializeQuestions();

        mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 800,600);

        displayQuestion();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeQuestions(){
        questions = new ArrayList<>();

        questions.add(new Question("In which continent is Lesotho?","Africa", Arrays.asList("Asia","Europe","North America")));
        questions.add(new Question("What is Lesotho's currency?","Loti", Arrays.asList("Dollar","Euro","Rand")));
        questions.add(new Question("what is the most widely spoken language in Lesotho?","Sesotho", Arrays.asList("English","Afrikaans","Setswana")));
        questions.add(new Question("what is the official religion of Lesotho?","Christianity", Arrays.asList("Islam","Hinduism","Buddhism")));
        questions.add(new Question("Which river flows through Lesotho?","Senqu", Arrays.asList("Zambezi","Zamfara","Nile")));

        Collections.shuffle(questions);
    }
    private void displayQuestion(){
        if(currentQuestionIndex < questions.size()){
            Question currentQuestion = questions.get(currentQuestionIndex);

            VBox questionBox = new VBox(10);
            questionBox.setAlignment(Pos.CENTER);
            questionBox.setPadding(new Insets(20));

            Label questionLabel = new Label(currentQuestion.getQuestion());
            questionLabel.setStyle("-fx-font-size:22pt;");

            ImageView imageView = new ImageView(new Image("Cannabis-in-Lesotho-scaled.jpg"));
            imageView.setFitWidth(400);
            imageView.setFitHeight(300);

            HBox answerBox = new HBox(10);
            answerBox.setAlignment(Pos.CENTER);

            ToggleGroup answerGroup = new ToggleGroup();
            for (String option : currentQuestion.getOptions()){
                RadioButton radioButton = new RadioButton(option);
                radioButton.setToggleGroup(answerGroup);
                answerBox.getChildren().add(radioButton);
            }
            Button submitButton = new Button("submit");
            submitButton.setOnAction(event -> {
                RadioButton selectedRadioButton = (RadioButton) answerGroup.getSelectedToggle();
                if (selectedRadioButton != null) {
                    String selectedAnswer = selectedRadioButton.getText();
                    if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                        score++;
                    }
                    currentQuestionIndex++;
                    displayQuestion();
                } else {
                   Alert alert = new Alert(Alert.AlertType.WARNING);
                   alert.setTitle("Warning");
                   alert.setHeaderText(null);
                   alert.setContentText("select an answer please!");
                   alert.showAndWait();
                }
            });
           questionBox.getChildren().addAll(questionLabel,imageView,answerBox,submitButton);
           mainLayout.setCenter(questionBox);
        }else {
            Label finalScoreLabel = new Label("Final score:" + score + "/" + questions.size());
            finalScoreLabel.setStyle("-fx-font-size: 22pt;");
            mainLayout.setCenter(finalScoreLabel);
        }
    }
}

