package kennan.co.ke.transcoder_01.core.usecase.batchProcessor;

import kennan.co.ke.transcoder_01.DI.DIConfiguration;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.entity.DirectoryMapper;
import kennan.co.ke.transcoder_01.repository.RepositoryTranscode.TranscodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Queue;

/**
 * A Batch initializer. This class basically takes a Queue of @DirectoryMapper and distributes them to own thread
 * then passes then to Transcoder repository, service for processing
 */
public class BatchProcess {

    private BatchProcess(Queue<DirectoryMapper> mappers) {
        this.mappers = mappers;
    }

    private final Queue<DirectoryMapper> mappers;
    private static final TranscodeRepository repository = (new AnnotationConfigApplicationContext(DIConfiguration.class))
            .getBean(TranscodeRepository.class);
    private static final Logger log = LoggerFactory.getLogger(BatchProcess.class);


    public static BatchProcess createWithDirectoryMappers(Queue<DirectoryMapper> mappers) {
        return new BatchProcess(mappers);
    }


    public void thenExecute() throws IOException {
        while (!mappers.isEmpty())
            configureMediaAndStartBatchRunner(mappers.remove());
    }


    private static void configureMediaAndStartBatchRunner(DirectoryMapper mapper) throws IOException {
        Media media = new Media();
        media.setDirectory(mapper.getToDirectory());
        media.setName("media" + mapper.getExtention(mapper.getFromName()));

        Thread batchThread = new Thread(new BatchRunner(media));
        batchThread.start();
    }

    private static class BatchRunner implements Runnable {

        private BatchRunner(Media media) {
            this.media = media;
        }

        private final Media media;

        @Override
        public void run() {
            log.info("Batch " + Thread.currentThread().getName() + " started: Processing "
                    + media.getDirectory() + media.getName());
            try {
                this.write();
            } catch (PathNotFoundException | InterruptedException e) {
                log.info("Batch " + Thread.currentThread().getName() + " terminated while processing"
                        + media.getDirectory() + media.getName());
                e.printStackTrace();
            }
        }

        /**
         * Pass/ dispatch the process to repository as a nested thread
         *
         * @throws PathNotFoundException if the path to the file is not found
         * @throws InterruptedException  if the thread is interrupted in anyway when still alive
         */

        private void write() throws PathNotFoundException, InterruptedException {
            repository.dispatch(media);
        }
    }


    public static void main(String[] args) {
//        Queue<DirectoryMapper> queue = new LinkedList<>();
//        queue.add(new DirectoryMapper(
//                "media.mp4",
//                Paths.get("uploads/media_example/media_example.mp4"),
//                "mediafilesystem\\P010538\\99\\video\\142/"));
//
//        queue.add(new DirectoryMapper(
//                "media.mp4",
//                Paths.get("uploads/media_example/media_example.mp4"),
//                "mediafilesystem\\P010538\\109\\video\\172/"));
//
//        queue.add(new DirectoryMapper(
//                "media.mp4",
//                Paths.get("uploads/media_example/media_example.mp4"),
//                "mediafilesystem\\P010556\\104\\video\\170/"));

//        try {
//            BatchProcessService.createWithDirectoryMappers(queue).thenExecute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
