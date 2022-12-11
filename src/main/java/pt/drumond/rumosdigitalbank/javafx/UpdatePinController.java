package pt.drumond.rumosdigitalbank.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdatePinController {
    Stage stage;

    public UpdatePinController() {
        stage = new Stage();
    }

    public void setStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdatePinController.class.getResource("update-pin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Update PIN");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }



    public void login(ActionEvent actionEvent) { //TODO remove unused argument
        System.out.println("PIN atualizado"); //TODO to be deleted
    }
}