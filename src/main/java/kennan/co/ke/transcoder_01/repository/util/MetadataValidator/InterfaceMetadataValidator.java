package kennan.co.ke.transcoder_01.repository.util.MetadataValidator;

import javafx.util.Pair;
import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.core.entity.Media;

import java.io.IOException;
import java.text.ParseException;

public interface InterfaceMetadataValidator {

    boolean isTimeRangeValid(Media media, Pair<String, String> range) throws ParseException, IOException, InvalidTimeRangeException;

    boolean isMediaInDisk();


}
