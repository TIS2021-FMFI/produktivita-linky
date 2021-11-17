package DatabazaLinka.RowDateGateway;

import java.sql.Date;

public class Daily_statistics {
    private Date date;
    private Integer shift;
    private Double pallets;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public Double getPallets() {
        return pallets;
    }

    public void setPallets(Double pallets) {
        this.pallets = pallets;
    }

    @Override
    public String toString() {
        return "Daily_statistics{" +
                "date=" + date +
                ", shift=" + shift +
                ", pallets=" + pallets +
                '}';
    }

}
