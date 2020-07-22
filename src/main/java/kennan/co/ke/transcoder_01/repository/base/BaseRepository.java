package kennan.co.ke.transcoder_01.repository.base;


import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.base.AbstractTranscoderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;


public interface BaseRepository {

    boolean isPathValid(String path);

    static final Logger log = LoggerFactory.getLogger(BaseRepository.class);

    void dispatch(Media media, String... params) throws
            InterruptedException,
            IOException,
            ParseException,
            PathNotFoundException,
            InvalidTimeRangeException;
}
