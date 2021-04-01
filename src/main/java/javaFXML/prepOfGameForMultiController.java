package javaFXML;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.App;


import java.net.URL;
import java.util.ResourceBundle;

public class prepOfGameForMultiController implements Initializable,viewChanger {

    @FXML
    public BorderPane prepOfGameForMulti;
    public Button MenuButton;
    public Button GameForManyButton;
    public RadioButton players2;
    public RadioButton players3;
    public RadioButton players4;
    public TextField player1;
    public TextField player2;
    public TextField player3;
    public TextField player4;

    public ToggleGroup numberOfPlayers = new ToggleGroup();

    @Override
    public void changePane(String FXMLFileName) {
        MyLoader loader = new MyLoader();
        Pane view = loader.getNextStage(FXMLFileName);
        prepOfGameForMulti.setCenter(view);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        players2.setToggleGroup(numberOfPlayers);
        players3.setToggleGroup(numberOfPlayers);
        players4.setToggleGroup(numberOfPlayers);

        player1.setDisable(true);
        player2.setDisable(true);
        player3.setDisable(true);
        player4.setDisable(true);
        GameForManyButton.setDisable(true);
    }

    public void handleButton2Action() {
        changePane("menu");
    }

    public void handleButtonAction() {

        getNumberOfPlayersFromRadioButton();

        setNicknamesForUser();

        clearScoresAndCreateThem();

        changePane("gameForMulti");
    }

    public void handleAction1(){

        deleteRedundantDisabledControllers();
        player3.setDisable(true);
        player4.setDisable(true);

    }
    public void handleAction2(){

        deleteRedundantDisabledControllers();
        player3.setDisable(false);
        player4.setDisable(true);

    }
    public void handleAction3(){

        deleteRedundantDisabledControllers();
        player3.setDisable(false);
        player4.setDisable(false);

    }

    public void deleteRedundantDisabledControllers(){

        player1.setDisable(false);
        player2.setDisable(false);
        GameForManyButton.setDisable(false);

    }

    public void getNumberOfPlayersFromRadioButton(){
        if (numberOfPlayers.getSelectedToggle().equals(players2)){
            App.setNumberOfPlayers(2);
        } else if  (numberOfPlayers.getSelectedToggle().equals(players3)){
            App.setNumberOfPlayers(3);
        } else if (numberOfPlayers.getSelectedToggle().equals(players4)){
            App.setNumberOfPlayers(4);
        }

    }

    public void setNicknamesForUser(){

        App.addUsersNickname(player1.getText());
        App.addUsersNickname(player2.getText());

        if (App.getNumberOfPlayers() == 3)
            App.addUsersNickname(player3.getText());

        if (App.getNumberOfPlayers() == 4)
            App.addUsersNickname(player3.getText());
            App.addUsersNickname(player4.getText());
    }

    public void clearScoresAndCreateThem(){
        App.clearUsersScores();

        for (int i = 0;i <App.getNumberOfPlayers();i++){
            App.addScoreToUsersScores(0.00);
        }

    }

}
