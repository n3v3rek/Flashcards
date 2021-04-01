package questions;

import main.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DrawingMachine extends Thread{

    private Flashcard DrawnFlashcard;
    private ArrayList<String> DrawnAnswers = new ArrayList<>();
    boolean WordInPolishToEnglish;

    public DrawingMachine(){
    }

    @Override
    public void run() {
        clearDrawnThings();
        randomReverseLanguage();
        drawnRandomFlashcard();
        drawnAnswers();
    }

    public void drawnRandomFlashcard() {
        Random rand = new Random();
        this.DrawnFlashcard = App.availableFlashcards.get(rand.nextInt(App.availableFlashcards.size()));
    }

    public void drawnAnswers() {

        if (this.WordInPolishToEnglish == true)                             //It mean that answers are in english
        {
            DrawnAnswers.add(DrawnFlashcard.getWordInEnglish());
        } else {                                                              //It mean that answers are in polish
            DrawnAnswers.add(DrawnFlashcard.getWordInPolish());
        }

        Flashcard tempToDrawnAnswers;

        while (DrawnAnswers.size() < 4) {
            Random rand = new Random();
            tempToDrawnAnswers = App.availableFlashcards.get(rand.nextInt(App.availableFlashcards.size()));
            boolean isFlashcardAlreadyIn = false;

            if (this.WordInPolishToEnglish == true) {                         //It mean that answers are in english
                for (int i = 0; i < DrawnAnswers.size(); i++) {
                    if (tempToDrawnAnswers.getWordInEnglish().equals(DrawnAnswers.get(i))) {
                        isFlashcardAlreadyIn = true;
                        break;
                    }
                }
                if (isFlashcardAlreadyIn == false) {
                    DrawnAnswers.add(tempToDrawnAnswers.getWordInEnglish());
                }
            } else {                                                           //It mean that answers are in polish
                for (int i = 0; i < DrawnAnswers.size(); i++) {
                    if (tempToDrawnAnswers.getWordInPolish().equals(DrawnAnswers.get(i))) {
                        isFlashcardAlreadyIn = true;
                        break;
                    }
                }
                if (isFlashcardAlreadyIn == false) {
                    DrawnAnswers.add(tempToDrawnAnswers.getWordInPolish());
                }
            }

        }

        shuffleAnswers();

    }

    public void shuffleAnswers() {
        Collections.shuffle(DrawnAnswers);
    }

    public Flashcard getDrawnFlashcard() {
        return DrawnFlashcard;
    }

    public void setDrawnFlashcard(Flashcard drawnFlashcard) {
        DrawnFlashcard = drawnFlashcard;
    }

    public ArrayList<String> getDrawnAnswers() {
        return DrawnAnswers;
    }

    public void setDrawnAnswers(ArrayList<String> drawnAnswers) {
        DrawnAnswers = drawnAnswers;
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

    public boolean clearDrawnThings() {
        try {
            DrawnFlashcard = null;
            DrawnAnswers.clear();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isWordInPolishToEnglish() {
        return WordInPolishToEnglish;
    }

    public void setWordInPolishToEnglish(boolean wordInPolishToEnglish) {
        WordInPolishToEnglish = wordInPolishToEnglish;
    }
}
