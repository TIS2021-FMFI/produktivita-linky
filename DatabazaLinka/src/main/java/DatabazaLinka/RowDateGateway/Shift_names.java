package DatabazaLinka.RowDateGateway;

import DatabazaLinka.DbContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class Shift_names {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Shift_names{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public void insert() throws SQLException {
        String sql = "INSERT INTO shift_names (id,name)"+
                "VALUES (?, ?)";

        Connection connection = DbContext.getConnection();

        try(PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1,id);
            s.setString(2,name);
            s.executeUpdate();
        }
    }

}
