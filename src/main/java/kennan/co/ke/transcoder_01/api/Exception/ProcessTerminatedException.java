package kennan.co.ke.transcoder_01.api.Exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class ProcessTerminatedException extends Exception{
    private final List<ObjectError> errors;

    public static ProcessTerminatedException createWith(List<ObjectError> errors) {
        return new ProcessTerminatedException(errors);
    }

    private ProcessTerminatedException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}
