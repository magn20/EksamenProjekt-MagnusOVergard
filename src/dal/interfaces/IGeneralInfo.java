package dal.interfaces;

import be.GeneralInfo;
import be.TPLGeneralInfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IGeneralInfo {
    public List<GeneralInfo> getGeneralInfo(int templateId) throws SQLException, IOException;
    public GeneralInfo createGeneralInfo(GeneralInfo generalInfo) throws SQLException, IOException;
    public void updateGeneralInfo(GeneralInfo generalInfo) throws SQLException, IOException;
}
