package etutor.bpmndispatcher.service;

import etutor.bpmndispatcher.evaluation.Analysis;
import etutor.bpmndispatcher.evaluation.BpmnAnalysis;
import etutor.bpmndispatcher.evaluation.DefaultReport;
import etutor.bpmndispatcher.evaluation.Grading;
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
