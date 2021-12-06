package DatabazaLinka.UserInterface;

import DatabazaLinka.DbContext;
import DatabazaLinka.RowDateGateway.*;
import DatabazaLinka.TransactionScript.Vynimka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HlavneMenu {
    boolean stop = false;
    public void start() throws SQLException, Vynimka,IOException {

        stop = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("VITAJ V DATABAZE LINKY!");
        System.out.println("ZVOL PONUKA PRE PONUKU");
        System.out.println();
        print();
        System.out.println();

        while (!stop) {
            String line = br.readLine();
            if (line == null) {
                return;
            }

            System.out.println();

            handle(line);
            print();
        }
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
}
}
