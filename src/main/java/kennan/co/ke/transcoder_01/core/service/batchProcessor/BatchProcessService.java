package kennan.co.ke.transcoder_01.core.service.batchProcessor;

import kennan.co.ke.transcoder_01.DI.DIConfiguration;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.DirectoryMapper;
import kennan.co.ke.transcoder_01.repository.RepositoryTranscode.TranscodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class BatchProcessService {

    private BatchProcessService(Queue<DirectoryMapper> mappers) {
        log.info(" Batch process created ==========");
        this.mappers = mappers;
    }

    private final Queue<DirectoryMapper> mappers;
    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DIConfiguration.class);
    private final TranscodeRepository repository = context.getBean(TranscodeRepository.class);
    private static final Logger log = LoggerFactory.getLogger(BatchProcessService.class);


    public static BatchProcessService createWithMappers(Queue<DirectoryMapper> mappers){
        return new BatchProcessService(mappers);
    }

    public void then() throws IOException {

        log.debug(" Mappers in Batch " + mappers.size());
        while (!mappers.isEmpty()){
            DirectoryMapper mapper = mappers.remove();

            Media media = new Media();
            media.setDirectory(mapper.getToDirectory() +"/");
            media.setName("media"+ mapper.getExtention(mapper.getName()));

            Thread batchThread = new Thread(new Batch(media));
            batchThread.start();
        }
    }


    private  class Batch implements Runnable {

        private Batch(Media media){
            this.media = media;
        }

        private final Media media;
        @Override
        public void run() {
            log.info("Batch " + Thread.currentThread().getName() + " started");
            try {
                this.write();
            } catch (PathNotFoundException | InterruptedException e) {
                log.info("Batch " + Thread.currentThread().getName() + " terminated");
                e.printStackTrace();
            }
        }

        private void write() throws PathNotFoundException, InterruptedException {
            repository.dispatch(media);
        }
    }


    public static void main(String[] args) {
        Queue<DirectoryMapper> queue = new LinkedList<>();
        queue.add(new DirectoryMapper(
                "media.mp4",
                Paths.get("uploads/media_example/media_example.mp4"),
                "C:\\Apache24\\mediafilesystem\\P010538\\99\\video\\142"));

        queue.add(new DirectoryMapper(
                "media.mp4",
                Paths.get("uploads/media_example/media_example.mp4"),
                "C:\\Apache24\\mediafilesystem\\P010538\\109\\video\\172"));

        queue.add(new DirectoryMapper(
                "media.mp4",
                Paths.get("uploads/media_example/media_example.mp4"),
                "C:\\Apache24\\mediafilesystem\\P010556\\104\\video\\170"));

        try {
            BatchProcessService.createWithMappers(queue).then();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
