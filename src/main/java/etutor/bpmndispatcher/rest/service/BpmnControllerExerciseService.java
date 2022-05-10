package etutor.bpmndispatcher.rest.service;

import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import etutor.bpmndispatcher.rest.dto.repositories.TestConfigDTORepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class BpmnControllerExerciseService {
    private final TestConfigDTORepository testConfigDTORepository;

    private final String TASK_TYPE = "bpmn";

    public BpmnControllerExerciseService(TestConfigDTORepository testConfigDTORepository) {
        this.testConfigDTORepository = testConfigDTORepository;
    }

    public int createExercise(TestConfigDTO testConfigDTO) throws IOException {
        if (testConfigDTO == null) throw new IOException("no Config");
        TestConfigDTO result = this.testConfigDTORepository.save(testConfigDTO);
        return result.getId();
    }

    public TestConfigDTO read(int id) throws Exception {
        if (id == -1) {
            throw new Exception("wrong ID: " + id);
        } else {
            Optional<TestConfigDTO> result = testConfigDTORepository.findById(id);
            if (result.isPresent())
                return result.get();
            else
                throw new Exception("no Config with ID: " + id + " found!");
        }
    }

    public int updateExercise(TestConfigDTO testConfigDTO, int id) throws Exception {
        Optional<TestConfigDTO> dbTestconfig = testConfigDTORepository.findById(id);
        if (dbTestconfig.isEmpty()) {
            throw new Exception("no Exercise with id: " + id);
        }
        TestConfigDTO dbResult = dbTestconfig.get();
        dbResult.setLabels(testConfigDTO.getLabels());
        dbResult.setTasksInCorrectOrder(testConfigDTO.getTasksInCorrectOrder());
        TestConfigDTO result = testConfigDTORepository.save(dbResult);
        return result.getId();
    }

    public void deleteExercise(int id) {
        if (id != -1)
            testConfigDTORepository.deleteById(id);
    }

}
