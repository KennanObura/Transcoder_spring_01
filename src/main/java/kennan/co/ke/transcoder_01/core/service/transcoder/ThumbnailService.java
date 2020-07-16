package kennan.co.ke.transcoder_01.core.service.transcoder;


import kennan.co.ke.transcoder_01.core.service.transcoder.base.AbstractTranscoderService;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.MediaModel;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.FINALIZING;
import static kennan.co.ke.transcoder_01.core.entity.AppEvent.TERMINATED;


public class ThumbnailService extends AbstractTranscoderService {


    private ThumbnailService(MediaModel mediaModel) {
        super(mediaModel);
        super.process = AppProcess.THUMBNAIL;
    }

    public static AbstractTranscoderService create(MediaModel mediaModel){
        return new ThumbnailService(mediaModel);
    }

    final private Media media = mediaModel.getMedia();


    @Override
    public void write() {
        this.runCommand();
    }


    private void runCommand() {
        try {
            Runtime.getRuntime().exec(singleThumbnailCommand());
            Runtime.getRuntime().exec(streamOfThumbnailCommand()).waitFor();
            AppMessage.write(FINALIZING, mediaModel, process);
        } catch (Exception e) {
            AppMessage.write(TERMINATED, e.toString(), mediaModel, process);
            throw new RuntimeException(e);
        }
    }


    private String[] singleThumbnailCommand() {
        String[] cmd = new String[11];
        cmd[0] = getFfmpegPath();
        cmd[1] = "-i";
        cmd[2] = this.media.getDirectory() + this.media.getName();
        cmd[3] = "-ss";
        cmd[4] = "00:00:14.435";
        cmd[5] = "-vframes";
        cmd[6] = "1";
        cmd[7] = "-s";
        cmd[8] = "1280x720";
        cmd[9] = mediaModel.getOutputDirectory();
        cmd[10] = "-report";
        return cmd;
    }


    private String[] streamOfThumbnailCommand() {
        String[] cmd = new String[9];
        cmd[0] = getFfmpegPath();
        cmd[1] = "-i";
        cmd[2] = media.getDirectory() + media.getName();
        cmd[3] = "-vf";
        cmd[4] = "fps=1/10";
        cmd[5] = "-s";
        cmd[6] = "192x108";
        cmd[7] = mediaModel.getMasterDirectory() + "pic%d.png";
        cmd[8] = "-report";
        return cmd;
    }
}
