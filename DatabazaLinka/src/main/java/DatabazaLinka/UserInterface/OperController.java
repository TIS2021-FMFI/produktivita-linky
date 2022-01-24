package DatabazaLinka.UserInterface;

import DatabazaLinka.RowDateGateway.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.text.Text;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperController {
    @FXML
    public BarChart todayGraph;
    @FXML
    public StackedBarChart weekGraph;
    @FXML
    public Text boxes;
    @FXML
    public TableView table;
    @FXML
    public Text model;
    @FXML
    public Text nextModel;
    @FXML
    public Text shiftName;
    @FXML
    public TableColumn col1;
    @FXML
    public TableColumn col2;
    @FXML
    public Text time;
    @FXML
    public Button exitButton;
    @FXML
    public Button errorButton;

    public void setUp(HlavneMenu menu){
        col1.setCellValueFactory(new MapValueFactory<>("model"));
        col2.setCellValueFactory(new MapValueFactory<>("number"));
        todayGraph.setAnimated(false);
        weekGraph.setAnimated(false);
        exitButton.setOnAction(e -> {
            menu.operStage.close();
            menu.timeline.stop();
        });
    }

    public void setTime(String tm){
        time.setText(tm);
    }

    public void setTable(HlavneMenu menu) throws SQLException {
        Date date = new Date(System.currentTimeMillis());

        ObservableList<Map<String, Object>> items =
                FXCollections.<Map<String, Object>>observableArrayList();

        Map<String, Object> item1;
        List<Series> ser = Series_Finder.getInstance().findAll();
        for (int i = 0; i < ser.size(); i++) { //for kazdy dnesni model
            item1 = new HashMap<>();
            double pocet = T_raw_data_history_Finder.getInstance()
                    .findByDateSeriesAndShift(date, ser.get(i).getId() , menu.shift).getPaletts();
            if(pocet > 0){
                item1.put("model", ser.get(i).getName());
                item1.put("number", (int)pocet);
                items.add(item1);
            }
        }

        table.getItems().setAll(items);
    }

    public void setTodayGraph(int vyrobene, int malobyt){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("");

        series1.getData().add(new XYChart.Data(malobyt, "Malo byt"));
        series1.getData().add(new XYChart.Data(vyrobene, "Je"));


        todayGraph.getData().setAll(series1);
    }

    public void setGraph(StackedBarChart b){
        weekGraph = b;
    }

    public void setBoxes(int i, int j, int k){
        if (k < 0){
            boxes.setText(i + "/" + j + "/" + "NaN");
        }else{
            boxes.setText(i + "/" + j + "/" + k);
        }
    }
    public void setModel(String mod){
        if(mod != null){
            model.setText(mod);
        }
        else{
            model.setText("----");
        }
    }
    public void setNextModel(String mod){
        if(mod != null){
            nextModel.setText(mod);
        }
        else{
            nextModel.setText("----");
        }
    }
    public void setShiftName(String sh){
        if(sh != null){
            shiftName.setText(sh);
        }
        else{
            shiftName.setText("----");
        }
    }
}

