package kennan.co.ke.transcoder_01.api;

import kennan.co.ke.transcoder_01.repository.RepositoryTranscode.TranscodeRepository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TranscoderControllerTest {

    @Autowired
    TranscodeRepository repository;

    @Autowired
    TranscoderController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void repositoryInjects() throws Exception {
        assertThat(repository).isNotNull();
    }




}
