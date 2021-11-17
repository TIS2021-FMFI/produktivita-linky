package DatabazaLinka.RowDateGateway;

public class Weekly_statistics_Finder {
    private static final Weekly_statistics_Finder INSTANCE = new Weekly_statistics_Finder();

    public static Weekly_statistics_Finder getInstance() {
        return INSTANCE;
    }

    private Weekly_statistics_Finder() {
    }
}