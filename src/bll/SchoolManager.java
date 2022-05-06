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

    private SchoolDAO schoolDao;
    private DatabaseConnector connector;
    {
        try {
            connector = new DatabaseConnector();
            schoolDao = new SchoolDAO(connector.getConnection());
        } catch (SQLServerException | IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<School> getSchools() throws SQLException {
        return schoolDao.getSchool();
    }
    public School createSchool(String name) throws SQLException {
        return schoolDao.createSchool(name);
    }
    public boolean removeSchool(School school) throws SQLException {
        return schoolDao.removeSchool(school);
    }
    public void updateSchool(School school) throws SQLException {
        schoolDao.updateSchool(school);
    }





}
