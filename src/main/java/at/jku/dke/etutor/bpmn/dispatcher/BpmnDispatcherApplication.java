package at.jku.dke.etutor.bpmn.dispatcher;

import at.jku.dke.etutor.bpmn.dispatcher.config.ApplicationProperties;
import at.jku.dke.etutor.bpmn.dispatcher.config.AsyncConfiguration;
import at.jku.dke.etutor.bpmn.dispatcher.config.DataSourceConfiguration;
import at.jku.dke.etutor.bpmn.dispatcher.rest.controller.ETutorBpmnController;
import at.jku.dke.etutor.bpmn.dispatcher.rest.controller.ETutorGradingController;
import at.jku.dke.etutor.bpmn.dispatcher.rest.controller.ETutorSubmissionController;
import at.jku.dke.etutor.bpmn.dispatcher.service.ReportService;
import at.jku.dke.etutor.bpmn.dispatcher.service.RepositoryService;
import at.jku.dke.etutor.bpmn.dispatcher.service.SubmissionDispatcherService;
import at.jku.dke.etutor.bpmn.dispatcher.service.bpmnValidationModul.BpmnDeploymentService;
import at.jku.dke.etutor.bpmn.dispatcher.service.bpmnValidationModul.BpmnModuleService;
import at.jku.dke.etutor.bpmn.dispatcher.service.bpmnValidationModul.BpmnTestEngineConnector;
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
