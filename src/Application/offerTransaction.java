package Application;

public class offerTransaction extends Transactions {
    private String offerType;
    private String offerStream;
    private String offerEventName;
    private int offerEventYear;
    private int payPerViewOfferPrice = 0;

    public offerTransaction(String offerType, String offerStream, String offerEventName, int offerEventYear) {
        this.offerType = offerType;
        this.offerStream = offerStream;
        this.offerEventName = offerEventName;
        this.offerEventYear = offerEventYear;
        setTransactionId();
        setDate();
    }

    protected String getOfferType() {
        return offerType;
    }

    protected String getOfferStream() {
        return offerStream;
    }

    protected String getOfferEventName() {
        return offerEventName;
    }

    protected int getOfferEventYear() {
        return offerEventYear;
    }

    protected int getPayPerViewOfferPrice() {
        return payPerViewOfferPrice;
    }

    protected void setPayPerViewOfferPrice(int payPerViewOfferPrice) {
        this.payPerViewOfferPrice = payPerViewOfferPrice;
    }
}
