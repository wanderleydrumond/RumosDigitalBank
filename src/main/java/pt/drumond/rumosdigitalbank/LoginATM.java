package pt.drumond.rumosdigitalbank;

import javafx.application.Application;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.controller.Bank;
import pt.drumond.rumosdigitalbank.javafx.LoginController;

import java.io.IOException;

public class LoginATM extends Application {
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
        /*FXMLLoader fxmlLoader = new FXMLLoader(LoginATM.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rumos Digital Bank ATM");
        stage.setScene(scene);
        stage.show();*/
        new LoginController().setStage(stage);
    }
}