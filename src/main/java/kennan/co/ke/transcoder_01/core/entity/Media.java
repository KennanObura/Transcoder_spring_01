package kennan.co.ke.transcoder_01.core.entity;

public class Media {
    private String id;
    private String name;
    private String directory;


    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return this.name;
    }


    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory(){
        return this.directory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
