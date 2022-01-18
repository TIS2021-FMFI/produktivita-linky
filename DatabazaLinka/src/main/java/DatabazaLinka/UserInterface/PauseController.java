package DatabazaLinka.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PauseController {
    @FXML
    public Text text2;

    public void setDate(String ttime) {
        //System.out.println(text2.getText());
        text2.setText(ttime);
    }
    public void setCurrentDate(){
        LocalTime dateTime = LocalTime.now();
        String timeStamp = DateTimeFormatter.ofPattern("HH:mm:ss").format(dateTime);
        setDate(timeStamp);
    }
}