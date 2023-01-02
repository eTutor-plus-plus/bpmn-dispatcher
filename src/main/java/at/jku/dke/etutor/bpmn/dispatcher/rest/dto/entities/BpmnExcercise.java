package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "bpmn_excercise")
public class BpmnExcercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Integer id;

    @OneToMany(mappedBy = "bpmnExcercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<TestConfigDTO> testConfigSet = new LinkedHashSet<>();

    public Set<TestConfigDTO> getTestConfigSet() {
        return testConfigSet;
    }

    public void setTestConfigSet(Set<TestConfigDTO> testConfigSet) {
        this.testConfigSet = testConfigSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BpmnExcercise{" +
                "id=" + id +
                '}';
    }
}
