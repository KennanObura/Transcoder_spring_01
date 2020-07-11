package kennan.co.ke.transcoder_01.DI;

import kennan.co.ke.transcoder_01.repository.RepositoryTranscode.TranscodeRepository;
import kennan.co.ke.transcoder_01.repository.RepositoryVideoSplitter.VideoSplitterRepository;
import kennan.co.ke.transcoder_01.repository.base.AbstractRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Config {

    @Bean
    public AbstractRepository transcodeRepository(){
        return new TranscodeRepository();
    }

    @Bean AbstractRepository videoSplitterRepository(){
        return new VideoSplitterRepository();
    }
}
