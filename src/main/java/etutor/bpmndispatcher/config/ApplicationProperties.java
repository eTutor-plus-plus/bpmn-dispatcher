package etutor.bpmndispatcher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Used to retrieve the application properties
 */
@ConfigurationProperties(value = "application")
public class ApplicationProperties {
    private ApiUrlBpmnModul apiUrlBpmnModul = new ApiUrlBpmnModul();

    private Async async = new Async();

    private DataSource datasource = new DataSource();

    private Grading grading = new Grading();


    public Async getAsync() {
        return async;
    }

    public void setAsync(Async async) {
        this.async = async;
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    public Grading getGrading() {
        return grading;
    }

    public void setGrading(Grading grading) {
        this.grading = grading;
    }

    public static class ApiUrlBpmnModul {
        private String apiUrlBpmnModul;

        public String getApiUrlBpmnModul() {
            return apiUrlBpmnModul;
        }

        public void setApiUrlBpmnModul(String apiUrlBpmnModul) {
            this.apiUrlBpmnModul = apiUrlBpmnModul;
        }
    }

    /**
     * The properties needed to configure the async TaskExecutor
     */
    public static class Async {
        private int maxPoolSize;
        private int corePoolSize;
        private int queueCapacity;
        private String threadNamePrefix;

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        public String getThreadNamePrefix() {
            return threadNamePrefix;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }
    }

    public static class DataSource {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private long maxLifetime;
        private int maxPoolSize;

        public DataSource() {

        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driver) {
            this.driverClassName = driver;
        }

        public long getMaxLifetime() {
            return maxLifetime;
        }

        public void setMaxLifetime(long maxLifetime) {
            this.maxLifetime = maxLifetime;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }
    }

    /**
     * The properties for the grading package
     */
    public static class Grading {
        private String connSuperUser;
        private String connSuperPwd;
        private String corsPolicy;
        private String JDBCDriver;
        private long sleepDuration;

        public String getConnSuperUser() {
            return connSuperUser;
        }

        public void setConnSuperUser(String connSuperUser) {
            this.connSuperUser = connSuperUser;
        }

        public String getConnSuperPwd() {
            return connSuperPwd;
        }

        public void setConnSuperPwd(String connSuperPwd) {
            this.connSuperPwd = connSuperPwd;
        }

        public String getCorsPolicy() {
            return corsPolicy;
        }

        public void setCorsPolicy(String corsPolicy) {
            this.corsPolicy = corsPolicy;
        }

        public String getJDBCDriver() {
            return JDBCDriver;
        }

        public void setJDBCDriver(String JDBCDriver) {
            this.JDBCDriver = JDBCDriver;
        }

        public long getSleepDuration() {
            return sleepDuration;
        }

        public void setSleepDuration(long sleepDuration) {
            this.sleepDuration = sleepDuration;
        }
    }
}
