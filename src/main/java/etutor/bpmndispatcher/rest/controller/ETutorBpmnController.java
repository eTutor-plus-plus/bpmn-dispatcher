package etutor.bpmndispatcher.rest.controller;

import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import etutor.bpmndispatcher.rest.service.BpmnControllerExerciseService;
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
    private final BpmnControllerExerciseService bpmnControllerExerciseService;

    public ETutorBpmnController(BpmnControllerExerciseService bpmnControllerExerciseService) {
        this.logger = LoggerFactory.getLogger(ETutorBpmnController.class);
        this.bpmnControllerExerciseService = bpmnControllerExerciseService;
    }

    /**
     * @param exerciseDTO = Testconfig
     * @return a ResponseEntity with ID
     */
    @PostMapping("/exercise")
    public ResponseEntity<Integer> createExercise(@RequestBody TestConfigDTO exerciseDTO) {
        try {
            int id = bpmnControllerExerciseService.createExercise(exerciseDTO);
            return ResponseEntity.ok(id);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(500).body(-1);
        }
    }

    /**
     * Returns the exercise definition for a given id
     *
     * @param id the id of the exercise
     * @return an Exercise
     */
    @GetMapping("/exercise/solution/id/{id}")
    public ResponseEntity<TestConfigDTO> getSolutionAndSorting(@PathVariable int id) {
        try {
            return ResponseEntity.ok(bpmnControllerExerciseService.read(id));
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
    public ResponseEntity<Integer> updateExercise(@RequestBody TestConfigDTO testConfigDTO, @PathVariable int id) {
        logger.info("Update Exercise: " + testConfigDTO);
        try {
            return ResponseEntity.ok(bpmnControllerExerciseService.updateExercise(testConfigDTO, id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(500).body(-1);
        }
    }

    /**
     * Deletes an exercise
     *
     * @param id the id of the exercise
     * @return a ResponseEntity
     */
    @DeleteMapping("exercise/id/{id}")
    public ResponseEntity<String> deleteExercise(@PathVariable int id) {
        try {
            bpmnControllerExerciseService.deleteExercise(id);
            return ResponseEntity.ok("Exercise with id " + id + " deleted");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseEntity.status(500).body("Could not delete exercise with id " + id);
    }

}
