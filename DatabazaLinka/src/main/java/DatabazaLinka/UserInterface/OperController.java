package DatabazaLinka.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    public void setTodayGraph(int vyrobene, int malobyt){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("");
        series1.getData().add(new XYChart.Data("Je", vyrobene));
        series1.getData().add(new XYChart.Data("Malo byt", malobyt));

        todayGraph.getData().addAll(series1);
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

