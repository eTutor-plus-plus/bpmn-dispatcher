package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities;

import java.util.UUID;

/**
 * Wrapper for and Generator of unique submissionId's
 * (in order to facilitate sending the id as part of an EntityModel<T> Object(no Strings possible))
 */

public class SubmissionId {
    private String submissionId;
    private String isBpmnTask;

    public SubmissionId() {
        // empty constructor
    }

    public SubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * Factory method that takes the submission and generates a unique uuid.
     *
     * @param submission The submission from the student
     * @return a new SubmissionId Instance which wraps the generated ID
     */
    public static SubmissionId createId(Submission submission) {
        UUID uuid = UUID.randomUUID();
        submission.setSubmissionId(uuid.toString());
        return new SubmissionId(uuid.toString());
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String toString() {
        return submissionId;
    }

    public String getIsBpmnTask() {
        return isBpmnTask;
    }

    public void setIsBpmnTask(String isBpmnTask) {
        this.isBpmnTask = isBpmnTask;
    }
}
