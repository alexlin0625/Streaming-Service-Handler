package Application;

public class StreamingService {
    private String streamShortName;
    private String streamLongName;
    private int streamSubscriptionFee;
    private int streamCurrentRevenue;
    private int streamPreviousRevenue;
    private int streamTotalRevenue;
    private int[] streamLicensing;
    private final int LIMIT_STREAMS = 10;

    public StreamingService(String streamShortName, String streamLongName, int streamSubscriptionFee) {
        this.streamShortName = streamShortName;
        this.streamLongName = streamLongName;
        this.streamSubscriptionFee = streamSubscriptionFee;
        streamCurrentRevenue = 0;
        streamPreviousRevenue = 0;
        streamTotalRevenue = 0;
        streamLicensing = new int[LIMIT_STREAMS];
    }
}