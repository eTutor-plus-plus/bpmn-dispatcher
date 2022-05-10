//package etutor.bpmndispatcher.service.bpmnValidationModul;
//
//import ch.qos.logback.classic.Logger;
//import etutor.bpmndispatcher.config.ApplicationProperties;
//import etutor.bpmndispatcher.service.SubmissionDispatcherService;
//import okhttp3.*;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//public class BpmnRuntimeService {
//    private final ApplicationProperties applicationProperties;
//    private final Logger logger;
//    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
//
//    public BpmnRuntimeService(ApplicationProperties applicationProperties) {
//        this.applicationProperties = applicationProperties;
//        this.logger = (Logger) LoggerFactory.getLogger(SubmissionDispatcherService.class);
//    }
//
//    public String deployBpmn(String bpmnXml) throws Exception {
//        //p
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(bpmnXml, mediaType);
//        Request request = new Request.Builder()
//                .url(this.applicationProperties.getApiUrlBpmnModul() + "/deployment")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/xml")
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            if (response.body() != null) {
//                return response.body().string();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        throw new Exception("Deployment failed");
//    }
//
//    public boolean deleteProcess(String definitionID) {
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create("", mediaType);
//        Request request = new Request.Builder()
//                .url(this.applicationProperties.getApiUrlBpmnModul() + "/deployment?definitionID=" + definitionID)
//                .method("DELETE", body)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.code() == 200;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
