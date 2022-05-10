package etutor.bpmndispatcher.rest.dto.entities;

import java.util.List;

public class TestConfig {
    private List<String> tasksInCorrectOrder;
    private List<String> labels;

    public TestConfig(TestConfigDTO testConfigDTO) {
        this.tasksInCorrectOrder = testConfigDTO.getTasksInCorrectOrder();
        this.labels = testConfigDTO.getLabels();
    }

    public List<String> getTasksInCorrectOrder() {
        return tasksInCorrectOrder;
    }

    public void setTasksInCorrectOrder(List<String> tasksInCorrectOrder) {
        this.tasksInCorrectOrder = tasksInCorrectOrder;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "TestConfig{" +
                "tasksInCorrectOrder=" + tasksInCorrectOrder +
                ", labels=" + labels +
                '}';
    }
}
