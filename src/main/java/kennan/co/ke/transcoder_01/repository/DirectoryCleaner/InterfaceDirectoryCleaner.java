package kennan.co.ke.transcoder_01.repository.DirectoryCleaner;

import kennan.co.ke.transcoder_01.core.service.batchProcessor.BatchProcessService;

import java.io.IOException;

public interface InterfaceDirectoryCleaner {

    public BatchProcessService run() throws IOException;


}
