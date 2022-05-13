package dal.interfaces;

import be.School;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ISchool {
    public List<School> getSchool() throws SQLException, IOException;
    public School createSchool(String name) throws SQLException, IOException;
    public void updateSchool(School school) throws SQLException, IOException;
    public boolean removeSchool(School school) throws SQLException, IOException;
}
