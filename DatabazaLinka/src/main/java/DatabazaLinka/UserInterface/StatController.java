package DatabazaLinka.UserInterface;

import DatabazaLinka.DbContext;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatController {
    @FXML
    public DatePicker date1;
    @FXML
    public DatePicker date2;
    @FXML
    public RadioButton zmenaA;
    @FXML
    public RadioButton zmenaB;
    @FXML
    public RadioButton zmenaAll;
    @FXML
    public CheckBox error;
    @FXML
    public CheckBox c30l; //PE20
    @FXML
    public CheckBox c45l; //PE40
    @FXML
    public CheckBox c60l; //PE60
    @FXML
    public CheckBox ine;
    @FXML
    public CheckBox all;
    @FXML
    public CheckBox udrzba;
    @FXML
    public Button export;
    @FXML
    public Button ret;


    private String getQuery() {
        String query = "SELECT DISTINCT dat.date, dat.shift";

        if (c30l.isSelected()) query += ", p30.p30";
        if (c45l.isSelected()) query += ", p45.p45";
        if (c60l.isSelected()) query += ", p60.p60";
        if (error.isSelected()) query += ", poruchy.duration AS error";
        if (udrzba.isSelected()) query += ", udrzby.duration AS udrzba";
        if (ine.isSelected()) query += ", ine.duration AS ine";

        query += " FROM t_raw_data_history dat";

        if (c30l.isSelected()) query += " LEFT JOIN (" +
                " SELECT t30.date AS datum, t30.shift AS shift, SUM(t30.paletts) AS p30" +
                " FROM t_raw_data_history t30" +
                " JOIN series s30 ON t30.series = s30.id" +
                " WHERE s30.name = 'PE20'" +
                " GROUP BY t30.date, t30.shift" +
                " ) p30 ON dat.date = p30.datum AND dat.shift = p30.shift";

        if (c45l.isSelected()) query += " LEFT JOIN (" +
                " SELECT t45.date AS datum, t45.shift AS shift, SUM(t45.paletts) AS p45" +
                " FROM t_raw_data_history t45" +
                " JOIN series s45 ON t45.series = s45.id" +
                " WHERE s45.name = 'PE40'" +
                " GROUP BY t45.date, t45.shift" +
                " ) p45 ON dat.date = p45.datum AND dat.shift = p45.shift";

        if (c60l.isSelected()) query += " LEFT JOIN (" +
                " SELECT t60.date AS datum, t60.shift AS shift, SUM(t60.paletts) AS p60" +
                " FROM t_raw_data_history t60" +
                " JOIN series s60 ON t60.series = s60.id" +
                " WHERE s60.name = 'PE60'" +
                " GROUP BY t60.date, t60.shift" +
                " ) p60 ON dat.date = p60.datum AND dat.shift = p60.shift";

        if (error.isSelected()) query += " LEFT JOIN (" +
                " SELECT DATE(timestamp_begin) AS datum, SUM(duration) AS duration" +
                " FROM events" +
                " WHERE id_event = 1" +
                " GROUP BY DATE(timestamp_begin)" +
                " ORDER BY DATE(timestamp_begin)" +
                " ) poruchy ON dat.date = poruchy.datum";

        if (udrzba.isSelected()) query += " LEFT JOIN (" +
                " SELECT DATE(timestamp_begin) AS datum, SUM(duration) AS duration" +
                " FROM events" +
                " WHERE id_event = 2" +
                " GROUP BY DATE(timestamp_begin)" +
                " ORDER BY DATE(timestamp_begin)" +
                " ) udrzby ON dat.date = udrzby.datum";

        if (ine.isSelected()) query += " LEFT JOIN (" +
                " SELECT DATE(timestamp_begin) AS datum, SUM(duration) AS duration" +
                " FROM events" +
                " WHERE id_event NOT IN (1, 2)" +
                " GROUP BY DATE(timestamp_begin)" +
                " ORDER BY DATE(timestamp_begin)" +
                " ) ine ON dat.date = ine.datum";

        query += " WHERE TRUE";

        if (zmenaA.isSelected()) query += " AND dat.shift = 1";
        if (zmenaB.isSelected()) query += " AND dat.shift = 2";

        query += " AND ( FALSE";
        if (c30l.isSelected()) query += " OR p30.p30 IS NOT NULL";
        if (c45l.isSelected()) query += " OR p45.p45 IS NOT NULL";
        if (c60l.isSelected()) query += " OR p60.p60 IS NOT NULL";
        if (error.isSelected()) query += " OR poruchy.duration IS NOT NULL";
        if (udrzba.isSelected()) query += " OR udrzby.duration IS NOT NULL";
        if (ine.isSelected()) query += " OR ine.duration IS NOT NULL";
        query += " )";

        if (date1.getValue() != null) query += String.format(" AND dat.date >= '%s'", date1.getValue().toString());
        if (date2.getValue() != null) query += String.format(" AND dat.date <= '%s'", date2.getValue().toString());
        query += " ORDER BY dat.date DESC";

        return query;
    }

    private String parseResult(ResultSet rs) throws SQLException {
        int c = 1;
        String result = rs.getDate(c++).toString();
        result += ";" + rs.getInt(c++);
        if (c30l.isSelected()) result += ";" + rs.getDouble(c++);
        if (c45l.isSelected()) result += ";" + rs.getDouble(c++);
        if (c60l.isSelected()) result += ";" + rs.getDouble(c++);
        if (error.isSelected()) result += ";" + rs.getDouble(c++);
        if (udrzba.isSelected()) result += ";" + rs.getDouble(c++);
        if (ine.isSelected()) result += ";" + rs.getDouble(c);
        result += "\n";
        return result;
    }

    public void setUp(HlavneMenu menu) {
        ToggleGroup group = new ToggleGroup();
        zmenaA.setToggleGroup(group);
        zmenaB.setToggleGroup(group);
        zmenaAll.setToggleGroup(group);

        all.setOnAction(this::allClicked);
        error.setOnAction(this::otherClicked);
        c30l.setOnAction(this::otherClicked);
        c45l.setOnAction(this::otherClicked);
        c60l.setOnAction(this::otherClicked);
        ine.setOnAction(this::otherClicked);
        udrzba.setOnAction(this::otherClicked);

        allClicked(null);

        export.setOnAction(e -> {
            Connection connection = DbContext.getConnection();

            String sql = getQuery();
            //System.out.printf(sql);
            try {
                PrintWriter writer = new PrintWriter("export.csv");
                writer.append("sep=;");
                writer.append("\n");
                writer.append("Datum;");
                writer.append("Zmena;");
                if (c30l.isSelected()) writer.append(" 30l;");
                if (c45l.isSelected()) writer.append(" 45l;");
                if (c60l.isSelected()) writer.append(" 60l;");
                if (error.isSelected()) writer.append(" Chyby;");
                if (udrzba.isSelected()) writer.append(" Udrzba;");
                if (ine.isSelected()) writer.append(" Ine;");
                writer.append("\n");
                try (PreparedStatement s = connection.prepareStatement(sql)) {
                    try (ResultSet rs = s.executeQuery()) {
                        while (rs.next()) {
                            //System.out.println(parseResult(rs));
                            writer.append(parseResult(rs));
                        }
                        writer.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        ret.setOnAction(e -> {
            menu.adminStage.setTitle("Menu");
            menu.adminStage.setScene(menu.guiAdmin);
        });
    }

    private void allClicked(ActionEvent e) {
        boolean allSet = all.isSelected();
        error.setSelected(allSet);
        c30l.setSelected(allSet);
        c45l.setSelected(allSet);
        c60l.setSelected(allSet);
        ine.setSelected(allSet);
        udrzba.setSelected(allSet);
    }

    private void otherClicked(ActionEvent e) {
        if (error.isSelected() && c30l.isSelected() && c45l.isSelected() && c60l.isSelected() && ine.isSelected() &&
                udrzba.isSelected()) {
            all.setSelected(true);
        } else if (all.isSelected()) {
            all.setSelected(false);
        }
    }
}
