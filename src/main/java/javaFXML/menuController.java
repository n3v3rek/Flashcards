package javaFXML;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.App;
import java.net.URL;
import java.util.ResourceBundle;

public class menuController implements Initializable,viewChanger{

    @FXML
    public BorderPane menuPane;
    public Label presentNickname;
    public TextField newNickname;
    public Label nickname_1,nickname_2,nickname_3,nickname_4,nickname_5;
    public Label score_1,score_2,score_3,score_4,score_5;

    @Override
    public void changePane(String FXMLFileName) {
        MyLoader loader = new MyLoader();
        Pane view = loader.getNextStage(FXMLFileName);
        menuPane.setCenter(view);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        presentNickname.setText(App.getUserNickname());
        App.scoreTableUpdates();
        updateScoreTableGUI();
    }

    public void handleButton1Action() {
        changePane("gameForOne");
    }

    public void handleButton2Action() {
        changePane("prepOfGameForMulti");
    }

    public void handleButton3Action() {
        changePane("edition");
    }

    public void handleButton4Action() {
        System.exit(0);
    }

    public void handleButton5Action(){
        App.setUserNickname(newNickname.getText());
        presentNickname.setText(App.getUserNickname());
        newNickname.setText("");
    }

    public void updateScoreTableGUI()
    {
        try {
            nickname_1.setText(App.scores.getNickname(1));
            nickname_2.setText(App.scores.getNickname(2));
            nickname_3.setText(App.scores.getNickname(3));
            nickname_4.setText(App.scores.getNickname(4));
            nickname_5.setText(App.scores.getNickname(5));

            score_1.setText(App.scores.getScore(1));
            score_2.setText(App.scores.getScore(2));
            score_3.setText(App.scores.getScore(3));
            score_4.setText(App.scores.getScore(4));
            score_5.setText(App.scores.getScore(5));
        }catch (Exception e){
            String massage = "No DB conn";

            nickname_1.setText(massage);
            nickname_2.setText(massage);
            nickname_3.setText(massage);
            nickname_4.setText(massage);
            nickname_5.setText(massage);

            score_1.setText(massage);
            score_2.setText(massage);
            score_3.setText(massage);
            score_4.setText(massage);
            score_5.setText(massage);
        }
    }

}
