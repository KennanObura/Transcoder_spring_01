package kennan.co.ke.transcoder_01.core.usecase.transcoder.sprint.tileGenerator;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public interface InterfaceTileGenerator {
    public boolean thumbnailsMerged(String outputDirectory, TreeMap<Integer, File> dirContents) throws IOException;
}
