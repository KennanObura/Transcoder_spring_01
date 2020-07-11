package kennan.co.ke.transcoder_01.repository.base;

import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.repository.response.ResponseMessage;
import org.springframework.context.annotation.Bean;

public abstract class AbstractRepository implements BaseRepository {

    @Bean
    @Override
    public abstract void dispatch(Media media, String ...params) throws InterruptedException, InvalidTimeRangeException;

//    public abstract boolean isTokenValid(String token);

}
