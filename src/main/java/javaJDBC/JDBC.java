package javaJDBC;

import questions.Flashcard;
import score.Score;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class JDBC {

    static String query = "SELECT * FROM bestscorestable";
    static Connection conn = null;
    static String url = "jdbc:mysql://localhost:3306/";
    static String dbName = "flashcards";
    static String driver = "com.mysql.jdbc.Driver";
    static String userName = "student";
    static String password = "student123";

    //Polaczenie z baza

    public static void connectWithDB()
    {
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url+dbName,userName,password);
        }catch (Exception e) {
            System.out.println("No DB connection - Error in " .getClass().getName());
        }
    }

    // Rozlaczenie polaczenia z baza

    public static void disconnectWithDB()
    {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("No DB connection - Error in " .getClass().getName());
        }
    }

    public static void main(String[] args) { };

    //Dodaje nowe fiszki do bazy

    public static void addNewFlashcard(String wordInEnglish,String wordInPolish) {
        connectWithDB();
        query = "INSERT INTO flashcards VALUES (NULL,\"" + wordInEnglish + "\",\"" + wordInPolish + "\")";

        if (conn == null) {
            System.out.println("To use statment, first u need to connect to DB!");
        } else {
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(query);
            } catch (Exception e) {
                System.out.println("No DB connection - Error in " .getClass().getName());
            }
        }
        disconnectWithDB();
    }

    //Pobiera z bazy tabele z najlepszymi wynikami

    public static ArrayList<Score> getScoreTable(){
        connectWithDB();
        query = "SELECT * FROM bestscorestable ORDER BY scoreOfPlayer DESC;";
        ArrayList<Score> temp = new ArrayList<>();

        if (conn == null) {
            System.out.println("To use statment, first u need to connect to DB!");
        } else {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                int numberOfRecords = 5;
                try {
                    for (int i = 5; i> 0; i--){
                        rs.next();
                        temp.add(new Score(Double.parseDouble(rs.getString(3)),rs.getString(2)));
                    }
                    /*
                    while (rs.next()) {
                        temp.add(new Score(Double.parseDouble(rs.getString(3)),rs.getString(2)));
                    }
                     */
                } catch (Exception e)
                {
                    System.out.println("No DB connection - Error in " .getClass().getName());
                }

            } catch (Exception e) {
                System.out.println("No DB connection - Error in " .getClass().getName());
            }
        }
        disconnectWithDB();
        return temp;
    }

    //Pobiera z bazy tabele z fiszkami

    public static ArrayList<Flashcard> getFlashcards(){
        connectWithDB();
        query = "SELECT * FROM flashcards;";
        ArrayList<Flashcard> temp = new ArrayList<>();

        if (conn == null) {
            System.out.println("To use statment, first u need to connect to DB!");
        } else {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                try {
                    while (rs.next()) {
                        Flashcard tempFlashcard = new Flashcard(rs.getString(3),rs.getString(2));
                        temp.add(tempFlashcard);
                    }
                } catch (Exception e)
                {
                    System.out.println("No DB connection - Error in " .getClass().getName());
                }

            } catch (Exception e) {
                System.out.println("No DB connection - Error in " .getClass().getName());
            }
        }
        disconnectWithDB();
        return temp;
    }

    //Usuwanie z bazy

    public static void deleteFlashcard (Flashcard flashcardToBeDeleted){
        connectWithDB();
        query = "DELETE FROM flashcards where wordInEnglish=\""+flashcardToBeDeleted.getWordInEnglish()+"\" and wordInPolish=\"" + flashcardToBeDeleted.getWordInPolish() + "\";";
        if (conn == null) {
            System.out.println("To use statment, first u need to connect to DB!");
        } else {
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(query);
            } catch (Exception e) {
                System.out.println("No DB connection - Error in " .getClass().getName());
            }
        }
        disconnectWithDB();
    }

    public static void addScoreToDataBase(String Nickname,double score){
        connectWithDB();
        query = "INSERT INTO bestscorestable VALUES (NULL,\"" + Nickname + "\"," + score + ")";
        if (conn == null) {
            System.out.println("To use statment, first u need to connect to DB!");
        } else {
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(query);
            } catch (Exception e) {
                System.out.println("No DB connection - Error in " .getClass().getName());
            }
        }
        disconnectWithDB();
    }
}
