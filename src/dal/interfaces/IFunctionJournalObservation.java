package dal.interfaces;

import be.FunctionJournalObservation;
import be.HealthJournalObservation;

import java.io.IOException;
import java.sql.SQLException;

public interface IFunctionJournalObservation {
    public FunctionJournalObservation getFunctionJournalObservation(int citizenId) throws SQLException, IOException;
    public FunctionJournalObservation createFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException, IOException;
    public void updateFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException, IOException;
}
