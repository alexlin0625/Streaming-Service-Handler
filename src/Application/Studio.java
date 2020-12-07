package Application;

public class Studio {
    private final String shortName;
    private final String longName;
    private int studioCurrentRevenue;
    private int studioPreviousRevenue;
    private int studioTotalRevenue;

    public Studio (String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
        this.studioCurrentRevenue = 0;
        this.studioPreviousRevenue = 0;
        this.studioTotalRevenue = 0;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public int getStudioCurrentRevenue() {
        return studioCurrentRevenue;
    }

    protected void setStudioCurrentRevenue(int studioCurrentRevenue) {
        this.studioCurrentRevenue = studioCurrentRevenue;
    }

    public int getStudioPreviousRevenue() {
        return studioPreviousRevenue;
    }

    protected void setStudioPreviousRevenue(int studioPreviousRevenue) {
        this.studioPreviousRevenue = studioPreviousRevenue;
    }

    public int getStudioTotalRevenue() {
        return studioTotalRevenue;
    }

    protected void setStudioTotalRevenue(int studioTotalRevenue) {
        this.studioTotalRevenue = studioTotalRevenue;
    }
}
