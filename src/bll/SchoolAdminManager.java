package bll;

import be.SchoolAdmin;
import dal.db.SchoolAdminDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class SchoolAdminManager {

    //create instance of SchoolAdminDAO
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
