package DatabazaLinka;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import DatabazaLinka.TransactionScript.Vynimka;

import DatabazaLinka.UserInterface.LoginMenu;
import DatabazaLinka.UserInterface.LoginMenu;
import DatabazaLinka.UserInterface.HlavneMenu;
import org.postgresql.ds.PGSimpleDataSource;

public class DatabazaLinka {
    public static void main(String[] args) throws SQLException, IOException, Vynimka {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName("db.dai.fmph.uniba.sk");
        ds.setPortNumber(5432);
        ds.setDatabaseName("playground");
        ds.setUser("zaikner1@uniba.sk");
        ds.setPassword("databaza");

        try (Connection connection = ds.getConnection()) {
            DbContext.setConnection(connection);
            HlavneMenu menu = new HlavneMenu();
            menu.start();
        } finally {
            DbContext.clear();
        }
    }
}
