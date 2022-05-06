package dal.interfaces;

import be.Student;
import be.Teacher;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IStudent {
    public ObservableList<Student> getStudentsFromSchool(int schoolID) throws SQLException;
    public List<Student> getStudents() throws SQLException;
    public Student createStudent(Student student) throws SQLException;
    public void updateStudent(Student student) throws SQLException;
    public boolean removeStudent(Student student) throws SQLException;
}
