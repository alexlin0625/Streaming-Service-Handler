package Application;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class TestCaseReader {
    private int numDemos;
    private DemoGroup[] demographicGroups;
    private final int LIMIT_DEMO = 10;

    private int numStudios;
    private Studio[] studios;
    private final int LIMIT_STUDIO = 10;

    private int numStreams;
    private StreamingService[] streamingServices;
    private final int LIMIT_STREAMS = 10;

    private int numEvents;
    private Event[] events;
    private final int LIMIT_EVENTS = 10;

    private int numOffers;
    private offerTransaction[] offersTransactions;
    private final int LIMIT_OFFERS = 10;

    private int numWatches;
    private watchTransaction[] watchTransactions;
    private final int LIMIT_WATCHES = 10;

    // transactions cache: "month+year",
    private Map<String, offerTransaction[]> offerTransactionsCache;
    private Map<String, watchTransaction[]> watchTransactionsCache;

    // testing purpose for time stamp
    private int monthTimeStamp;
    private int yearTimeStamp;

    public TestCaseReader() {
        numDemos = 0;
        demographicGroups = new DemoGroup[LIMIT_DEMO];

        numStudios = 0;
        studios = new Studio[LIMIT_STUDIO];

        numStreams = 0;
        streamingServices = new StreamingService[LIMIT_STREAMS];

        numEvents = 0;
        events = new Event[LIMIT_EVENTS];

        numOffers = 0;
        offersTransactions = new offerTransaction[LIMIT_OFFERS];

        numWatches = 0;
        watchTransactions = new watchTransaction[LIMIT_WATCHES];

        offerTransactionsCache = new LinkedHashMap<>();
        watchTransactionsCache = new LinkedHashMap<>();

        monthTimeStamp = 12;
        yearTimeStamp = 2020;
    }

    public void processInstructions(Boolean verboseMode) {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while(true) {
            try {
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                if(tokens[0].equals("create_demo")) {
                    if(verboseMode) { System.out.println("create_demo_acknowledged"); }
                    if(numDemos >= LIMIT_DEMO) { continue; }
                    // short name, long name, accounts size
                    DemoGroup newGroup = new DemoGroup(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                    demographicGroups[numDemos] = newGroup;
                    numDemos++;

                } else if(tokens[0].equals("create_studio")) {
                    if(verboseMode) { System.out.println("create_studio_acknowledged"); }
                    if(numStudios >= LIMIT_STUDIO) { continue; }
                    // short name, long name
                    Studio newStudio = new Studio(tokens[1], tokens[2]);
                    studios[numStudios] = newStudio;
                    numStudios++;

                } else if(tokens[0].equals("create_stream")) {
                    if(verboseMode) { System.out.println("create_stream_acknowledged"); }
                    if(numStreams >= LIMIT_STUDIO) { continue; }
                    // short name, long name, subscription fee
                    StreamingService newStream = new StreamingService(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                    streamingServices[numStreams] = newStream;
                    numStreams++;

                } else if(tokens[0].equals("create_event")) {
                    if(verboseMode) { System.out.println("create_event_acknowledged"); }
                    if(numEvents >= LIMIT_EVENTS) { continue; }

                    // name, type, studio, year, duration, license_fee
                    Event newEvent = new Event(tokens[1], tokens[2], tokens[3],
                            Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                    events[numEvents] = newEvent;
                    numEvents++;

                } else if(tokens[0].equals("offer_movie") || tokens[0].equals("offer_ppv")) {
                    if(verboseMode) { System.out.println("offer_event_acknowledged"); }
                    if(numOffers >= LIMIT_EVENTS) { continue; }

                    String offerType = tokens[0].substring(6);
                    // type[0], stream, event name, event year, ppv price(if not movie)
                    offerTransaction newOffer = new offerTransaction(offerType, tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                    // set price for pay_per_view offer
                    if(offerType.equals("ppv")) {
                        newOffer.setPayPerViewOfferPrice(Integer.parseInt(tokens[4]));
                    }

                    // stream service must license the event from studio
                    String payStudio = "";
                    int payLicenceFee = 0;
                    // search the events by matching event name and year
                    for(Event event : events) {
                        if(event != null && event.getEventFullName().equals(tokens[2]) && event.getEventYear() == Integer.parseInt(tokens[3])) {
                            payStudio = event.getEventStudioOwner();
                            payLicenceFee += event.getEventLicenseFee();
                            newOffer.setPrice(payLicenceFee); // set license fee price in transaction
                        }
                    }
                    // add offer's transaction into record
                    offersTransactions[numOffers] = newOffer;
                    numOffers++;

                    // charge licence fee from stream service which event is offered to
                    for(StreamingService service : streamingServices) {
                        if(service != null && service.getStreamShortName().equals(tokens[1])) {
                            service.setStreamLicensingFee(service.getStreamLicensingFee()+payLicenceFee);
                        }
                    }
                    // studio which created the event gained licence fee charged from Steaming Service
                    for(Studio studio : studios) {
                        if(studio != null && studio.getShortName().equals(payStudio)) {
                            studio.setStudioCurrentRevenue(studio.getStudioCurrentRevenue()+payLicenceFee);
                            studio.setStudioTotalRevenue(studio.getStudioTotalRevenue()+payLicenceFee);
                        }
                    }

                } else if(tokens[0].equals("watch_event")) {
                    if(verboseMode) { System.out.println("watch_event_acknowledged"); }
                    if(numWatches >= LIMIT_WATCHES) { continue; }

                    // group, stream_service, event name, event year, %
                    watchTransaction newWatch = new watchTransaction(tokens[1], tokens[2], tokens[3],
                            Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));

                    String curr_stream_service_name = null;
                    String event_type = "";
                    offerTransaction offer_event = null;

                    // get corresponding stream service which current event(name/year) was offered to
                    for(offerTransaction offer : offersTransactions) {
                        if(offer != null && offer.getOfferEventName().equals(tokens[3]) &&
                                offer.getOfferEventYear() == Integer.parseInt(tokens[4]) &&
                                offer.getOfferStream().equals(tokens[2])) {
                            offer_event = offer;
                            curr_stream_service_name = offer.getOfferStream();
                            event_type = offer.getOfferType();
                        }
                    }

                    StreamingService stream = null;
                    DemoGroup demo = null;

                    // get target stream service and demo group
                    for(StreamingService service : streamingServices) {
                        if(service != null && service.getStreamShortName().equals(curr_stream_service_name)) {
                            stream = service;
                        }
                    }
                    for(DemoGroup group : demographicGroups) {
                        if(group != null && group.getShortName().equals(tokens[1])) {
                            demo = group;
                        }
                    }
                    if(demo == null || stream == null) { continue; }

                    // get % of new subscribers
                    int currSubbedPercent = demo.getPercentageSubscribed();
                    int newSubPercentage = Integer.parseInt(tokens[5]) - currSubbedPercent;
                    // if no extra subscribers
                    if(event_type.equals("movie") && newSubPercentage < 0) { continue; }

                    int watchViewingCost = 0;
                    int totalAccountsInGroup = demo.getDemoAccounts();
                    // calculate the fees based on percentage and event type
                    if(event_type.equals("movie")) {
                        watchViewingCost = (((newSubPercentage*totalAccountsInGroup)/100) * stream.getStreamSubscriptionFee());
                    } else if(event_type.equals("ppv")) {
                        watchViewingCost = (((newSubPercentage*totalAccountsInGroup)/100) * offer_event.getPayPerViewOfferPrice());
                    }

                    // update demo-group expense
                    demo.setDemoCurrentSpending(demo.getDemoCurrentSpending()+watchViewingCost);
                    demo.setDemoTotalSpending(demo.getDemoTotalSpending()+watchViewingCost);
                    // update stream service revenue
                    stream.setStreamCurrentRevenue(stream.getStreamCurrentRevenue()+watchViewingCost);
                    stream.setStreamTotalRevenue(stream.getStreamTotalRevenue()+watchViewingCost);
                    // update new percentage of subscribers of demo-group
                    demo.setPercentageSubscribed(Integer.parseInt(tokens[5]));

                    // set price and save to record
                    watchTransactions[numWatches] = newWatch;
                    numWatches++;

                } else if(tokens[0].equals("next_month")) {
                    if (verboseMode) { System.out.println("next_month_acknowledged"); }

                    // cache past offers and watch transactions. Then reset them for new month
                    String monthYear = ""+monthTimeStamp+yearTimeStamp;
                    offerTransactionsCache.put(monthYear, offersTransactions);
                    watchTransactionsCache.put(monthYear, watchTransactions);

                    // reset transactions for current month
                    offersTransactions = new offerTransaction[LIMIT_OFFERS];
                    watchTransactions = new watchTransaction[LIMIT_WATCHES];
                    numOffers = 0;
                    numWatches = 0;

                    // update current timestamp
                    if(monthTimeStamp == 12) { yearTimeStamp++; }
                    monthTimeStamp = monthTimeStamp%12 + 1;

                    // update current and previous dollar amounts
                    for(DemoGroup group : demographicGroups) {
                        if(group == null) { continue; }
                        group.setDemoPreviousSpending(group.getDemoCurrentSpending());
                        group.setDemoCurrentSpending(0);
                        group.setPercentageSubscribed(0);
                    }
                    for(StreamingService service : streamingServices) {
                        if(service == null) { continue; }
                        service.setStreamPreviousRevenue(service.getStreamCurrentRevenue());
                        service.setStreamTotalLicenseFee(service.getStreamTotalLicenseFee()+service.getStreamLicensingFee());
                        service.setStreamCurrentRevenue(0);
                        service.setStreamLicensingFee(0);
                    }
                    for(Studio studio : studios) {
                        if(studio == null) { continue; }
                        studio.setStudioPreviousRevenue(studio.getStudioCurrentRevenue());
                        studio.setStudioCurrentRevenue(0);
                    }

                } else if(tokens[0].equals("display_demo")) {
                    if (verboseMode) { System.out.println("display_demo_acknowledged"); }

                    for(DemoGroup group : demographicGroups) {
                        if(group != null && group.getShortName().equals(tokens[1])) {
                            group.displayInformation();
                        }
                    }

                } else if(tokens[0].equals("display_stream")) {
                    if (verboseMode) { System.out.println("display_stream_acknowledged"); }

                    for(StreamingService service : streamingServices) {
                        if(service != null && service.getStreamShortName().equals(tokens[1])) {
                            service.displayInformation();
                        }
                    }

                } else if(tokens[0].equals("display_studio")) {
                    if (verboseMode) { System.out.println("display_studio_acknowledged"); }
                    for(Studio studio : studios) {
                        if(studio != null && studio.getShortName().equals(tokens[1])) {
                            studio.displayInformation();
                        }
                    }

                } else if(tokens[0].equals("display_events")) {
                    if (verboseMode) { System.out.println("display_events_acknowledged"); }
                    List<List<String>> rows = new ArrayList<>();
                    List<String> headers = Arrays.asList("Type", "Name", "Year", "Duration", "Studio", "License Fee");
                    rows.add(headers);
                    for(Event event : events) {
                        if(event == null) { continue; }
                        rows.add(Arrays.asList(event.getEventType(), event.getEventFullName(), String.valueOf(event.getEventYear()),
                                String.valueOf(event.getEventDuration()), event.getEventStudioOwner(), String.valueOf(event.getEventLicenseFee())));
                    }
                    System.out.println(formatAsTable(rows));

                } else if(tokens[0].equals("display_offers")) {
                    if (verboseMode) { System.out.println("display_offers_acknowledged"); }
                    List<List<String>> rows = new ArrayList<>();
                    List<String> headers = Arrays.asList("Stream", "Type", "Name", "Year", "Pay-Per-View Price");
                    rows.add(headers);
                    for(offerTransaction offer : offersTransactions) {
                        if(offer == null) { continue; }
                        rows.add(Arrays.asList(offer.getOfferStream(), offer.getOfferType(), offer.getOfferEventName(),
                                ""+offer.getOfferEventYear(), ""+offer.getPayPerViewOfferPrice()));
                    }
                    System.out.println(formatAsTable(rows));

                } else if(tokens[0].equals("display_time")) {
                    if (verboseMode) { System.out.println("display_time_acknowledged"); }
                    System.out.println("Month: " + monthTimeStamp + ", " + "Year: " + yearTimeStamp);

                } else if(tokens[0].equals("quit") || tokens[0].equals("stop")) {
                    break;
                }
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Invalid Command");
            }
        }
    }
    public static String formatAsTable(List<List<String>> rows) {
        int[] maxLengths = new int[rows.get(0).size()];
        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++)
            {
                maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
            }
        }

        StringBuilder formatBuilder = new StringBuilder();
        for (int maxLength : maxLengths) {
            formatBuilder.append("%-").append(maxLength + 2).append("s");
        }
        String format = formatBuilder.toString();

        StringBuilder result = new StringBuilder();
        for (List<String> row : rows) {
            result.append(String.format(format, row.toArray(new String[0]))).append("\n");
        }
        return result.toString();
    }
}
