package etutor.bpmndispatcher.service.bpmnValidationModul;

import etutor.bpmndispatcher.config.ApplicationProperties;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BpmnDeploymentService {
    private static final Logger logger = LoggerFactory.getLogger(BpmnDeploymentService.class);
    private final ApplicationProperties applicationproperties;
    private final OkHttpClient client;

    public BpmnDeploymentService(ApplicationProperties applicationProperties) {
        this.applicationproperties = applicationProperties;
        logger.info(logger.getName() + "- is started");
        client = new OkHttpClient().newBuilder().build();
    }

    public String deployNewBpmnWithString(String xml) throws IOException {
        logger.info("start Deployment");
        Response response = null;
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("upload", applicationproperties.getDeployment().getDeployName(),
                        RequestBody.create(xml, MediaType.parse("application/octet-stream")))
                .build();
        Request request = new Request.Builder()
                .url(applicationproperties.getDeployment().getUploadURL())
                .method("POST", body)
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        try {
            response = client.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } finally {
            if (response != null)
                response.close();
        }
        return "Deployment failed";
    }

    public boolean deleteProcess(String definitionID) throws IOException {
        if (definitionID == null || definitionID.isBlank()) throw new IOException("no definitionID");
        logger.info("start delete Deployment with definitionID: " + definitionID);

        Response response = null;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create("{}", JSON);
        Request request = new Request.Builder()
                .url("http://localhost:8080/engine-rest/process-definition/" + definitionID)
                .method("DELETE", body)
                .build();
        try {
            response = client.newCall(request).execute();
            logger.info("end delete Deployment");
            return response.code() == 200;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (response != null)
                response.close();
        }
    }
}
