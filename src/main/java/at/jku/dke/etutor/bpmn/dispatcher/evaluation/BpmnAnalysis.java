package at.jku.dke.etutor.bpmn.dispatcher.evaluation;

import java.util.ArrayList;
import java.util.List;

public class BpmnAnalysis extends DefaultAnalysis implements Analysis {
    private boolean deploymentSuccesful;
    private String error;

    private String errorDescription;
    private List<String> currentIds;
    private List<String> currentDefinitionId;

    public BpmnAnalysis() {
        this.deploymentSuccesful = true;
        this.error = null;
        this.errorDescription = "";
        this.currentIds = new ArrayList<>();
        this.currentDefinitionId = new ArrayList<>();
    }

    public boolean isDeploymentSuccesful() {
        return deploymentSuccesful;
    }

    public void setDeploymentSuccesful(boolean deploymentSuccesful) {
        this.deploymentSuccesful = deploymentSuccesful;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getCurrentIds() {
        return currentIds;
    }

    public void setCurrentIds(List<String> currentIds) {
        this.currentIds = currentIds;
    }

    public List<String> getCurrentDefinitionId() {
        return currentDefinitionId;
    }

    public void setCurrentDefinitionId(List<String> currentDefinitionId) {
        this.currentDefinitionId = currentDefinitionId;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
