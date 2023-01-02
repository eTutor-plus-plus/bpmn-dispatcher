package at.jku.dke.etutor.bpmn.dispatcher.service;

import at.jku.dke.etutor.bpmn.dispatcher.evaluation.Analysis;
import at.jku.dke.etutor.bpmn.dispatcher.evaluation.BpmnAnalysis;
import at.jku.dke.etutor.bpmn.dispatcher.evaluation.DefaultReport;
import at.jku.dke.etutor.bpmn.dispatcher.evaluation.Grading;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    public DefaultReport report(Analysis analysis, Grading grading) throws ExerciseNotValidException {
        DefaultReport report = new DefaultReport();
        if (!(analysis instanceof BpmnAnalysis)) throw new ExerciseNotValidException("wrong Analysis");

        if (((BpmnAnalysis) analysis).getError() != null) {
            report.setError("<label>" + ((BpmnAnalysis) analysis).getError() + "</label>");
            report.setDescription("<p>" + ((BpmnAnalysis) analysis).getErrorDescription() + "</p>");
        }

        return report;
    }
}
