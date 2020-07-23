package kennan.co.ke.transcoder_01.repository.common.MetadataValidator;

import javafx.util.Pair;
import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.core.entity.Media;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MetadataValidator  {



    public static boolean isTimeRangeValid(Media media, Pair<String, String> range) throws ParseException, IOException, InvalidTimeRangeException {

        if (range.getKey().isEmpty() || range.getValue().isEmpty())
            throw InvalidTimeRangeException.createWith("Empty starttime or endtime");


        if (!isValidFormat(range.getKey()) || !isValidFormat(range.getValue()))
            throw InvalidTimeRangeException.createWith("Invalid time format, provide in [hh:mm:ss]");


        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date startTime = dateFormat.parse(range.getKey());
        Date endTime = dateFormat.parse(range.getValue());
        Date mediaDuration = dateFormat.parse(durationChecker(media));

        return startTime.before(mediaDuration) && (endTime.before(mediaDuration) || endTime.equals(mediaDuration));
    }

    private static boolean isValidFormat(String timeInPut) {
        String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
        Matcher matcher = pattern.matcher(timeInPut);
        return matcher.matches();
    }


    public static long getDuration(Media media) throws IOException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date reference = dateFormat.parse("00:00:00");
        Date date = dateFormat.parse(durationChecker(media));
        return (date.getTime() - reference.getTime()) / 1000L;
    }

    public boolean isMediaInDisk() {
        return false;
    }


    private static String durationChecker(Media media) throws IOException {

        String cmd = "ffprobe -v error -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 "
                + media.getDirectory() + media.getName() + " -sexagesimal";

        Process result = Runtime.getRuntime().exec(cmd.split(" "));

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(result.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(result.getErrorStream()));

        if (stdError.readLine() != null)
            return null;

        String duration = stdInput.readLine();
        System.out.println(duration + "==t==");
        return duration;

    }


}
