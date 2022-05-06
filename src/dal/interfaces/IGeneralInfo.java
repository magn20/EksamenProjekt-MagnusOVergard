package dal.interfaces;

import be.GeneralInfo;
import be.TPLGeneralInfo;

import java.sql.SQLException;
import java.util.List;

public interface IGeneralInfo {
    public List<GeneralInfo> getGeneralInfo(int templateId) throws SQLException;
    public GeneralInfo createGeneralInfo(GeneralInfo generalInfo) throws SQLException;
    public void updateGeneralInfo(GeneralInfo generalInfo) throws SQLException;
    public boolean removeGeneralInfo(GeneralInfo generalInfo) throws SQLException;
}
