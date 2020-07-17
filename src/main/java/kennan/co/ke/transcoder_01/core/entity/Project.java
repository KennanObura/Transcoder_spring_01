package kennan.co.ke.transcoder_01.core.entity;

import org.springframework.lang.Nullable;

import java.util.List;

public class Project {

    private String project_id;

    private String project_number;
    @Nullable
    private List<Content> content_items;

    public String getProjectId() {
        return project_id;
    }

    public void setProjectId(String project_id) {
        this.project_id = project_id;
    }

    public String getProjectName() {
        return project_number;
    }

    @Nullable
    public List<Content> getContents(){
        return this.content_items;
    }

    public void setProject_number(String project_number) {
        this.project_number = project_number;
    }



    @Override
    public String toString() {
        return "Project{" +
                "project_id='" + project_id + '\'' +
                ", project_name='" + project_number + '\'' +
                ", contentList=" + content_items +
                '}';
    }

    public void setContent_items(@Nullable List<Content> content_items) {
        this.content_items = content_items;
    }
}


