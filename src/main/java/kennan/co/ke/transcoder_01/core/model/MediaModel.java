package kennan.co.ke.transcoder_01.core.model;

import kennan.co.ke.transcoder_01.core.entity.Media;

public class MediaModel {
    public MediaModel(Media media,
                      String startTime,
                      String endTime,
                      String sort) {
        this.media = media;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sort = sort;
    }


    public MediaModel(Media media) {
        this.media = media;
    }

    private final Media media;
    private String startTime;
    private String endTime;
    private String sort;
    private String outputDirectory;
    private String masterDirectory;

    public Media getMedia() {
        return media;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSort() {
        return sort;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getMasterDirectory() {
        return masterDirectory;
    }

    public void setMasterDirectory(String masterDirectory) {
        this.masterDirectory = masterDirectory;
    }
}
