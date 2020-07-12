package kennan.co.ke.transcoder_01.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Path Provided Not Valid") //404
public class PathNotFoundException extends Exception {
    public static PathNotFoundException createWith(String pathToFile) {
        return new PathNotFoundException(pathToFile);
    }
    private PathNotFoundException(String pathToFile) {
        super("PathNotFoundException with " + pathToFile);
    }
}
