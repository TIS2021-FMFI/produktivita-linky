package DatabazaLinka.UserInterface;

import DatabazaLinka.DbContext;
import DatabazaLinka.RowDateGateway.*;
import DatabazaLinka.TransactionScript.Vynimka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HlavneMenu{
    boolean stop = false;

    Random rnd = new Random();
    Scene prestavka;
    Scene oper;
    Boolean pr = false;

    Stage pstage;
    int x = 700;
    int y = 500;

    OperController opc;
    SuperController suc;
    PauseController pac;
    public void start(Stage primaryStage) throws SQLException, Vynimka,IOException {

        stop = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("VITAJ V DATABAZE LINKY!");
        System.out.println("ZVOL PONUKA PRE PONUKU");
        System.out.println();
        print();
        System.out.println();

        skuska();

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/operator.fxml");
        loader.setController(new OperController());
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        opc = loader.getController();


        FXMLLoader loader2 = new FXMLLoader();
        xmlUrl = getClass().getResource("/guiSupervisor.fxml");
        loader2.setController(new SuperController());
        loader2.setLocation(xmlUrl);
        Parent admin = loader2.load();
        suc = loader2.getController();

        FXMLLoader loader3 = new FXMLLoader();
        xmlUrl = getClass().getResource("/prestavka.fxml");
        loader3.setController(new PauseController());
        loader3.setLocation(xmlUrl);
        pac = loader3.getController();
        Parent pres = loader3.load();
        prestavka = new Scene(pres , x, y);
        pstage = primaryStage;

        Stage secondStage = new Stage();
        secondStage.setTitle("Admin");
        secondStage.setScene(new Scene(admin,  x, y));
        secondStage.show();

        oper = new Scene(root, x, y);
        primaryStage.setTitle("Operator");
        primaryStage.setScene(oper);
        primaryStage.show();

        //testovanie

        update();
        opc.setUp();
        suc.setUp(this);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            update();
        }));

        Timeline timelineDatetime = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
            updateDatetime();
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timelineDatetime.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timelineDatetime.play();

    }

    public void pause(){
        pr = !pr;
        if(pr){
            pstage.setScene(prestavka);
        }
        else {
            pstage.setScene(oper);
        }
    }

    public void updateDatetime(){
        pac.setCurrentDate();
    }

    public void update(){

        opc.setTodayGraph(rnd.nextInt(500), 500);

        opc.setWeekGraph();

        opc.setBoxes(25, 40, 75);
        opc.setModel("PLONG");
        opc.setNextModel(null);
        opc.setShiftName("Doobedna");

        opc.setTable();
    }

    public void handle(String option)throws IOException,NullPointerException,SQLException {
        try {
            switch (option) {
                default: skuska(); break;

                    //System.out.println("NESPRAVNA VOLBA"); break;
            }
        }
        catch (Exception e){
            System.out.println("Nastala ina chyba!");
            System.out.println(e.getMessage());
        }

    }
    public void print() {
        System.out.println("|==============FUNGUJE=================|");

    }
    public void skuska() throws SQLException{
//        List<Events> vrat =(Events_Finder.getInstance()
//                .findByTimestampInterval((new Timestamp(121,10,1,0,0,0,0)),
//                        new Timestamp(121,10,7,0,0,0,0)));
//    for (Events i:vrat){
//        System.out.println(i);;
//    }
        System.out.println(Daily_statistics_Finder.getInstance().findByDateAndShift(new Date(121,10,1),1));
    }
}
