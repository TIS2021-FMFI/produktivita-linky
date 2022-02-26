package DatabazaLinka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import DatabazaLinka.RowDateGateway.Events;
import DatabazaLinka.RowDateGateway.Events_Finder;
import DatabazaLinka.TransactionScript.Vynimka;

import DatabazaLinka.UserInterface.LoginMenu;
import DatabazaLinka.UserInterface.LoginMenu;
import DatabazaLinka.UserInterface.HlavneMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;

import javax.swing.*;

public class DatabazaLinka extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        File configFile = new File("src/main/resources/db.properties");

        PGSimpleDataSource ds = new PGSimpleDataSource();

        try {
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
            throw ex;
        } catch (IOException ex) {
            // I/O error
            throw ex;
        }

        try {
            Connection connection = ds.getConnection();
            DbContext.setConnection(connection);
            HlavneMenu menu = new HlavneMenu();
            menu.start(primaryStage);
        }catch(PSQLException ex){//error ak nejde databaza
            JOptionPane.showMessageDialog(null, "Database error",
                        "Error", JOptionPane.ERROR_MESSAGE);
            Platform.exit();
        }catch (Exception exx){//iny error
            JOptionPane.showMessageDialog(null, "Unknown error error",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw exx;
        }
    }
    public static void main(String[] args) throws SQLException, IOException, Vynimka {
        launch(args);
    }
}