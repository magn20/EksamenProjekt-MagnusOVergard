package dal.interfaces;

import be.TPLGeneralInfo;
import be.TPLHealthJournal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ITPLHealthJournal {
    public List<TPLHealthJournal> getTPLHealthJournal(int templateId) throws SQLException, IOException;
    public TPLHealthJournal createTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException, IOException;
    public void updateTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException, IOException;
}
