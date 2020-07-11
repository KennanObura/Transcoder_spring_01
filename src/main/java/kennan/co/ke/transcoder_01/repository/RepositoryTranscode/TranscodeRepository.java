package kennan.co.ke.transcoder_01.repository.RepositoryTranscode;

import kennan.co.ke.transcoder_01.core.Sprint;
import kennan.co.ke.transcoder_01.core.StreamableHsl;
import kennan.co.ke.transcoder_01.core.Thumbnail;
import kennan.co.ke.transcoder_01.core.entity.Media;
import kennan.co.ke.transcoder_01.core.model.MediaModel;
import kennan.co.ke.transcoder_01.repository.base.AbstractRepository;
import kennan.co.ke.transcoder_01.repository.response.ResponseMessage;
import kennan.co.ke.transcoder_01.repository.response.Status;
import kennan.co.ke.transcoder_01.repository.util.PathValidator.PathValidator;

public class TranscodeRepository
        extends AbstractRepository
        implements InterfaceTranscodeRepository{
    /**
     * @param params is empty
     */
    @Override
    public void dispatch(Media media, String... params) {
        transcode(media);
    }

    @Override
    public boolean isPathValid(String path) {
        return PathValidator.exist(path);
    }


    private ResponseMessage<String> transcode(Media media) {

        if(!isPathValid(media.getDirectory() + media.getName()))
            return new ResponseMessage<>(404, Status.ERROR);

        MediaModel thumbnailContainer = new MediaModel(media);
        thumbnailContainer.setMasterDirectory(media.getDirectory() + "thumbnails/");
        thumbnailContainer.setOutputDirectory(thumbnailContainer.getMasterDirectory() + "main_thumbnail.png");


        MediaModel sprintContainer = new MediaModel(media);
        sprintContainer.setMasterDirectory(media.getDirectory() + "sprint/");
        sprintContainer.setOutputDirectory(sprintContainer.getMasterDirectory() + "index.jpg");


        MediaModel hslContainer = new MediaModel(media);
        hslContainer.setMasterDirectory(media.getDirectory() + "hsl");



        Thread hslGeneratorThread = new Thread(new StreamableHsl(hslContainer));
        hslGeneratorThread.start();


        Thread thumbnailGeneratorThread = new Thread(new Thumbnail(thumbnailContainer));
        thumbnailGeneratorThread.start();

        try {
            thumbnailGeneratorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Interrupted");


        }

        Thread sprintGeneratorThread = new Thread(new Sprint(sprintContainer));
        sprintGeneratorThread.start();

        try {
            sprintGeneratorThread.join();
            hslGeneratorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Interrupted");
            return new ResponseMessage<String>(500, Status.ERROR);
        }

        return new ResponseMessage<String>(201, Status.SUCCESS);
    }

}
