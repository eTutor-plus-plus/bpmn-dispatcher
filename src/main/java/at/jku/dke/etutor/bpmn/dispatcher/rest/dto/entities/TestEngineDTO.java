package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities;


import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.testEngine.TestEngineRuntimeDTO;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Transactional
public class TestEngineDTO {
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "test_engine_runtimedto", referencedColumnName = "id")
    public TestEngineRuntimeDTO testEngineRuntimeDTO;
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public TestEngineDTO() {
        this.testEngineRuntimeDTO = new TestEngineRuntimeDTO();
    }

    public TestEngineDTO(TestEngineRuntimeDTO testEngineRuntimeDTO) {
        this.testEngineRuntimeDTO = testEngineRuntimeDTO;
    }

    public TestEngineRuntimeDTO getTestEngineRuntimeDTO() {
        return testEngineRuntimeDTO;
    }

    public void setTestEngineRuntimeDTO(TestEngineRuntimeDTO testEngineRuntimeDTO) {
        this.testEngineRuntimeDTO = testEngineRuntimeDTO;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TestEngineDTO{" +
                "id=" + id +
                ", testEngineRuntimeDTO=" + testEngineRuntimeDTO.toString() +
                '}';
    }
}
