package kennan.co.ke.transcoder_01.repository.base;

import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.repository.util.PathValidator.PathValidator;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.text.ParseException;

public abstract class AbstractRepository implements BaseRepository {


    @Override
    public abstract void dispatch(Media media, String ...params) throws
            InterruptedException,
            IOException,
            ParseException, PathNotFoundException, InvalidTimeRangeException;


    @Override
    public boolean isPathValid(String path) {
        return PathValidator.exist(path);
    }
}
