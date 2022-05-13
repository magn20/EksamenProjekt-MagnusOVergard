package dal.interfaces;

import be.Citizen;
import be.Student;
import be.Teacher;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IStudent {
    public ObservableList<Student> getStudentsFromSchool(int schoolID) throws SQLException, IOException;
    public List<Student> getStudents() throws SQLException, IOException;
    public Student createStudent(Student student) throws SQLException, IOException;
    public void updateStudent(Student student) throws SQLException, IOException;
    public boolean removeStudent(Student student) throws SQLException, IOException;
    public ObservableList<Student> getStudentForCitizen(int citizenId) throws SQLException, IOException;
}
