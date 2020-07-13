package kennan.co.ke.transcoder_01.repository.RepositoryTranscode;

import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.SprintService;
import kennan.co.ke.transcoder_01.core.StreamableHslService;
import kennan.co.ke.transcoder_01.core.ThumbnailService;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.MediaModel;
import kennan.co.ke.transcoder_01.repository.base.AbstractRepository;


public class TranscodeRepository
        extends AbstractRepository
        implements InterfaceTranscodeRepository {
    /**
     * @param params is empty
     */
    @Override
    public void dispatch(Media media, String... params) throws InterruptedException, PathNotFoundException {
        transcode(media);
    }


    private void transcode(Media media) throws InterruptedException, PathNotFoundException {

        if (!isPathValid(media.getDirectory() + media.getName())) {
            throw PathNotFoundException.createWith(media.getDirectory() + media.getName());
        }

        MediaModel thumbnailContainer = new MediaModel(media);
        thumbnailContainer.setMasterDirectory(media.getDirectory() + "thumbnails/");
        thumbnailContainer.setOutputDirectory(thumbnailContainer.getMasterDirectory() + "main_thumbnail.png");


        MediaModel sprintContainer = new MediaModel(media);
        sprintContainer.setMasterDirectory(media.getDirectory() + "sprint/");
        sprintContainer.setOutputDirectory(sprintContainer.getMasterDirectory() + "index.jpg");


        MediaModel hslContainer = new MediaModel(media);
        hslContainer.setMasterDirectory(media.getDirectory() + "hsl");


        Thread hslGeneratorThread = new Thread(StreamableHslService.create(hslContainer));
        hslGeneratorThread.start();


        Thread thumbnailGeneratorThread = new Thread(ThumbnailService.create(thumbnailContainer));
        thumbnailGeneratorThread.start();


        thumbnailGeneratorThread.join();

        Thread sprintGeneratorThread = new Thread(SprintService.create(sprintContainer));
        sprintGeneratorThread.start();
    }

}
