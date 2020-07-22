package kennan.co.ke.transcoder_01.core.usecase.transcoder.sprint;


import kennan.co.ke.transcoder_01.DI.DIConfiguration;
import kennan.co.ke.transcoder_01.core.common.DirectoryWalker;
import kennan.co.ke.transcoder_01.core.common.ThumbnailUtil;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.base.AbstractTranscoderService;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.model.MediaContainer;
import kennan.co.ke.transcoder_01.core.usecase.transcoder.sprint.tileGenerator.TileGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SprintService extends AbstractTranscoderService {


    private SprintService(MediaContainer mediaModel) {
        super(mediaModel);
        super.process = AppProcess.SPRINT;
    }

    final private static int SPRINT_HEIGHT = 108;
    final private static int SPRINT_WIDTH = 192;
    final private String contentDirectory = mediaModel.getMasterDirectory();

    @Autowired
    private final TileGenerator tileGenerator = (new AnnotationConfigApplicationContext(DIConfiguration.class))
            .getBean(TileGenerator.class);


    public static AbstractTranscoderService create(MediaContainer mediaModel) {
        return new SprintService(mediaModel);
    }


    @Override
    public void write() {
        try {
            runCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void runCommand() throws IOException {
        boolean hasThumbnailGeneratorFinished = ThumbnailUtil
                .generate(mediaModel, process)
                .ofDimensions(SPRINT_WIDTH, SPRINT_HEIGHT);

        if (hasThumbnailGeneratorFinished && tileGenerator
                .thumbnailsMerged(mediaModel.getOutputDirectory(), getSortedCandidatesFromDirectory(new TreeMap<>()))) {
            log.info("Sprint generator finished");
            //call db  layer for save
        }
    }


    private TreeMap<Integer, File> getSortedCandidatesFromDirectory(TreeMap<Integer, File> sortedCandidates)
            throws IOException {
        for (File file : DirectoryWalker.getContentsFromDirectory(contentDirectory))
            sortedCandidates.put(getFileAbsoluteIndex(file.getName()), file);
        return sortedCandidates;
    }

    /**
     * Happens files are stored in the format pic%d.jpg (pic23.png)
     * with this, the method should return int 23
     *
     * @param filename from directory
     * @return int stripped from filename
     * @throws IOException ;
     */

    private static int getFileAbsoluteIndex(String filename) throws IOException {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(filename);
        if (!matcher.find()) throw new IOException("Image name is not indexed");
        return Integer.parseInt(matcher.group());

    }

    public static void main(String[] args) throws IOException {

    }
}
