package DatabazaLinka.UserInterface;

import DatabazaLinka.DbContext;
import DatabazaLinka.RowDateGateway.*;
import DatabazaLinka.TransactionScript.Vynimka;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;

public class HlavneMenu{
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
    PauseController udrzbaController;
    PauseController chybaContoller;


    int x = 700;
    int y = 500;

    String heslo;
    boolean ide = false;
    int shift = 1;
    int ciel;
    Timeline timeline;
    StackedBarChart graph1;
    StackedBarChart graph2;

    StackedBarChart graph11;
    StackedBarChart graph22;

    LocalTime zmena1zaciatok;
    LocalTime zmena1koniec;

    LocalTime zmena1prestavka1zaciatok;
    LocalTime zmena1prestavka1koniec;

    LocalTime zmena1prestavka2zaciatok;
    LocalTime zmena1prestavka2koniec;

    LocalTime zmena1prestavka3zaciatok;
    LocalTime zmena1prestavka3koniec;

    LocalTime zmena2zaciatok;
    LocalTime zmena2koniec;

    LocalTime zmena2prestavka1zaciatok;
    LocalTime zmena2prestavka1koniec;

    LocalTime zmena2prestavka2zaciatok;
    LocalTime zmena2prestavka2koniec;

    LocalTime zmena2prestavka3zaciatok;
    LocalTime zmena2prestavka3koniec;

    Properties props;

