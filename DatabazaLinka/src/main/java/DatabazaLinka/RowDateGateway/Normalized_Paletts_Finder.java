package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Normalized_Paletts_Finder {
    private static final Normalized_Paletts_Finder INSTANCE = new Normalized_Paletts_Finder();

    public static Normalized_Paletts_Finder getInstance() {
        return INSTANCE;
    }

    private Normalized_Paletts_Finder() {
    }
    public List<Normalized_Paletts> findBySeriesNormalized(int series) throws SQLException {

        String sql = "SELECT t.series, t.paletts*s.worth as paletts, s.name  "+
                "FROM t_raw_data as t INNER JOIN series s on s.id = t.series" +
                " where series = ? and t.paletts is not null ORDER BY t.timestamp;";

        Connection connection = DbContext.getConnection();

        List<Normalized_Paletts> result = new ArrayList<>();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,series);

            try (ResultSet rs = s.executeQuery()){
                while (rs.next()){
                    result.add(load(rs));
                }
            }
        }
        return result;
    }
    public Normalized_Paletts findByDateSeriesShiftNormalized(Date date, int series, int shift) throws SQLException {
        String sql = "SELECT series, t.paletts*s.worth as paletts, s.name "+
                "FROM t_raw_data_history as t INNER JOIN series s on s.id = t.series" +
                " where series = ? and t.shift = ? and t.date = ? and t.paletts is not null ;";

        Connection connection = DbContext.getConnection();


        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,series);
            s.setInt(2,shift);
            s.setDate(3,date);

            try (ResultSet rs = s.executeQuery()){
                if (rs.next()){
                    return load(rs);
                }
            }
        }
        Normalized_Paletts result = new Normalized_Paletts();
        result.setPaletts(0D);
        result.setSeries(series);
        return result;
    }
    public Normalized_Paletts findByDateShiftNormalizedALl(Date date, int shift) throws SQLException {
        String sql = "SELECT null as series,sum(t.paletts*s.worth)  as paletts, '' as name "+
                "FROM t_raw_data_history as t INNER JOIN series  as s on s.id = t.series" +
                " where  t.shift = ? and t.date = ? and t.paletts is not null ;";

        Connection connection = DbContext.getConnection();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,shift);
            s.setDate(2,date);
            try (ResultSet rs = s.executeQuery()){
                if (rs.next()){
                    return load(rs);
                }
            }
        }
        return null;
    }

    public Normalized_Paletts load(ResultSet rs) throws SQLException {
        Normalized_Paletts log = new Normalized_Paletts();
        log.setSeries(rs.getInt("series"));
        log.setPaletts(rs.getDouble("paletts"));
        log.setName(rs.getString("name"));
        return log;
    }
}
