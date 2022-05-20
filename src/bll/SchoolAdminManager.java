package bll;

import be.SchoolAdmin;
import be.Teacher;
import dal.db.DatabaseConnector;
import dal.db.SchoolAdminDAO;
import dal.db.TeacherDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class SchoolAdminManager {

    private SchoolAdminDAO schoolAdminDAO;
    {
        schoolAdminDAO = new SchoolAdminDAO();
    }

    public ObservableList<SchoolAdmin> getSchoolAdmins() throws SQLException, IOException {
        return schoolAdminDAO.getSchoolAdmins();
    }
    public SchoolAdmin createSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException {
        return schoolAdminDAO.createSchoolAdmin(schoolAdmin);
    }
    public boolean removeSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException {
        return schoolAdminDAO.removeSchoolAdmin(schoolAdmin);
    }
    public void updateSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException {
        schoolAdminDAO.updateSchoolAdmin(schoolAdmin);
    }



}
