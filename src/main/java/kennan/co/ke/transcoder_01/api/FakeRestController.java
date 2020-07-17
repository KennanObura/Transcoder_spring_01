package kennan.co.ke.transcoder_01.api;



import kennan.co.ke.transcoder_01.core.model.ProjectModel;
import kennan.co.ke.transcoder_01.repository.DirectoryCleaner.DirectoryCleanerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import org.springframework.web.client.RestTemplate;



public class FakeRestController implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(FakeRestController.class);

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        final String restUrl = "https://video.nts.nl/api/projects/?client=kk&mode=main";

        ProjectModel projects = restTemplate.getForObject(restUrl, ProjectModel.class);

        log.info("==== RESTful API Response using Spring RESTTemplate START =======");
        assert projects != null;

        DirectoryCleanerRepository.createWithProjects(projects)
                .run()
                .then();


        log.info(projects.toString());
        log.info("==== RESTful API Response using Spring RESTTemplate END =======");

    }
}
