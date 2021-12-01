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
                "WHERE  timestamp BETWEEN ? and ?";
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
    public T_raw_data load(ResultSet rs) throws SQLException {
        T_raw_data log = new T_raw_data();

        log.setTimestamp(rs.getTimestamp("timestamp"));
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
