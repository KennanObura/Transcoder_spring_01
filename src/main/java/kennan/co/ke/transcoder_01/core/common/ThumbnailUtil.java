package kennan.co.ke.transcoder_01.core.common;

import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.model.MediaContainer;

import kennan.co.ke.transcoder_01.repository.common.MetadataValidator.MetadataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.FINALIZING;
import static kennan.co.ke.transcoder_01.core.entity.AppEvent.TERMINATED;





public class ThumbnailUtil implements Runnable {

    private ThumbnailUtil(MediaContainer mediaModel, AppProcess process, String dimension) {
        this.mediaModel = mediaModel;
        this.process = process;
        this.dimension = dimension;
        this.media = mediaModel.getMedia();
    }



    protected static final Logger log = LoggerFactory.getLogger(ThumbnailUtil.class);


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


    private void commandRunner(String timeInSeconds, String output) {
        try {

            Process runtimeProcess = Runtime.getRuntime().exec(command(dimension, timeInSeconds, output));
            int exitVal = runtimeProcess.waitFor();
            readCommandRunnerResult(runtimeProcess);
            AppMessage.write(FINALIZING, mediaModel, process);
            if(exitVal == 0) {
                log.info("Process : " + process + " executed successfully");
                AppMessage.write(FINALIZING, mediaModel, process);
            }

        } catch (Exception e) {
            AppMessage.write(TERMINATED, e.toString(), mediaModel, process);
            throw new RuntimeException(e);
        }
    }


    private long getMediaLength() throws IOException, ParseException {
        return MetadataValidator.getDuration(media);
    }


    /***
     * A decision to use single screen shot at a time is to make this process reliable. One screen shot, when time is seeked
     * first is relatively faster that depending on the shell to generate thumbnails in a single command. Single command
     *  approach worked fine for small media and failled for large media,
     *
     *  The approach now is better since a single screenshot is seen as a separate process
     *
     *
     *  ::      Seek time of the media
     *              calculate @interval, we need around 100 shots to make tiles
     *              calculate @initial as incrementing interval on where to take the frame
     *
     *
     * @throws IOException if any error
     * @throws ParseException if any error
     */

    private  void start() throws IOException, ParseException {
        long duration = getMediaLength();
        long interval;

        if(duration < 100) interval = 1;
        else interval = duration / 100;

        long initial = 1;
        int output = 0;
        while (initial < duration) {
            initial += interval;
            commandRunner(String.valueOf(initial), String.format("pic%d.png", output++));
        }
    }


    private static void readCommandRunnerResult(Process process) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null)
            log.info(line);
    }

    private String[] command(String dimension, String frameAtInSeconds, String output) {
//        ffmpeg -i input.flv -ss 00:00:14.435 -vframes 1 out.png
        String[] cmd = new String[10];

        cmd[0] = FFMPEGPATH.get();
        cmd[1] = "-ss";
        cmd[2] = frameAtInSeconds;
        cmd[3] = "-i";
        cmd[4] = media.getDirectory() + media.getName();
        cmd[5] = "-vframes";
        cmd[6] = "1";
        cmd[7] = "-s";
        cmd[8] = dimension;
        cmd[9] = mediaModel.getMasterDirectory() + output;
        return cmd;
    }

    @Override
    public void run() {
        try {
            start();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        Media media = new Media();
        media.setName("media.mp4");
        media.setId("2");
        media.setContentId(10);
        media.setProjectName("P10001");
        media.setMediaType("mp4/video");


        System.out.println(seekTimeInterval(1000));
    }

    public  static String seekTimeInterval(int timeInSeconds) {
        int hours, minutes, seconds;
        hours = timeInSeconds / 3600;
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = timeInSeconds / 60;
        timeInSeconds = timeInSeconds - (minutes * 60);
        seconds = timeInSeconds;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }
}
