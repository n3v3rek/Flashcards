package score;

public class Score implements Comparable<Score>{

    private double score;
    private String username;

    public Score(double scoreFromGame,String usernameOfScorer )
    {
        this.score = scoreFromGame;
        this.username = usernameOfScorer;
    }

    public String getUsername() {
        return username;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(Score scoreToCompare) {
        return Double.compare(this.score, scoreToCompare.getScore());
    }
}
