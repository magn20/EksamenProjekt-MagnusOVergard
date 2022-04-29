package dal.interfaces;

import be.Student;
import be.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface IStudent {
    public List<Student> getStudents();
    public Student createStudent(Student student);
    public void updateStudent(Student student) throws SQLException;
    public boolean removeStudent(Student student);
}
