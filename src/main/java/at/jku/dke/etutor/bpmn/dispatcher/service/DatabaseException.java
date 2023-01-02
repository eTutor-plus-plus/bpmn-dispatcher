package at.jku.dke.etutor.bpmn.dispatcher.service;

/**
 * Exception that leverages an SQLException
 */
public class DatabaseException extends Exception {

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String s) {
        super(s);
    }
}
