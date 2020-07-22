package kennan.co.ke.transcoder_01.repository.RepositoryTranscode;

import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.common.DirectoryWalker;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.sprint.SprintService;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.hsl.StreamableHslService;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.thumbnail.ThumbnailService;
import kennan.co.ke.transcoder_01.core.entity.Media;

import kennan.co.ke.transcoder_01.core.model.MediaContainer;
import kennan.co.ke.transcoder_01.repository.base.AbstractRepository;

import static kennan.co.ke.transcoder_01.constants.Constants.*;


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


        final String mediaRootDir = media.getDirectory();
        log.info(media.toString());


        if (!isPathValid(mediaRootDir + media.getName())) {
            log.debug(mediaRootDir + " As path is not valid");
            throw PathNotFoundException.createWith(media.getDirectory() + media.getName());
        }

        MediaContainer thumbnailContainer = new MediaContainer(media);
        thumbnailContainer.setMasterDirectory(mediaRootDir+ DIR_THUMBNAILS);
        thumbnailContainer.setOutputDirectory(thumbnailContainer.getMasterDirectory() + NICKNAME_THUMBNAIL);


        MediaContainer sprintContainer = new MediaContainer(media);
        sprintContainer.setMasterDirectory(mediaRootDir + DIR_SPRINT);
        sprintContainer.setOutputDirectory(sprintContainer.getMasterDirectory() + NICKNAME_SPRINT);


        MediaContainer hslContainer = new MediaContainer(media);
        hslContainer.setMasterDirectory(mediaRootDir + DIR_STREAMABLEHSL);

        Thread hslGeneratorThread = new Thread(StreamableHslService.create(hslContainer));
        Thread thumbnailGeneratorThread = new Thread(ThumbnailService.create(thumbnailContainer));
        Thread sprintGeneratorThread = new Thread(SprintService.create(sprintContainer));

        hslGeneratorThread.start();
        thumbnailGeneratorThread.start();
        sprintGeneratorThread.start();

    }

}
