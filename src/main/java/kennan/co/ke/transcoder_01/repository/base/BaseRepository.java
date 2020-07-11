package kennan.co.ke.transcoder_01.repository.base;


import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.core.entity.Media;


public interface BaseRepository {
    void dispatch(Media media, String ...params) throws InterruptedException, InvalidTimeRangeException;
}
