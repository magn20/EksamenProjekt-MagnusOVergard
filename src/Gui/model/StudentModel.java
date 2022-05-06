package Gui.model;

import be.Student;
import bll.StudentManager;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class StudentModel {

    StudentManager studentManager = new StudentManager();


    public ObservableList<Student> getStudents() throws SQLException {
        return studentManager.getStudents();
    }
    public ObservableList<Student> getStudentsFromSchool(int schoolId) throws SQLException {
        return studentManager.getStudentsFromSchool(schoolId);
    }

    public Student createStudent(Student student) throws SQLException {
        return studentManager.createStudent(student);
    }

    public boolean removeStudent(Student student) throws SQLException {
        return studentManager.removeStudent(student);
    }
    public void updateStudent(Student student) throws SQLException {
        studentManager.updateStudent(student);
    }

}
