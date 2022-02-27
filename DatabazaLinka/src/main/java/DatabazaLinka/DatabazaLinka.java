package DatabazaLinka;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import DatabazaLinka.TransactionScript.Vynimka;

import DatabazaLinka.UserInterface.HlavneMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;

import javax.swing.*;

public class DatabazaLinka extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        PGSimpleDataSource ds = new PGSimpleDataSource();
        try {
            File configFile = new File("src/main/resources/db.properties");

            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            ds.setServerName(props.getProperty("name"));
            ds.setPortNumber(Integer.parseInt(props.getProperty("port")));
            ds.setDatabaseName(props.getProperty("dname"));
            ds.setUser(props.getProperty("user"));
            ds.setPassword(props.getProperty("password"));
            reader.close();
        } catch (FileNotFoundException ex) {
            // file does not exist
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("File not found");
            alert.setTitle("Error");
            alert.showAndWait();

            ex.printStackTrace();

            wait(5000);

            Platform.exit();
        } catch (IOException ex) {
            // I/O error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Unknown error");
            alert.setTitle("Error");
            alert.showAndWait();

            ex.printStackTrace();

            wait(5000);

            Platform.exit();
        }

        try {
            Connection connection = ds.getConnection();
            DbContext.setConnection(connection);
            HlavneMenu menu = new HlavneMenu();

            menu.start(primaryStage);
        }catch(PSQLException ex){//error ak nejde databaza
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Database error");
            alert.setTitle("Error");
            alert.showAndWait();

            ex.printStackTrace();

            wait(5000);

            Platform.exit();
        }catch (Exception exx){//iny error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Unknown error");
            alert.setTitle("Error");
            alert.showAndWait();

            exx.printStackTrace();

            wait(5000);

            Platform.exit();
        }
    }
    public static void main(String[] args) throws SQLException, IOException, Vynimka {
        launch(args);
    }
}