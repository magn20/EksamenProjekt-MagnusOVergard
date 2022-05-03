package dal.interfaces;

import be.TPLGeneralInfo;
import be.Template;
import dal.db.TPLGeneralInfoDAO;

import java.sql.SQLException;
import java.util.List;

public interface ITPLGeneralInfo {
    public List<TPLGeneralInfo> getTPLGeneralInfo(int templateId);
    public TPLGeneralInfo createTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo);
    public void updateTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) throws SQLException;
    public boolean removeTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo);
}
