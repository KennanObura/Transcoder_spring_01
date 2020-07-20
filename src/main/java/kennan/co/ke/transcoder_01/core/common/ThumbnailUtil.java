package kennan.co.ke.transcoder_01.core.common;

import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.model.MediaModel;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.FINALIZING;
import static kennan.co.ke.transcoder_01.core.entity.AppEvent.TERMINATED;

public class ThumbnailUtil {

    private ThumbnailUtil(MediaModel mediaModel, AppProcess process) {
        this.mediaModel = mediaModel;
        this.process = process;
        this.media = mediaModel.getMedia();
    }


    public static ThumbnailUtil generate(MediaModel mediaModel, AppProcess process){
        return new ThumbnailUtil(mediaModel, process);
    }

    public boolean ofDimensions(int width, int height){
        return commandRunner(width, height);
    }



    private final MediaModel mediaModel;
    private final AppProcess process;
    private final Media media;



    private boolean commandRunner(int x, int y) {
        String dimension = x+"x"+y;
        try {
            Runtime.getRuntime().exec(command(dimension)).waitFor();
            AppMessage.write(FINALIZING, mediaModel, process);
            return true;
        } catch (Exception e) {
            AppMessage.write(TERMINATED, e.toString(), mediaModel, process);
            throw new RuntimeException(e);
        }
    }



    private String[] command(String dimension) {
        String[] cmd = new String[9];
        cmd[0] = FFMPEGPATH.get();
        cmd[1] = "-i";
        cmd[2] = media.getDirectory() + media.getName();
        cmd[3] = "-vf";
        cmd[4] = "fps=1";
        cmd[5] = "-s";
        cmd[6] = dimension;
        cmd[7] = mediaModel.getMasterDirectory() + "pic%d.png";
        cmd[8] = "-report";
        return cmd;
    }
}
