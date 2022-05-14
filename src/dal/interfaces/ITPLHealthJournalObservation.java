package dal.interfaces;

import be.HealthJournalObservation;
import be.TPLHealthJournalObservation;

import java.io.IOException;
import java.sql.SQLException;

public interface ITPLHealthJournalObservation {
    public TPLHealthJournalObservation getTPLHealthJournalObservation(int templateId) throws SQLException, IOException;
    public TPLHealthJournalObservation createTPLHealthJournalObservation(TPLHealthJournalObservation tplHealthJournalObservation) throws SQLException, IOException;
    public void updateTPLHealthJournalObservation(TPLHealthJournalObservation tplHealthJournalObservation) throws SQLException, IOException;
}
