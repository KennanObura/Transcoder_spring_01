package kennan.co.ke.transcoder_01.repository.DirectoryManager;

import kennan.co.ke.transcoder_01.api.FakeRestController;
import kennan.co.ke.transcoder_01.core.entity.Content;
import kennan.co.ke.transcoder_01.core.entity.Project;
import kennan.co.ke.transcoder_01.core.entity.TempMedia;
import kennan.co.ke.transcoder_01.core.model.DirectoryMapper;
import kennan.co.ke.transcoder_01.core.model.ProjectModel;
import kennan.co.ke.transcoder_01.core.service.directoryManager.DirectoryManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class DirectoryManagerRepository {
    private final ProjectModel projects;

    private final Queue<DirectoryMapper> directoryMapperList = new LinkedList<>();

    private static final Logger log = LoggerFactory.getLogger(DirectoryManagerRepository.class);

    private DirectoryManagerRepository(ProjectModel projects) {
        this.projects = projects;
    }

    public static DirectoryManagerRepository createWithProjects(ProjectModel projects) {
        return new DirectoryManagerRepository(projects);
    }

    public void run() {
        for (Project project : projects.getProjects()) {
            if (project.getContents() != null) {
                for (Content content : project.getContents())
                    if (content.getMediaList() != null)
                        for (TempMedia media : content.getMediaList()) {
                            String ROOT_PATH = "C:\\Apache24";
                            String fromFilePath = ROOT_PATH + "/htdocs/uploads/" + media.getPath();

                            String toDirectory = ROOT_PATH + "/mediafilesystem/"
                                    + project.getProjectName() + "/"
                                    + content.getContent_id() + "/"
                                    + getFileType(media.getMedia_type()) + "/"
                                    + media.getMedia_id() + "/";

                            directoryMapperList.add(new DirectoryMapper(media.getPath(), Paths.get(fromFilePath), toDirectory));
                        }
            }
        }
    }

    public void then() throws IOException {
        log.info(" queue size ==========" + directoryMapperList.size());
        DirectoryManagerService.create(directoryMapperList).run();

    }

    private String getFileType(String type) {
        if (isVideo(type)) return "video";
        else return "audio";
    }

    private boolean isVideo(String string) {
        return string.contains("video");
    }


    public static void main(String[] args) throws Exception {
        FakeRestController fakeRestController = new FakeRestController();
        fakeRestController.run();
    }

}
