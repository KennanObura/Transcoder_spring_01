package kennan.co.ke.transcoder_01.core.entity;


import org.springframework.lang.Nullable;

import java.util.List;

public class Content {

    private String content_id;
    @Nullable
    private List<TempMedia> media_items;

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    @Nullable
    public List<TempMedia> getMediaList() {
        return media_items;
    }




    @Override
    public String toString() {
        return "Content{" +
                "content_id='" + content_id + '\'' +
                ", mediaList=" + media_items +
                '}';
    }

    public void setMedia_items(@Nullable List<TempMedia> media_items) {
        this.media_items = media_items;
    }
}


