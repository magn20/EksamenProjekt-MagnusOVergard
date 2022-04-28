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
    private DatabaseConnector connector;
    {
        try {
            connector = new DatabaseConnector();
            teacherDAO = new TeacherDAO(connector.getConnection());
        } catch (SQLServerException | IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Teacher> getTeachers(){
        return teacherDAO.getTeachers();
    }
    public Teacher createTeacher(Teacher teacher){
        return teacherDAO.createTeacher(teacher);
    }
    public boolean removeTeacher(Teacher teacher) throws SQLException {
        return teacherDAO.removeTeacher(teacher);
    }
    public void updateTeacher(Teacher teacher) throws SQLException {
        teacherDAO.updateTeacher(teacher);
    }



}
