package etutor.bpmndispatcher.evaluation;

import java.io.Serializable;

/**
 * Defines methods with regards to an analysis of a submission
 */
public interface Analysis extends Serializable {
    /**
     * Returns the submission
     *
     * @return the submission
     */
    Serializable getSubmission();

    /**
     * Sets the submission
     *
     * @param submission the submission
     */
    void setSubmission(Serializable submission);

    /**
     * Returns wheter the submission suits the solution
     *
     * @return a boolean value
     */
    boolean submissionSuitsSolution();

    /**
     * Sets wheter the submission suits the solution
     *
     * @param b the boolean value
     */
    void setSubmissionSuitsSolution(boolean b);
}
