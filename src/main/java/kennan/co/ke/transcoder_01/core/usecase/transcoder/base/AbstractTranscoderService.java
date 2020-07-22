package kennan.co.ke.transcoder_01.core.usecase.transcoder.base;


import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.model.MediaContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.INITIALIZING;


public abstract class AbstractTranscoderService
        implements
        TranscoderInterface,
        Runnable {

    public AbstractTranscoderService(MediaContainer mediaModel) {
        this.mediaModel = mediaModel;
        this.file = new File(this.mediaModel.getMedia().getName());
        this.createDirectory(mediaModel.getMasterDirectory());
    }

    public final MediaContainer mediaModel;
    public final File file;
    public AppProcess process;
    protected static final Logger log = LoggerFactory.getLogger(AbstractTranscoderService.class);


    @Override
    abstract public void write() throws InterruptedException;

    @Override
    public void run() {
        AppMessage.write(INITIALIZING, mediaModel, process);
        System.out.println("Thread "+ Thread.currentThread().getName() + " of service " + process);
        try {
            this.write();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.debug("Error on process" + process + " " + e.toString(), new Throwable("Bad error"));
        }
    }


    @Override
    public boolean createDirectory(String directory) {
        System.out.println("creating dir : " + directory);
        return new File(directory).mkdirs();
    }


}
