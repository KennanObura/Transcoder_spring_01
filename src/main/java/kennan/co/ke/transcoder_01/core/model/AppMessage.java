package kennan.co.ke.transcoder_01.core.model;

import kennan.co.ke.transcoder_01.core.entity.AppEvent;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.LogMessageType;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.util.ActivityLogger;


/**
 * A representation of Event messages resulting from app activities
 * Maps Events and Processes to generate readable texts [String] consumed by {@ActivityLogger}
 * Events are either INITIALIZING, INPROGRESS, FINALIZING or TERMINATED
 */

public class AppMessage {
    public AppMessage(
            Media media,
            AppProcess process,
            LogMessageType messageType) {
        this.process = process;
        this.messageType = messageType;
        this.media = media;
    }


    private final AppProcess process;
    private final LogMessageType messageType;
    private final Media media;

    private String whichEvent(AppEvent event) {
        switch (event) {
            case TERMINATED:
                return "Process " + this.process + " of " + media.getName() + " terminated :- ";
            case INPROGRESS:
                return "Process " + this.process + " of " + media.getName() + " in progress...";
            case FINALIZING:
                return "Finalizing " + this.process + " of " + media.getName() + ". files written to /" + media.getDirectory();
            default:
                return "Generating " + this.process + " of " + media.getName();
        }
    }

    public void write(AppEvent event) {
        dumpToLogger(whichEvent(event));
    }

    public void write(AppEvent event, String exceptionString) {
        dumpToLogger(whichEvent(event) + exceptionString);
    }

    private void dumpToLogger(String message) {
        ActivityLogger activityLogger = new ActivityLogger(message, messageType);
        activityLogger.run();
    }
}
