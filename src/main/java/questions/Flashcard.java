package questions;

public class Flashcard {

    private String wordInPolish;
    private String wordInEnglish;

    public Flashcard(String wordInPolish, String wordInEnglish) {
        this.wordInPolish = wordInPolish;
        this.wordInEnglish = wordInEnglish;
    }

    public Flashcard(Flashcard temp) {
        this.wordInPolish = temp.wordInPolish;
        this.wordInEnglish = temp.wordInEnglish;
    }

    public boolean correctTranslationToENG(String AnswerInEnglish) {
        return this.wordInEnglish.equals(AnswerInEnglish);
    }

    public boolean correctTranslationToPOL(String AnswerInPolish) {
        return this.wordInPolish.equals(AnswerInPolish);
    }

    public String getWordInPolish() {
        return wordInPolish;
    }

    public void setWordInPolish(String wordInPolish) {
        this.wordInPolish = wordInPolish;
    }

    public String getWordInEnglish() {
        return wordInEnglish;
    }

    public void setWordInEnglish(String wordInEnglish) {
        this.wordInEnglish = wordInEnglish;
    }

    @Override
    public String toString() {
        return wordInPolish + " - " + wordInEnglish;
    }
}
