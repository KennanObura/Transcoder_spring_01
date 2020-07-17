package kennan.co.ke.transcoder_01.core.entity;

public class TempMedia {
    private String media_id;
    private String media_directory;
    private String media_type;
    private String media_alias;


    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_directory() {
        return media_directory;
    }

    public void setMedia_directory(String media_directory) {
        this.media_directory = media_directory;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getPath() {
        return media_alias;
    }

    public void setMedia_alias(String media_alias) {
        this.media_alias = media_alias;
    }


    @Override
    public String toString() {
        return "TempMedia{" +
                "media_id='" + media_id + '\'' +
                ", media_directory='" + media_directory + '\'' +
                ", media_type='" + media_type + '\'' +
                ", media_alias='" + media_alias + '\'' +
                '}';
    }
}
