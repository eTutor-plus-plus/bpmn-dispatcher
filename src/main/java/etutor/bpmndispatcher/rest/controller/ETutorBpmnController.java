package etutor.bpmndispatcher.rest.controller;

import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import etutor.bpmndispatcher.service.bpmn.BpmnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Controller for handling resources of the datalog module
 */
@RestController
@RequestMapping("/bpmn")
public class ETutorBpmnController {
    private final Logger logger;
    private final BpmnService bpmnService;

    public ETutorBpmnController(BpmnService bpmnService) {
        this.logger = LoggerFactory.getLogger(ETutorBpmnController.class);
        this.bpmnService = bpmnService;
    }

    /**
     * @param exerciseDTO = Testconfig
     * @return a ResponseEntity with ID
     */
    @PutMapping("/exercise")
    public ResponseEntity<Long> createExercise(@RequestBody TestConfigDTO exerciseDTO) {
        Long id;
        try {
            id = bpmnService.createExercise(exerciseDTO);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(500).body(-1L);
        }
        return ResponseEntity.ok(id);
    }

    /**
     * Returns the exercise definition for a given id
     *
     * @param id the id of the exercise
     * @return an Exercise
     */
    @GetMapping("/exercise/solution/id/{id}")
    public ResponseEntity<TestConfigDTO> getSolutionAndSorting(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bpmnService.read(id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Updates an exercise
     *
     * @param testConfigDTO the TestConfig
     * @param id            the id of the exercise
     * @return a ResponseEntity
     */
    @PostMapping("exercise/id/{id}")
    public ResponseEntity<Long> updateExercise(@RequestBody TestConfigDTO testConfigDTO, @PathVariable Long id) {
        logger.info("Update Exercise: " + testConfigDTO);
        try {
            return ResponseEntity.ok(bpmnService.updateExercise(testConfigDTO, id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).body(-1L);
        }
    }

    /**
     * Deletes an exercise
     *
     * @param id the id of the exercise
     * @return a ResponseEntity
     */
    @DeleteMapping("exercise/id/{id}")
    public ResponseEntity<String> deleteExercise(@PathVariable Long id) {
        String response = "Exercise with id " + id + " deleted";
        try {
            bpmnService.deleteExercise(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseEntity.status(500).body("Could not delete exercise with id " + id);
    }

}
