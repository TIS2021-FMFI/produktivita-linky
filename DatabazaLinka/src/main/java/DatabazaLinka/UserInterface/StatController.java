package DatabazaLinka.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

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
    public CheckBox c30l;
    @FXML
    public CheckBox c45l;
    @FXML
    public CheckBox c60l;
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

    public void setUp(){
        udrzba.setOnAction(e -> {
            //blablabla
        });

        ret.setOnAction(e -> {
            //blebleble
        });
    }

}
