package dal.interfaces;

import be.TPLGeneralInfo;
import be.TPLHealthJournal;

import java.sql.SQLException;
import java.util.List;

public interface ITPLHealthJournal {
    public List<TPLHealthJournal> getTPLHealthJournal(int templateId);
    public TPLHealthJournal createTPLHealthJournal(TPLHealthJournal tplHealthJournal);
    public void updateTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException;
}
