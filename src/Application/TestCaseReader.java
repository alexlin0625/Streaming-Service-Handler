package Application;
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

                    DemoGroup newGroup = new DemoGroup(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                    demographicGroups[numDemos] = newGroup;
                    numDemos++;

                } else if(tokens[0].equals("create_studio")) {
                    if(verboseMode) { System.out.println("create_studio_acknowledged"); }
                    if(numStudios >= LIMIT_STUDIO) { continue; }

                    Studio newStudio = new Studio(tokens[1], tokens[2]);
                    studios[numStudios] = newStudio;
                    numStudios++;

                } else if(tokens[0].equals("create_stream")) {
                    if(verboseMode) { System.out.println("create_stream_acknowledged"); }
                    if(numStreams >= LIMIT_STUDIO) { continue; }

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
                    // type, stream, event name, event year, ppv price(if not movie)
                    offerTransaction newOffer = new offerTransaction(offerType, tokens[2], tokens[3], Integer.parseInt(tokens[4]));
                    // set price for pay_per_view offer
                    if(offerType.equals("ppv")) {
                        newOffer.setPayPerViewOfferPrice(Integer.parseInt(tokens[5]));
                    }

                    // stream service must license the event from studio
                    String payStudio = "";
                    int payLicenceFee = 0;
                    // search the events by matching event name and year
                    for(Event event : events) {
                        if(event.getEventFullName().equals(tokens[2]) && event.getEventYear() == Integer.parseInt(tokens[4])) {
                            payStudio = event.getEventStudioOwner();
                            payLicenceFee = event.getEventLicenseFee();
                            newOffer.setPrice(payLicenceFee); // set license fee price in transaction
                        }
                    }
                    // add offer's transaction into record
                    offersTransactions[numOffers] = newOffer;
                    numOffers++;

                    // charge licence fee from stream service which event is offered to
                    for(StreamingService service : streamingServices) {
                        if(service.getStreamShortName().equals(tokens[1])) {
                            service.setStreamLicensingFee(service.getStreamLicensingFee()+payLicenceFee);
                        }
                    }
                    // studio which created the event gained licence fee charged from Steaming Service
                    for(Studio studio : studios) {
                        if(studio.getShortName().equals(payStudio)) {
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
                        if(offer.getOfferEventName().equals(tokens[3]) && offer.getOfferEventYear() == Integer.parseInt(tokens[4]) &&
                                offer.getOfferStream().equals(tokens[2])) {
                            offer_event = offer;
                            curr_stream_service_name = offer.getOfferStream();
                            event_type = offer.getOfferType();
                        }
                    }
                    // get target stream service and demo group
                    StreamingService stream = null;
                    DemoGroup demo = null;
                    for(StreamingService service : streamingServices) {
                        if(service.getStreamShortName().equals(curr_stream_service_name)) {
                            stream = service;
                        }
                    }
                    for(DemoGroup group : demographicGroups) {
                        if(group.getShortName().equals(tokens[1])) {
                            demo = group;
                        }
                    }
                    if(demo == null || stream == null) { continue; }

                    // get % of new subscribers
                    int currSubbedPercent = demo.getPercentageSubscribed();
                    int newSubPercentage = Integer.parseInt(tokens[5]) - currSubbedPercent;
                    if(newSubPercentage < 0) { continue; }

                    int watchViewingCost = 0;
                    int totalAccountsInGroup = demo.getDemoAccounts();
                    // calculate the fees based on percentage and event type
                    if(event_type.equals("movie")) {
                        watchViewingCost = ((newSubPercentage/100)*totalAccountsInGroup) * stream.getStreamSubscriptionFee();
                    } else if(event_type.equals("ppv")) {
                        watchViewingCost =  ((newSubPercentage/100)*totalAccountsInGroup) * offer_event.getPayPerViewOfferPrice();
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
                }
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Invalid Command");
            }
        }
    }
}
