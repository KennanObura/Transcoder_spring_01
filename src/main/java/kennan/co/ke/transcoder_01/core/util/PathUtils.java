package kennan.co.ke.transcoder_01.core.util;

import kennan.co.ke.transcoder_01.core.entity.Media;

public class PathUtils {
    Media media;

    PathUtils(Media media){
        this.media = media;
    }

//    public Boolean updateMediaMetadataIfFilePathExist() {
//
//        String cmd = "ffprobe -v error -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 "
//                + media.getDirectory() + media.getName() + " -sexagesimal";
//
//        try {
//            Process result = Runtime.getRuntime().exec(cmd.split(" "));
//
//            BufferedReader stdInput = new BufferedReader(new
//                    InputStreamReader(result.getInputStream()));
//
//            BufferedReader stdError = new BufferedReader(new
//                    InputStreamReader(result.getErrorStream()));
//
//            if (!stdError.readLine().isEmpty()) {
//                dumpError(stdError.readLine());
//                return false;
//            }
//
//            media.setDuration(stdInput.readLine());
//            return true;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
