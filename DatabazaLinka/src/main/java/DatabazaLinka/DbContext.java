package DatabazaLinka;

import java.sql.Connection;

public class DbContext {

    private static Connection connection;

    public static void setConnection(Connection connection) {
        if (connection == null) {
            throw new NullPointerException("Nemozes mat prazdne spojenie");
        }
        DbContext.connection = connection;
    }

    public static Connection getConnection() {
        if (connection == null) {
            throw new IllegalStateException("Spojenie zatial nebolo nadviazane");
        }
        return connection;
    }

    public static void clear() {
        connection = null;
    }
}
