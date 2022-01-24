package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Series_Finder {
    private static final Series_Finder INSTANCE = new Series_Finder();

    public static Series_Finder getInstance() {
        return INSTANCE;
    }

    private Series_Finder() {
    }
    public List<Series> findAll() throws SQLException {
        String sql = "SELECT *" +
                "FROM Series ";
        Connection connection = DbContext.getConnection();

        List<Series> result = new ArrayList<>();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            try (ResultSet rs = s.executeQuery()){
                while (rs.next()){
                    result.add(load(rs));
                }
            }
        }//eventu jednou farbou
        return result;
    }
    public Series findById(int id) throws SQLException {
        String sql = "SELECT *" +
                "FROM Series WHERE \"id\" = ?";
        Connection connection = DbContext.getConnection();

        List<Series> result = new ArrayList<>();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,id);
            try (ResultSet rs = s.executeQuery()){
                if (rs.next()){
                    return load(rs);
                }
            }
        }
        return null;
    }
    public Series load(ResultSet rs) throws SQLException {
        Series log = new Series();
        log.setId(rs.getInt("id"));
        log.setName(rs.getString("name"));
        log.setWorth(rs.getDouble("worth"));
        return log;
    }
}
