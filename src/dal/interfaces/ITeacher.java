package dal.interfaces;

import be.School;
import be.Teacher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ITeacher {
    public List<Teacher> getTeachers() throws SQLException, IOException;
    public Teacher createTeacher(Teacher teacher) throws SQLException, IOException;
    public void updateTeacher(Teacher teacher) throws SQLException, IOException;
    public boolean removeTeacher(Teacher teacher) throws SQLException, IOException;
}
