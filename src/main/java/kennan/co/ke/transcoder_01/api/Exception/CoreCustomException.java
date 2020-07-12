package kennan.co.ke.transcoder_01.api.Exception;


public class CoreCustomException extends Exception{
    private final String errors;

    public static CoreCustomException createWith(String errors) {
        return new CoreCustomException(errors);
    }

    public CoreCustomException(String errors) {
        this.errors = errors;
    }

    public String getErrors() {
        return errors;
    }
}
