package at.jku.dke.etutor.bpmn.dispatcher.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories.TestConfigDTORepository;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories.TestEngineDTORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Bean
    CommandLineRunner initDatabase(TestConfigDTORepository repository, TestEngineDTORepository testEngineDTORepository) {

//        return args -> {
//            log.info("Preloading " + repository.save(new TestConfigDTO(List.of("Test", "Test2"), List.of("Test", "Test2"))));
//            log.info("Preloading " + testEngineDTORepository.save(new TestEngineDTO()));
//        };
        return args -> {
            // TestConfigDTO test = repository.save(new TestConfigDTO(List.of("a", "b", "c", "Test", "Test2", "Test3"), List.of("a", "b")));
            // log.info("Preloading " + repository.save(new TestConfigDTO(List.of("Test", "Test2"), List.of("Test", "Test2"))));
            // log.info("Preloading " + test);
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            MediaType mediaType = MediaType.parse("application/json");
//            RequestBody body = RequestBody.create(objectMapper.writeValueAsString(test), mediaType);
//            Request request = new Request.Builder()
//                    .url("http://localhost:8080/bpmn?processID=BPMN-Modul-process")
//                    .method("POST", body)
//                    .addHeader("Content-Type", "application/json")
//                    .build();
//            try (Response response = client.newCall(request).execute()) {
//                assert response.body() != null;
//                TestEngineDTO testEngineDTO = objectMapper.readValue(response.body().string(), TestEngineDTO.class);
//                log.info("Preloading " + testEngineDTORepository.save(testEngineDTO));
//            } catch (Exception e) {
//                log.warn(e.getMessage());
//            }
        };
    }
}
