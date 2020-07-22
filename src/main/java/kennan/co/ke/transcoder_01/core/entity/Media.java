package kennan.co.ke.transcoder_01.core.entity;


import kennan.co.ke.transcoder_01.core.common.DirectoryWalker;
import org.springframework.lang.Nullable;

import java.io.Serializable;

public class Media  implements Serializable {
    private String id;
    private String name;
    @Nullable
    private String directory;
    private String projectName;
    private int contentId;
    private String mediaType;


    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return this.name;
    }


    public void setDirectory(String directory){
        this.directory = directory;
    }


    private final static String ROOT = DirectoryWalker.getEnvironmentSpecificRootPath();

    public String getDirectory(){
//        if(directory == null || directory.isEmpty()) return directory;
        return ROOT + projectName +"/" + contentId + "/" + this.getMediaType() + "/" + id +"/";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getContentId() {
        return contentId;
    }

    public MEDIATYPE getMediaType() {
        if (isVideo(mediaType)) return MEDIATYPE.video;
        else return MEDIATYPE.audio;
    }

    private boolean isVideo(String mediaFileType) {
        return mediaFileType.contains("video");
    }

    @Override
    public String toString() {
        return "Media{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", directory='" + directory + '\'' +
                ", projectName='" + projectName + '\'' +
                ", contentId=" + contentId +
                ", mediaType=" + mediaType +
                '}';
    }

}
