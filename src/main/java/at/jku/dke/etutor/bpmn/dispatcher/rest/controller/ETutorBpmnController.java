package at.jku.dke.etutor.bpmn.dispatcher.rest.controller;

import at.jku.dke.etutor.bpmn.dispatcher.ETutorCORSPolicy;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.BpmnExcercise;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.TestConfigDTO;
import at.jku.dke.etutor.bpmn.dispatcher.rest.service.BpmnControllerExerciseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Controller for handling resources of the datalog module
 */
@RestController
@RequestMapping("/bpmn")
@CrossOrigin(origins = ETutorCORSPolicy.CORS_POLICY)
public class ETutorBpmnController {
    private final Logger logger;
    private final BpmnControllerExerciseService bpmnControllerExerciseService;

    public ETutorBpmnController(BpmnControllerExerciseService bpmnControllerExerciseService) {
        this.logger = LoggerFactory.getLogger(ETutorBpmnController.class);
        this.bpmnControllerExerciseService = bpmnControllerExerciseService;
    }

//    /**
//     * @param exerciseDTO = Testconfig
//     * @return a ResponseEntity with ID
//     */
//    @PostMapping("/exercise")
//    public ResponseEntity<Integer> createExercise(@RequestBody TestConfigDTO exerciseDTO) {
//        try {
//            int id = bpmnControllerExerciseService.createExercise(exerciseDTO);
//            return ResponseEntity.ok(id);
//        } catch (IOException e) {
//            logger.warn(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }

//    /**
//     * @param exerciseDTO = Testconfig
//     * @return a ResponseEntity with ID
//     */
//    @PostMapping("/bpmnExercise")
//    public ResponseEntity<Integer> createBpmnExercise(@RequestBody List<TestConfigDTO> exerciseDTO) {
//        try {
//            int id = bpmnControllerExerciseService.createBpmnExercise(exerciseDTO);
//            return ResponseEntity.ok(id);
//        } catch (IOException e) {
//            logger.warn(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }

    /**
     * @param exerciseDTO = Testconfig
     * @return a ResponseEntity with ID
     */
    @PostMapping("/exercise")
    public ResponseEntity<Integer> createBpmnExercise(@RequestBody List<TestConfigDTO> exerciseDTO) {
        try {
            int id = bpmnControllerExerciseService.createBpmnExercise(exerciseDTO);
            return ResponseEntity.ok(id);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

//    /**
//     * Returns the exercise definition for a given id
//     *
//     * @param id the id of the exercise
//     * @return an Exercise
//     */
//    @GetMapping("/exercise/solution/id/{id}")
//    public ResponseEntity<TestConfigDTO> getSolutionAndSorting(@PathVariable int id) {
//        try {
//            return ResponseEntity.ok(bpmnControllerExerciseService.read(id));
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

    /**
     * Returns the exercise definition for a given id
     *
     * @param id the id of the exercise
     * @return an Exercise
     */
    @GetMapping("/exercise/solution/id/{id}")
    public ResponseEntity<BpmnExcercise> getExercise(@PathVariable int id) {
        try {
            return ResponseEntity.ok(bpmnControllerExerciseService.readBpmn(id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
//    /**
//     * Returns the exercise definition for a given id
//     *
//     * @param id the id of the exercise
//     * @return an Exercise
//     */
//    @GetMapping("/bpmnExercise/id/{id}")
//    public ResponseEntity<BpmnExcercise> getExercise(@PathVariable int id) {
//        try {
//            return ResponseEntity.ok(bpmnControllerExerciseService.readBpmn(id));
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }


//    /**
//     * Updates an exercise
//     *
//     * @param testConfigDTO the TestConfig
//     * @param id            the id of the exercise
//     * @return a ResponseEntity
//     */
//    @PostMapping("exercise/id/{id}")
//    public ResponseEntity<Integer> updateExercise(@RequestBody TestConfigDTO testConfigDTO, @PathVariable int id) {
//        logger.info("Update Exercise: " + testConfigDTO);
//        try {
//            return ResponseEntity.ok(bpmnControllerExerciseService.updateExercise(testConfigDTO, id));
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

//    /**
//     * Updates an exercise
//     *
//     * @param exerciseDTO the TestConfig
//     * @param id          the id of the exercise
//     * @return a ResponseEntity
//     */
//    @PostMapping("bpmnExercise/id/{id}")
//    public ResponseEntity<Integer> updateBpmnExercise(@RequestBody List<TestConfigDTO> exerciseDTO, @PathVariable int id) {
//        logger.info("Update Exercise: " + exerciseDTO.toString());
//        try {
//            return ResponseEntity.ok(bpmnControllerExerciseService.updateBpmnExercise(exerciseDTO, id));
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

    /**
     * Updates an exercise
     *
     * @param exerciseDTO the TestConfig
     * @param id          the id of the exercise
     * @return a ResponseEntity
     */
    @PostMapping("exercise/id/{id}")
    public ResponseEntity<Integer> updateBpmnExercise(@RequestBody List<TestConfigDTO> exerciseDTO, @PathVariable int id) {
        logger.info("Update Exercise: " + exerciseDTO.toString());
        try {
            return ResponseEntity.ok(bpmnControllerExerciseService.updateBpmnExercise(exerciseDTO, id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//    /**
//     * Deletes an exercise
//     *
//     * @param id the id of the exercise
//     * @return a ResponseEntity
//     */
//    @DeleteMapping("exercise/id/{id}")
//    public ResponseEntity<String> deleteExercise(@PathVariable int id) {
//        try {
//            bpmnControllerExerciseService.deleteExercise(id);
//            return ResponseEntity.ok("Exercise with id " + id + " deleted");
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not delete exercise with id " + id);
//    }

    /**
     * Deletes an exercise
     *
     * @param id the id of the exercise
     * @return a ResponseEntity
     */
    @DeleteMapping("exercise/id/{id}")
    public ResponseEntity<String> deleteBpmnExercise(@PathVariable int id) {
        try {
            bpmnControllerExerciseService.deleteBpmnExercise(id);
            return ResponseEntity.ok("Exercise with id " + id + " deleted");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not delete exercise with id " + id);
    }
//
//    /**
//     * Deletes an exercise
//     *
//     * @param id the id of the exercise
//     * @return a ResponseEntity
//     */
//    @DeleteMapping("bpmnExercise/id/{id}")
//    public ResponseEntity<String> deleteBpmnExercise(@PathVariable int id) {
//        try {
//            bpmnControllerExerciseService.deleteBpmnExercise(id);
//            return ResponseEntity.ok("Exercise with id " + id + " deleted");
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not delete exercise with id " + id);
//    }

}
