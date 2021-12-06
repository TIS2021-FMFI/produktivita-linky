package DatabazaLinka.RowDateGateway;

public class Normalized_Paletts {
    private Integer series;
    private Double paletts;
    private String name;

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPaletts() {
        return paletts;
    }

    public void setPaletts(Double paletts) {
        this.paletts = paletts;
    }

    @Override
    public String toString() {
        return "Normalized_Paletts{" +
                "series=" + series +
                ", paletts=" + paletts +
                ", name='" + name + '\'' +
                '}';
    }
}
