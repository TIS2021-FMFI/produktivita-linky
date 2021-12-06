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
//        ds.setServerName("213.160.162.7");
//        ds.setPortNumber(5432);
//        ds.setDatabaseName("rps");
//        ds.setUser("rps_testing");
//        ds.setPassword("ex!z?G@oR3l");

        try (Connection connection = ds.getConnection()) {
            DbContext.setConnection(connection);
            HlavneMenu menu = new HlavneMenu();
            menu.start();
        } finally {
            DbContext.clear();
        }
    }
}
