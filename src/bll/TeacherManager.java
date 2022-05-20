package bll;

import be.School;
import be.Teacher;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.DatabaseConnector;
import dal.db.TeacherDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class TeacherManager {

    private TeacherDAO teacherDAO;

    {
        teacherDAO = new TeacherDAO();
    }

    public ObservableList<Teacher> getTeachers() throws SQLException, IOException {
        return teacherDAO.getTeachers();
    }
    public Teacher createTeacher(Teacher teacher) throws SQLException, IOException {
        return teacherDAO.createTeacher(teacher);
    }
    public boolean removeTeacher(Teacher teacher) throws SQLException, IOException {
        return teacherDAO.removeTeacher(teacher);
    }
    public void updateTeacher(Teacher teacher) throws SQLException, IOException {
        teacherDAO.updateTeacher(teacher);
    }



}
