package DatabazaLinka.UserInterface;

import DatabazaLinka.DbContext;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.File;
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
    public CheckBox error;/*
    @FXML
    public CheckBox c30l; //PE20
    @FXML
    public CheckBox c45l; //PE40
    @FXML
    public CheckBox c60l; //PE60*/
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
    @FXML
    public CheckBox PLONG;
    @FXML
    public CheckBox PLA10;
    @FXML
    public CheckBox PLA05;
    @FXML
    public CheckBox PE05;
    @FXML
    public CheckBox PE10;
    @FXML
    public CheckBox PE40;
    @FXML
    public CheckBox PE60;
    @FXML
    public CheckBox PE20;

    @FXML
    public StackedBarChart graph;

    public void setGraph(StackedBarChart b){
        graph = b;
    }

    private String getQuery() {
        String query = "SELECT DISTINCT dat.date, dat.shift";

        if (PE05.isSelected()) query += ", p5.p5";
        if (PE10.isSelected()) query += ", p10.p10";
        if (PE20.isSelected()) query += ", p30.p30";
        if (PE40.isSelected()) query += ", p45.p45";
        if (PE60.isSelected()) query += ", p60.p60";

        if (PLA05.isSelected()) query += ", p31.p31";
        if (PLA10.isSelected()) query += ", p41.p41";
        if (PLONG.isSelected()) query += ", p61.p61";

        if (error.isSelected()) query += ", poruchy.duration AS error";
        if (udrzba.isSelected()) query += ", udrzby.duration AS udrzba";
        if (ine.isSelected()) query += ", ine.duration AS ine";

        query += " FROM t_raw_data_history dat";

        if (PE05.isSelected()) query += " LEFT JOIN (" +
                " SELECT t5.date AS datum, t5.shift AS shift, SUM(t5.paletts) AS p5" +
                " FROM t_raw_data_history t5" +
                " JOIN series s5 ON t5.series = s5.id" +
                " WHERE s5.name = 'PE05'" +
                " GROUP BY t5.date, t5.shift" +
                " ) p5 ON dat.date = p5.datum AND dat.shift = p5.shift";

        if (PE10.isSelected()) query += " LEFT JOIN (" +
                " SELECT t10.date AS datum, t10.shift AS shift, SUM(t10.paletts) AS p10" +
                " FROM t_raw_data_history t10" +
                " JOIN series s10 ON t10.series = s10.id" +
                " WHERE s10.name = 'PE10'" +
                " GROUP BY t10.date, t10.shift" +
                " ) p10 ON dat.date = p10.datum AND dat.shift = p10.shift";

        if (PE20.isSelected()) query += " LEFT JOIN (" +
                " SELECT t30.date AS datum, t30.shift AS shift, SUM(t30.paletts) AS p30" +
                " FROM t_raw_data_history t30" +
                " JOIN series s30 ON t30.series = s30.id" +
                " WHERE s30.name = 'PE20'" +
                " GROUP BY t30.date, t30.shift" +
                " ) p30 ON dat.date = p30.datum AND dat.shift = p30.shift";

        if (PE40.isSelected()) query += " LEFT JOIN (" +
                " SELECT t45.date AS datum, t45.shift AS shift, SUM(t45.paletts) AS p45" +
                " FROM t_raw_data_history t45" +
                " JOIN series s45 ON t45.series = s45.id" +
                " WHERE s45.name = 'PE40'" +
                " GROUP BY t45.date, t45.shift" +
                " ) p45 ON dat.date = p45.datum AND dat.shift = p45.shift";

        if (PE60.isSelected()) query += " LEFT JOIN (" +
                " SELECT t60.date AS datum, t60.shift AS shift, SUM(t60.paletts) AS p60" +
                " FROM t_raw_data_history t60" +
                " JOIN series s60 ON t60.series = s60.id" +
                " WHERE s60.name = 'PE60'" +
                " GROUP BY t60.date, t60.shift" +
                " ) p60 ON dat.date = p60.datum AND dat.shift = p60.shift";

        if (PLA05.isSelected()) query += " LEFT JOIN (" +
                " SELECT t31.date AS datum, t31.shift AS shift, SUM(t31.paletts) AS p31" +
                " FROM t_raw_data_history t31" +
                " JOIN series s31 ON t31.series = s31.id" +
                " WHERE s31.name = 'PLA05'" +
                " GROUP BY t31.date, t31.shift" +
                " ) p31 ON dat.date = p31.datum AND dat.shift = p31.shift";

        if (PLA10.isSelected()) query += " LEFT JOIN (" +
                " SELECT t41.date AS datum, t41.shift AS shift, SUM(t41.paletts) AS p41" +
                " FROM t_raw_data_history t41" +
                " JOIN series s41 ON t41.series = s41.id" +
                " WHERE s41.name = 'PLA10'" +
                " GROUP BY t41.date, t41.shift" +
                " ) p41 ON dat.date = p41.datum AND dat.shift = p41.shift";

        if (PLONG.isSelected()) query += " LEFT JOIN (" +
                " SELECT t61.date AS datum, t61.shift AS shift, SUM(t61.paletts) AS p61" +
                " FROM t_raw_data_history t61" +
                " JOIN series s61 ON t61.series = s61.id" +
                " WHERE s61.name = 'PLONG'" +
                " GROUP BY t61.date, t61.shift" +
                " ) p61 ON dat.date = p61.datum AND dat.shift = p61.shift";

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
        if (PE05.isSelected()) query += " OR p5.p5 IS NOT NULL";
        if (PE10.isSelected()) query += " OR p10.p10 IS NOT NULL";
        if (PE20.isSelected()) query += " OR p30.p30 IS NOT NULL";
        if (PE40.isSelected()) query += " OR p45.p45 IS NOT NULL";
        if (PE60.isSelected()) query += " OR p60.p60 IS NOT NULL";

        if (PLA05.isSelected()) query += " OR p31.p31 IS NOT NULL";
        if (PLA10.isSelected()) query += " OR p41.p41 IS NOT NULL";
        if (PLONG.isSelected()) query += " OR p61.p61 IS NOT NULL";

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
        if (PE60.isSelected()) result += ";" + rs.getDouble(c++);
        if (PE40.isSelected()) result += ";" + rs.getDouble(c++);
        if (PE20.isSelected()) result += ";" + rs.getDouble(c++);
        if (PE05.isSelected()) result += ";" + rs.getDouble(c++);

        if (PE10.isSelected()) result += ";" + rs.getDouble(c++);
        if (PLA05.isSelected()) result += ";" + rs.getDouble(c++);
        if (PLONG.isSelected()) result += ";" + rs.getDouble(c++);
        if (PLA10.isSelected()) result += ";" + rs.getDouble(c++);

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
        PLONG.setOnAction(this::otherClicked);
        PLA10.setOnAction(this::otherClicked);
        PLA05.setOnAction(this::otherClicked);
        PE10.setOnAction(this::otherClicked);
        PE05.setOnAction(this::otherClicked);
        PE40.setOnAction(this::otherClicked);
        PE60.setOnAction(this::otherClicked);
        udrzba.setOnAction(this::otherClicked);
        ine.setOnAction(this::otherClicked);

        allClicked(null);

        export.setOnAction(e -> {
            Connection connection = DbContext.getConnection();

            String sql = getQuery();
            //System.out.printf(sql);
            try {
                File f = new File("export.csv");
                if (f.delete())                      //returns Boolean value
                {
                    PrintWriter writer = new PrintWriter("export.csv");
                    writer.append("sep=;");
                    writer.append("\n");
                    writer.append("Datum;");
                    writer.append("Zmena;");
                    if (PE60.isSelected()) writer.append(" PE60;");
                    if (PE40.isSelected()) writer.append(" PE40;");
                    if (PE20.isSelected()) writer.append(" PE20;");
                    if (PE10.isSelected()) writer.append(" PE10;");
                    if (PE05.isSelected()) writer.append(" PE05;");
                    if (PLA05.isSelected()) writer.append(" PLA05;");
                    if (PLA10.isSelected()) writer.append(" PLA10;");
                    if (PLONG.isSelected()) writer.append(" PLONG;");

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
                }
            } catch(FileNotFoundException ex){
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
        PLONG.setSelected(allSet);
        PLA10.setSelected(allSet);
        PLA05.setSelected(allSet);
        PE10.setSelected(allSet);
        PE05.setSelected(allSet);
        PE40.setSelected(allSet);
        PE60.setSelected(allSet);
        PE20.setSelected(allSet);
        ine.setSelected(allSet);
        udrzba.setSelected(allSet);
    }

    private void otherClicked(ActionEvent e) {
        if (error.isSelected() && PLONG.isSelected() && PLA10.isSelected() && PLA05.isSelected() && PE60.isSelected()
                && PE10.isSelected() && PE05.isSelected() && PE40.isSelected() && PE20.isSelected() && ine.isSelected() &&
                udrzba.isSelected()) {
            all.setSelected(true);
        } else if (all.isSelected()) {
            all.setSelected(false);
        }
    }
}
