package kennan.co.ke.transcoder_01.repository.RepositoryVideoSplitter;

import javafx.util.Pair;
import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.core.model.MediaModel;
import kennan.co.ke.transcoder_01.repository.base.AbstractRepository;
import kennan.co.ke.transcoder_01.repository.util.MetadataValidator.MetadataValidator;
import kennan.co.ke.transcoder_01.core.VideoSplitter;
import kennan.co.ke.transcoder_01.core.entity.Media;
import java.io.IOException;
import java.text.ParseException;


public class VideoSplitterRepository extends AbstractRepository
        implements InterfaceVideoSplitterRepository {


    /**
     * @param media provides a list of parameters needed.
     */


    @Override
    public void dispatch(Media media, String... params) throws InterruptedException, InvalidTimeRangeException {

        final String starttime = params[0];
        final String endtime = params[1];
        final String sort = params[2];

        if (isInputValid(media, new Pair<>(starttime, endtime)))
//            throw new InvalidTimeRangeException(error);


        runGeneratorThread(media, starttime, endtime, sort);
    }


    @Override
    public boolean isInputValid(Media media, Pair<String, String> metadata) {
        final MetadataValidator metadataValidator = new MetadataValidator(media);
        try {
            return metadataValidator.isTimeRangeValid(metadata);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void runGeneratorThread(Media media, String... params) throws InterruptedException {
        final MediaModel mediaModel = new MediaModel(media, params[0], params[1], params[2]);
        mediaModel.setMasterDirectory(media.getDirectory() + "chunks/");
        mediaModel.setOutputDirectory(mediaModel.getMasterDirectory() + "split_[" + params[2] + "]_" + media.getName());

        Thread splitGeneratorThread = new Thread(new VideoSplitter(mediaModel));

        splitGeneratorThread.start();
        splitGeneratorThread.join();
    }
}
