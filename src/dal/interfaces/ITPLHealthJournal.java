package dal.interfaces;

import be.TPLGeneralInfo;
import be.TPLHealthJournal;

import java.sql.SQLException;
import java.util.List;

public interface ITPLHealthJournal {
    public List<TPLHealthJournal> getTPLHealthJournal(int templateId) throws SQLException;
    public TPLHealthJournal createTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException;
    public void updateTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException;
}
