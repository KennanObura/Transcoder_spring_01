package kennan.co.ke.transcoder_01.repository.util.MetadataValidator;

import javafx.util.Pair;
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


public class MetadataValidator implements InterfaceMetadataValidator {

    public MetadataValidator(Media media) {
        this.media = media;
    }

    private final Media media;


    @Override
    public boolean isTimeRangeValid(Pair<String, String> range) throws ParseException, IOException {

        if (range.getKey().isEmpty() || range.getValue().isEmpty())
            throw new ParseException("Empty starttime or endtime", 1);

        if (!isValidFormat(range.getKey()) || !isValidFormat(range.getValue()))
            throw new ParseException("Time specified is out of range", 1);

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date startTime = dateFormat.parse(range.getKey());
        Date endTime = dateFormat.parse(range.getValue());
        Date mediaDuration = dateFormat.parse(checker());

        return startTime.before(mediaDuration) && (endTime.before(mediaDuration) || endTime.equals(mediaDuration));
    }

    private boolean isValidFormat(String timeInPut) {
        String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
        Matcher matcher = pattern.matcher(timeInPut);
        return matcher.matches();
    }

    @Override
    public boolean isMediaInDisk() {
        return false;
    }


    private String checker() throws IOException {
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
