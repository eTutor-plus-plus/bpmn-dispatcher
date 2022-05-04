package etutor.bpmndispatcher.rest.dto.entities;


import etutor.bpmndispatcher.rest.dto.entities.testEngine.TestEngineRuntimeDTO;

import javax.persistence.*;

@Entity
public class TestEngineDTO {
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "testEngineRuntimeDTO")
    public TestEngineRuntimeDTO testEngineRuntimeDTO;
    @Id
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
