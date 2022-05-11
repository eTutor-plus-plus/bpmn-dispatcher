package etutor.bpmndispatcher.rest.controller;

import etutor.bpmndispatcher.service.bpmnValidationModul.BpmnDeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
//    private final BpmnDeploymentService bpmnDeploymentService;

    public TestController(BpmnDeploymentService bpmnDeploymentService) {
//        this.bpmnDeploymentService = bpmnDeploymentService;
    }


    @PostMapping(path = "")
    public ResponseEntity<String> deployBPMN(@RequestBody String bpmnXml) {
        JSONObject jsonObject_failed = new JSONObject();
        try {
            jsonObject_failed.put("result", "Parse not available");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        if (bpmnXml == null || bpmnXml.isBlank()) return ResponseEntity.status(500).body(jsonObject_failed.toString());
//        logger.info(bpmnXml);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("test", "test");
            jsonObject.put("xml", bpmnXml);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        logger.info(jsonObject.toString());
        return ResponseEntity.ok(jsonObject.toString());
        //        String xml = null;
//        try {
//            xml = jsonObject.getString("xml");
////            logger.info(xml);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        if (xml != null) {
//            try {
//                String result = bpmnDeploymentService.deployNewBpmnWithString(xml);
//                if (result.contains("ParseException")) {
//                    return ResponseEntity.status(400).body(result);
//                } else {
//                    JSONObject obj = new JSONObject(result);
//                    String id = obj.getString("id");
//                    logger.info("ID:+++++++" + id);
//                    String definitionID = obj.getJSONObject("deployedProcessDefinitions").keys().next().toString();
//                    logger.warn(definitionID);
//                    logger.info("---Deployed!---");
//                    return ResponseEntity.ok(definitionID);
//                }
//            } catch (IOException e) {
//                logger.warn(e.getMessage());
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return ResponseEntity.status(500).body("Deployment");
    }
}
