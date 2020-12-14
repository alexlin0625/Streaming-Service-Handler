package Application;

public class StreamingService {
    private String streamShortName;
    private String streamLongName;
    private final int streamSubscriptionFee;
    private int streamCurrentRevenue;
    private int streamPreviousRevenue;
    private int streamTotalRevenue;
    private int streamLicensingFee;

    public StreamingService(String streamShortName, String streamLongName, int streamSubscriptionFee) {
        this.streamShortName = streamShortName;
        this.streamLongName = streamLongName;
        this.streamSubscriptionFee = streamSubscriptionFee;
        streamCurrentRevenue = 0;
        streamPreviousRevenue = 0;
        streamTotalRevenue = 0;
        streamLicensingFee = 0;
    }

    public String getStreamShortName() {
        return streamShortName;
    }

    public String getStreamLongName() {
        return streamLongName;
    }

    public int getStreamSubscriptionFee() {
        return streamSubscriptionFee;
    }

    public int getStreamCurrentRevenue() {
        return streamCurrentRevenue;
    }

    protected void setStreamCurrentRevenue(int streamCurrentRevenue) {
        this.streamCurrentRevenue = streamCurrentRevenue;
    }

    public int getStreamPreviousRevenue() {
        return streamPreviousRevenue;
    }

    protected void setStreamPreviousRevenue(int streamPreviousRevenue) {
        this.streamPreviousRevenue = streamPreviousRevenue;
    }

    public int getStreamTotalRevenue() {
        return streamTotalRevenue;
    }

    protected void setStreamTotalRevenue(int streamTotalRevenue) {
        this.streamTotalRevenue = streamTotalRevenue;
    }

    public int getStreamLicensingFee() {
        return streamLicensingFee;
    }

    void setStreamLicensingFee(int streamLicensingFee) {
        this.streamLicensingFee = streamLicensingFee;
    }
}