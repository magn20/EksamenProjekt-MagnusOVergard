package dal.interfaces;

import be.HealthJournal;
import be.HealthJournalObservation;

import java.sql.SQLException;
import java.util.List;

public interface IHealthJournalObservation {
    public HealthJournalObservation getHealthJournalObservation(int citizenId) throws SQLException;
    public HealthJournalObservation createHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException;
    public void updateHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException;
}
