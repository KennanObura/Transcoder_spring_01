package kennan.co.ke.transcoder_01.repository.response;

public class ResponseMessage<T> {
    public ResponseMessage(
            int code,
            Status status){
        this.code = code;
        this.status = status;
    }
    private  int code;
    private T data;
    private Status status;
}


