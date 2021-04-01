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

public class gameForOneController implements Initializable,viewChanger {

    public long startTime;
    public int currentQuestion = 0;

    @FXML
    public BorderPane gameForOnePane;
    public Button AnswerA;
    public Button AnswerB;
    public Button AnswerC;
    public Button AnswerD;
    public Label LabelPytanie;
    public Pane QuestionPane;
    public Label scoreOfUser;
    public Label QuestionsCounter;


    @Override
    public void changePane(String FXMLFileName) {
        MyLoader loader = new MyLoader();
        Pane view = loader.getNextStage(FXMLFileName);
        gameForOnePane.setCenter(view);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noAccessToButtons(true);
    }

    public void handleButton1Action() {
        System.exit(0);
    }

    public void handleButton2Action(){ changePane("menu"); }

    public void handleButton3Action() throws Exception{
        App.clearUsersScores();
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

    public void handleTheAnswerButton (Button buttonToHandleWith) throws Exception{

        highlightTheQuestionPaneAndAddScore(buttonToHandleWith);

        incrementQuestionNumber();

        if (currentQuestion <= App.getMaxNumberOfQuestions()) {
            changeQuestionCounter();
            NewQuestion();
        }
        else {
            noAccessToButtons(true);
            LabelPytanie.setText("Nickname: " +App.getUserNickname() + ",  Score - " + App.getUserScore());
            JDBC.addScoreToDataBase(App.getUserNickname(),App.getUserScore());
        }

    }

    public void noAccessToButtons(boolean state){                       //Blokada odpowiedzi przed startem gry

        AnswerA.setDisable(state);
        AnswerB.setDisable(state);
        AnswerC.setDisable(state);
        AnswerD.setDisable(state);

    }

    public void setTheAnswerButtons(){                                  //Wczytywanie odpowiedzi na Buttony

        AnswerA.setText(App.question.getAnswer(1));
        AnswerB.setText(App.question.getAnswer(2));
        AnswerC.setText(App.question.getAnswer(3));
        AnswerD.setText(App.question.getAnswer(4));

    }

    public void setTheQuestionLabel(){                                  //Ustawianie pytania na Labelu

        if (App.question.isWordInPolishToEnglish())
            LabelPytanie.setText("Przetłumacz słowo : " + App.question.getQuizFlashcard().getWordInPolish());
        else
            LabelPytanie.setText("Przetłumacz słowo : " + App.question.getQuizFlashcard().getWordInEnglish());

    }

    public void createAndExecuteNewTask(){                              //Tworzenie taskow odpowiadajacych za pobieranie fiszek

        App.createNewTasks();

        App.Executor.execute(App.drawingMachine);
        App.Executor.execute(App.question);

    }

    public void highlightTheQuestionPaneAndAddScore(Button buttonToCheckAnswerFrom){                    //Podswietlenie po odpowiedzi
        if (App.question.checkAnswers(buttonToCheckAnswerFrom.getText())){
            QuestionPane.setStyle("-fx-background-color: green");
            AddScoreToUser(howManyPoints(this.startTime));
        }
        else
        {
            QuestionPane.setStyle("-fx-background-color: red");
        }
    }

    public void AddScoreToUser(Double scoreToBeAddedForGoodAnswer){                                     //Dodawanie punktow za pytania do punktow uzytkownika
        App.addToUserScore(scoreToBeAddedForGoodAnswer);
        scoreOfUser.setText(Double.toString(round(App.getUserScore(), 2, BigDecimal.ROUND_HALF_UP)));
    }

    public Double howManyPoints(long startTime){                                                        //Obliczanie punktow
        if (System.currentTimeMillis() - startTime > 10000){
            return 100.0;
        }
        else
            return 100.0 + 100.0 * (10000-(System.currentTimeMillis() - startTime))/10000;
    }

    public void changeQuestionCounter(){                                                                //Ustawianie licznika pytan
        QuestionsCounter.setText("Pytanie "+currentQuestion+"/"+App.getMaxNumberOfQuestions());
    }

    public void incrementQuestionNumber(){                                                             //Inkrementowanie numeru pytania
        currentQuestion++;
    }

    public void setCurrentQuestion(int currentQuestion) {                                               //Do ustawiania pierwszego pytania
        this.currentQuestion = currentQuestion;
    }

    public void prepareScoreCounter(){                                                                  //Zerowanie licznika
        App.setUserScore(0.0);
        scoreOfUser.setText(Double.toString(App.getUserScore()));
    }

    public static double round(double unrounded, int precision, int roundingMode)
    {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }
}
