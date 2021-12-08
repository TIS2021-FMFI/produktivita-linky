package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.*;
import java.util.List;

public class Daily_statistics_Finder {
    private static final Daily_statistics_Finder INSTANCE = new Daily_statistics_Finder();

    public static Daily_statistics_Finder getInstance() {
        return INSTANCE;
    }

    private Daily_statistics_Finder() {
    }
    //tato funkcia popocita kolko z jednotlivých modelov sa vyrobilo, a upsertuje to  do history
    public Daily_statistics findByShiftAndSeriesandDate(int shift,int series,Date date) throws SQLException {
        Daily_statistics result = new Daily_statistics();
        result.setShift(shift);

        List<T_raw_data> all = T_raw_data_Finder.getInstance().findBySeriesAndShiftandDate(series,shift,date);
        if (all.size() == 0){
            result.setPallets(0D);
            System.out.println("tu");
            return result;
        }
        int size = all.size();
        double add = 0;
        int i = 0;
        while (i+1 < size){
            if (all.get(i).getPaletts() > all.get(i+1).getPaletts()){
                add+=all.get(i).getPaletts();
            }
            i++;}
        add+=all.get(i).getPaletts();
        result.setPallets(add);

        T_raw_data_history vloz = new T_raw_data_history();
        vloz.setShift(shift);
        vloz.setPaletts(add);
        vloz.setSeries(series);
        vloz.setDate(date);
        vloz.upsert();
        return result;
    }
    public Daily_statistics load(ResultSet rs) throws SQLException {
        Daily_statistics log = new Daily_statistics();

        log.setPallets(rs.getDouble("paletts"));
        log.setShift(rs.getInt("shift"));
        return log;
    }
}
