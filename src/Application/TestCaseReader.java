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

    public TestCaseReader() {
        numDemos = 0;
        demographicGroups = new DemoGroup[LIMIT_DEMO];

        numStudios = 0;
        studios = new Studio[LIMIT_STUDIO];

        numStreams = 0;
        streamingServices = new StreamingService[LIMIT_STREAMS];

        numEvents = 0;
        events = new Event[LIMIT_EVENTS];
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
                    if(verboseMode) {
                        System.out.println("create_demo_acknowledged");
                        if(numDemos >= LIMIT_DEMO) {
                            continue;
                        }
                        DemoGroup newGroup = new DemoGroup(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                        demographicGroups[numDemos] = newGroup;
                        numDemos++;
                    }
                } else if(tokens[0].equals("create_studio")) {
                    if(verboseMode) {
                        System.out.println("create_studio_acknowledged");
                        if(numStudios >= LIMIT_STUDIO) {
                            continue;
                        }
                        Studio newStudio = new Studio(tokens[1], tokens[2]);
                        studios[numStudios] = newStudio;
                        numStudios++;
                    }
                } else if(tokens[0].equals("create_stream")) {
                    if(verboseMode) {
                        System.out.println("create_stream_acknowledged");
                        if(numStreams >= LIMIT_STUDIO) {
                            continue;
                        }
                        StreamingService newStream = new StreamingService(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                        streamingServices[numStreams] = newStream;
                        numStreams++;
                    }
                } else if(tokens[0].equals("create_event")) {
                    if(verboseMode) {
                        System.out.println("create_event_acknowledged");
                        if(numEvents >= LIMIT_EVENTS) {
                            continue;
                        }
                        Event newEvent = new Event(tokens[1], tokens[2], tokens[3],
                                Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                        events[numEvents] = newEvent;
                        numEvents++;
                    }
                } else if(tokens[0].equals("offer_event")) {
                    if(verboseMode) {
                        System.out.println("offer_event_acknowledged");
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Invalid Command");
            }
        }
    }
}
