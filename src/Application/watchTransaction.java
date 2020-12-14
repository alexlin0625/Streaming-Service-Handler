package Application;

public class watchTransaction extends Transactions {
    private String watchDemoGroup;
    private String watchStream;
    private String watchEventName;
    private int watchEventYear;
    private int watchPercentage;

    public watchTransaction(String watchDemoGroup, String watchStream, String watchEventName, int watchEventYear, int watchPercentage) {
        this.watchDemoGroup = watchDemoGroup;
        this.watchStream = watchStream;
        this.watchEventName = watchEventName;
        this.watchEventYear = watchEventYear;
        this.watchPercentage = watchPercentage;
        setTransactionId();
        setDate();
    }


}
