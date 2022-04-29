package Gui.model;

import be.Student;
import bll.StudentManager;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class StudentModel {

    StudentManager studentManager = new StudentManager();


    public ObservableList<Student> getStudents(){
        return studentManager.getStudents();
    }
    public Student createStudent(Student student){
        return studentManager.createStudent(student);
    }

    public boolean removeStudent(Student student) throws SQLException {
        return studentManager.removeStudent(student);
    }
    public void updateStudent(Student student) throws SQLException {
        studentManager.updateStudent(student);
    }

}
