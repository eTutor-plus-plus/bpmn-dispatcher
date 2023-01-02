package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities;


import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.interfaces.TestConfig_Interface;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Transactional
public class TestConfigDTO implements TestConfig_Interface, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @Column(nullable = false)
    private boolean wrongPath = false;

    @ElementCollection
    @CollectionTable(name = "test_configdto_tasks_in_correct_order",
            joinColumns = @JoinColumn(name = "config_id"))
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "tasks_in_correct_order")
    private List<String> tasksInCorrectOrder = new java.util.ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "test_configdto_labels",
            joinColumns = @JoinColumn(name = "config_id"))
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "labels")
    private List<String> labels = new java.util.ArrayList<>();

    @ManyToOne
    @JsonBackReference
    private BpmnExcercise bpmnExcercise;

    public TestConfigDTO() {
    }

    public TestConfigDTO(boolean wrongPath, List<String> tasksInCorrectOrder, List<String> labels, BpmnExcercise bpmnExcercise) {
        this.wrongPath = wrongPath;
        this.tasksInCorrectOrder = tasksInCorrectOrder;
        this.labels = labels;
        this.bpmnExcercise = bpmnExcercise;
    }

    public BpmnExcercise getBpmnExcercise() {
        return bpmnExcercise;
    }

    public void setBpmnExcercise(BpmnExcercise bpmnExcercise) {
        this.bpmnExcercise = bpmnExcercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isWrongPath() {
        return wrongPath;
    }

    public void setWrongPath(boolean shouldHaveCorrectOrder) {
        this.wrongPath = shouldHaveCorrectOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestConfigDTO)) return false;
        TestConfigDTO that = (TestConfigDTO) o;
        return getId() == that.getId() && isWrongPath() == that.isWrongPath() && getTasksInCorrectOrder().equals(that.getTasksInCorrectOrder()) && getLabels().equals(that.getLabels()) && getBpmnExcercise().equals(that.getBpmnExcercise());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isWrongPath(), getTasksInCorrectOrder(), getLabels(), getBpmnExcercise());
    }

    @Override
    public String toString() {
        return "TestConfigDTO{" +
                "id=" + id +
                ", shouldHaveCorrectOrder=" + wrongPath +
                ", tasksInCorrectOrder=" + tasksInCorrectOrder +
                ", labels=" + labels +
                '}';
    }
}

//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(name = "test_configdto_tasks_in_correct_order",
//            joinColumns = {@JoinColumn(name = "test_configdto_id")})
//    @Column(name = "tasks_in_correct_order", length = 2048)
//    @Column(name = "tasks_in_correct_order")
//    @ElementCollection(targetClass = String.class)
//    private List<String> tasksInCorrectOrder;
//    //    @ElementCollection(targetClass = String.class)
////    @CollectionTable(name = "test_configdto_labels",
////            joinColumns = {@JoinColumn(name = "test_configdto_id")})
////    @Column(name = "labels", length = 2048)
//    @Column(name = "labels")
//    @ElementCollection(targetClass = String.class)
//    private List<String> labels;
