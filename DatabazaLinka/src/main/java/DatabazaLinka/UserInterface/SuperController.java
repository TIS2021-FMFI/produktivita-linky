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
import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
    @FXML
    public TextField from1;
    @FXML
    public TextField to1;
    @FXML
    public TextField from2;
    @FXML
    public TextField to2;
    @FXML
    public TextField from3;
    @FXML
    public TextField to3;

    HlavneMenu mm;

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
            try {
                editCon();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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

        A.setOnAction(e -> {
            B.setSelected(false);
        });
        B.setOnAction(e -> {
            A.setSelected(false);
        });

        mm = menu;
    }

    public void editCon() throws IOException {
        File configFile = new File("src/main/resources/config.properties");
        FileWriter writer = new FileWriter(configFile);
        int dl = 0;
        boolean err = false;

        if (!changeDL.getText().equals("")){
            try{
                dl = Integer.parseInt(changeDL.getText());
                System.out.println(dl);
            }
            catch (Exception ex){
                err = true;
            }
        }

        String fr1 = "";
        String fr2 = "";
        String fr3 = "";
        String t1 = "";
        String t2 = "";
        String t3 = "";
        boolean x = false;
        boolean y = false;
        boolean z = false;

        String rex = "^[0-2]\\d:[0-6]\\d$";
        if(!from1.getText().equals("") && !to1.getText().equals("")){
            fr1 = from1.getText();
            t1 = to1.getText();
            if(fr1.matches(rex) && t1.matches(rex)) {
                x = true;
            }
            else{
                err = true;
            }
        }
        if(!from2.getText().equals("") && !to2.getText().equals("")){
            fr2 = from2.getText();
            t2 = to2.getText();
            if(fr2.matches(rex) && t2.matches(rex)) {
                y = true;
            }
            else{
                err = true;
            }
        }
        if(!from3.getText().equals("") && !to3.getText().equals("")){
            fr3 = from3.getText();
            t3 = to3.getText();
            if(fr3.matches(rex) && t3.matches(rex)) {
                z = true;
            }
            else{
                err = true;
            }
        }

        if(err) {
            JOptionPane.showMessageDialog(null, "Wrong input",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Properties changed",
                    "Notice", JOptionPane.INFORMATION_MESSAGE);
        }

        try {
            FileReader reader = new FileReader(configFile);

            if (dl > 0) {
                mm.props.setProperty("ciel", String.valueOf(dl));
            }

            if (A.isSelected()) {
                if (x) {
                    mm.props.setProperty("zmena1prestavka1zaciatok", fr1);
                    mm.props.setProperty("zmena1prestavka1koniec", t1);
                }
                if (y) {
                    mm.props.setProperty("zmena1prestavka2zaciatok", fr2);
                    mm.props.setProperty("zmena1prestavka2koniec", t2);
                }
                if (z) {
                    mm.props.setProperty("zmena1prestavka3zaciatok", fr3);
                    mm.props.setProperty("zmena1prestavka3koniec", t3);
                }
            } else if (B.isSelected()) {
                if (x) {
                    mm.props.setProperty("zmena2prestavka1zaciatok", fr1);
                    mm.props.setProperty("zmena2prestavka1koniec", t1);
                }
                if (y) {
                    mm.props.setProperty("zmena2prestavka2zaciatok", fr2);
                    mm.props.setProperty("zmena2prestavka2koniec", t2);
                }
                if (z) {
                    mm.props.setProperty("zmena2prestavka3zaciatok", fr3);
                    mm.props.setProperty("zmena2prestavka3koniec", t3);
                }
            }
            mm.props.store(writer, "property file");
            writer.close();
            reader.close();
        } catch (FileNotFoundException ex) {
                // file does not exist
            throw ex;
        } catch (IOException ex) {
            // I/O error
            throw ex;
        }
    }

    public void plannedEvent(){

    }

    public void setGraph(StackedBarChart b){
        graph = b;
    }
}