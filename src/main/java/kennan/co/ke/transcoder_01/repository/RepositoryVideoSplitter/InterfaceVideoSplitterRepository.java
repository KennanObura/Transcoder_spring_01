package kennan.co.ke.transcoder_01.repository.RepositoryVideoSplitter;

import javafx.util.Pair;
import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.core.entity.Media;

import java.io.IOException;
import java.text.ParseException;


public interface InterfaceVideoSplitterRepository {
    boolean isInputValid(
            Media media,
            Pair<String, String> metadata) throws IOException, ParseException, InvalidTimeRangeException;


}
