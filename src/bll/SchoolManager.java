package bll;

import be.School;
import dal.db.SchoolDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class SchoolManager {

    //Create instances of SchoolDao
    private SchoolDAO schoolDao = new SchoolDAO();


    public ObservableList<School> getSchools() throws SQLException, IOException {
        return schoolDao.getSchool();
    }
    public School createSchool(String name) throws SQLException, IOException {
        return schoolDao.createSchool(name);
    }
    public boolean removeSchool(School school) throws SQLException, IOException {
        return schoolDao.removeSchool(school);
    }
    public void updateSchool(School school) throws SQLException, IOException {
        schoolDao.updateSchool(school);
    }





}
