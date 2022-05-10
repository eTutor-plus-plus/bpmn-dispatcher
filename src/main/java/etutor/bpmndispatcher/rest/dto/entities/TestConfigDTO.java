package etutor.bpmndispatcher.rest.dto.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import etutor.bpmndispatcher.rest.dto.interfaces.TestConfig_Interface;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Transactional
public class TestConfigDTO implements TestConfig_Interface, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

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

    public TestConfigDTO() {
    }

    public TestConfigDTO(List<String> tasksInCorrectOrder, List<String> labels) {
        this.tasksInCorrectOrder = tasksInCorrectOrder;
        this.labels = labels;
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

    @Override
    public String toString() {
        return "TestConfigDTO{" +
                "id=" + id +
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
