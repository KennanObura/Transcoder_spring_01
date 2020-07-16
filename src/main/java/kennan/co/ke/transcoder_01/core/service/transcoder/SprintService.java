package kennan.co.ke.transcoder_01.core.service.transcoder;


import javafx.util.Pair;
import kennan.co.ke.transcoder_01.core.service.transcoder.base.AbstractTranscoderService;
import kennan.co.ke.transcoder_01.core.model.AppMessage;
import kennan.co.ke.transcoder_01.core.entity.AppProcess;
import kennan.co.ke.transcoder_01.core.model.MediaModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static kennan.co.ke.transcoder_01.core.entity.AppEvent.FINALIZING;
import static kennan.co.ke.transcoder_01.core.entity.AppEvent.TERMINATED;



public class SprintService extends AbstractTranscoderService {


    private SprintService(MediaModel mediaModel) {
        super(mediaModel);
        super.process = AppProcess.SPRINT;
    }

    final private static int SPRINT_HEIGHT = 108;
    final private static int SPRINT_WIDTH = 192;
    final private String contentDirectory = mediaModel.getMedia().getDirectory() + "thumbnails";


    public static AbstractTranscoderService create(MediaModel mediaModel){
        return new SprintService(mediaModel);
    }


    @Override
    public void write() {
        try {
            List<File> dirContents = getContentsFromDirectory(contentDirectory);
            if (margeImages(dirContents))
                AppMessage.write(FINALIZING, mediaModel, process);
            else
                AppMessage.write(TERMINATED, mediaModel, process);

        } catch (IOException e) {
            AppMessage.write(TERMINATED, e.toString(), mediaModel, process);
            e.printStackTrace();
        }
    }


    private boolean margeImages(List<File> dirContents) throws IOException {
        BufferedImage tempImage = new BufferedImage(
                SPRINT_WIDTH,
                (SPRINT_HEIGHT +1 ) * dirContents.size(), //work these out
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = tempImage.getGraphics();


        for (int i = 0; i < dirContents.size(); i++)
            drawImage(i, dirContents, graphics); //draw image one by one to fill temp

        graphics.dispose(); // dispose off after done drawing
        return ImageIO.write(tempImage, "jpg", new File(mediaModel.getOutputDirectory()));
    }


    private static void drawImage(int index, List<File> dirContents, Graphics graphics) throws IOException {
        File image = dirContents.get(index);
        BufferedImage bufferedImage = ImageIO.read(image);
        Pair<Integer, Integer> position = indexToCoordinates(index);
        graphics.drawImage(bufferedImage, position.getKey(), position.getValue(), null);
    }


    private static Pair<Integer, Integer> indexToCoordinates(int index) {
        int y = (SPRINT_HEIGHT + 1) * index;
        return new Pair<>(0, y);
    }


    private static Pair<Integer, Integer> getImageDimension(File file) throws IOException {
        BufferedImage image = ImageIO.read(new File(String.valueOf(file)));
        return new Pair<>(image.getWidth(), image.getHeight());
    }


    private static List<File> getContentsFromDirectory(String directory) throws IOException {
        List<File> filesInFolder = Files.walk(Paths.get(directory))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        return filesInFolder;
    }
}
