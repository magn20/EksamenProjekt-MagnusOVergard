package dal.interfaces;

import be.FunctionJournalObservation;
import be.HealthJournalObservation;

import java.sql.SQLException;

public interface IFunctionJournalObservation {
    public FunctionJournalObservation getFunctionJournalObservation(int citizenId) throws SQLException;
    public FunctionJournalObservation createFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException;
    public void updateFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException;
}
