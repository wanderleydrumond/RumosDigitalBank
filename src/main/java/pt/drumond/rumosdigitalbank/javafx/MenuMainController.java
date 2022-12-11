package pt.drumond.rumosdigitalbank.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuMainController {
    @FXML
    private AnchorPane anchorPaneWelcome;
    private Stage stage;
    public void setStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuMainController.class.getResource("menu-main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Update PIN");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void logout(ActionEvent actionEvent) {
        getStageInstance();
        System.out.println("Logged out"); //TODO to be deleted
        System.out.println(actionEvent.getEventType()); // TODO to be deleted
        stage.close();
    }

    private void getStageInstance() { //TODO este método deve ser implementado em um único lugar
        stage = (Stage) anchorPaneWelcome.getScene().getWindow();
    }
}