package at.jku.dke.etutor.bpmn.dispatcher.rest.service;

import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories.BpmnExcerciseRepository;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories.TestConfigDTORepository;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.BpmnExcercise;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.TestConfigDTO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BpmnControllerExerciseService {
    private final TestConfigDTORepository testConfigDTORepository;

    private final BpmnExcerciseRepository bpmnExcerciseRepository;

    private final String TASK_TYPE = "bpmn";

    public BpmnControllerExerciseService(TestConfigDTORepository testConfigDTORepository, BpmnExcerciseRepository bpmnExcerciseRepository) {
        this.testConfigDTORepository = testConfigDTORepository;
        this.bpmnExcerciseRepository = bpmnExcerciseRepository;
    }

    public int createExercise(TestConfigDTO testConfigDTO) throws IOException {
        if (testConfigDTO == null) throw new IOException("no Config");
        TestConfigDTO result = this.testConfigDTORepository.save(testConfigDTO);
        return result.getId();
    }

    public int createBpmnExercise(List<TestConfigDTO> testConfigList) throws IOException {
        if (testConfigList == null) throw new IOException("no Config");
        BpmnExcercise bpmnExcercise = this.bpmnExcerciseRepository.save(new BpmnExcercise());
        testConfigList.forEach((x) -> x.setBpmnExcercise(bpmnExcercise));
        List<TestConfigDTO> set = this.testConfigDTORepository.saveAll(testConfigList);
        LoggerFactory.getLogger(BpmnControllerExerciseService.class).warn(set.toString());
        return bpmnExcercise.getId();
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

    public BpmnExcercise readBpmn(int id) throws Exception {
        if (id == -1) {
            throw new Exception("wrong ID: " + id);
        } else {
            Optional<BpmnExcercise> result = bpmnExcerciseRepository.findById(id);
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

    public int updateBpmnExercise(List<TestConfigDTO> testConfigList, int id) throws Exception {
        Optional<BpmnExcercise> dbTestconfig = bpmnExcerciseRepository.findById(id);
        if (dbTestconfig.isEmpty()) {
            throw new Exception("no Exercise with id: " + id);
        }
        BpmnExcercise dbResult = dbTestconfig.get();
        dbResult.getTestConfigSet().clear();
        testConfigList.forEach((x) -> x.setBpmnExcercise(dbResult));
        List<TestConfigDTO> set = this.testConfigDTORepository.saveAll(testConfigList);
        return dbResult.getId();
    }

    public void deleteExercise(int id) {
        if (id != -1)
            testConfigDTORepository.deleteById(id);
    }

    public void deleteBpmnExercise(int id) {
        if (id >= 0)
            bpmnExcerciseRepository.deleteById(id);
    }

}
