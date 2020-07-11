package kennan.co.ke.transcoder_01.api.Exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class InvalidTimeRangeException extends Exception{
    private final List<ObjectError> errors;

    public static InvalidTimeRangeException createWith(List<ObjectError> errors) {
        return new InvalidTimeRangeException(errors);
    }

    public InvalidTimeRangeException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}
