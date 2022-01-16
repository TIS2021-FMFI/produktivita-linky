package DatabazaLinka.UserInterface;

import DatabazaLinka.RowDateGateway.*;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SuperController {
    @FXML
    public StackedBarChart graph;
    @FXML
    public Button exitButton;
    @FXML
    public Button submit;
    @FXML
    public Button statisticsButton;


    public void setUp(HlavneMenu menu) throws SQLException {
        exitButton.setOnAction(e -> {
            menu.adminStage.setTitle("Menu");
            if (menu.adminPrihlaseny){
                menu.adminStage.setScene(menu.adminMenu);
            }
            else{
                menu.adminStage.setScene(menu.monitorMenu);
            }
        });

        submit.setOnAction(e -> {

        });

        statisticsButton.setOnAction(e -> {
            menu.adminStage.setTitle("Stats");
            menu.adminStage.setScene(menu.statistick);
        });
        //open(menu);
    }
}