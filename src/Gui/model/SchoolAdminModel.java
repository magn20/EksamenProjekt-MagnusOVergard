package Gui.model;

import be.SchoolAdmin;
import be.Teacher;
import bll.SchoolAdminManager;
import bll.TeacherManager;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class SchoolAdminModel {

    SchoolAdminManager schoolAdminManager = new SchoolAdminManager();

    public ObservableList<SchoolAdmin> getSchoolAdmins() throws SQLException, IOException {
        return schoolAdminManager.getSchoolAdmins();
    }
    public SchoolAdmin createSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException {
        return schoolAdminManager.createSchoolAdmin(schoolAdmin);
    }
    public boolean removeSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException {
        return schoolAdminManager.removeSchoolAdmin(schoolAdmin);
    }
    public void updateSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException {
        schoolAdminManager.updateSchoolAdmin(schoolAdmin);
    }

}
