package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Event_type_Finder {
    private static final Event_type_Finder INSTANCE = new Event_type_Finder();

    public static Event_type_Finder getInstance() {
        return INSTANCE;
    }

    private Event_type_Finder() {
    }

    public Event_type findById(int id) throws SQLException {
        String sql = "SELECT * " +
                "FROM event_types " +
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

    public List<Event_type> findAll() throws SQLException {
        String sql = "SELECT * " +
                "FROM event_types";
        Connection connection = DbContext.getConnection();

        List<Event_type> result = new ArrayList<>();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            try (ResultSet rs = s.executeQuery()){
                while (rs.next()){
                    result.add(load(rs));
                }
            }
        }
        return result;
    }

    public Event_type load(ResultSet rs) throws SQLException {
        Event_type log = new Event_type();

        log.setId(rs.getInt("id"));
        log.setName(rs.getString("name"));

        return log;
    }
}
