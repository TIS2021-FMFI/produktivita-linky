package DatabazaLinka;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
        ds.setServerName("db.dai.fmph.uniba.sk");
        ds.setPortNumber(5432);
        ds.setDatabaseName("playground");
        ds.setUser("zaikner1@uniba.sk");
        ds.setPassword("databaza");

        try (Connection connection = ds.getConnection()) {
            DbContext.setConnection(connection);
            HlavneMenu menu = new HlavneMenu();
            menu.start(primaryStage);
        } finally {
            DbContext.clear();
        }
    }
    public static void main(String[] args) throws SQLException, IOException, Vynimka {
        launch(args);
    }
}
