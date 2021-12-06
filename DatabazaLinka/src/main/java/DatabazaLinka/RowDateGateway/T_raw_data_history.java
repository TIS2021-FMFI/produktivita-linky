package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class T_raw_data_history {
    private Integer series;
    private Double paletts;
    private Integer shift;
    private Date date;

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Double getPaletts() {
        return paletts;
    }

    public void setPaletts(Double paletts) {
        this.paletts = paletts;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void upsert() throws SQLException {
        String sql =
                "INSERT INTO t_raw_data_history (series,paletts,shift,date)"+
                        "VALUES (?, ?,?,?)" +
                        "ON CONFLICT(Date,Shift,Series) DO UPDATE " +
                        "SET series = ?,paletts = ?,shift = ?, date = ?";

        Connection connection = DbContext.getConnection();

        try(PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,series);
            s.setDouble(2,paletts);
            s.setDate(4,date);
            s.setInt(3,shift);
            s.setInt(5,series);
            s.setDouble(6,paletts);
            s.setDate(8,date);
            s.setInt(7,shift);
            s.executeUpdate();
        }
    }
    @Override
    public String toString() {
        return "T_raw_data_history{" +
                "series=" + series +
                ", paletts=" + paletts +
                ", shift=" + shift +
                ", date=" + date +
                '}';
    }
}
