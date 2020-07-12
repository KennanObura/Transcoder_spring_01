package kennan.co.ke.transcoder_01.api;


import kennan.co.ke.transcoder_01.api.Error.ApiError;
import kennan.co.ke.transcoder_01.api.Exception.CoreCustomException;
import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.api.Exception.ProcessTerminatedException;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.repository.RepositoryVideoSplitter.VideoSplitterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VideoSplitterController {

    private final VideoSplitterRepository repository = new VideoSplitterRepository();

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


    @ExceptionHandler(ProcessTerminatedException.class)
    public ResponseEntity<ApiError> handleProcessTerminatedException(ProcessTerminatedException exception) {
        List<String> errorMessages = exception.getErrors()
                .stream()
                .map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ApiError(errorMessages), HttpStatus.BAD_REQUEST);
    }
}
