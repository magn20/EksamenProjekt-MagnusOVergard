package Gui.model;

import be.School;
import bll.SchoolManager;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class SchoolModel {

    SchoolManager schoolManager = new SchoolManager();

    public ObservableList<School> getSchools() throws SQLException {
        return schoolManager.getSchools();
    }
    public School createSchool(String name) throws SQLException {
        return schoolManager.createSchool(name);
    }
    public boolean removeSchool(School school) throws SQLException {
        return schoolManager.removeSchool(school);
    }
    public void updateSchool(School school) throws SQLException {
        schoolManager.updateSchool(school);
    }
}
