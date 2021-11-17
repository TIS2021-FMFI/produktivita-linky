package DatabazaLinka.RowDateGateway;

import java.sql.Timestamp;

public class Events {
    private Integer id;
    private Integer id_event;
    private Timestamp timestamp_begin;
    private Timestamp timestamp_end;

    private Double potencionaly_washed_pallets;

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

    @Override
    public String toString() {
        return "Events{" +
                "id=" + id +
                ", id_event=" + id_event +
                ", timestamp_begin=" + timestamp_begin +
                ", timestamp_end=" + timestamp_end +
                ", potencionaly_washed_pallets=" + potencionaly_washed_pallets +
                '}';
    }
}
