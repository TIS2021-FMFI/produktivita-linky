package DatabazaLinka.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PauseController {
    @FXML
    public Text date;

    public void setDate(String time) {
        date.setText(time);
    }
    public  void setCurrentDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        String timeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
        setDate(timeStamp);
    }
}