package at.jku.dke.etutor.bpmn.dispatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * The Configuration to enable async handling of requests see { at.jku.dke.etutor.grading.service.SubmissionDispatcherService#run(Submission, Locale)}
 */
@Configuration
public class AsyncConfiguration {
    private final ApplicationProperties applicationProperties;

    public AsyncConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * A Bean representing the configuration
     *
     * @return an Executor instance
     */
    @Bean(name = "asyncExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(applicationProperties.getAsync().getCorePoolSize());
        executor.setMaxPoolSize(applicationProperties.getAsync().getMaxPoolSize());
        executor.setQueueCapacity(applicationProperties.getAsync().getQueueCapacity());
        executor.setThreadNamePrefix(applicationProperties.getAsync().getThreadNamePrefix());
        executor.initialize();
        return executor;
    }
}
