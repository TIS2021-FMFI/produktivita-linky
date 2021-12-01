package DatabazaLinka.UserInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.text.Text;

import java.util.HashMap;
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

    public void setUp(){
        col1.setCellValueFactory(new MapValueFactory<>("model"));
        col2.setCellValueFactory(new MapValueFactory<>("number"));
        todayGraph.setAnimated(false);
        weekGraph.setAnimated(false);
    }

    public void setTable(){


        ObservableList<Map<String, Object>> items =
                FXCollections.<Map<String, Object>>observableArrayList();

        Map<String, Object> item1;
        for (int i = 0; i < 5; i++) { //for kazdy dnesni model
            item1 = new HashMap<>();
            item1.put("model", "PL0" + i);
            item1.put("number", 17 * i  - i + "");
            items.add(item1);
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

    public void setWeekGraph(){
        XYChart.Series series1 = new XYChart.Series();//pre kazdu vec v grafe jedna seria
        XYChart.Series series2 = new XYChart.Series();

        series1.setName("PLONG");
        series2.setName("PSHORT");

        series1.getData().add(new XYChart.Data("Pondelok", 200));
        series2.getData().add(new XYChart.Data("Pondelok", 100));

        series1.getData().add(new XYChart.Data("Utorok", 300));
        series2.getData().add(new XYChart.Data("Utorok", 100));

        series1.getData().add(new XYChart.Data("Streda", 300));
        series2.getData().add(new XYChart.Data("Streda", 100));

        series1.getData().add(new XYChart.Data("Stvrtok", 300));
        series2.getData().add(new XYChart.Data("Stvrtok", 100));

        series1.getData().add(new XYChart.Data("Piatok", 300));
        series2.getData().add(new XYChart.Data("Piatok", 100));

        series1.getData().add(new XYChart.Data("Sobota", 300));
        series2.getData().add(new XYChart.Data("Sobota", 100));



        weekGraph.getData().setAll(series1, series2);
    }

    public void setBoxes(int i, int j, int k){
        boxes.setText(i + "/" + j + "/" + k);
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

