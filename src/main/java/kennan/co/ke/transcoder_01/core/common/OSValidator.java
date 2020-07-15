package kennan.co.ke.transcoder_01.core.common;

public class OSValidator {
    private static String OS = System.getProperty("os.name").toLowerCase();

    private static boolean isWindows() {
        return OS.contains("win");
    }

    private static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    public static String getOperatingSystemEnvironment() {
        if (isWindows()) return "win";
        else if (isUnix()) return "uni";
        throw new RuntimeException("OS not recognized");
    }

    public static void main(String[] args) {
        System.out.println(getOperatingSystemEnvironment());
    }
}
