package Application;

public class DemoGroup {
    private final String shortName;
    private final String longName;
    private int demoAccounts;
    private int demoCurrentSpending;
    private int demoPreviousSpending;
    private int demoTotalSpending;
    private String[] demoWatchHistory;
    private final int LIMIT_DEMOS = 10;

    // constructor
    public DemoGroup (String shortName, String longName, int demoAccounts) {
        this.shortName = shortName;
        this.longName = longName;
        this.demoAccounts = demoAccounts;
        demoCurrentSpending = 0;
        demoPreviousSpending = 0;
        demoTotalSpending = 0;
        demoWatchHistory = new String[LIMIT_DEMOS];
    }
}
