package kennan.co.ke.transcoder_01.core.base;

import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.model.MediaModel;
import kennan.co.ke.transcoder_01.core.util.OSValidator;

import java.io.File;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.INITIALIZING;
import static kennan.co.ke.transcoder_01.core.entity.LogMessageType.INFO;

public abstract class Transcoder
        implements
        TranscoderInterface,
        Runnable {

    public Transcoder(MediaModel mediaModel) {
        this.mediaModel = mediaModel;
        this.file = new File(this.mediaModel.getMedia().getName());
        this.createDirectory(this.mediaModel.getMedia().getDirectory());
    }

    public MediaModel mediaModel;
    public File file;
    public AppProcess process;
    public final static String ffmpegPath = "/usr/bin/ffmpeg";


    @Override
    abstract public void write();


    @Override
    public void run() {
        AppMessage message = new AppMessage(
                this.mediaModel.getMedia(),
                this.process,
                INFO);

        message.write(INITIALIZING);
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
