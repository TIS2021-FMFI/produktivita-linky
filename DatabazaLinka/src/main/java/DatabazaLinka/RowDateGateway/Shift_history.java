package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Shift_history {
    private Integer id;
    private Timestamp timestamp_begin;
    private Timestamp timestamp_end;
    private Integer goal;
    private Integer shift;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    @Override
    public String toString() {
        return "Shift_history{" +
                "id=" + id +
                ", timestamp_begin=" + timestamp_begin +
                ", timestamp_end=" + timestamp_end +
                ", goal=" + goal +
                ", shift=" + shift +
                '}';
    }
    public void insert() throws SQLException {
        String sql =
                "INSERT INTO shift_history (id,timestamp_begin,timestamp_end,goal,shift)"+
                "VALUES (?, ?,?,?,?)";

        Connection connection = DbContext.getConnection();

        try(PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,id);
            s.setTimestamp(2,timestamp_begin);
            s.setTimestamp(3,timestamp_end);
            s.setInt(4,goal);
            s.setInt(5,shift);
            s.executeUpdate();
        }
    }
    public void update() throws SQLException{
        String sql = "UPDATE shift_history " +
                "SET shift = ?, timestamp_begin = ?," +
                " timestamp_end = ?, goal = ?,  " +
                   "WHERE id = ?";
        Connection connection = DbContext.getConnection();

        try(PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(5,id);
            s.setTimestamp(2,timestamp_begin);
            s.setTimestamp(3,timestamp_end);
            s.setInt(4,goal);
            s.setInt(1,shift);
            s.executeUpdate();
        }
    }

}
