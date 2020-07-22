package kennan.co.ke.transcoder_01.core.model;

import kennan.co.ke.transcoder_01.core.entity.AppEvent;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.LogMessageType;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.common.ActivityLogger;


/**
 * A representation of Event messages resulting from app activities
 * Maps Events and Processes to generate readable texts [String] consumed by {@ActivityLogger}
 * Events are either INITIALIZING, INPROGRESS, FINALIZING or TERMINATED
 */

public class AppMessage {

    public static void write(AppEvent event, MediaContainer media, AppProcess process) {
        dumpToLogger(whichEvent(event, media, process), whichTypeOfMessage(event));
    }

    public static void write(AppEvent event, String exceptionString, MediaContainer media, AppProcess process) {
        dumpToLogger(whichEvent(event, media, process) + exceptionString, whichTypeOfMessage(event));
    }

    public static void write(AppEvent event, String exceptionString, Media media) {
        dumpToLogger(exceptionString, whichTypeOfMessage(event));
    }

    private static String whichEvent(AppEvent event, MediaContainer media, AppProcess process) {
        switch (event) {
            case TERMINATED:
                return "Process " + process + " of " + media.getMedia().getName() + " terminated :- ";
            case INPROGRESS:
                return "Process " + process + " of " + media.getMedia().getName() + " in progress...";
            case FINALIZING:
                return "Finalizing " + process + " of " + media.getMedia().getName() + ". files written to /" + media.getMasterDirectory();
            default:
                return "Generating " + process + " of " + media.getMedia().getName();
        }
    }

    private static LogMessageType whichTypeOfMessage(AppEvent event) {
        switch (event) {
            case FINALIZING:
                return LogMessageType.SUCCESS;
            case TERMINATED:
                return LogMessageType.ERROR;
            default:
                return LogMessageType.INFO;
        }
    }

    private static void dumpToLogger(String message, LogMessageType messageType) {
        ActivityLogger.createWith(message, messageType).run();
    }
}
