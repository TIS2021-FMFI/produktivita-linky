package DatabazaLinka.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class ChybyController {
    @FXML
    public Button exitButton;
    @FXML
    public MenuButton menuButton;
    @FXML
    public Button submitButton;

    String currentError = "nic";

    public void setUp(){
        submitButton.setOnAction(e -> {
            System.out.println(currentError);
        });

        MenuItem docas = new MenuItem("Priklad 1");
        docas.setOnAction(e -> {
            currentError = "e1";
            menuButton.setText("Priklad 1");
        });
        menuButton.getItems().add(docas);

        docas = new MenuItem("Priklad 2");
        docas.setOnAction(e -> {
            currentError = "e2";
            menuButton.setText("Priklad 2");
        });
        menuButton.getItems().add(docas);
    }
}
