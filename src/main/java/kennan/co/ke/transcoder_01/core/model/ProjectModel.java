package kennan.co.ke.transcoder_01.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import kennan.co.ke.transcoder_01.core.entity.Project;

import java.util.List;

public class ProjectModel {

    @JsonProperty
    private List<Project> data;

    public List<Project> getProjects() {
        return data;
    }

    public void setProjects(List<Project> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProjectModel{" +
                "data=" + data +
                '}';
    }
}
