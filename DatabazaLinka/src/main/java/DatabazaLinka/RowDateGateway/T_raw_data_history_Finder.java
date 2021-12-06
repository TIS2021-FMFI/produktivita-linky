package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class T_raw_data_history_Finder {
    private static final T_raw_data_history_Finder INSTANCE = new T_raw_data_history_Finder();

    public static T_raw_data_history_Finder getInstance() {
        return INSTANCE;
    }

    private T_raw_data_history_Finder() {
    }
    public T_raw_data_history findByDateSeriesAndShift(Date date, int series, int shift) throws SQLException {
        String sql = "SELECT * From t_raw_data_history Where series = ? and shift = ? and date = ?";

        Connection connection = DbContext.getConnection();

       T_raw_data_history result = new T_raw_data_history();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,series);
            s.setInt(2,shift);
            s.setDate(3,date);
            try (ResultSet rs = s.executeQuery()){
                if(rs.next()){
                    return load(rs);
                }
            }
        }
        result.setDate(date);
        result.setShift(shift);
        result.setSeries(series);
        result.setPaletts(0D);

        return result;
    }
    public T_raw_data_history load(ResultSet rs) throws SQLException {
        T_raw_data_history log = new T_raw_data_history();

        log.setDate(rs.getDate("date"));
        log.setSeries(rs.getInt("series"));
        log.setPaletts(rs.getDouble("paletts"));
        log.setShift(rs.getInt("shift"));

        return log;
    }
}
