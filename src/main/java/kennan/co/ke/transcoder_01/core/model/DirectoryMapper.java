package kennan.co.ke.transcoder_01.core.model;


import java.io.IOException;
import java.nio.file.Path;

public class DirectoryMapper {

    public DirectoryMapper(
            String name,
            Path fromFile,
            String toDirectory){
        this.fromFile = fromFile;
        this.toDirectory = toDirectory;
        this.name = name;
    }

    private final Path fromFile;
    private final String toDirectory;
    private final String name;


    public String getToDirectory() {
        return toDirectory;
    }

    public String getToFilePath() throws IOException {
        return toDirectory + "/media" + getExtention(name);
    }

    private static String getExtention(String name) throws IOException {
        if(name.isEmpty()) throw new IOException("No valid name provided");
        int count = 0;
        while (name.charAt(count) != '.') count++;
        return name.substring(count);
    }

    public Path getFromFile() {
        return fromFile;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getExtention("f_1781.mp3"));
    }
}
