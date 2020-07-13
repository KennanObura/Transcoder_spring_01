package kennan.co.ke.transcoder_01.api;


import kennan.co.ke.transcoder_01.DI.DIConfiguration;
import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.repository.RepositoryVideoSplitter.VideoSplitterRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;


@RestController
public class VideoSplitterController {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DIConfiguration.class);
    private final VideoSplitterRepository repository = context.getBean(VideoSplitterRepository.class);


    @PostMapping(
            value = "/split",
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> index(
            @RequestBody Media media,
            @RequestParam String starttime,
            @RequestParam String endtime,
            @RequestParam Integer sort) throws
            InvalidTimeRangeException, PathNotFoundException, IOException, ParseException {
        repository.dispatch(media, starttime, endtime, String.valueOf(sort));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
