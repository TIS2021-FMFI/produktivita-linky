package DatabazaLinka.RowDateGateway;

public class Series_Finder {
    private static final Series_Finder INSTANCE = new Series_Finder();

    public static Series_Finder getInstance() {
        return INSTANCE;
    }

    private Series_Finder() {
    }
}
