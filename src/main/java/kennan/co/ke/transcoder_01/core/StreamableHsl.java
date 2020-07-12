package kennan.co.ke.transcoder_01.core;


import kennan.co.ke.transcoder_01.core.base.Transcoder;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.MediaModel;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.*;


public class StreamableHsl extends Transcoder {


    public StreamableHsl(MediaModel mediaModel) {
        super(mediaModel);
        super.process = AppProcess.STREAMABLEHSL;
        this.createDirectory(mediaModel.getMasterDirectory());
    }


    private final Media media = mediaModel.getMedia();


    @Override
    public void write() {
        this.runCommand();
    }

    private void runCommand() {
        try {
            Runtime.getRuntime().exec(command()).waitFor();
            AppMessage.write(FINALIZING, mediaModel, process);
        } catch (Exception e) {
            AppMessage.write(FINALIZING, e.toString(), mediaModel, process);
            throw new RuntimeException(e);
        }
    }

    private String[] command() {
        String[] command = new String[46];
        command[0] = ffmpegPath;
        command[1] = "-y";
        command[2] = "-i";
        command[3] = media.getDirectory() + media.getName();
        command[4] = "-preset";
        command[5] = "slow";
        command[6] = "-g";
        command[7] = "48";
        command[8] = "-sc_threshold";
        command[9] = "0";
        command[10] = "-map";
        command[11] = "0:0";
        command[12] = "-map";
        command[13] = "0:1";
        command[14] = "-map";
        command[15] = "0:0";
        command[16] = "-map";
        command[17] = "0:1";
        command[18] = "-s:v:0";
        command[19] = "640x360";
        command[20] = "-c:v:0";
        command[21] = "libx264";
        command[22] = "-b:v:0";
        command[23] = "365k";
        command[24] = "-s:v:1";
        command[25] = "960x540";
        command[26] = "-c:v:1";
        command[27] = "libx264";
        command[28] = "-b:v:1";
        command[29] = "2000k";
        command[30] = "-c:a";
        command[31] = "copy";
        command[32] = "-var_stream_map";
        command[33] = "v:0,a:0 v:1,a:1";
        command[34] = "-master_pl_name";
        command[35] = "master.m3u8";
        command[36] = "-f";
        command[37] = "hls";
        command[38] = "-hls_time";
        command[39] = "6";
        command[40] = "-hls_list_size";
        command[41] = "0";
        command[42] = "-hls_segment_filename";
        command[43] = mediaModel.getMasterDirectory() + "/v%v/file_sq%d.ts";
        command[44] = mediaModel.getMasterDirectory() + "/v%v/index.m3u8";
        command[45] = "-report";

        return command;
    }

}
