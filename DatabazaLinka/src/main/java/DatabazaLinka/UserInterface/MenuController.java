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

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MenuController {
    @FXML
    public Button editButton;
    @FXML
    public Button loginButton;
    @FXML
    public Button startButton;
    @FXML
    public Button exitButton;
    @FXML
    public Button configButton;

    public void setUp(HlavneMenu menu){
        startButton.setOnAction(e -> menu.startOper());

        loginButton.setOnAction(e -> menu.loginAction());

        editButton.setOnAction(e -> {
            try {
                menu.editButt();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        try {
            configButton.setOnAction(e -> menu.editConfig());
        }
        catch (Exception exception){
            //jeden controller pre dve veci
        }

        exitButton.setOnAction(e -> menu.exitProg());
    }
}
