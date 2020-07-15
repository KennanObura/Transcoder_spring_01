package kennan.co.ke.transcoder_01.core.service.imageSelection;

import kennan.co.ke.transcoder_01.core.model.MediaModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ThumbnailSelectionService {
    final private String contentDirectory;

    private ThumbnailSelectionService(String directory) {
        this.contentDirectory = directory;
    }

    public ThumbnailSelectionService create(String contentDirectory) {
        return new ThumbnailSelectionService(contentDirectory);
    }


    private List<File> getContentsFromDirectory() throws IOException {
        List<File> filesInFolder = Files.walk(Paths.get(contentDirectory))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        return filesInFolder;
    }

    private void getResolution(){

    }

}
