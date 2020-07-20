package kennan.co.ke.transcoder_01.repository.DirectoryCleaner;

import kennan.co.ke.transcoder_01.core.usecase.batchProcessor.BatchProcess;

import java.io.IOException;

public interface InterfaceDirectoryCleaner {

    public BatchProcess configure() throws IOException;


}
