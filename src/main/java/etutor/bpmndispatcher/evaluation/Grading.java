package etutor.bpmndispatcher.evaluation;

import java.io.Serializable;

/**
 * Interface defining methods for a grading of a submission
 */
public interface Grading extends Serializable {

    /**
     * Returns the achieved points
     *
     * @return the points
     */
    double getPoints();

    /**
     * Sets the points
     *
     * @param points the points
     */
    void setPoints(double points);

    /**
     * Returns the max points
     *
     * @return the max points
     */
    double getMaxPoints();

    /**
     * Sets the max points
     *
     * @param maxPoints the max points
     */
    void setMaxPoints(double maxPoints);
}
