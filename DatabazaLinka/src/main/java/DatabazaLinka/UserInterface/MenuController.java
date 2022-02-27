package DatabazaLinka.UserInterface;


import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

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
