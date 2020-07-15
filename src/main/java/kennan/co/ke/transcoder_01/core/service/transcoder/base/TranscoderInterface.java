package kennan.co.ke.transcoder_01.core.service.transcoder.base;

import java.io.IOException;

public interface TranscoderInterface {

    void write() throws IOException;

    boolean createDirectory(String directory);

}