    public void start(Stage primaryStage) throws SQLException, Vynimka,IOException {
        readProp();


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
        /*
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/ineguiSupervisor.fxml");
        loader.setController(new SuperController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        superController = loader.getController();
        superController.setUp(this);

        guiMonitor = new Scene(root, x, y);*/

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
        operController.setUp(this);

        operator = new Scene(root, x, y);

        //chyby
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/chyby.fxml");
        loader.setController(new ChybyController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        chybyController = loader.getController();
        chybyController.setUp(this);

        chyby = new Scene(root, x, y);

        //statistic
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/statistics.fxml");
        loader.setController(new StatController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        statController = loader.getController();
        statController.setUp(this);

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
        chybaContoller = loader.getController();

        chyba = new Scene(root, x, y);

        //udrzba
        loader = new FXMLLoader();
        xmlUrl = getClass().getResource("/udrzba.fxml");
        loader.setController(new PauseController());
        loader.setLocation(xmlUrl);
        root = loader.load();
        udrzbaController = loader.getController();

        udrzba = new Scene(root, x, y);

        graph1 = stackSet(operController.weekGraph, 1);
        graph2 = stackSet(operController.weekGraph, 2);
        graph11 = stackSet(superController.graph, 1);
        graph22 = stackSet(superController.graph, 2);
    }

    public void readProp() throws IOException {
        File configFile = new File("src/main/resources/config.properties");

        try {
            FileReader reader = new FileReader(configFile);
            props = new Properties();
            props.load(reader);

            heslo = props.getProperty("heslo");
            ciel = Integer.valueOf(props.getProperty("ciel"));

            zmena1zaciatok = LocalTime.parse(props.getProperty("zmena1zaciatok"));
            zmena1koniec = LocalTime.parse(props.getProperty("zmena1koniec"));

            zmena1prestavka1zaciatok = LocalTime.parse(props.getProperty("zmena1prestavka1zaciatok"));
            zmena1prestavka1koniec = LocalTime.parse(props.getProperty("zmena1prestavka1koniec"));

            zmena1prestavka2zaciatok = LocalTime.parse(props.getProperty("zmena1prestavka2zaciatok"));
            zmena1prestavka2koniec = LocalTime.parse(props.getProperty("zmena1prestavka2koniec"));

            zmena1prestavka3zaciatok = LocalTime.parse(props.getProperty("zmena1prestavka3zaciatok"));
            zmena1prestavka3koniec = LocalTime.parse(props.getProperty("zmena1prestavka3koniec"));

            zmena2zaciatok = LocalTime.parse(props.getProperty("zmena2zaciatok"));
            zmena2koniec = LocalTime.parse(props.getProperty("zmena2koniec"));

            zmena2prestavka1zaciatok = LocalTime.parse(props.getProperty("zmena2prestavka1zaciatok"));
            zmena2prestavka1koniec = LocalTime.parse(props.getProperty("zmena2prestavka1koniec"));

            zmena2prestavka2zaciatok = LocalTime.parse(props.getProperty("zmena2prestavka2zaciatok"));
            zmena2prestavka2koniec = LocalTime.parse(props.getProperty("zmena2prestavka2koniec"));

            zmena2prestavka3zaciatok = LocalTime.parse(props.getProperty("zmena2prestavka3zaciatok"));
            zmena2prestavka3koniec = LocalTime.parse(props.getProperty("zmena2prestavka3koniec"));

            reader.close();
        } catch (FileNotFoundException ex) {
            // file does not exist
            throw ex;
        } catch (IOException ex) {
            // I/O error
            throw ex;
        }
    }

    public void updateDatetime(){
        LocalTime now = LocalTime.now();
        String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        operController.setTime(time);
        pauseController.setCurrentDate();
        chybaContoller.setCurrentDate();
        udrzbaController.setCurrentDate();
        if (now.isAfter(zmena1prestavka1zaciatok) && now.isBefore(zmena1prestavka1koniec) ||
                now.isAfter(zmena1prestavka2zaciatok) && now.isBefore(zmena1prestavka2koniec) ||
                now.isAfter(zmena1prestavka3zaciatok) && now.isBefore(zmena1prestavka3koniec) ||
                now.isAfter(zmena2prestavka1zaciatok) && now.isBefore(zmena2prestavka1koniec) ||
                now.isAfter(zmena2prestavka2zaciatok) && now.isBefore(zmena2prestavka2koniec) ||
                now.isAfter(zmena2prestavka3zaciatok) && now.isBefore(zmena2prestavka3koniec)){
            operStage.setTitle("Prestavka");
            operStage.setScene(prestavka);

        }else if(operStage.getScene() == prestavka){
            operStage.setTitle("Operator");
            operStage.setScene(operator);
        }
    }

    public List<Double> terazMax(){
        double teraz = 0;
        double max = 0;
        LocalTime now = LocalTime.now();
        if (shift == 1){
            max = ChronoUnit.MILLIS.between(zmena1zaciatok, zmena1koniec);
            max -= ChronoUnit.MILLIS.between(zmena1prestavka1zaciatok, zmena1prestavka1koniec);
            max -= ChronoUnit.MILLIS.between(zmena1prestavka2zaciatok, zmena1prestavka2koniec);
            max -= ChronoUnit.MILLIS.between(zmena1prestavka3zaciatok, zmena1prestavka3koniec);

            if(now.isBefore(zmena1koniec)) {
                teraz = ChronoUnit.MILLIS.between(zmena1zaciatok, LocalTime.now());

                if(now.isAfter(zmena1prestavka1koniec)){
                    teraz -= ChronoUnit.MILLIS.between(zmena1prestavka1zaciatok, zmena1prestavka1koniec);

                    if(now.isAfter(zmena1prestavka2koniec)){
                        teraz -= ChronoUnit.MILLIS.between(zmena1prestavka2zaciatok, zmena1prestavka2koniec);

                        if(now.isAfter(zmena1prestavka3koniec)){
                            teraz -= ChronoUnit.MILLIS.between(zmena1prestavka3zaciatok, zmena1prestavka3koniec);
                        }
                    }
                }
            }
            else{
                teraz = max;

            }
        }else{
            max = ChronoUnit.MILLIS.between(zmena2zaciatok, zmena2koniec);
            max -= ChronoUnit.MILLIS.between(zmena2prestavka1zaciatok, zmena2prestavka1koniec);
            max -= ChronoUnit.MILLIS.between(zmena2prestavka2zaciatok, zmena2prestavka2koniec);
            max -= ChronoUnit.MILLIS.between(zmena2prestavka3zaciatok, zmena2prestavka3koniec);

            if(now.isBefore(zmena2koniec)) {
                teraz = ChronoUnit.MILLIS.between(zmena2zaciatok, LocalTime.now());

                if(now.isAfter(zmena2prestavka1koniec)){
                    teraz -= ChronoUnit.MILLIS.between(zmena2prestavka1zaciatok, zmena2prestavka1koniec);

                    if(now.isAfter(zmena2prestavka2koniec)){
                        teraz -= ChronoUnit.MILLIS.between(zmena2prestavka2zaciatok, zmena2prestavka2koniec);

                        if(now.isAfter(zmena2prestavka3koniec)){
                            teraz -= ChronoUnit.MILLIS.between(zmena2prestavka3zaciatok, zmena2prestavka3koniec);
                        }
                    }
                }
            }
            else{
                teraz = max;
            }
        }
        List<Double> x = new ArrayList<>();
        x.add(teraz);
        x.add(max);

        return x;
    }

    public int kolkoMaloByt(){
        List<Double> a = terazMax();

        double kolko = a.get(0)/a.get(1) * ciel;
        return (int)kolko;
    }

    public int kolkoRzchlost(int pal){

        List<Double> a = terazMax();
        double teraz = a.get(0);
        double max = a.get(1);

        int ostava = ciel - pal;
        double cas = max - teraz;

        if (cas == 0.0){
            return -1;

        }
        double z = (ostava/cas)*1000*60*60;

        return (int)z;
    }

    public void update() throws SQLException{
        System.out.println("UPDATE");
        if(LocalTime.now().isAfter(zmena2zaciatok)){
            shift = 2;
        }
        Date date = new Date(System.currentTimeMillis());

        Daily_statistics_Finder.getInstance().findByShiftandDateAll(1, date);
        Daily_statistics_Finder.getInstance().findByShiftandDateAll(2, date);
        double pallets = Normalized_Paletts_Finder.getInstance().findByDateShiftNormalizedALl(date, shift).getPaletts();
        //System.out.println(pallets);

        operController.setTodayGraph((int)pallets, kolkoMaloByt());

        //operController.setWeekGraph(this);

        T_raw_data raw = T_raw_data_Finder.getInstance().findLast();
        int kolko = kolkoRzchlost((int)pallets);
        operController.setBoxes(raw.getPerf_real_per_min(), raw.getPerf_norm_per_min(), kolko);
        try {
            operController.setModel(Series_Finder.getInstance().findById(raw.getSeries()).getName());
        }catch (NullPointerException nl){
            operController.setModel("--------");
        }
        try {
            operController.setNextModel(Series_Finder.getInstance().findById(raw.getNext_series()).getName());
        }catch(NullPointerException nl){
            operController.setNextModel("--------");
        }

        if(shift == 1) {
            operController.setShiftName("Doobedna");
            superController.setGraph(graph1);
        }else {
            operController.setShiftName("Poobedna");
            superController.setGraph(graph2);
        }
        operController.setTable(this);
    }

    public StackedBarChart stackSet(StackedBarChart graph, int sh) throws SQLException {
        List<XYChart.Series> series = new ArrayList<>();

        List<Series> ser = Series_Finder.getInstance().findAll();
        for (int i = 0; i < ser.size(); i++) { //for kazdy dnesni model
            XYChart.Series docas = new XYChart.Series();
            docas.setName(ser.get(i).getName());
            series.add(docas);
        }


        List<Event_type> eve = Event_type_Finder.getInstance().findAll();
        for (int i = 0; i < eve.size(); i++) { //for kazdy event
            XYChart.Series docas = new XYChart.Series();
            docas.setName(eve.get(i).getName());
            series.add(docas);
        }

        int dni = 0;

        LocalDate date = LocalDate.now();

        List<Events> dayeve;
        while(dni < 5){

            date = date.minusDays(1);
            if(Normalized_Paletts_Finder.getInstance().findByDateShiftNormalizedALl(Date.valueOf(date), sh).getPaletts() > 0){
                dni++;
                for (int k = 0; k < ser.size(); k++) {
                    double a = Normalized_Paletts_Finder.getInstance().findByDateSeriesShiftNormalized(Date.valueOf(date),
                            ser.get(k).getId(), sh).getPaletts();
                    series.get(k).getData().add(new XYChart.Data(Date.valueOf(date).toString(), a));
                    //System.out.println(series.get(k).getName());
                }

                for (int k = 0; k < eve.size(); k++) {
                    dayeve = Events_Finder.getInstance().findByDateAndType(eve.get(k).getId(), Date.valueOf(date));
                    for (int x = 0; x < dayeve.size(); x++) {
                        series.get(ser.size() + k).getData().add(new XYChart.Data(Date.valueOf(date).toString(),
                                dayeve.get(x).getPotencionaly_washed_pallets()));
                    }

                    //System.out.println(series.get(ser.size() + k).getName());
                }
                //series.get(0).getData().add(new XYChart.Data(Date.valueOf(date).toString(), 100));
            }
        }


        graph.getData().setAll();


        graph.setLegendVisible(true);

        for (int i = 0; i < series.size(); i++) {
            graph.getData().add(series.get(i));
        }
        return graph;
    }

    public void print() {
        System.out.println("|==============FUNGUJE=================|");

    }

    public void startOper() { //akcia pre tlacitko yacat v menu - zacne zobrazenie operator
        operStage.setScene(operator);
        operStage.setTitle("Operator");
        operStage.show();

        //update timeline
        try {
            update();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(timeline != null){
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(60), ev -> {
            try {
                update();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        //cas timeline
        Timeline timelineDatetime = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
            updateDatetime();
        }));

        timelineDatetime.setCycleCount(Animation.INDEFINITE);

        timelineDatetime.play();

        System.out.println("Start");
    }

    public void loginAction() { //login - logout / atd / akcia na menu button
        //docasna zmena bez hesla

        if (!adminPrihlaseny){
            String inputHeslo = JOptionPane.showInputDialog(null, "Heslo",
                    "Login", JOptionPane.INFORMATION_MESSAGE);
            if(inputHeslo.equals(heslo)) {
                adminStage.setTitle("Admin menu");
                adminStage.setScene(adminMenu);
                adminPrihlaseny = true;
            }
        }
        else if (adminPrihlaseny){
            adminStage.setTitle("Monitor menu");
            adminStage.setScene(monitorMenu);
            adminPrihlaseny = false;
        }
        System.out.println("Login");
    }

    public void editButt() throws SQLException { //co sa po kliknuti na vykonat zmenu
        System.out.println("Edit");
        if (adminPrihlaseny){
            adminStage.setTitle("Admin");
            adminStage.setScene(guiAdmin);
        }
        else{
            adminStage.setTitle("Monitor");
            adminStage.setScene(guiAdmin);
        }
        if(shift == 1) {
            superController.setGraph(graph1);
        }
        else{
            superController.setGraph(graph2);
        }
        //superController.open(this);
    }

    public void editConfig() { //co sa po kliknuti na zmenit config
        ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "src/main/resources/config.properties");
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Config");
    }

    public void exitProg() { // exit program
        System.out.println("Exit");
        DbContext.clear();
        Platform.exit();
    }
}