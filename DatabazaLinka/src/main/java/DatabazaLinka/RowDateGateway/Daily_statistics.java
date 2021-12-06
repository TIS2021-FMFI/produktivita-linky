package DatabazaLinka.RowDateGateway;

import java.sql.Date;

public class Daily_statistics {
    private Integer shift;
    private Double pallets;

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
                ", shift=" + shift +
                ", pallets=" + pallets +
                '}';
    }

}
