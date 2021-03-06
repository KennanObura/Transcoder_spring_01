package kennan.co.ke.transcoder_01.repository.DirectoryCleaner;

import kennan.co.ke.transcoder_01.api.FakeRestController;
import kennan.co.ke.transcoder_01.constants.Constants;
import kennan.co.ke.transcoder_01.core.common.OSValidator;
import kennan.co.ke.transcoder_01.core.entity.*;
import kennan.co.ke.transcoder_01.core.model.ProjectModel;
import kennan.co.ke.transcoder_01.core.usecase.batchProcessor.BatchProcess;
import kennan.co.ke.transcoder_01.core.usecase.directoryCleaner.DirectoryResourcesUtil;
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
    public BatchProcess configure() throws IOException {
//        int count = 0;
        for (Project project : projects.getProjects()) {
            if (project.getContents() != null) {
                for (Content content : project.getContents())
                    if (content.getMediaList() != null)
                        for (TempMedia media : content.getMediaList()) {
//                            if (count > 10 && count < 14) // count is only for testing threads
                                configureDirectoryMapperAndPutToQueue(project, content, media);
//                            count++;
                        }


            }
        }
        return startBatchProcessService();
    }


    private void configureDirectoryMapperAndPutToQueue(Project project, Content content, TempMedia media) {

        String ROOT_PATH = getEnvironmentSpecificRootPath();
//        String fromFilePath = ROOT_PATH + "uploads/" + media.getPath();
        String fromFilePath = ROOT_PATH +  Constants.FROM_TEMP_RESOURCE_DIRECTORY
                + project.getProjectName() + "/"
                + content.getContent_id() + "/"
                + getFileType(media.getMedia_type()) + "/"
                + media.getMedia_id() + "/hsl";
        String toDirectory = ROOT_PATH + Constants.PARENT_RESOURCE_DIRECTORY
                + project.getProjectName() + "/"
                + content.getContent_id() + "/"
                + getFileType(media.getMedia_type()) + "/"
                + media.getMedia_id() + "/hsl";

        directoryMapperList.add(new DirectoryMapper(media.getPath(), Paths.get(fromFilePath), "media", toDirectory));
    }

    private static String getEnvironmentSpecificRootPath(){
        if(OSValidator.getOperatingSystemEnvironment().equals("uni"))
            return "";
        else return Constants.WIN_ROOT_PATH;
    }


    private BatchProcess startBatchProcessService() throws IOException {
        DirectoryResourcesUtil.create(new LinkedList<>(directoryMapperList)).copyToDirectory();
        return BatchProcess.createWithDirectoryMappers(directoryMapperList);
    }


    private String getFileType(String mediaFileType) {
        if (isVideo(mediaFileType)) return MEDIATYPE.video.name();
        else return MEDIATYPE.audio.name();
    }

    private boolean isVideo(String mediaFileType) {
        return mediaFileType.contains("video");
    }



    public static void main(String[] args) throws Exception {
        FakeRestController fakeRestController = new FakeRestController();
        fakeRestController.run();
    }

}
