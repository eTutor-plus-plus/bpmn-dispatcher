package etutor.bpmndispatcher.rest.dto.entities.testEngine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TestEngineRuntimeDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean processInOrder = false;
    private boolean containsAllLabels = false;
    private boolean processHaveParallelGateway = false;
    private boolean canReachLastTask = false;

    public TestEngineRuntimeDTO() {
    }

    public TestEngineRuntimeDTO(boolean processInOrder, boolean containsAllLabels, boolean processHaveParallelGateway, boolean canReachLastTask) {
        this.processInOrder = processInOrder;
        this.containsAllLabels = containsAllLabels;
        this.processHaveParallelGateway = processHaveParallelGateway;
        this.canReachLastTask = canReachLastTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isProcessInOrder() {
        return processInOrder;
    }

    public void setProcessInOrder(boolean processInOrder) {
        this.processInOrder = processInOrder;
    }

    public boolean isProcessHaveParallelGateway() {
        return processHaveParallelGateway;
    }

    public void setProcessHaveParallelGateway(boolean processHaveParallelGateway) {
        this.processHaveParallelGateway = processHaveParallelGateway;
    }

    public boolean isCanReachLastTask() {
        return canReachLastTask;
    }

    public void setCanReachLastTask(boolean canReachLastTask) {
        this.canReachLastTask = canReachLastTask;
    }

    public boolean isContainsAllLabels() {
        return containsAllLabels;
    }

    public void setContainsAllLabels(boolean containsAllLabels) {
        this.containsAllLabels = containsAllLabels;
    }

    @Override
    public String toString() {
        return "TestEngineRuntimeDTO{" +
                "processInOrder=" + processInOrder +
                ", containsAllLabels=" + containsAllLabels +
                ", processHaveParallelGateway=" + processHaveParallelGateway +
                ", canReachLastTask=" + canReachLastTask +
                '}';
    }
}
