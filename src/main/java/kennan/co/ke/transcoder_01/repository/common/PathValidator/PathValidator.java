package kennan.co.ke.transcoder_01.repository.common.PathValidator;

import java.io.File;

public class PathValidator {
    public static boolean exist(String path){
        File file = new File(path);
        return file.exists();
    }
}
