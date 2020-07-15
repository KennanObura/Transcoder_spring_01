package kennan.co.ke.transcoder_01.api;


import kennan.co.ke.transcoder_01.repository.RepositoryVideoSplitter.VideoSplitterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VideoSplitterControllerTest {

    @Autowired
    VideoSplitterRepository repository;

    @Autowired
    VideoSplitterController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void repositoryInjects() throws Exception {
        assertThat(repository).isNotNull();
    }

}
