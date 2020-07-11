package kennan.co.ke.transcoder_01.api;

import kennan.co.ke.transcoder_01.api.Error.ApiError;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.api.Exception.ProcessTerminatedException;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.repository.RepositoryTranscode.TranscodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TranscoderController {

    private final TranscodeRepository repository = new TranscodeRepository();

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<String> index(
            @RequestBody Media media) throws
            PathNotFoundException,
            ProcessTerminatedException,
            InterruptedException {
        repository.dispatch(media);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @ExceptionHandler(PathNotFoundException.class)
    public ResponseEntity<ApiError> handlePathNotFoundException(PathNotFoundException exception) {
        List<String> errorMessages = exception.getErrors()
                .stream()
                .map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ApiError(errorMessages), HttpStatus.BAD_REQUEST);
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
