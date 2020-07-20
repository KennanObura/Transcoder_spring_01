package kennan.co.ke.transcoder_01.core;

import kennan.co.ke.transcoder_01.core.usecase.transcoder.sprint.tileGenerator.TileGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SprintServiceTest {


    @Autowired
    TileGenerator tileGenerator;

    @Test
    public void tileGeneratorInjected() {
        assertThat(tileGenerator).isNotNull();
    }


}
