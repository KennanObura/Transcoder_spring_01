package kennan.co.ke.transcoder_01.core.common;

public class FFMPEGPATH {
    public static String get(){
        return getFfmpegPath();
    }

    private static String getFfmpegPath() {
        if (getCurrentEnvironment().equals("uni"))
            return "/usr/bin/ffmpeg";
        else return "C:\\ffmpeg\\bin\\ffmpeg";
    }

    /**
     * @return String name of the OS environment
     */
    private static String getCurrentEnvironment() {
        return OSValidator.getOperatingSystemEnvironment();
    }
}
