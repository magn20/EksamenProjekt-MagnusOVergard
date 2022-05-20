package dal.interfaces;

import be.SchoolAdmin;
import be.Teacher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ISchoolAdmin {
    public List<SchoolAdmin> getSchoolAdmins() throws SQLException, IOException;
    public SchoolAdmin createSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException;
    public void updateSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException;
    public boolean removeSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException;
}
