package kennan.co.ke.transcoder_01.repository.RepositoryVideoSplitter;

import javafx.util.Pair;
import kennan.co.ke.transcoder_01.api.Exception.InvalidTimeRangeException;
import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.constants.Constants;
import kennan.co.ke.transcoder_01.core.model.MediaContainer;
import kennan.co.ke.transcoder_01.repository.base.AbstractRepository;
import kennan.co.ke.transcoder_01.repository.common.MetadataValidator.MetadataValidator;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.videoSplitter.VideoSplitterService;
import kennan.co.ke.transcoder_01.core.entity.Media;


import java.io.IOException;
import java.text.ParseException;


public class VideoSplitterRepository extends AbstractRepository
        implements InterfaceVideoSplitterRepository {


    /**
     * @param media provides a list of parameters needed.
     */


    @Override
    public void dispatch(Media media, Object... params) throws
            InvalidTimeRangeException, PathNotFoundException, IOException, ParseException {

        if (!isPathValid(media.getDirectory() + media.getName())) {
            throw PathNotFoundException.createWith(media.getDirectory() + media.getName());
        }

        final String starttime = (String) params[0];
        final String endtime = (String) params[1];
        final String sort = (String) params[2];

        Pair<String, String> range = new Pair<>(starttime, endtime);
        if (!isInputValid(media, range))
            throw InvalidTimeRangeException.createWith(range.toString());
        else runGeneratorThread(media, starttime, endtime, sort);
    }


    @Override
    public boolean isInputValid(Media media, Pair<String, String> metadata) throws
            InvalidTimeRangeException, IOException, ParseException {
        return MetadataValidator.isTimeRangeValid(media, metadata);
    }


    private void runGeneratorThread(Media media, String... params) {
        final MediaContainer mediaModel = new MediaContainer(media, params[0], params[1], params[2]);
        mediaModel.setMasterDirectory(media.getDirectory() + Constants.DIR_SPLIT_CHUNKS);
        mediaModel.setOutputDirectory(mediaModel.getMasterDirectory() + "split_" + params[2] + "_" + media.getName());

        Thread splitGeneratorThread = new Thread(VideoSplitterService.create(mediaModel));
        splitGeneratorThread.start();
    }
}
