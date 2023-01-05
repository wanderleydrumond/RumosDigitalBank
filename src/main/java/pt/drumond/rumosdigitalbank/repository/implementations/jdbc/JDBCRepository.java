package pt.drumond.rumosdigitalbank.repository.implementations.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JDBCRepository {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private final String SCHEMA = "rumos_digital_bank",
                         DATABASE = "mysql",
                         HOST = "localhost",
                         PORT = "3306",
                         URL = "jdbc:"+ DATABASE + "://" + HOST + ":" + PORT + "/" + SCHEMA,
                         USER = "root",
                         PASSWORD = "admin";

    protected void openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    protected void closeConnection() throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}