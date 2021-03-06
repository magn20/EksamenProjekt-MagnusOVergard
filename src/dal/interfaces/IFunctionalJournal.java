package dal.interfaces;

import be.FunctionalJournal;
import be.TPLFunctionalJournal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IFunctionalJournal {
    public List<FunctionalJournal> getFunctionalJournal(int citizenId) throws SQLException, IOException;
    public FunctionalJournal createFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException, IOException;
    public void updateFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException, IOException;
}
