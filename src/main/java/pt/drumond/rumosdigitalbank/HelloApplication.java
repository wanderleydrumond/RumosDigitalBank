package pt.drumond.rumosdigitalbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.controller.Bank;

import java.io.IOException;

public class HelloApplication extends Application {
    static Bank bank;

    public static void main(String[] args) {
        bank = new Bank();
        bank.initialMenu();
    }

    public void startSelectedApp(int option) {
        switch (option) {
            case 1 -> launch();
            case 2 -> bank.startAppManagement();
            default -> System.exit(0);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}