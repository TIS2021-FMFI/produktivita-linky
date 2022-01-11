package DatabazaLinka.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;

import javax.swing.*;

public class SuperController {
    @FXML
    public StackedBarChart graph;
    @FXML
    public Button submit;
    @FXML
    public Button login;
    @FXML
    public Button pause;

    int userRight = 0;

    public void setUp(HlavneMenu menu){

    }

    private void login(){//po zavolani vyziada v popupe heslo, potom ho skontroluje a
        //zmení prístupové práva
        String heslo = JOptionPane.showInputDialog(null, "Heslo",
                "Login", JOptionPane.INFORMATION_MESSAGE);

        if (heslo != null) {
            //SELECT level FROM pristup WHERE mysmas(heslo) == heslo

            //placeholder
            if (heslo.equals("heslo")) {
                userRight = 2;
                JOptionPane.showMessageDialog(null, "Dobré heslo, vitaj supervízor.",
                        "Login success", JOptionPane.INFORMATION_MESSAGE);
            } else if (heslo.equals("veslo")) {
                userRight = 1;
                JOptionPane.showMessageDialog(null, "Dobré heslo, vitaj administrátor.",
                        "Login success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Zlé heslo",
                        "Login error", JOptionPane.ERROR_MESSAGE);
            }  //error message - asi ostane
        }
    }

    private void logout(){
        if (userRight != 0) {
            int odhlas = JOptionPane.showConfirmDialog(null, "Odhlásiť?",
                    "Logout", JOptionPane.YES_NO_OPTION);

            if (odhlas == 0) {
                userRight = 0;
                JOptionPane.showMessageDialog(null, "Úspešne odhlásené.",
                        "Logout success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}