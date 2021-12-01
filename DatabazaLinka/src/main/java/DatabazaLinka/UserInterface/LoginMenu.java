package DatabazaLinka.UserInterface;

import javax.swing.*;

public class LoginMenu {
    static int userRight = 0; //0 = user, 1 = level one admin, 2 = level 2 admin
    public static void main(String[] args) {

        login();//placeholder volanie, len aby bolo vidiet ze to ide
        logout();
    }

    private static void login(){//po zavolani vyziada v popupe heslo, potom ho skontroluje a
                                //zmení prístupové práva
        String heslo = JOptionPane.showInputDialog(null, "Heslo",
                "Login", JOptionPane.INFORMATION_MESSAGE);

        if (heslo != null) {
            //SELECT level FROM pristup WHERE mysmas(heslo) == heslo

            //placeholder
            if (heslo.equals("heslo")) {
                userRight = 2;
                JOptionPane.showMessageDialog(null, "Dobré heslo, vitaj supervízor.",
                        "Login success", JOptionPane.INFORMATION_MESSAGE);
            } else if (heslo.equals("veslo")) {
                userRight = 1;
                JOptionPane.showMessageDialog(null, "Dobré heslo, vitaj administrátor.",
                        "Login success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Zlé heslo",
                        "Login error", JOptionPane.ERROR_MESSAGE);
            }  //error message - asi ostane
        }
    }

    private static void logout(){
        if (userRight != 0) {
            int odhlas = JOptionPane.showConfirmDialog(null, "Odhlásiť?",
                    "Logout", JOptionPane.YES_NO_OPTION);

            if (odhlas == 0) {
                userRight = 0;
                JOptionPane.showMessageDialog(null, "Úspešne odhlásené.",
                        "Logout success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}