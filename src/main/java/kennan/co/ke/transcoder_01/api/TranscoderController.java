package kennan.co.ke.transcoder_01.api;

import kennan.co.ke.transcoder_01.DI.DIConfiguration;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.repository.RepositoryTranscode.TranscodeRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class TranscoderController {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DIConfiguration.class);
    private final TranscodeRepository repository = context.getBean(TranscodeRepository.class);

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<String> index(
            @RequestBody Media media) throws
            InterruptedException, PathNotFoundException {
        repository.dispatch(media);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
