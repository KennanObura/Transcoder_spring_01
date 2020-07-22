package kennan.co.ke.transcoder_01.core.usecase.transcoder.thumbnail.imageSelection;

import kennan.co.ke.transcoder_01.api.Exception.PathNotFoundException;
import kennan.co.ke.transcoder_01.core.common.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static kennan.co.ke.transcoder_01.constants.Constants.MAX_THUMBNAILS_AS_BEST;


/**
 * A service.
 * Collects all images from specified directory by calling @DirectoryWalker util,
 * uses an algorithm of simple ranking to get top (five) @MAX_FILES of best candidates and disregarding the rest
 * (Deleting them) from directory
 * <p>
 * <p>
 * that is:::
 * From created x-number of thumbnails all at different points.
 * Take the largest (in bytes) file and discard the rest
 * <p>
 * <p>
 * This bases from the idea that jpeg files of a monotone 'boring' image, (eg an all black screen),
 * compress into a much smaller files than an image with many objects and colors in it.
 * <p>
 * By this we end up with at least viable 80/20 candidates. .
 * <p>
 * Might need improvement, >>>>>>>>research
 */


public class BestCandidateSelector implements Runnable {
    final private String contentDirectory;


    private BestCandidateSelector(String contentDirectory) {
        this.contentDirectory = contentDirectory;
    }


    private static final Logger log = LoggerFactory.getLogger(BestCandidateSelector.class);


    public static BestCandidateSelector select(String contentDirectory) {
        return new BestCandidateSelector(contentDirectory);
    }


    /**
     * Consider all files as candidates and store in a map ranked based on quality
     *
     * @param sortedCandidates TreeMap container
     * @return a Map
     * @throws IOException if anything goes wrong while getting  directory contents
     */
    private TreeMap<Long, File> getSortedCandidatesFromDirectory(TreeMap<Long, File> sortedCandidates)
            throws IOException {
        for (File file : DirectoryWalker.getContentsFromDirectory(contentDirectory))
            sortedCandidates.put(file.length(), file);
        return sortedCandidates;
    }


    private void pickTopCandidatesAndDisregardTheRest() throws IOException, PathNotFoundException {
        int count = 0;
        TreeMap<Long, File> mapOfFiles = getSortedCandidatesFromDirectory(new TreeMap<>(Collections.reverseOrder()));

        for (Map.Entry<Long, File> entry : mapOfFiles.entrySet()) {
            File image = entry.getValue();

            if (count < MAX_THUMBNAILS_AS_BEST) {
                File newImage = new File(contentDirectory + String.format("%d_thumbnail.png", count++));
                if (image.renameTo(newImage)) log.info("File: " + image + " reserved as one of bests");
            } else {
                if (disregard(image)) log.info("File: " + image + " deleted");
            }

        }
    }

    private boolean disregard(File file) {
        return file.delete();
    }


    @Override
    public void run() {
        try {
            pickTopCandidatesAndDisregardTheRest();
        } catch (IOException | PathNotFoundException e) {
            e.printStackTrace();
            log.debug("File copy interrupted " + e.toString());
        }
    }

    /**
     * Unit test
     */
    public static void main(String[] args) {
        Thread thread = new Thread(BestCandidateSelector.select("uploads/P10077_documentary/thumbnails/"));
        thread.start();
    }
}
