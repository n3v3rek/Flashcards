package javaFXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import main.App;

import java.net.URL;


public class MyLoader {

    public Pane getNextStage(String nameOfFile){

        Pane view = new Pane();

        try {
            URL fileUrl = App.class.getResource("/" +nameOfFile + ".fxml");
            if (fileUrl == null){
                throw new java.io.FileNotFoundException("FMXL file can't be found");
            }
            view = new FXMLLoader().load(fileUrl);
        }catch (Exception e){
            System.out.println("There is no stage named " + nameOfFile);
        }
        return view;
    }


}

