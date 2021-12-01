package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Event_type_Finder {
    private static final Event_type_Finder INSTANCE = new Event_type_Finder();

    public static Event_type_Finder getInstance() {
        return INSTANCE;
    }

    private Event_type_Finder() {
    }

    public Event_type findById(int id) throws SQLException {
        String sql = "SELECT *" +
                "FROM event_type " +
                "WHERE id = ?;";
        Connection connection = DbContext.getConnection();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1, id);
            try (ResultSet rs = s.executeQuery()){
                if(rs.next()){
                    return load(rs);
                }
            }
        }
        return null;
    }
    public Event_type load(ResultSet rs) throws SQLException {
        Event_type log = new Event_type();

        log.setId(rs.getInt("id"));
        log.setName(rs.getString("name"));

        return log;
    }
}
