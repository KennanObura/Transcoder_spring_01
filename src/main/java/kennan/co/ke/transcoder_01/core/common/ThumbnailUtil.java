package kennan.co.ke.transcoder_01.core.common;

import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.model.MediaContainer;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.FINALIZING;
import static kennan.co.ke.transcoder_01.core.entity.AppEvent.TERMINATED;

public class ThumbnailUtil implements Runnable {

    private ThumbnailUtil(MediaContainer mediaModel, AppProcess process, String dimension) {
        this.mediaModel = mediaModel;
        this.process = process;
        this.dimension = dimension;
        this.media = mediaModel.getMedia();
    }


    public static ThumbnailUtil generate(MediaContainer mediaModel, AppProcess process, String dimension) {
        return new ThumbnailUtil(mediaModel, process, dimension);
    }

//    public boolean ofDimensions(int width, int height){
//        return commandRunner(width, height);
//    }


    private final MediaContainer mediaModel;
    private final AppProcess process;
    private final Media media;
    private final String dimension;


    private boolean commandRunner() {
        try {
            Runtime.getRuntime().exec(cmd(dimension)).waitFor();
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

    private String[] cmd(String dimension) {
//        ffmpeg -i input.flv -vf "select='eq(pict_type,PICT_TYPE_I)'" -vsync vfr thumb%04d.png

        String[] cmd = new String[11];
        cmd[0] = FFMPEGPATH.get();
        cmd[1] = "-i";
        cmd[2] = media.getDirectory() + media.getName();
        cmd[3] = "-vf";
        cmd[4] = "select='eq(pict_type,PICT_TYPE_I)'";
        cmd[5] = "-vsync";
        cmd[6] = "vfr";
        cmd[7] = "-s";
        cmd[8] = dimension;
        cmd[9] = mediaModel.getMasterDirectory() + "pic%d.png";
        cmd[10] = "-report";
        return cmd;
    }

    @Override
    public void run() {
        commandRunner();
    }
}
