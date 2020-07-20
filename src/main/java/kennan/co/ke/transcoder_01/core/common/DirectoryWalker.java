package kennan.co.ke.transcoder_01.core.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryWalker {

    public static List<File> getContentsFromDirectory(String contentDirectory) throws IOException {
        return Files.walk(Paths.get(contentDirectory))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }
}
