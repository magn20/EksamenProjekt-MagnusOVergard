package dal.interfaces;

import be.HealthJournal;
import be.HealthJournalObservation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IHealthJournalObservation {
    public HealthJournalObservation getHealthJournalObservation(int citizenId) throws SQLException, IOException;
    public HealthJournalObservation createHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException, IOException;
    public void updateHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException, IOException;
}
