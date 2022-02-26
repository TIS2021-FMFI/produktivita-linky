package DatabazaLinka.UserInterface;

import DatabazaLinka.RowDateGateway.Event_type;
import DatabazaLinka.RowDateGateway.Event_type_Finder;
import DatabazaLinka.RowDateGateway.Events;
import DatabazaLinka.RowDateGateway.Events_Finder;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ChybyController {
    @FXML
    public Button exitButton;
    @FXML
    public MenuButton menuButton;
    @FXML
    public Button startButton;
    @FXML
    public Button endButton;
    @FXML
    public Text odCas;

    Timestamp startTS;
    int currentError = -1;
    HlavneMenu mm;

    public void setUp(HlavneMenu menu) throws SQLException {
        //pre kazde tlacitko sa setupne akcia
        startButton.setOnAction(e -> {
            System.out.println(currentError);
            startEvent();
        });

        endButton.setOnAction(e -> {
            try {
                endEvent();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        exitButton.setOnAction(e -> {
            menu.adminStage.setTitle("Operator");
            menu.adminStage.setScene(menu.guiAdmin);
        });

        MenuItem docas;
        List<Event_type> eve = Event_type_Finder.getInstance().findAll();
        for (int i = 0; i < eve.size(); i++) { //for kazdy event
            int dc = eve.get(i).getId();
            docas = new MenuItem(eve.get(i).getName());
            String name = eve.get(i).getName();
            docas.setOnAction(e -> {
                currentError = dc;
                menuButton.setText(name);
            });
            menuButton.getItems().add(docas);
        }
        mm = menu;
    }
    //zacne event, ulozi timestamp
    public void startEvent(){
        if(currentError < 0){
            return;
        }
        startTS = new Timestamp(System.currentTimeMillis());
        if (currentError == 1){
            mm.operStage.setScene(mm.chyba);
            mm.operStage.setTitle("Error");
        }
        else{
            mm.operStage.setScene(mm.udrzba);
            mm.operStage.setTitle("Udrzba");
        }
        odCas.setText("Zaciatok: " + startTS.toString());
        //System.out.println(startTS.hashCode());
    }
    public void endEvent() throws SQLException {
        if(currentError < 0){
            return;
        }
        //nahodi naspet oper screen
        mm.operStage.setScene(mm.operator);
        mm.operStage.setTitle("Operator");

        Events event = new Events();

        event.setId(startTS.hashCode()*10 + currentError);
        event.setId_event(currentError);
        event.setTimestamp_begin(startTS);
        Timestamp temp = new Timestamp(System.currentTimeMillis());
        double duration = (double)(temp.getTime() - startTS.getTime());

        event.setTimestamp_end(temp);
        duration = duration/(60 * 1000);
        event.setDuration(duration); //duration je v minutach


        event.setPotencionaly_washed_pallets(duration * 800/430);
        System.out.println("Pot washed: " + event.getPotencionaly_washed_pallets());
        System.out.println("Duration: " + event.getDuration());

        event.insert();
        JOptionPane.showMessageDialog(null, "Event bol zaznamenaný",
                "Notice", JOptionPane.INFORMATION_MESSAGE);
    }
}
