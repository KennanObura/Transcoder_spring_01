package kennan.co.ke.transcoder_01.core;


import kennan.co.ke.transcoder_01.core.base.Transcoder;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.MediaModel;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.FINALIZING;
import static kennan.co.ke.transcoder_01.core.entity.AppEvent.TERMINATED;
import static kennan.co.ke.transcoder_01.core.entity.LogMessageType.*;

public class Thumbnail extends Transcoder {


    public Thumbnail(MediaModel mediaModel) {
        super(mediaModel);
        super.process = AppProcess.THUMBNAIL;
        this.createDirectory(mediaModel.getMasterDirectory());
    }


    final private Media media = mediaModel.getMedia();


    @Override
    public void write() {
        this.runCommand();
    }


    private void runCommand() {
        AppMessage message;

        try {
            Runtime.getRuntime().exec(singleThumbnailCommand());
            Runtime.getRuntime().exec(streamOfThumbnailCommand()).waitFor();
            message = new AppMessage(media, process, SUCCESS);
            message.write(FINALIZING);
        } catch (Exception e) {
            message = new AppMessage(media, process, ERROR);
            message.write(TERMINATED, e.toString());
            throw new RuntimeException(e);
        }
    }


    private String[] singleThumbnailCommand() {
        String[] cmd = new String[9];
        cmd[0] = ffmpegPath;
        cmd[1] = "-i";
        cmd[2] = this.media.getDirectory() + this.media.getName();
        cmd[3] = "-ss";
        cmd[4] = "00:00:14.435";
        cmd[5] = "-vframes";
        cmd[6] = "1";
        cmd[7] = mediaModel.getOutputDirectory();
        cmd[8] = "-report";
        return cmd;
    }


    private String[] streamOfThumbnailCommand() {
        String[] cmd = new String[7];
        cmd[0] = ffmpegPath;
        cmd[1] = "-i";
        cmd[2] = media.getDirectory() + media.getName();
        cmd[3] = "-vf";
        cmd[4] = "fps=1";
        cmd[5] = mediaModel.getMasterDirectory() + "pic%d.png";
        cmd[6] = "-report";
        return cmd;
    }
}
