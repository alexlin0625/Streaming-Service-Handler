package Application;

public class DemoGroup {
    private final String shortName;
    private final String longName;
    private final int demoAccounts;
    private int demoCurrentSpending;
    private int demoPreviousSpending;
    private int demoTotalSpending;
    private int percentageSubscribed;

    // constructor
    public DemoGroup (String shortName, String longName, int demoAccounts) {
        this.shortName = shortName;
        this.longName = longName;
        this.demoAccounts = demoAccounts;
        demoCurrentSpending = 0;
        demoPreviousSpending = 0;
        demoTotalSpending = 0;
        percentageSubscribed = 0;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public int getDemoAccounts() {
        return demoAccounts;
    }

    public int getDemoCurrentSpending() {
        return demoCurrentSpending;
    }

    protected void setDemoCurrentSpending(int demoCurrentSpending) {
        this.demoCurrentSpending = demoCurrentSpending;
    }

    public int getDemoPreviousSpending() {
        return demoPreviousSpending;
    }

    protected void setDemoPreviousSpending(int demoPreviousSpending) {
        this.demoPreviousSpending = demoPreviousSpending;
    }

    public int getDemoTotalSpending() {
        return demoTotalSpending;
    }

    protected void setDemoTotalSpending(int demoTotalSpending) {
        this.demoTotalSpending = demoTotalSpending;
    }

    public int getPercentageSubscribed() {
        return percentageSubscribed;
    }

    protected void setPercentageSubscribed(int percentageSubscribed) {
        this.percentageSubscribed = percentageSubscribed;
    }

    protected void displayInformation() {
        System.out.println("demo_name:       " + shortName + ", "+ longName);
        System.out.println("size:            " + demoAccounts);
        System.out.println("current_period:  " + demoCurrentSpending);
        System.out.println("previous_period: " + demoPreviousSpending);
        System.out.println("total:           " + demoTotalSpending);
    }
}
