package javaFXML;

import javaJDBC.JDBC;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.App;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class gameForMultiController implements Initializable,viewChanger {

    public int Current_player = 1;
    public int currentQuestion = 0;
    public long startTime;

    @FXML
    public BorderPane gameForOneMultiPane;
    public Button AnswerA;
    public Button AnswerB;
    public Button AnswerC;
    public Button AnswerD;
    public Label LabelPytanie;
    public Pane QuestionPane;
    public Label scoreOfUser;
    public Label QuestionsCounter;
    public Label Nickname_1;
    public Label Nickname_2;
    public Label Nickname_3;
    public Label Nickname_4;
    public Label Score_1;
    public Label Score_2;
    public Label Score_3;
    public Label Score_4;
    public Label Current_playerCon;
    public Button startButton;

    @Override
    public void changePane(String FXMLFileName) {
        MyLoader loader = new MyLoader();
        Pane view = loader.getNextStage(FXMLFileName);
        gameForOneMultiPane.setCenter(view);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       preparePlayerBox();
       noAccessToButtons(true);
       Current_playerCon.setText(App.getUsersNickname(1));
    }

    public void handleButton1Action() {
        System.exit(0);
    }

    public void handleButton2Action(){ changePane("menu"); }

    public void handleButton3Action() throws Exception{

        prepareScoreCounter();
        NewQuestion();
        noAccessToButtons(false);
        setCurrentQuestion(1);
        changeQuestionCounter();
    }

    public void handleButton4Action()throws Exception{
        handleTheAnswerButton(AnswerA);
    }

    public void handleButton5Action()throws Exception{
        handleTheAnswerButton(AnswerB);
    }

    public void handleButton6Action()throws Exception {
        handleTheAnswerButton(AnswerC);
    }

    public void handleButton7Action()throws Exception{
        handleTheAnswerButton(AnswerD);
    }

    public void NewQuestion()throws Exception {

        createAndExecuteNewTask();

        Thread.sleep(300);

        setTheQuestionLabel();

        setTheAnswerButtons();

        startTime = System.currentTimeMillis();
    }

    public void handleTheAnswerButton (Button buttonToHandleWith) throws Exception {

        highlightTheQuestionPaneAndAddScore(buttonToHandleWith);
        incrementQuestionNumber();

        if (currentQuestion <= App.getMaxNumberOfQuestions()) {
            changeQuestionCounter();
            NewQuestion();
        } else {
            noAccessToButtons(true);
            LabelPytanie.setText("Nickname: " + App.getUsersNickname(Current_player) + ",  Score - " + App.getUsersScore(Current_player));

            addScoresToPlayerBox();

            if (Current_player == App.getNumberOfPlayers()){
                    for (int i = 0;i<App.getNumberOfPlayers();i++){
                        JDBC.addScoreToDataBase(App.getUsersNickname(i+1),App.getUsersScore(i+1));
                    }
                    startButton.setDisable(true);
                    App.clearUsersScores();
                    App.clearUsersNick();
                    App.userScore = 0.00;
            }else{
                Current_player = Current_player + 1;
                Current_playerCon.setText(App.getUsersNickname(Current_player));

            }


        }
    }                   //Do edycji

    public void preparePlayerBox(){                                             //DOBRE

        Nickname_1.setText(App.getUsersNickname(1));
        Nickname_2.setText(App.getUsersNickname(2));

        Score_1.setText("0.00");
        Score_2.setText("0.00");

        if (App.getNumberOfPlayers() == 2){

            Nickname_3.setText("Nie gra");
            Nickname_4.setText("Nie gra");

            Score_3.setText("Nie gra");
            Score_4.setText("Nie gra");

        }

        if (App.getNumberOfPlayers() == 3) {

            Nickname_3.setText(App.getUsersNickname(3));
            Score_3.setText("0.00");

            Nickname_4.setText("Nie gra");
            Score_4.setText("Nie gra");
        }

        if (App.getNumberOfPlayers() == 4) {

            Score_3.setText("0.00");
            Score_4.setText("0.00");

            Nickname_3.setText(App.getUsersNickname(3));
            Nickname_4.setText(App.getUsersNickname(4));

        }
    }

    public void noAccessToButtons(boolean state){                                             //DOBRE

        AnswerA.setDisable(state);
        AnswerB.setDisable(state);
        AnswerC.setDisable(state);
        AnswerD.setDisable(state);

    }

    public void createAndExecuteNewTask(){                                             //DOBRE

        App.createNewTasks();

        App.Executor.execute(App.drawingMachine);
        App.Executor.execute(App.question);

    }

    public void AddScoreToUser(Double scoreToBeAddedForGoodAnswer){
        App.addScoresOfUser(Current_player,scoreToBeAddedForGoodAnswer);
        scoreOfUser.setText(Double.toString(round(App.getUsersScore(Current_player), 2, BigDecimal.ROUND_HALF_UP)));
    }

    public static double round(double unrounded, int precision, int roundingMode){                           //DONE
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }

    public Double howManyPoints(long startTime){                                                            //DONE
        if (System.currentTimeMillis() - startTime > 10000){
            return 100.0;
        }
        else
            return 100.0 + 100.0 * (10000-(System.currentTimeMillis() - startTime))/10000;
    }

    public void prepareScoreCounter(){
        App.addScoreToUsersScores(0.0);
        scoreOfUser.setText(Double.toString(App.getUsersScore(Current_player)));
    }

    public void setTheAnswerButtons(){                                                                      //DONE

        AnswerA.setText(App.question.getAnswer(1));
        AnswerB.setText(App.question.getAnswer(2));
        AnswerC.setText(App.question.getAnswer(3));
        AnswerD.setText(App.question.getAnswer(4));

    }

    public void setTheQuestionLabel(){                                                                    //DONE

        if (App.question.isWordInPolishToEnglish())
            LabelPytanie.setText("Przetłumacz słowo : " + App.question.getQuizFlashcard().getWordInPolish());
        else
            LabelPytanie.setText("Przetłumacz słowo : " + App.question.getQuizFlashcard().getWordInEnglish());

    }

    public void highlightTheQuestionPaneAndAddScore(Button buttonToCheckAnswerFrom){                       //DONE
        if (App.question.checkAnswers(buttonToCheckAnswerFrom.getText())){
            QuestionPane.setStyle("-fx-background-color: green");
            AddScoreToUser(howManyPoints(this.startTime));
        }
        else
        {
            QuestionPane.setStyle("-fx-background-color: red");
        }
    }

    public void changeQuestionCounter(){                                                                    //DONE
        QuestionsCounter.setText("Pytanie "+currentQuestion+"/"+App.getMaxNumberOfQuestions());
    }

    public void incrementQuestionNumber(){                                                                   //DONE
        currentQuestion++;
    }

    public void setCurrentQuestion(int currentQuestion) {                                                    //DONE
        this.currentQuestion = currentQuestion;
    }

    public void addScoresToPlayerBox(){
        if (Current_player == 1){
            Score_1.setText(Double.toString(App.getUsersScore(Current_player)));
        } else if (Current_player == 2){
            Score_2.setText(Double.toString(App.getUsersScore(Current_player)));
        } else if (Current_player == 3){
            Score_3.setText(Double.toString(App.getUsersScore(Current_player)));
        } else if (Current_player == 4){
            Score_4.setText(Double.toString(App.getUsersScore(Current_player)));
        }
    }
}
