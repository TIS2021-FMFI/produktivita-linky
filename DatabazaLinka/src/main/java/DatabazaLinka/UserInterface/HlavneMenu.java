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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.midi.Soundbank;

public class HlavneMenu{
    boolean stop = false;
    int shift = 1;

    Random rnd = new Random();

    Boolean pr = false;
    Boolean adminPrihlaseny = false;

    Stage operStage = new Stage();;
    Stage adminStage = new Stage();;

    Scene prestavka;
    Scene operator;
    Scene monitorMenu;
    Scene adminMenu;
    Scene guiMonitor;
    Scene guiAdmin;
    Scene udrzba;
    Scene statistick;
    Scene chyba;
    Scene chyby;

    MenuController menuController;
    OperController operController;
    SuperController superController;
    PauseController pauseController;
    ChybyController chybyController;
    StatController statController;

    int x = 700;
    int y = 500;

    public void start(Stage primaryStage) throws SQLException, Vynimka,IOException {

        stop = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("VITAJ V DATABAZE LINKY!");
        System.out.println("ZVOL PONUKA PRE PONUKU");
        System.out.println();
        print();
        System.out.println();

        //skuska();

        //monitor menu
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/menuMonitor.fxml");
        loader.setController(new MenuController());
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        menuController = loader.getController();
        menuController.setUp(this);

        monitorMenu = new Scene(root, x, y);
        adminStage.setTitle("Monitor menu");
        adminStage.setScene(monitorMenu);
        adminStage.show();

        //admin menu
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/menuAdmin.fxml");
        loader.setController(new MenuController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        menuController = loader.getController();
        menuController.setUp(this);

        adminMenu = new Scene(root, x, y);

        //guiMonitor
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/ineguiSupervisor.fxml");
        loader.setController(new SuperController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        superController = loader.getController();
        superController.setUp(this);

        guiMonitor = new Scene(root, x, y);

        //guiAdmin
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/guiAdmin.fxml");
        loader.setController(new SuperController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        superController = loader.getController();
        superController.setUp(this);

        guiAdmin = new Scene(root, x, y);

        //operator
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/operator.fxml");
        loader.setController(new OperController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        operController = loader.getController();
        operController.setUp();

        operator = new Scene(root, x, y);

        //chyby
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/chyby.fxml");
        loader.setController(new ChybyController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        chybyController = loader.getController();
        chybyController.setUp();

        chyby = new Scene(root, x, y);

        //statistic
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/statistics.fxml");
        loader.setController(new StatController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        statController = loader.getController();

        statistick = new Scene(root, x, y);


        //prestavka
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/prestavka.fxml");
        loader.setController(new PauseController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        pauseController = loader.getController();

        prestavka = new Scene(root, x, y);

        //chyba
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/chyba.fxml");
        loader.setController(new PauseController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        pauseController = loader.getController();

        chyba = new Scene(root, x, y);

        //udrzba
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/udrzba.fxml");
        loader.setController(new PauseController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        pauseController = loader.getController();

        udrzba = new Scene(root, x, y);

        //testovanie

        //update();
        //opc.setUp();
        Date date = new Date(System.currentTimeMillis());
        Daily_statistics_Finder.getInstance().findByShiftandDateAll(shift, date);
        System.out.println(Normalized_Paletts_Finder.getInstance().findByDateShiftNormalizedALl(date, shift).getPaletts());

        Timeline timelineDatetime = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
            updateDatetime();
        }));


        timelineDatetime.setCycleCount(Animation.INDEFINITE);

        timelineDatetime.play();

    }

    public void pause(){
        pr = !pr;
        if(pr){
            operStage.setScene(prestavka);
        }
        else {
            operStage.setScene(operator);
        }
    }

    public void updateDatetime(){
        //pac.setCurrentDate();
    }

    public void update() throws SQLException{
        /*System.out.println("UPDATE");
        Date date = new Date(System.currentTimeMillis());
        Daily_statistics_Finder.getInstance().findByShiftandDateAll(shift, date);
        System.out.println(Normalized_Paletts_Finder.getInstance().findByDateShiftNormalizedALl(date, shift).getPaletts() + 1);*/

        operController.setTodayGraph(rnd.nextInt(500), 500);

        operController.setWeekGraph();

        operController.setBoxes(25, 40, 75);
        operController.setModel("PLONG");
        operController.setNextModel(null);
        operController.setShiftName("Doobedna");

        operController.setTable();
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
    }

    public void startOper() { //akcia pre tlacitko yacat v menu - zacne zobrazenie operator
        operStage.setScene(operator);
        operStage.setTitle("Operator");
        operStage.show();

        if (adminPrihlaseny){
            adminStage.setTitle("Admin");
            adminStage.setScene(guiAdmin);
        }
        else{
            adminStage.setTitle("Monitor");
            adminStage.setScene(guiMonitor);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            try {
                update();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        System.out.println("Start");
    }

    public void loginAction() { //login - logout / atd / akcia na menu button
        //docasna zmena bez hesla
        if (!adminPrihlaseny){
            adminStage.setTitle("Admin menu");
            adminStage.setScene(adminMenu);
            adminPrihlaseny = true;
        }
        else if (adminPrihlaseny){
            adminStage.setTitle("Monitor menu");
            adminStage.setScene(monitorMenu);
            adminPrihlaseny = false;
        }
        System.out.println("Login");
    }

    public void editButt() { //co sa po kliknuti na vykonat zmenu
        System.out.println("Edit");
    }

    public void editConfig() { //co sa po kliknuti na zmenit config
        System.out.println("Config");
    }

    public void exitProg() { // exit program
        System.out.println("Exit");
        DbContext.clear();
        Platform.exit();
    }
}