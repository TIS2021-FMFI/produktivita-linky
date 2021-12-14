package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Events {
    private Integer id;
    private Integer id_event;
    private Timestamp timestamp_begin;
    private Timestamp timestamp_end;
    private Double duration;
    private Double potencionaly_washed_pallets;

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_event() {
        return id_event;
    }

    public void setId_event(Integer id_event) {
        this.id_event = id_event;
    }

    public Timestamp getTimestamp_begin() {
        return timestamp_begin;
    }

    public void setTimestamp_begin(Timestamp timestamp_begin) {
        this.timestamp_begin = timestamp_begin;
    }

    public Timestamp getTimestamp_end() {
        return timestamp_end;
    }

    public void setTimestamp_end(Timestamp timestamp_end) {
        this.timestamp_end = timestamp_end;
    }

    public double getPotencionaly_washed_pallets() {
        return potencionaly_washed_pallets;
    }

    public void setPotencionaly_washed_pallets(Double potencionaly_washed_pallets) {
        this.potencionaly_washed_pallets = potencionaly_washed_pallets;
    }
    public void insert() throws SQLException {
        String sql = "INSERT INTO events (id,id_event,timestamp_begin,timestamp_end,potencionaly_washed_pallets,duration)"+
                "VALUES (?, ?,?,?,?,?)";

        Connection connection = DbContext.getConnection();

        try(PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,id);
            s.setInt(2,id_event);
            s.setTimestamp(3,timestamp_begin);
            s.setTimestamp(4,timestamp_end);
            s.setDouble(5,potencionaly_washed_pallets);
            s.setDouble(6,duration);
            s.executeUpdate();
        }
    }
    public void update() throws SQLException{
        String sql = "UPDATE events " +
                "SET potencionaly_washed_pallets = ?, timestamp_begin = ?," +
                " timestamp_end = ?, id_event = ?,duration = ?  " +
                "WHERE id = ?";
        Connection connection = DbContext.getConnection();

        try(PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(6,id);
            s.setDouble(5,duration);
            s.setInt(4,id_event);
            s.setTimestamp(2,timestamp_begin);
            s.setTimestamp(3,timestamp_end);
            s.setDouble(1,potencionaly_washed_pallets);
            s.executeUpdate();
        }
    }

    @Override
    public String toString() {
        return "Events{" +
                "id=" + id +
                ", id_event=" + id_event +
                ", timestamp_begin=" + timestamp_begin +
                ", timestamp_end=" + timestamp_end +
                ", duration=" + duration +
                ", potencionaly_washed_pallets=" + potencionaly_washed_pallets +
                '}';
    }
}
