package kennan.co.ke.transcoder_01.core.usecase.transcoder.base;

import java.io.IOException;

public interface TranscoderInterface {

    void write() throws IOException, InterruptedException;

    boolean createDirectory(String directory);

}
