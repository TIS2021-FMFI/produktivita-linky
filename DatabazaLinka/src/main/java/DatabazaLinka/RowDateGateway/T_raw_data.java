package DatabazaLinka.RowDateGateway;

import java.sql.Timestamp;

public class T_raw_data {
    private Timestamp timestamp;
    private Integer series;
    private Integer paletts;
    private Integer next_series;
    private Integer perf_norm_per_h;
    private Integer perf_real_per_h;
    private Integer perf_norm_per_min;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getPaletts() {
        return paletts;
    }

    public void setPaletts(Integer paletts) {
        this.paletts = paletts;
    }

    public Integer getNext_series() {
        return next_series;
    }

    public void setNext_series(Integer next_series) {
        this.next_series = next_series;
    }

    public Integer getPerf_norm_per_h() {
        return perf_norm_per_h;
    }

    public void setPerf_norm_per_h(Integer perf_norm_per_h) {
        this.perf_norm_per_h = perf_norm_per_h;
    }

    public Integer getPerf_real_per_h() {
        return perf_real_per_h;
    }

    public void setPerf_real_per_h(Integer perf_real_per_h) {
        this.perf_real_per_h = perf_real_per_h;
    }

    public Integer getPerf_norm_per_min() {
        return perf_norm_per_min;
    }

    public void setPerf_norm_per_min(Integer perf_norm_per_min) {
        this.perf_norm_per_min = perf_norm_per_min;
    }

    public Integer getPerf_real_per_min() {
        return perf_real_per_min;
    }

    public void setPerf_real_per_min(Integer perf_real_per_min) {
        this.perf_real_per_min = perf_real_per_min;
    }

    private Integer perf_real_per_min;

    @Override
    public String toString() {
        return "T_raw_data{" +
                "timestamp=" + timestamp +
                ", series=" + series +
                ", paletts=" + paletts +
                ", next_series=" + next_series +
                ", perf_norm_per_h=" + perf_norm_per_h +
                ", perf_real_per_h=" + perf_real_per_h +
                ", perf_norm_per_min=" + perf_norm_per_min +
                ", perf_real_per_min=" + perf_real_per_min +
                '}';
    }
}
