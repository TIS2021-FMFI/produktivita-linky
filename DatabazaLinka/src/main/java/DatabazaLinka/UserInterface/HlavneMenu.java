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
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HlavneMenu{
    boolean stop = false;
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
        OperController opc = loader.getController();

        Parent root = loader.load();


        FXMLLoader loader2 = new FXMLLoader();
        xmlUrl = getClass().getResource("/guiSupervisor.fxml");
        //loader2.setController(new OperController());
        loader2.setLocation(xmlUrl);

        Parent admin = loader2.load();//FXMLLoader.load(getClass().getResource("sample.fxml"));

        Stage secondStage = new Stage();
        secondStage.setTitle("Admin");
        secondStage.setScene(new Scene(admin,  700, 500));
        secondStage.show();

        primaryStage.setTitle("Operator");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();

        //testovanie
        opc.setTodayGraph(200, 500);
        opc.setBoxes(25,40,75);
        opc.setModel("PLONG");
        opc.setNextModel(null);
        opc.setShiftName("Doobedna");

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
