package kennan.co.ke.transcoder_01.core.usecase.transcoder.sprint.tileGenerator;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;


public class TileGenerator implements InterfaceTileGenerator {

    protected static final Logger log = LoggerFactory.getLogger(TileGenerator.class);
    final private static int SPRINT_HEIGHT = 108;
    final private static int SPRINT_WIDTH = 192;

    public static TileGenerator create(String outputDirectory, TreeMap<Integer, File> sortedCandidatesFromDirectory) {
        return new TileGenerator(outputDirectory, sortedCandidatesFromDirectory);
    }

    private TileGenerator(String outputDirectory, TreeMap<Integer, File> sortedCandidatesFromDirectory) {
        this.outputDirectory = outputDirectory;
        this.sortedCandidatesFromDirectory = sortedCandidatesFromDirectory;
        System.out.println(sortedCandidatesFromDirectory.size() + " of Tree map");
    }


    private final String outputDirectory;
    private final TreeMap<Integer, File> sortedCandidatesFromDirectory;







    private boolean margeThumbnails() throws IOException {
        BufferedImage tempImage = new BufferedImage(SPRINT_WIDTH,
                (SPRINT_HEIGHT + 1) * sortedCandidatesFromDirectory.size(), //work these out
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = tempImage.getGraphics();


        for (int i = 0; i < sortedCandidatesFromDirectory.size(); i++)
            drawImage(i, sortedCandidatesFromDirectory.get(i), graphics); //draw image one by one to fill temp
//            System.out.println(sortedCandidatesFromDirectory.get(i));

        graphics.dispose(); // dispose off after done drawing
        System.out.println(outputDirectory + " output to =================================");
        return ImageIO.write(tempImage, "jpg", new File(outputDirectory));
    }


    private static void drawImage(int index, File image, Graphics graphics) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image);
        Pair<Integer, Integer> position = indexToCoordinates(index);
        graphics.drawImage(bufferedImage, position.getKey(), position.getValue(), null);

        /*
         * Delete file after used
         */
        if (image.delete()) log.info("File " + image.getName() + " tilled and deleted");
    }


    private static Pair<Integer, Integer> indexToCoordinates(int index) {
        int y = (SPRINT_HEIGHT + 1) * index;
        return new Pair<>(0, y);
    }

    @Override
    public void run() {
        try {
            if (margeThumbnails()) log.info("Tile created");
        } catch (IOException e) {
            log.debug("Error " + e.toString());
            e.printStackTrace();
        }
    }
}
