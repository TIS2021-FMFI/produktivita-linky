package DatabazaLinka.UserInterface;

import DatabazaLinka.RowDateGateway.*;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField from4;
    @FXML
    public TextField to4;

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
            try {
                plannedEvent();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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

        boolean zmena = false;
        try {
            FileReader reader = new FileReader(configFile);

            if (dl > 0) {
                mm.props.setProperty("ciel", String.valueOf(dl));
                zmena = true;
            }

            if (A.isSelected()) {
                if (x) {
                    mm.props.setProperty("zmena1prestavka1zaciatok", fr1);
                    mm.props.setProperty("zmena1prestavka1koniec", t1);
                    zmena = true;
                }
                if (y) {
                    mm.props.setProperty("zmena1prestavka2zaciatok", fr2);
                    mm.props.setProperty("zmena1prestavka2koniec", t2);
                    zmena = true;
                }
                if (z) {
                    mm.props.setProperty("zmena1prestavka3zaciatok", fr3);
                    mm.props.setProperty("zmena1prestavka3koniec", t3);
                    zmena = true;
                }
            } else if (B.isSelected()) {
                if (x) {
                    mm.props.setProperty("zmena2prestavka1zaciatok", fr1);
                    mm.props.setProperty("zmena2prestavka1koniec", t1);
                    zmena = true;
                }
                if (y) {
                    mm.props.setProperty("zmena2prestavka2zaciatok", fr2);
                    mm.props.setProperty("zmena2prestavka2koniec", t2);
                    zmena = true;
                }
                if (z) {
                    mm.props.setProperty("zmena2prestavka3zaciatok", fr3);
                    mm.props.setProperty("zmena2prestavka3koniec", t3);
                    zmena = true;
                }
            }
            mm.props.store(writer, "ked tam je \\: tak to neva");
            writer.close();
            reader.close();
        } catch (FileNotFoundException ex) {
                // file does not exist
            throw ex;
        } catch (IOException ex) {
            // I/O error
            throw ex;
        }

        if(err) {
            JOptionPane.showMessageDialog(null, "Wrong input",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(zmena){
            JOptionPane.showMessageDialog(null, "Properties changed",
                    "Notice", JOptionPane.INFORMATION_MESSAGE);
            mm.readProp();
        }
    }

    public void plannedEvent() throws SQLException {
        System.out.println(datePicker.getValue());
        String rex = "^[0-2]\\d:[0-6]\\d$";

        String fr1 = "";
        String t1 = "";
        if(!from4.getText().equals("") && !to4.getText().equals("") && datePicker.getValue() != null){
            fr1 = from4.getText();
            t1 = to4.getText();

            if(fr1.matches(rex) && t1.matches(rex)){
                LocalTime f = LocalTime.parse(fr1);
                LocalTime t = LocalTime.parse(t1);

                LocalDateTime zac = LocalDateTime.of(datePicker.getValue(), f);
                LocalDateTime kon = LocalDateTime.of(datePicker.getValue(), t);

                Events event = new Events();
                Timestamp tamZac = Timestamp.valueOf(zac);
                Timestamp tamKon = Timestamp.valueOf(kon);

                event.setId(tamZac.hashCode());
                event.setId_event(2);
                event.setTimestamp_begin(tamZac);
                event.setTimestamp_end(tamKon);

                double duration = (double)(tamKon.getTime() - tamZac.getTime());
                duration = duration/(60 * 1000);

                event.setDuration(duration);
                event.setPotencionaly_washed_pallets(duration * 800/430);

                event.insert();
            }
        }
    }

    public void setGraph(StackedBarChart b){
        graph = b;
    }
}