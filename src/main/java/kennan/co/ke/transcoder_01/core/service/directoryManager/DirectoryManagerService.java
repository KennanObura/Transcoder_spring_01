package kennan.co.ke.transcoder_01.core.service.directoryManager;


import kennan.co.ke.transcoder_01.api.FakeRestController;
import kennan.co.ke.transcoder_01.core.model.DirectoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class DirectoryManagerService {
    private final Queue<DirectoryMapper> mappers;
    private static final Logger log = LoggerFactory.getLogger(DirectoryManagerService.class);

    private DirectoryManagerService(Queue<DirectoryMapper> mappers) {
        this.mappers = mappers;
    }

    public static DirectoryManagerService create(Queue<DirectoryMapper> mappers) {
        return new DirectoryManagerService(mappers);
    }


    public void run() throws IOException {
        log.info(" Started well with items" + mappers.size());
        while (!mappers.isEmpty()) {
            DirectoryMapper container = mappers.poll();
            if (createDirectory(container.getToDirectory()))
                log.info("Dir created");
            else
                log.info("Maybe exist");
            if (new File(container.getToDirectory()).exists() && move(container) != null)
                log.info("File moved to" + container.getToDirectory());
        }
    }

    public boolean createDirectory(String directory) {
        System.out.println("creating dir : " + directory);
        return new File(directory).mkdirs();
    }

    private Path move(DirectoryMapper mapper) throws IOException {
        return Files.copy(mapper.getFromFile(), Paths.get(mapper.getToFilePath()));
    }


    public static void main(String[] args) throws Exception {
        Queue<DirectoryMapper> queue = new LinkedList<>();
        queue.add(new DirectoryMapper(
                "media.mp4",
                Paths.get("uploads/media_example/media_example.mp4"),
                "work/12/video/"));

        queue.add(new DirectoryMapper(
                "media.mp4",
                Paths.get("uploads/media_example/media_example.mp4"),
                "work/16/video/"));

        queue.add(new DirectoryMapper(
                "media.mp4",
                Paths.get("uploads/media_example/media_example.mp4"),
                "work/14/video/"));

        DirectoryManagerService.create(queue).run();

    }

}
