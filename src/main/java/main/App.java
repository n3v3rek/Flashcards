package main;


import javaJDBC.JDBC;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import questions.DrawingMachine;
import questions.Flashcard;
import questions.Quiz;
import score.ScoreTable;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {

    public static String userNickname;
    public static Double userScore = new Double(0);
    public static ScoreTable scores = new ScoreTable();
    public static int maxNumberOfQuestions = 20;
    public static int numberOfPlayers = 0;

    public static ArrayList<String> usersNickname = new ArrayList<>();
    public static ArrayList<Double> usersScores = new ArrayList<>();

    public static ArrayList<Flashcard> availableFlashcards = new ArrayList<>();

    //zmienne do backendu

    public static ExecutorService Executor = Executors.newSingleThreadExecutor();
    public static Quiz question;
    public static DrawingMachine drawingMachine;

    public void start(Stage primaryStage) throws Exception {

        getFlashcardsFromDB();

        Parent root = FXMLLoader.load(getClass().getResource("/start.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        }


    public static void main(String[] args) {
        launch(args);
    }

    public static void setUserNickname(String nickname) {
        userNickname = nickname;
    }

    public static String getUserNickname(){
        return userNickname;
    }

    public static void scoreTableUpdates(){
        scores.setScoreTable(javaJDBC.JDBC.getScoreTable());
    }

    public synchronized static void CopyFromMachine(){
        question.setWordInPolishToEnglish(drawingMachine.isWordInPolishToEnglish());
        question.setQuizFlashcard(drawingMachine.getDrawnFlashcard());
        question.setQuizAnswers(drawingMachine.getDrawnAnswers());
    }

    public static synchronized void createNewTasks(){
        question = new Quiz();
        drawingMachine = new DrawingMachine();
    }

    public static synchronized void addToUserScore(Double scoreToBeAdded){
        userScore = userScore + scoreToBeAdded;
    }

    public static synchronized Double getUserScore(){
        return userScore;
    }

    public static int getMaxNumberOfQuestions() {
        return maxNumberOfQuestions;
    }

    public static void setUserScore(Double userScore) {
        App.userScore = userScore;
    }

    public static void setScoresOfUser (int indexOfUser,Double score){
        usersScores.add(indexOfUser-1,score);
    }

    public static void addUsersNickname (String nickname){
        usersNickname.add(nickname);
    }

    public static String getUsersNickname(int indexOfUser){
        return usersNickname.get(indexOfUser-1);
    }

    public static Double getUsersScore(int indexOfUser){
        return usersScores.get(indexOfUser-1);
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        App.numberOfPlayers = numberOfPlayers;
    }

    public static void addScoreToUsersScores(Double scoreOfUser){
        usersScores.add(scoreOfUser);
    }

    public static void clearUsersScores(){
        usersScores.clear();
    }

    public static void clearUsersNick(){
        usersNickname.clear();
    }

    public static void addScoresOfUser (int indexOfUser,Double score){
        usersScores.set(indexOfUser-1, usersScores.get(indexOfUser-1)+score);
    }

    public static void refreshDB(){
        availableFlashcards.clear();
        getFlashcardsFromDB();
    }

    public static void getFlashcardsFromDB(){
        availableFlashcards = JDBC.getFlashcards();
    }
}






