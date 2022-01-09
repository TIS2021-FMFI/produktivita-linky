package DatabazaLinka;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import DatabazaLinka.RowDateGateway.Events;
import DatabazaLinka.RowDateGateway.Events_Finder;
import DatabazaLinka.TransactionScript.Vynimka;

import DatabazaLinka.UserInterface.LoginMenu;
import DatabazaLinka.UserInterface.LoginMenu;
import DatabazaLinka.UserInterface.HlavneMenu;
import javafx.application.Application;
import javafx.stage.Stage;
import org.postgresql.ds.PGSimpleDataSource;

public class DatabazaLinka extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName("213.160.162.7");
        ds.setPortNumber(5432);
        ds.setDatabaseName("rps");
        ds.setUser("rps_application");
        ds.setPassword("sz!c6J1P}l@@+w");

        try (Connection connection = ds.getConnection()) {
            DbContext.setConnection(connection);


            HlavneMenu menu = new HlavneMenu();
            menu.start(primaryStage);
        } finally {
            //DbContext.clear();
        }
    }
    public static void main(String[] args) throws SQLException, IOException, Vynimka {
        launch(args);
    }
}