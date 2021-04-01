package score;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreTable {

    private ArrayList<Score> bestScoresTable = new ArrayList<>();

    public ScoreTable (){
        for (int i=0;i<5;i++) {
            bestScoresTable.add(new Score(0, "Noone"));
        }
    }

    public void addScore(Score scoreToBeAdded){

        if(this.bestScoresTable.size() == 0)
            this.bestScoresTable.add(scoreToBeAdded);
        else if (this.bestScoresTable.size() <5) {
            this.bestScoresTable.add(scoreToBeAdded);
            sortTableUpToDown();
        }
        else
        if (this.bestScoresTable.get(4).getScore() < scoreToBeAdded.getScore()){

            this.bestScoresTable.remove(4);
            this.bestScoresTable.add(scoreToBeAdded);
            sortTableUpToDown();
        }
    }

    public void printScoreTable(){
        for (int i=0;i<bestScoresTable.size();i++)
            System.out.println(i + 1 + ". Username = " + bestScoresTable.get(i).getUsername() + " Score = " + bestScoresTable.get(i).getScore());
    }

    public void sortTableUpToDown(){
        Collections.sort(bestScoresTable);
        Collections.reverse(bestScoresTable);
    }

    public String getNickname(int index){
        return bestScoresTable.get(index-1).getUsername();
    }

    public String getScore(int index){
        return "" + bestScoresTable.get(index-1).getScore();
    }

    public void setScoreTable(ArrayList<Score> temp){
        this.bestScoresTable = temp;
    }
}
