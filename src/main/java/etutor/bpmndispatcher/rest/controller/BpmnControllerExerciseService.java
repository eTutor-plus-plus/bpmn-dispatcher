package etutor.bpmndispatcher.rest.controller;

import etutor.bpmndispatcher.config.ApplicationProperties;
import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import etutor.bpmndispatcher.rest.dto.repositories.GradingDTORepository;
import etutor.bpmndispatcher.rest.dto.repositories.TestConfigDTORepository;
import etutor.bpmndispatcher.service.SubmissionDispatcherService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class BpmnControllerExerciseService {
    private final ApplicationProperties properties;
    private final SubmissionDispatcherService dispatcherService;
    private final GradingDTORepository gradingDTORepository;
    private final TestConfigDTORepository testConfigDTORepository;
    private final String TASK_TYPE = "bpmn";

    public BpmnControllerExerciseService(ApplicationProperties properties, SubmissionDispatcherService dispatcherService, GradingDTORepository gradingDTORepository, TestConfigDTORepository testConfigDTORepository) {
        this.properties = properties;
        this.dispatcherService = dispatcherService;
        this.gradingDTORepository = gradingDTORepository;
        this.testConfigDTORepository = testConfigDTORepository;
    }

    public Long createExercise(TestConfigDTO testConfigDTO) throws IOException {
        if (testConfigDTO == null) throw new IOException("no Config");
        TestConfigDTO result = this.testConfigDTORepository.save(testConfigDTO);
        return result.getId();
    }

    public TestConfigDTO read(Long id) throws Exception {
        if (id == null || id == -1) {
            throw new Exception("wrong ID: " + id);
        } else {
            Optional<TestConfigDTO> result = testConfigDTORepository.findById(id);
            if (result.isPresent())
                return result.get();
            else
                throw new Exception("no Config with ID: " + id + " found!");
        }
    }

    public Long updateExercise(TestConfigDTO testConfigDTO, Long id) throws Exception {
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

    public void deleteExercise(Long id) {
        if (id != null && id != -1)
            testConfigDTORepository.deleteById(id);
    }

}
