package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities;


import at.jku.dke.etutor.objects.dispatcher.SubmissionDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity representing a submission.
 * Fields are corresponding to the core-interfaces (see package core/evaluation).
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "submission")
public class Submission implements Serializable {
    @Id
    private String submissionId;

    private String taskType;
    private int exerciseId;


    @ElementCollection
    @CollectionTable(name = "submission_attribute_mapping",
            joinColumns = {@JoinColumn(name = "submission", referencedColumnName = "submissionId")})
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "attribute_value", length = 80000)
    private Map<String, String> passedAttributes;


    @ElementCollection
    @CollectionTable(name = "submission_parameter_mapping",
            joinColumns = {@JoinColumn(name = "submission", referencedColumnName = "submissionId")})
    @MapKeyColumn(name = "parameter_key")
    @Column(name = "parameter_value")
    private Map<String, String> passedParameters;
    private int maxPoints;

    public Submission() {
        passedParameters = new HashMap<>();
        passedAttributes = new HashMap<>();
    }

    public Submission(SubmissionDTO submissionDTO) {
        this.maxPoints = submissionDTO.getMaxPoints();
        this.submissionId = submissionDTO.getSubmissionId();
        this.passedAttributes = submissionDTO.getPassedAttributes();
        this.passedParameters = submissionDTO.getPassedParameters();
        this.taskType = submissionDTO.getTaskType();
        this.exerciseId = submissionDTO.getExerciseId();
    }


    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Map<String, String> getPassedAttributes() {
        return passedAttributes;
    }

    public void setPassedAttributes(Map<String, String> passedAttributes) {
        this.passedAttributes = passedAttributes;
    }

    public Map<String, String> getPassedParameters() {
        return passedParameters;
    }

    public void setPassedParameters(Map<String, String> passedParameters) {
        this.passedParameters = passedParameters;
    }
}
