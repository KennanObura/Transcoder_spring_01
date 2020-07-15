package kennan.co.ke.transcoder_01.core.service.transcoder.base;


import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.model.MediaModel;
import kennan.co.ke.transcoder_01.core.common.OSValidator;

import java.io.File;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.INITIALIZING;


public abstract class AbstractTranscoderService
        implements
        TranscoderInterface,
        Runnable {

    public AbstractTranscoderService(MediaModel mediaModel) {
        this.mediaModel = mediaModel;
        this.file = new File(this.mediaModel.getMedia().getName());
        this.createDirectory(this.mediaModel.getMedia().getDirectory());
        this.createDirectory(mediaModel.getMasterDirectory());
    }

    public final MediaModel mediaModel;
    public final File file;
    public AppProcess process;
    public final static String ffmpegPath = "/usr/bin/ffmpeg";


    @Override
    abstract public void write();



    @Override
    public void run() {
        AppMessage.write(INITIALIZING, mediaModel, process);
        this.write();
    }


    @Override
    public boolean createDirectory(String directory) {
        System.out.println("creating dir : " + directory);
        return new File(directory).mkdirs();
    }


    /**
     * @return String name of the OS environment
     */
    public String getCurrentEnvironment() {
        return OSValidator.getOperatingSystemEnvironment();
    }

}
