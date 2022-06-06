package etutor.bpmndispatcher.service.bpmnValidationModul;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import etutor.bpmndispatcher.config.ApplicationProperties;
import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import etutor.bpmndispatcher.rest.dto.entities.TestEngineDTO;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BpmnTestEngineConnector {
    private final ApplicationProperties applicationProperties;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BpmnTestEngineConnector(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        client = new OkHttpClient().newBuilder().build();
    }


    public TestEngineDTO startTest(String id, TestConfigDTO testConfig) throws JsonProcessingException {
//        logger.info(objectMapper.writeValueAsString(testConfig));
//        return null;
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(objectMapper.writeValueAsString(testConfig), mediaType);
        Request request = new Request.Builder()
                .url(this.applicationProperties.getApiUrlBpmnModul().getUrl() + "bpmn?processID=" + id)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null)
                return objectMapper.readValue(response.body().string(), TestEngineDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Test failed");
    }
}
