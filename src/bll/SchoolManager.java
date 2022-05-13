package bll;

import be.School;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.DatabaseConnector;
import dal.db.SchoolDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SchoolManager {

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
