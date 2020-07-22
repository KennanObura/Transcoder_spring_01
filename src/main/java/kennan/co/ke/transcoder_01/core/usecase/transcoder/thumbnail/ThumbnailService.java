package kennan.co.ke.transcoder_01.core.usecase.transcoder.thumbnail;


import kennan.co.ke.transcoder_01.core.common.ThumbnailUtil;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.thumbnail.imageSelection.BestCandidateSelector;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.base.AbstractTranscoderService;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.model.MediaContainer;


public class ThumbnailService extends AbstractTranscoderService {

    private ThumbnailService(MediaContainer mediaModel) {
        super(mediaModel);
        super.process = AppProcess.THUMBNAIL;
    }

    public static AbstractTranscoderService create(MediaContainer mediaModel){
        return new ThumbnailService(mediaModel);
    }


    @Override
    public void write() {
        this.runCommand();
    }


    private void runCommand() {
        boolean hasThumbnailGeneratorFinished = ThumbnailUtil
                .generate(mediaModel, process)
                .ofDimensions(1280, 720);
        if(hasThumbnailGeneratorFinished) {
            Thread selector = new Thread(BestCandidateSelector.select(mediaModel.getMasterDirectory()));
            selector.start();
        }
    }

}
