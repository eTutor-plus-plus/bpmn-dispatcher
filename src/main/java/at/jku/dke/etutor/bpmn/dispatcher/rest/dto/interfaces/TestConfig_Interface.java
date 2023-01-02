package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.interfaces;

import java.util.List;

public interface TestConfig_Interface {
    List<String> getTasksInCorrectOrder();

    List<String> getLabels();
}
