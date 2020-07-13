package kennan.co.ke.transcoder_01.core;

import kennan.co.ke.transcoder_01.core.base.AbstractTranscoderService;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.MediaModel;


import java.io.IOException;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.FINALIZING;
import static kennan.co.ke.transcoder_01.core.entity.AppEvent.TERMINATED;


public class VideoSplitterService extends AbstractTranscoderService {
    private VideoSplitterService(MediaModel mediaModel) {
        super(mediaModel);
        super.process = AppProcess.SPLIT;
    }

    final private Media media = mediaModel.getMedia();


    public static AbstractTranscoderService create(MediaModel mediaModel) {
        return new VideoSplitterService(mediaModel);
    }


    @Override
    public void write() {
        try {
            System.out.println("Running ............................");
            Runtime.getRuntime().exec(command()).waitFor();
            AppMessage.write(FINALIZING, mediaModel, process);
        } catch (IOException | InterruptedException e) {
            AppMessage.write(TERMINATED, e.toString(), mediaModel, process);
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
