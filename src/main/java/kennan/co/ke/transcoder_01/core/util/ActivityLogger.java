package kennan.co.ke.transcoder_01.core.util;

import kennan.co.ke.transcoder_01.core.entity.LogMessageType;

import java.io.*;

/**
 *
 * A worker class responsible for writing a log file
 */
public class ActivityLogger {
    public ActivityLogger(
            String message,
            LogMessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }


    private final LogMessageType messageType;
    private final String message;
    private final static String filePathName = "logs/" + AppDateTime.dateOnly() + "/log.txt";
//    private final Queue<String> fileMessageQueue = new LinkedList<>();


    public void run() {
        this.write();
    }

    private void write() {
        if (createLogFile())
            try (FileWriter fileWriter = new FileWriter(filePathName, true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter out = new PrintWriter(bufferedWriter)) {
                out.println(generateReadableMessage());
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    }


    private String generateReadableMessage() {
        return this.messageType.toString() + " :: "
                + AppDateTime.getFull() + " ~~~ "
                + message + "\r";
    }

    private static boolean createDirectory(String directory) {
        return new File(directory).mkdirs();
    }



    private static boolean createLogFile() {
        createDirectory("logs/");
        createDirectory("logs/" + AppDateTime.dateOnly());

        try {
            File file = new File(filePathName);
            if (file.createNewFile())
                System.out.println("File created: " + file.getName());
            else
                System.out.println("File already exists.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

        String message = "Oho yes, works like charm";
        ActivityLogger log = new ActivityLogger(message, LogMessageType.SUCCESS);
        log.run();

        System.out.println();
    }
}
