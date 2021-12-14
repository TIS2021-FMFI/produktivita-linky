package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<Integer,T_raw_data_history> findByShiftandDateAll(int shift,Date date) throws SQLException {
        Daily_statistics result = new Daily_statistics();
        result.setShift(shift);

        List<T_raw_data> all = T_raw_data_Finder.getInstance().findByShiftDateAll(shift,date);
        Map<Integer,T_raw_data_history> number = new HashMap<>();

        for (T_raw_data s: T_raw_data_Finder.getInstance().findUniqueSeries(shift,date)){
            T_raw_data_history vloz = new T_raw_data_history();
            vloz.setShift(shift);
            vloz.setPaletts(0D);
            vloz.setSeries(s.getSeries());
            vloz.setDate(date);
            number.put(s.getSeries(), vloz);
        }
        int size = all.size();
        double add = 0;
        int i = 0;

        if (size == 0){
            return number;
        }

        Integer seria = all.get(0).getSeries();

        while (i+1 < size){
            if (all.get(i).getPaletts() == 0){
                seria = all.get(i).getSeries();
            }
            if (all.get(i).getPaletts() > all.get(i+1).getPaletts()){
                System.out.println(all.get(i));
                number.get(seria).setPaletts(number.get(seria).getPaletts()+all.get(i).getPaletts());
            }

            i++;}
        number.get(seria).setPaletts(number.get(seria).getPaletts()+all.get(i).getPaletts());

        for (T_raw_data_history log:number.values()){
            if (log.getPaletts() != 0) {
                log.upsert();
            }
        }
     return number;
    }
    public Daily_statistics load(ResultSet rs) throws SQLException {
        Daily_statistics log = new Daily_statistics();

        log.setPallets(rs.getDouble("paletts"));
        log.setShift(rs.getInt("shift"));
        return log;
    }
}
