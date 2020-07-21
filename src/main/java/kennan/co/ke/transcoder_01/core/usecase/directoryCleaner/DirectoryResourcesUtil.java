package kennan.co.ke.transcoder_01.core.usecase.directoryCleaner;


import kennan.co.ke.transcoder_01.core.entity.DirectoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;

/**
 *
 * A util responsible for cleaning a directory, or copying content from to a directory
 */
public class DirectoryResourcesUtil {
    private final Queue<DirectoryMapper> mappers;
    private static final Logger log = LoggerFactory.getLogger(DirectoryResourcesUtil.class);

    private DirectoryResourcesUtil(Queue<DirectoryMapper> mappers) {
        this.mappers = mappers;
    }

    public static DirectoryResourcesUtil create(Queue<DirectoryMapper> mappers) {
        return new DirectoryResourcesUtil(mappers);
    }


    public void clean() throws IOException {
        log.info(" Started well with items" + mappers.size());
        while (!mappers.isEmpty()) {
            DirectoryMapper container = mappers.poll();
            if (createDirectory(container.getToDirectory()))
                log.info("Dir created");
            else
                log.info("Maybe exist");
            if (Files.exists(container.getFromFile()) && new File(container.getToDirectory()).exists() && move(container) != null )
                log.info("File moved to " + container.getToDirectory());

        }
    }


    public void copy() throws IOException {
        log.info(" Started well with items" + mappers.size());
        while (!mappers.isEmpty()) {
            DirectoryMapper container = mappers.poll();
            if (createDirectory(container.getToDirectory()))
                log.info("Dir created");
            else
                log.info("Maybe exist");
            if (Files.exists(container.getFromFile()) && new File(container.getToDirectory()).exists() ) {
                copyDirectory(container);
                log.info("File moved to " + container.getToDirectory());
            }
        }
    }

    private static boolean createDirectory(String directory) {
        System.out.println("creating dir : " + directory);
        return new File(directory).mkdirs();
    }

    private static Path move(DirectoryMapper mapper) throws IOException {
        return Files.copy(mapper.getFromFile(), Paths.get(mapper.getToFilePath()));
    }


    private static void copyDirectory(DirectoryMapper mapper) throws IOException {
        org.apache.commons.io.FileUtils.copyDirectory(mapper.getFromFile().toFile(), new File(mapper.getToDirectory()));
    }



    public static void main(String[] args) throws Exception {
//        Queue<DirectoryMapper> queue = new LinkedList<>();
//        queue.add(new DirectoryMapper(
//                "media.mp4",
//                Paths.get("uploads/media_example/media_example.mp4"),
//                "work/12/video/"));
//
//        queue.add(new DirectoryMapper(
//                "media.mp4",
//                Paths.get("uploads/media_example/media_example.mp4"),
//                "work/16/video/"));
//
//        queue.add(new DirectoryMapper(
//                "media.mp4",
//                Paths.get("uploads/media_example/media_example.mp4"),
//                "work/14/video/"));

//        DirectoryCleanerService.create(queue).run();

    }

}
