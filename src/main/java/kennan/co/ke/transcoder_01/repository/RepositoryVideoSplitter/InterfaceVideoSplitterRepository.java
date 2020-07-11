package kennan.co.ke.transcoder_01.repository.RepositoryVideoSplitter;

import javafx.util.Pair;
import kennan.co.ke.transcoder_01.core.entity.Media;


public interface InterfaceVideoSplitterRepository {
    boolean isInputValid(
            Media media,
            Pair<String, String> metadata);
}
