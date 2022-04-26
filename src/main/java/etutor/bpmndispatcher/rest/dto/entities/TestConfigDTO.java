package etutor.bpmndispatcher.rest.dto.entities;


import etutor.bpmndispatcher.rest.dto.interfaces.TestConfig_Interface;

import javax.persistence.*;
import java.util.List;

@Entity
public class TestConfigDTO implements TestConfig_Interface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    private List<String> taskNames = new java.util.ArrayList<>();
    @ElementCollection
    private List<String> labels = new java.util.ArrayList<>();

    public TestConfigDTO() {
    }

    public TestConfigDTO(List<String> taskNames, List<String> labels) {
        this.taskNames = taskNames;
        this.labels = labels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTaskNames() {
        return taskNames;
    }

    public void setTaskNames(List<String> taskNames) {
        this.taskNames = taskNames;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "TestConfigDTO{" +
                "taskNames=" + taskNames +
                ", labels=" + labels +
                '}';
    }
}
