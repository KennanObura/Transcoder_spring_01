package kennan.co.ke.transcoder_01.core.usecase.transcoder.base;

import java.io.IOException;

public interface TranscoderInterface {

    void write() throws IOException;

    boolean createDirectory(String directory);

}
