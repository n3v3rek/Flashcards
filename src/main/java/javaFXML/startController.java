package javaFXML;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.App;


import java.net.URL;
import java.util.ResourceBundle;

public class startController implements Initializable,viewChanger {

    //@FXML
    public BorderPane startPane;
    public TextField nickname;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void changePane(String FXMLFileName){
        MyLoader loader = new MyLoader();
        Pane view = loader.getNextStage(FXMLFileName);
        startPane.setCenter(view);
    }

    public void handleButtonAction() {
        System.exit(0);
    }

    public void handleButton2Action(){
        if (nickname.getText().equals("")) {
            App.setUserNickname("Guest");
        } else {
            App.setUserNickname(nickname.getText());
        }
        changePane("menu");
    }

    public void handleButton3Action(){
        App.setUserNickname("Guest");
        changePane("menu"); }

}
