package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Shift_history_Finder {
    private static final Shift_history_Finder INSTANCE = new Shift_history_Finder();

    public static Shift_history_Finder getInstance() {
        return INSTANCE;
    }

    private Shift_history_Finder() {
    }

}
