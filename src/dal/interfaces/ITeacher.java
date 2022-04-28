package dal.interfaces;

import be.School;
import be.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface ITeacher {
    public List<Teacher> getTeachers();
    public Teacher createTeacher(Teacher teacher);
    public void updateTeacher(Teacher teacher) throws SQLException;
    public boolean removeTeacher(Teacher teacher);
}
