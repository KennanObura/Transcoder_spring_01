package kennan.co.ke.transcoder_01.core.usecase.transcoder.hsl;


import kennan.co.ke.transcoder_01.core.common.FFMPEGPATH;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.base.AbstractTranscoderService;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.MediaContainer;

import java.io.*;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.*;


public class StreamableHslService extends AbstractTranscoderService {


    private StreamableHslService(MediaContainer mediaModel) {
        super(mediaModel);
        super.process = AppProcess.hsl;
    }


    public static AbstractTranscoderService create(MediaContainer mediaModel) {
        return new StreamableHslService(mediaModel);
    }


    private final Media media = mediaModel.getMedia();


    @Override
    public void write() {
        this.runCommand();
    }


    private void runCommand() {
        try {

            Process runtimeProcess = Runtime.getRuntime().exec(command());
            int exitVal = runtimeProcess.waitFor();

            validateMasterPlaylist(mediaModel.getMasterDirectory());
            readCommandRunnerResult(runtimeProcess);
            if (exitVal == 0) {
                log.info("Process : " + process + " executed successfully");
                AppMessage.write(FINALIZING, mediaModel, process);
            }
            AppMessage.write(FINALIZING, mediaModel, process);
        } catch (Exception e) {
            AppMessage.write(TERMINATED, e.toString(), mediaModel, process);
            throw new RuntimeException(e);
        }
    }

    private static void validateMasterPlaylist(String masterDirectory) throws IOException {
        try {

            FileReader fileReader = new FileReader(new File(masterDirectory + "master.m3u8"));   //reads the file
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty() && line.charAt(0) == '/') {
                    line = line.substring(1);
                }
                stringBuilder.append(line);      //appends line to string buffer
                stringBuilder.append("\n");     //line feed
            }

            if (writeNewMasterList(stringBuilder, masterDirectory))
                log.info("New file: " + stringBuilder.toString());

            fileReader.close();    //closes the stream and release the resources
        } catch (Exception e) {
            log.info("Error " + e);
            throw new IOException("Error File" + e);

        }
    }

    private static boolean writeNewMasterList(StringBuilder stringBuilder, String masterDirectory) throws FileNotFoundException {
        FileOutputStream fileOut = new FileOutputStream(masterDirectory + "master.m3u8");
        try {
            fileOut.write(stringBuilder.toString().getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void readCommandRunnerResult(Process process) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = bufferedReader.readLine()) != null)
            log.info(line);
    }

    private String[] command() {
        String[] command = new String[46];
        command[0] = FFMPEGPATH.get();
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
