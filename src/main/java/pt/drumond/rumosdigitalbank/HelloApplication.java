package pt.drumond.rumosdigitalbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.app.Bank;

import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        System.out.print("""
                ╭══════════════════════$═══╮
                     RUMOS DIGITAL BANK
                ╰═══€══════════════════════╯
                Choose your option:
                0. Quit
                1. ATM
                2. Management
                                
                Option:\040""");

        switch (Integer.parseInt(scanner.nextLine())) {
            case 1 -> launch();
            case 2 -> bank.run(scanner, bank);
            default -> {
                scanner.close();
                System.exit(0);
            }
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