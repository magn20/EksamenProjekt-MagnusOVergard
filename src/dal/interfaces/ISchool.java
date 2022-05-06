package dal.interfaces;

import be.School;

import java.sql.SQLException;
import java.util.List;

public interface ISchool {
    public List<School> getSchool() throws SQLException;
    public School createSchool(String name) throws SQLException;
    public void updateSchool(School school) throws SQLException;
    public boolean removeSchool(School school) throws SQLException;
}
