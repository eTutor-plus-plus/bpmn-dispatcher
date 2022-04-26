package etutor.bpmndispatcher;

import etutor.bpmndispatcher.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootApplication
@EnableAsync
public class BpmnDispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(BpmnDispatcherApplication.class, args);
    }

}
