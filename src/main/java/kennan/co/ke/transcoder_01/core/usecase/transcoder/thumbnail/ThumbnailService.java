package kennan.co.ke.transcoder_01.core.usecase.transcoder.thumbnail;


import kennan.co.ke.transcoder_01.core.common.ThumbnailUtil;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.thumbnail.imageSelection.BestCandidateSelector;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.base.AbstractTranscoderService;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.model.MediaContainer;


public class ThumbnailService extends AbstractTranscoderService {

    private ThumbnailService(MediaContainer mediaModel) {
        super(mediaModel);
        super.process = AppProcess.thumbnail;
    }

    public static AbstractTranscoderService create(MediaContainer mediaModel) {
        return new ThumbnailService(mediaModel);
    }


    @Override
    public void write() throws InterruptedException {
        this.runCommand();
    }


    private void runCommand() throws InterruptedException {

        Thread generatorThread = new Thread(ThumbnailUtil.generate(mediaModel, process, "1280x720"));
        generatorThread.start();

        generatorThread.join();

        Thread selector = new Thread(BestCandidateSelector.select(mediaModel.getMasterDirectory()));
        selector.start();

    }

}
