package kennan.co.ke.transcoder_01.DI;

import kennan.co.ke.transcoder_01.repository.RepositoryTranscode.TranscodeRepository;
import kennan.co.ke.transcoder_01.repository.RepositoryVideoSplitter.VideoSplitterRepository;
import kennan.co.ke.transcoder_01.repository.base.AbstractRepository;
import kennan.co.ke.transcoder_01.repository.common.MetadataValidator.MetadataValidator;
import org.springframework.beans.factory.InjectionPoint;
import org.slf4j.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Field;

import static java.util.Optional.*;

@Configuration
@ComponentScan
public class DIConfiguration {

    @Bean
    public AbstractRepository transcodeRepository() {
        return new TranscodeRepository();
    }

    @Bean
    public AbstractRepository videoSplitterRepository() {
        return new VideoSplitterRepository();
    }


    @Bean
    public MetadataValidator metadataValidator() {return new MetadataValidator(); }

    @Bean
    @Scope("prototype")
    public Logger produceLogger(InjectionPoint injectionPoint) {
        Class<?> classOnWired = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(classOnWired);
    }

//    @Bean
//    public StreamableHslService streamableHslService() {return new StreamableHslService(); }
//
//    @Bean
//    public ThumbnailService thumbnailService() {return new ThumbnailService();
//    }
//
//    @Bean
//    public SprintService sprintService() {return new SprintService();
//    }
//
//    @Bean
//    public VideoSplitterService videoSplitterService() {return new VideoSplitterService();
//    }
}
