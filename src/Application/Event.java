package Application;

import java.util.List;

public class Event {
    private String eventFullName;
    private String eventType;
    private String eventStudioOwner;
    private int eventYear;
    private int eventDuration;
    private int eventLicenseFee;

    public Event(String eventFullName, String eventType, String eventStudioOwner, int eventYear, int eventDuration, int eventLicenseFee) {
        this.eventFullName = eventFullName;
        this.eventType = eventType;
        this.eventStudioOwner = eventStudioOwner;
        this.eventYear = eventYear;
        this.eventDuration = eventDuration;
        this.eventLicenseFee = eventLicenseFee;
    }

    public String getEventFullName() {
        return eventFullName;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventStudioOwner() {
        return eventStudioOwner;
    }

    public int getEventYear() {
        return eventYear;
    }

    public int getEventDuration() {
        return eventDuration;
    }

    public int getEventLicenseFee() {
        return eventLicenseFee;
    }
}
