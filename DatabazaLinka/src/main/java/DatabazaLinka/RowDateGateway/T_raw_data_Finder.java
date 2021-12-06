package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class T_raw_data_Finder {
    private static final T_raw_data_Finder INSTANCE = new T_raw_data_Finder();

    public static T_raw_data_Finder getInstance() {
        return INSTANCE;
    }

    private T_raw_data_Finder() {
    }

    public List<T_raw_data> findByTimestampInterval(Timestamp t1, Timestamp t2) throws SQLException {
        String sql = "SELECT *" +
                "FROM t_raw_data " +
                "WHERE  Time_stamp BETWEEN ? and ?";
        Connection connection = DbContext.getConnection();

        List<T_raw_data> result = new ArrayList<>();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setTimestamp(1, t1);
            s.setTimestamp(2, t2);
            try (ResultSet rs = s.executeQuery()){
                while (rs.next()){
                    result.add(load(rs));
                }
            }
        }
        return result;
    }
    public List<T_raw_data> findAll() throws SQLException {
        String sql = "SELECT *" +
                "FROM t_raw_data; ";
        Connection connection = DbContext.getConnection();

        List<T_raw_data> result = new ArrayList<>();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            try (ResultSet rs = s.executeQuery()){
                while (rs.next()){
                    result.add(load(rs));
                }
            }
        }
        return result;
    }
    public T_raw_data findLast() throws SQLException {
        String sql = "SELECT *" +
                "FROM t_raw_data ORDER BY Time_stamp DESC LIMiT 1; ";
        Connection connection = DbContext.getConnection();
        System.out.println("tu");
        try (PreparedStatement s = connection.prepareStatement(sql)) {
            try (ResultSet rs = s.executeQuery()){
                if (rs.next()){
                    return (load(rs));
                }
            }
        }
        return null;
    }


    public List<T_raw_data> findBySeriesAndShift(int series,int shift) throws SQLException {
        String sql = "SELECT * From t_raw_data Where series = ? and shift = ? and paletts is not null  ORDER BY Time_stamp ";

        Connection connection = DbContext.getConnection();

        List<T_raw_data> result = new ArrayList<>();

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,series);
            s.setInt(2,shift);
            try (ResultSet rs = s.executeQuery()){
                while (rs.next()){
                    result.add(load(rs));
                }
            }
        }
        return result;
    }

    public T_raw_data load(ResultSet rs) throws SQLException {
        T_raw_data log = new T_raw_data();

        log.setTime_stamp(rs.getTimestamp("Time_stamp"));
        log.setSeries(rs.getInt("series"));
        log.setPaletts(rs.getInt("paletts"));
        log.setNext_series(rs.getInt("next_series"));
        log.setPerf_norm_per_h(rs.getInt("perf_norm_per_h"));
        log.setPerf_real_per_h(rs.getInt("perf_real_per_h"));
        log.setPerf_norm_per_min(rs.getInt("perf_norm_per_min"));
        log.setPerf_real_per_min(rs.getInt("perf_real_per_min"));

        return log;
    }
}
