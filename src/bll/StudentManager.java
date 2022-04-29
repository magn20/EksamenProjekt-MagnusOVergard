package bll;

import be.Student;
import be.Teacher;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.DatabaseConnector;
import dal.db.StudentDAO;
import dal.db.TeacherDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class StudentManager {

    private StudentDAO studentDAO;
    private DatabaseConnector connector;
    {
        try {
            connector = new DatabaseConnector();
            studentDAO = new StudentDAO(connector.getConnection());
        } catch (SQLServerException | IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Student> getStudents(){
        return studentDAO.getStudents();
    }
    public Student createStudent(Student student){
        return studentDAO.createStudent(student);
    }

    public boolean removeStudent(Student student) throws SQLException {
        return studentDAO.removeStudent(student);
    }
    public void updateStudent(Student student) throws SQLException {
        studentDAO.updateStudent(student);
    }



}
