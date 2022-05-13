package Gui.model;

import be.Student;
import bll.StudentManager;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class StudentModel {

    StudentManager studentManager = new StudentManager();


    public ObservableList<Student> getStudents() throws SQLException, IOException {
        return studentManager.getStudents();
    }
    public ObservableList<Student> getStudentsFromSchool(int schoolId) throws SQLException, IOException {
        return studentManager.getStudentsFromSchool(schoolId);
    }
    public ObservableList<Student> getStudentsFromCitizen(int citizenId) throws SQLException, IOException {
        return studentManager.getStudentsFromCitizen(citizenId);
    }

    public Student createStudent(Student student) throws SQLException, IOException {
        return studentManager.createStudent(student);
    }

    public boolean removeStudent(Student student) throws SQLException, IOException {
        return studentManager.removeStudent(student);
    }
    public void updateStudent(Student student) throws SQLException, IOException {
        studentManager.updateStudent(student);
    }

}
