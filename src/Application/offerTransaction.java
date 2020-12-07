package Application;

public class offerTransaction extends Transactions {
    private String offerType;
    private String offerStream;
    private String offerEventName;
    private int offerEventYear;

    public offerTransaction(String offerType, String offerStream, String offerEventName, int offerEventYear) {
        this.offerType = offerType;
        this.offerStream = offerStream;
        this.offerEventName = offerEventName;
        this.offerEventYear = offerEventYear;
        setTransactionId();
        setDate();
    }
}
