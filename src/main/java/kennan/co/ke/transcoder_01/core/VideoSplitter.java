package kennan.co.ke.transcoder_01.core;

import kennan.co.ke.transcoder_01.core.base.Transcoder;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.MediaModel;


import java.io.IOException;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.FINALIZING;
import static kennan.co.ke.transcoder_01.core.entity.AppEvent.TERMINATED;
import static kennan.co.ke.transcoder_01.core.entity.LogMessageType.ERROR;
import static kennan.co.ke.transcoder_01.core.entity.LogMessageType.SUCCESS;


public class VideoSplitter extends Transcoder {
    public VideoSplitter(MediaModel mediaModel) {
        super(mediaModel);
        super.process = AppProcess.SPLIT;
        if (this.createDirectory(mediaModel.getMasterDirectory()))
            System.out.println("Created dir :" + mediaModel.getMasterDirectory());
    }

    final private Media media = mediaModel.getMedia();


    @Override
    public void write() {
        AppMessage message;
        try {
            System.out.println("Running ............................");
            Runtime.getRuntime().exec(command());
            message = new AppMessage(media, this.process, SUCCESS);
            message.write(FINALIZING);
        } catch (IOException e) {
            message = new AppMessage(media, this.process, ERROR);
            message.write(TERMINATED, e.toString());
            e.printStackTrace();
        }
    }


    private String[] command() {
        String[] command = new String[11];
        command[0] = ffmpegPath;
        command[1] = "-t";
        command[2] = mediaModel.getEndTime();
        command[3] = "-i";
        command[4] = media.getDirectory() + media.getName();
        command[5] = "-async";
        command[6] = "1";
        command[7] = "-ss";
        command[8] = mediaModel.getStartTime();
        command[9] = mediaModel.getOutputDirectory();
        command[10] = "-report";

        return command;
    }
}
