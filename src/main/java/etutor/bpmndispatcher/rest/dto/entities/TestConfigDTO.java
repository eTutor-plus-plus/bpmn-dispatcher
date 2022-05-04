package etutor.bpmndispatcher.rest.dto.entities;


import etutor.bpmndispatcher.rest.dto.interfaces.TestConfig_Interface;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class TestConfigDTO implements TestConfig_Interface, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @ElementCollection
    private List<String> tasksInCorrectOrder = new java.util.ArrayList<>();
    @ElementCollection
    private List<String> labels = new java.util.ArrayList<>();

    public TestConfigDTO() {
    }

    public TestConfigDTO(List<String> tasksInCorrectOrder, List<String> labels) {
        this.tasksInCorrectOrder = tasksInCorrectOrder;
        this.labels = labels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTasksInCorrectOrder() {
        return tasksInCorrectOrder;
    }

    public void setTasksInCorrectOrder(List<String> taskNames) {
        this.tasksInCorrectOrder = taskNames;
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
                "id=" + id +
                ", taskNames=" + tasksInCorrectOrder +
                ", labels=" + labels +
                '}';
    }


}
