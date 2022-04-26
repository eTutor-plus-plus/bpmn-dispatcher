package etutor.bpmndispatcher.rest.test;

import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import etutor.bpmndispatcher.rest.dto.entities.TestEngineDTO;
import etutor.bpmndispatcher.rest.dto.entities.repositories.TestConfigDTORepository;
import etutor.bpmndispatcher.rest.dto.entities.repositories.TestEngineDTORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TestConfigDTORepository repository, TestEngineDTORepository testEngineDTORepository) {

        return args -> {
            log.info("Preloading " + repository.save(new TestConfigDTO(List.of("Test", "Test2"), List.of("Test", "Test2"))));
            log.info("Preloading " + testEngineDTORepository.save(new TestEngineDTO()));
        };
    }
}
