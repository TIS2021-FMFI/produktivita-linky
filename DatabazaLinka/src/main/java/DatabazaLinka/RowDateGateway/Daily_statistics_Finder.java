package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.*;

public class Daily_statistics_Finder {
    private static final Daily_statistics_Finder INSTANCE = new Daily_statistics_Finder();

    public static Daily_statistics_Finder getInstance() {
        return INSTANCE;
    }

    private Daily_statistics_Finder() {
    }
    public Daily_statistics findByDateAndShift(Date date,int shift) throws SQLException {
        String sql = "SELECT sum(paletts * worth) as paletts, Cast(timestamp as date) as date,shift "+
        "from t_raw_data as t join series as s  on s.id = t.series "+
        "where shift = ? and  Cast(timestamp as date) = ? " +
                " group by Cast(timestamp as date),shift";
        Connection connection = DbContext.getConnection();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1, shift);
            s.setDate(2,date);
            try (ResultSet rs = s.executeQuery()){
                if(rs.next()){
                    return load(rs);
                }
            }
        }
        return null;
    }
    public Daily_statistics load(ResultSet rs) throws SQLException {
        Daily_statistics log = new Daily_statistics();

        log.setDate(rs.getDate("date"));
        log.setPallets(rs.getDouble("paletts"));
        log.setShift(rs.getInt("shift"));
        return log;
    }
}
