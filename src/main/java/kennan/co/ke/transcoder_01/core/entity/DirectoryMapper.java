package kennan.co.ke.transcoder_01.core.entity;


import java.io.IOException;
import java.nio.file.Path;

public class DirectoryMapper {

    public DirectoryMapper(
            String fromName,
            Path fromFilePath,
            String toName,
            String toDirectory){
        this.fromFile = fromFilePath;
        this.toDirectory = toDirectory;
        this.fromName = fromName;
        this.toName = toName;
    }

    private final Path fromFile;
    private final String toDirectory;
    private final String fromName;
    private final String toName;


    public String getToDirectory() {
        return toDirectory;
    }

    public String getToFilePath() throws IOException {
        return toDirectory + "/"+toName + getExtention(fromName);
    }

    public String getFromName(){
        return fromName;
    }

    public String getExtention(String name) throws IOException {
        if(name.isEmpty()) throw new IOException("No valid name provided");
        int count = 0;
        while (name.charAt(count) != '.') count++;
        return name.substring(count);
    }

    public Path getFromFile() {
        return fromFile;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(getExtention("f_1781.mp3"));
    }
}
