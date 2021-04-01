package javaFXML;

import javaJDBC.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.App;
import questions.Flashcard;

import java.net.URL;
import java.util.ResourceBundle;

public class editController implements Initializable,viewChanger {

    public static ObservableList<Flashcard> ComboBoxOfFlashcards = FXCollections.observableArrayList();
    public static Flashcard toBeDeleted;
    public static int indexToBeDeleted;

    @FXML
    public TextField dodawanie_1;
    public TextField dodawanie_2;
    public BorderPane edition_pane;
    public Label efektOperacji;
    public ComboBox<Flashcard> usuwanieFiszek;
    public Label usuwanie;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareObservableList();
        prepareComboBoxDelete();
    }

    public void handleButtonAction() {
        System.exit(0);
    }

    public void handleButton2Action(){
        changePane("menu");
    }

    public void handleButton3Action(){
        try {
            JDBC.addNewFlashcard(dodawanie_1.getText(), dodawanie_2.getText());
            ComboBoxOfFlashcards.add(new Flashcard(dodawanie_2.getText(),dodawanie_1.getText()));
            dodawanie_1.setText("");
            dodawanie_2.setText("");
            efektOperacji.setText("Pomyślnie dodano fiszkę.");
            if (ComboBoxOfFlashcards.size() == 1){
                usuwanieFiszek.getSelectionModel().select(0);
            }
            App.refreshDB();

        }catch (Exception e) {
            efektOperacji.setText("Error - Connection with DB is not working.");
        }
    }

    public void handleButton4Action(){
        try {
            javaJDBC.JDBC.deleteFlashcard(ComboBoxOfFlashcards.get(indexToBeDeleted));
            ComboBoxOfFlashcards.remove(indexToBeDeleted);
            efektOperacji.setText("Pomyślnie usunięto fiszkę.");

            if (indexToBeDeleted == 0 && ComboBoxOfFlashcards.size() > 0) {
                usuwanieFiszek.getSelectionModel().select(0);
            }
            else{
                usuwanieFiszek.getSelectionModel().select(indexToBeDeleted );
            }

            App.refreshDB();

        }catch (Exception e){
            efektOperacji.setText("Error - Nie można usunąć z pustej bazy.");
        }

    }

    public EventHandler<ActionEvent> event_1 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                usuwanie.setText(usuwanieFiszek.getValue().getWordInPolish() + " - " + usuwanieFiszek.getValue().getWordInEnglish());
                indexToBeDeleted = ComboBoxOfFlashcards.indexOf(usuwanieFiszek.getValue());

            } catch (Exception e){
                usuwanie.setText("Baza jest pusta.");
            }
        }
    };

    @Override
    public void changePane(String FXMLFileName)
    {
        MyLoader loader = new MyLoader();
        Pane view = loader.getNextStage(FXMLFileName);
        edition_pane.setCenter(view);
    }

    public void prepareObservableList()
    {
        if(ComboBoxOfFlashcards.size()== 0){
            ComboBoxOfFlashcards.addAll(JDBC.getFlashcards());
        }
    }

    public void prepareComboBoxDelete(){
        usuwanieFiszek.setItems(ComboBoxOfFlashcards);
        usuwanieFiszek.getSelectionModel().select(0);
        usuwanie.setText(ComboBoxOfFlashcards.get(0).getWordInPolish() + " - " +ComboBoxOfFlashcards.get(0).getWordInEnglish());
        usuwanieFiszek.setOnAction(event_1);
    }


}
