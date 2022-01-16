package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Events_Finder {
    private static final Events_Finder INSTANCE = new Events_Finder();

    public static Events_Finder getInstance() {
        return INSTANCE;
    }

    private Events_Finder() {
    }
    public List<Events> findByTimestampInterval(Timestamp t1, Timestamp t2) throws SQLException {
        String sql = "SELECT *" +
                "FROM events " +
                "WHERE  timestamp_begin BETWEEN ? and ? or " +
                "timestamp_end BETWEEN ? and ?";
        Connection connection = DbContext.getConnection();

        List<Events> result = new ArrayList<>();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setTimestamp(1, t1);
            s.setTimestamp(2, t2);
            s.setTimestamp(3, t1);
            s.setTimestamp(4, t2);
            try (ResultSet rs = s.executeQuery()){
                while (rs.next()){
                    result.add(load(rs));
                }
            }
        }
        return result;
    }

    public List<Events> findByDateAndType(int type, Date date) throws SQLException {
        String sql = "SELECT *" +
                "FROM events " +
                "WHERE  Cast(\"timestamp_begin\" as date) = ? AND \"id_event\" = ?";
        Connection connection = DbContext.getConnection();

        List<Events> result = new ArrayList<>();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setDate(1, date);
            s.setInt(2, type);

            try (ResultSet rs = s.executeQuery()){
                while (rs.next()){
                    result.add(load(rs));
                }
            }
        }
        return result;
    }

    public Events load(ResultSet rs) throws SQLException {
        Events log = new Events();

        log.setId(rs.getInt("id"));
        log.setId_event(rs.getInt("id_event"));
        log.setTimestamp_begin(rs.getTimestamp("timestamp_begin"));
        log.setTimestamp_end(rs.getTimestamp("timestamp_end"));
        log.setPotencionaly_washed_pallets(rs.getDouble("potencionaly_washed_pallets"));
        log.setDuration(rs.getDouble("duration"));
        return log;
    }
}
