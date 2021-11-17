package DatabazaLinka.RowDateGateway;

public class Event_type {
    private Integer id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event_type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
