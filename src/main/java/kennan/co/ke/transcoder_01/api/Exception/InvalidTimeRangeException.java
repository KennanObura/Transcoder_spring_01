package kennan.co.ke.transcoder_01.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Time Range Not Valid") //404
public class InvalidTimeRangeException extends Exception {

    public static InvalidTimeRangeException createWith(String time) {
        return new InvalidTimeRangeException(time);
    }
    private InvalidTimeRangeException(String time) {
        super("InvalidTimeRangeException with " + time);
    }
}
