package DatabazaLinka.UserInterface;

import DatabazaLinka.RowDateGateway.Event_type;
import DatabazaLinka.RowDateGateway.Event_type_Finder;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.sql.SQLException;
import java.util.List;

public class ChybyController {
    @FXML
    public Button exitButton;
    @FXML
    public MenuButton menuButton;
    @FXML
    public Button submitButton;

    int currentError = -1;

    public void setUp(HlavneMenu menu) throws SQLException {
        submitButton.setOnAction(e -> {
            System.out.println(currentError);
        });

        exitButton.setOnAction(e -> {
            menu.operStage.setTitle("Operator");
            menu.operStage.setScene(menu.operator);
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
    }
}
