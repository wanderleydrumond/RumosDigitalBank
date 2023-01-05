module pt.drumond.rumosdigitalbank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    exports pt.drumond.rumosdigitalbank;
    exports pt.drumond.rumosdigitalbank.model; // Necessário para exibir os registros da tabela

    opens pt.drumond.rumosdigitalbank to javafx.fxml;
    opens pt.drumond.rumosdigitalbank.model to javafx.fxml; // Necessário para exibir os registros da tabela
}