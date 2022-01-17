package DatabazaLinka.UserInterface;

import DatabazaLinka.RowDateGateway.*;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.File;
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
    public Button errorButton;
    @FXML
    public Button submit;
    @FXML
    public Button submit2;
    @FXML
    public Button statisticsButton;
    @FXML
    public CheckBox A;
    @FXML
    public CheckBox B;

    @FXML
    public TextField changeDL;

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
            editCon();
        });
        submit2.setOnAction(e -> {

        });

        statisticsButton.setOnAction(e -> {
            menu.adminStage.setTitle("Stats");
            menu.adminStage.setScene(menu.statistick);
        });

        errorButton.setOnAction(e -> {
            System.out.println("Error");
            menu.adminStage.setTitle("Error");
            menu.adminStage.setScene(menu.chyby);
        });


    }

    public void editCon(){
        File configFile = new File("src/main/resources/config.properties");
        int dl = 0;
        boolean err = false;
        if (changeDL.getText() != null){
            try{
                dl = Integer.parseInt(changeDL.getText());
                System.out.println(dl);
            }
            catch (Exception ex){
                err = true;
            }
        }


        if(err){
            error();
        }
    }

    public void error(){
        JOptionPane.showMessageDialog(null, "Wrong input",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void plannedEvent(){

    }

    public void setGraph(StackedBarChart b){
        graph = b;
    }
}