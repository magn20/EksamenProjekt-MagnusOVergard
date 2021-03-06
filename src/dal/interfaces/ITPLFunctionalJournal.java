package dal.interfaces;

import be.TPLFunctionalJournal;
import be.TPLHealthJournal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ITPLFunctionalJournal {
    public List<TPLFunctionalJournal> getTPLFunctionalJournal(int templateId);
    public TPLFunctionalJournal createTPLFunctionalJournal(TPLFunctionalJournal tplFunctionalJournal) throws SQLException, IOException;
    public void updateTPLFunctionalJournal(TPLFunctionalJournal tplHealthJournal) throws SQLException, IOException;
}
