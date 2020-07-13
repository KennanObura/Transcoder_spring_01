package kennan.co.ke.transcoder_01.core.util;

import kennan.co.ke.transcoder_01.core.entity.LogMessageType;

import java.io.*;

/**
 *
 * A worker class responsible for writing a log file
 */
public class ActivityLogger {
    private ActivityLogger(
            String message,
            LogMessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }


    public static ActivityLogger createWith(
            String message,
            LogMessageType messageType) {
        return new ActivityLogger(message, messageType);
    }

    private final LogMessageType messageType;
    private final String message;
    private final static String filePathName = "logs/" + AppDateTime.getDateOnly() + "/log.txt";



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
                + AppDateTime.getFullDateTime() + " ~~~ "
                + message + "\r";
    }

    private static boolean createDirectory(String directory) {
        return new File(directory).mkdirs();
    }



    private static boolean createLogFile() {
        createDirectory("logs/");
        createDirectory("logs/" + AppDateTime.getDateOnly());

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
        ActivityLogger.createWith(message, LogMessageType.SUCCESS)
                .run();

        System.out.println();
    }
}
