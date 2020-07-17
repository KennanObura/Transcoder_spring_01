package kennan.co.ke.transcoder_01.repository.DirectoryCleaner;

import kennan.co.ke.transcoder_01.api.FakeRestController;
import kennan.co.ke.transcoder_01.core.entity.Content;
import kennan.co.ke.transcoder_01.core.entity.Project;
import kennan.co.ke.transcoder_01.core.entity.TempMedia;
import kennan.co.ke.transcoder_01.core.model.DirectoryMapper;
import kennan.co.ke.transcoder_01.core.model.ProjectModel;
import kennan.co.ke.transcoder_01.core.service.batchProcessor.BatchProcessService;
import kennan.co.ke.transcoder_01.core.service.directoryCleaner.DirectoryCleanerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class DirectoryCleanerRepository implements InterfaceDirectoryCleaner {
    private final ProjectModel projects;
    private final Queue<DirectoryMapper> directoryMapperList = new LinkedList<>();
    private static final Logger log = LoggerFactory.getLogger(DirectoryCleanerRepository.class);

    private DirectoryCleanerRepository(ProjectModel projects) {
        this.projects = projects;
    }

    public static DirectoryCleanerRepository createWithProjects(ProjectModel projects) {
        return new DirectoryCleanerRepository(projects);
    }

    @Override
    public BatchProcessService run() throws IOException {
        for (Project project : projects.getProjects()) {
            if (project.getContents() != null) {
                for (Content content : project.getContents())
                    if (content.getMediaList() != null)
                        for (TempMedia media : content.getMediaList())
                            configureDirectoryMapperAndPutToQueue(project, content, media);


            }
        }
        return startBatchProcessService();
    }


    private void configureDirectoryMapperAndPutToQueue(Project project, Content content, TempMedia media) {
//        String ROOT_PATH = "C:\\Apache24\\htdocs/";
        String ROOT_PATH = "";
//        String fromFilePath = ROOT_PATH + "uploads/" + media.getPath();

        String fromFilePath = ROOT_PATH + "uploads/P10077_documentary/media_example.mp4";
        String toDirectory = ROOT_PATH + "mediafilesystem/"
                + project.getProjectName() + "/"
                + content.getContent_id() + "/"
                + getFileType(media.getMedia_type()) + "/"
                + media.getMedia_id() + "/";

        directoryMapperList.add(new DirectoryMapper(media.getPath(), Paths.get(fromFilePath), toDirectory));
    }

    private BatchProcessService startBatchProcessService() throws IOException {
        log.info("Queue size ======== " + directoryMapperList.size());

        DirectoryCleanerService.create(new LinkedList<>(directoryMapperList)).run();
        return BatchProcessService.createWithMappers(directoryMapperList);
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
