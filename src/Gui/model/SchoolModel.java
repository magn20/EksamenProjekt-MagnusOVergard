package Gui.model;

import be.School;
import bll.SchoolManager;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SchoolModel {

    SchoolManager schoolManager = new SchoolManager();

    public ObservableList<School> getSchools() throws SQLException, IOException {
        return schoolManager.getSchools();
    }
    public School createSchool(String name) throws SQLException, IOException {
        return schoolManager.createSchool(name);
    }
    public boolean removeSchool(School school) throws SQLException, IOException {
        return schoolManager.removeSchool(school);
    }
    public void updateSchool(School school) throws SQLException, IOException {
        schoolManager.updateSchool(school);
    }
}
