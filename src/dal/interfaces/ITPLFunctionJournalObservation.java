package dal.interfaces;

import be.FunctionJournalObservation;
import be.TPLFunctionJournalObservation;
import be.TPLHealthJournalObservation;
import dal.db.TPLFunctionJournalObservationDao;

import java.io.IOException;
import java.sql.SQLException;

public interface ITPLFunctionJournalObservation {
    public TPLFunctionJournalObservation getTPLFunctionJournalObservation(int templateId) throws SQLException, IOException;
    public TPLFunctionJournalObservation createTPLFunctionJournalObservation(TPLFunctionJournalObservation tplFunctionJournalObservation) throws SQLException, IOException;
    public void updateTplFunctionJournalObservation(TPLFunctionJournalObservation tplFunctionJournalObservation) throws SQLException, IOException;
}
