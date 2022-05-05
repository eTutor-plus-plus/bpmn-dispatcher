package etutor.bpmndispatcher;

/**
 * Provides static constants for the application
 */
public class ETutorCORSPolicy {
    //    public static final String CORS_POLICY = "http://localhost:9000";
    public static final String CORS_POLICY = "*";

    private ETutorCORSPolicy() {
        throw new IllegalStateException("Utility class");
    }
}
