package etutor.bpmndispatcher;

import etutor.bpmndispatcher.config.ApplicationProperties;
import etutor.bpmndispatcher.config.AsyncConfiguration;
import etutor.bpmndispatcher.config.DataSourceConfiguration;
import etutor.bpmndispatcher.rest.controller.ETutorBpmnController;
import etutor.bpmndispatcher.rest.controller.ETutorGradingController;
import etutor.bpmndispatcher.rest.controller.ETutorSubmissionController;
import etutor.bpmndispatcher.service.ReportService;
import etutor.bpmndispatcher.service.RepositoryService;
import etutor.bpmndispatcher.service.SubmissionDispatcherService;
import etutor.bpmndispatcher.service.bpmnValidationModul.BpmnDeploymentService;
import etutor.bpmndispatcher.service.bpmnValidationModul.BpmnModuleService;
import etutor.bpmndispatcher.service.bpmnValidationModul.BpmnTestEngineConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootApplication
@ComponentScan(basePackageClasses = {
        BpmnModuleService.class,
        ETutorGradingController.class,
        ETutorSubmissionController.class,
        ETutorBpmnController.class,
        RepositoryService.class,
        SubmissionDispatcherService.class,
        AsyncConfiguration.class,
        DataSourceConfiguration.class,
        ReportService.class,
        BpmnTestEngineConnector.class,
        BpmnDeploymentService.class,
        BpmnDispatcherApplication.class
})
@EnableAsync
public class BpmnDispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(BpmnDispatcherApplication.class, args);
    }

}
