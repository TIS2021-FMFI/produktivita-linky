package DatabazaLinka.RowDateGateway;

import java.sql.ResultSet;
import java.sql.SQLException;

public class T_raw_data_history_Finder {
    private static final T_raw_data_history_Finder INSTANCE = new T_raw_data_history_Finder();

    public static T_raw_data_history_Finder getInstance() {
        return INSTANCE;
    }

    private T_raw_data_history_Finder() {
    }
    public T_raw_data_history load(ResultSet rs) throws SQLException {
        T_raw_data_history log = new T_raw_data_history();

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
