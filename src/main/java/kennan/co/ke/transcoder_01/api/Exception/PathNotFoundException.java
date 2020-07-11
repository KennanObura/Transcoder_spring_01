package kennan.co.ke.transcoder_01.api.Exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class PathNotFoundException extends Exception{
    private final List<ObjectError> errors;

    public static PathNotFoundException createWith(List<ObjectError> errors) {
        return new PathNotFoundException(errors);
    }

    private PathNotFoundException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}
