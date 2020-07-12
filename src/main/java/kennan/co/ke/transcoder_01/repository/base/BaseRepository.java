package kennan.co.ke.transcoder_01.repository.base;


import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.entity.Media;

import java.io.IOException;
import java.text.ParseException;


public interface BaseRepository {

    boolean isPathValid(String path);

    void dispatch(Media media, String... params) throws
            InterruptedException,
            IOException,
            ParseException,
            PathNotFoundException,
            InvalidTimeRangeException;
}
