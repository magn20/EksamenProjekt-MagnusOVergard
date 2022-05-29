package bll;

import be.Student;
import dal.db.StudentDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class StudentManager {

    //Creates instance of class
    private StudentDAO studentDAO;
    {
        studentDAO = new StudentDAO();
    }

    public ObservableList<Student> getStudents() throws SQLException, IOException {
        return studentDAO.getStudents();
    }

    public ObservableList<Student> getStudentsFromSchool(int schoolId) throws SQLException, IOException {
        return studentDAO.getStudentsFromSchool(schoolId);
    }

    public ObservableList<Student> getStudentsFromCitizen(int citizenId) throws SQLException, IOException {
        return studentDAO.getStudentForCitizen(citizenId);
    }

    public Student createStudent(Student student) throws SQLException, IOException {
        return studentDAO.createStudent(student);
    }

    public boolean removeStudent(Student student) throws SQLException, IOException {
        return studentDAO.removeStudent(student);
    }

    public void updateStudent(Student student) throws SQLException, IOException {
        studentDAO.updateStudent(student);
    }


}
