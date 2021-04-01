package questions;

import javafx.concurrent.Task;
import main.App;

import java.util.ArrayList;
import java.util.Random;

public class Quiz extends Task<Void> {

    private boolean WordInPolishToEnglish;
    private Flashcard QuizFlashcard;
    private ArrayList<String> QuizAnswers = new ArrayList<>();

    public Quiz() { }

    @Override
    protected Void call() throws Exception{
        App.CopyFromMachine();
        return null;
    }

    public boolean checkAnswers(String UserAnswer) {
        if (this.WordInPolishToEnglish == true) {                              //It mean that answers are in english
            return UserAnswer.equals(QuizFlashcard.getWordInEnglish());
        } else {                                                              //It mean that answers are in polish
            return UserAnswer.equals(QuizFlashcard.getWordInPolish());
        }
    }


    public String printAnswers() {
        return "1." + QuizAnswers.get(0) + " 2." + QuizAnswers.get(1) + " 3." + QuizAnswers.get(2) + " 4." + QuizAnswers.get(3);
    }


    public String getAnswer(int Index) {

        try {
            return QuizAnswers.get(Index-1);
        } catch (Exception e){
            e.printStackTrace();
            return "OutOfBanderiesError";
        }
    }

    public void randomReverseLanguage(){

        Random rand = new Random();

        if(rand.nextInt(2) == 0)
        {
            WordInPolishToEnglish = false;
        }else
        {
            WordInPolishToEnglish = true;
        }
    }

    public boolean isWordInPolishToEnglish() {
        return WordInPolishToEnglish;
    }

    public void setWordInPolishToEnglish(boolean wordInPolishToEnglish) {
        WordInPolishToEnglish = wordInPolishToEnglish;
    }

    public Flashcard getQuizFlashcard() {
        return QuizFlashcard;
    }

    public void setQuizFlashcard(Flashcard quizFlashcard) {
        QuizFlashcard = quizFlashcard;
    }

    public ArrayList<String> getQuizAnswers() {
        return QuizAnswers;
    }

    public void setQuizAnswers(ArrayList<String> quizAnswers) {
        QuizAnswers = quizAnswers;
    }

    public boolean clearDrawnThings() {
        try {
            QuizFlashcard = null;
            QuizAnswers.clear();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}



    /*public void run() {

        double startTime;
        double replyTime;
        int numberOfQuestion = 0;
        flashcard flashcard ;
        boolean istranslationFromPolish = isTranslationFromPolish();                                  // Jesli tak to tlumaczenie slowo polskiego na angielskie, jesli falsz to odwrotnie
        ArrayList <String> answers = new ArrayList<>();
        int numberAnsweredByUser;
        boolean isGoodAnswer;
        double scoreOfUser = 0;


        while (numberOfQuestion <= this.maxNumberOfQuestions)
        {
            startTime = System.currentTimeMillis();
            flashcard = GetRandomFlashcard();
            addAnswers(istranslationFromPolish,flashcard,answers);
            printQuestion(istranslationFromPolish,flashcard,answers);
            numberAnsweredByUser = getAnswerFromUser();
            isGoodAnswer = ifGoodChoose(istranslationFromPolish,answers.get(numberAnsweredByUser - 1),flashcard);
            replyTime = (System.currentTimeMillis() - startTime)/1000;                                                              //Zmiana do formatu sekund
            scoreOfUser = scoreOfUser + getPointsForTheAnswer(replyTime,isGoodAnswer);
            numberOfQuestion = numberOfQuestion + 1;
            clearAnswersTable(answers);
        }
        TableOfBestScores.addScore(new Score(scoreOfUser,this.username));

    }

}
*/
