package dal.db;

import be.Student;
import be.Teacher;
import dal.interfaces.IStudent;
import dal.interfaces.ITeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class StudentDAO implements IStudent {


    private Connection con;
    public StudentDAO(Connection con){
        this.con = con;
    }


    /**
     * gets all students
     * @return list of students
     */
    @Override
    public ObservableList<Student> getStudents() {
        ObservableList<Student> allStudents =  FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM Student";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                String fName = rs.getString("fName");
                String lName = rs.getString("Lname");
                String username = rs.getString("Username");
                String password = rs.getString("Password");

                int studentId = rs.getInt("StudentId");
                int schoolId = rs.getInt("StudentSchoolId");
                allStudents.add(new Student(studentId,schoolId, fName, lName,username,password));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allStudents;
    }

    /**
     * creates a student in database
     * @param student object that should be created for database
     * @return student object that was created
     */
    @Override
    public Student createStudent(Student student) {
        int insertedId = -1;
        try {
            String sqlStatement = "INSERT INTO Student(StudentSchoolId, Fname, Lname, Username, Password ) VALUES (?,?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, student.getSchoolId());
            statement.setString(2, student.getFName() );
            statement.setString(3, student.getLName());
            statement.setString(4, student.getUsername());
            statement.setString(5, student.getPassword());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Student(insertedId,student.getSchoolId(), student.getFName(),student.getLName(),student.getUsername(),student.getPassword());
    }

    /**
     * updates a student in database
     * @param student object holding updated values
     * @throws SQLException
     */
    @Override
    public void updateStudent(Student student) throws SQLException {

        String sql = "UPDATE Student SET StudentSchoolId = ?, Fname = ?, Lname = ?, Username = ?, Password = ? WHERE StudentId=?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, student.getSchoolId());
        preparedStatement.setString(2, student.getFName());
        preparedStatement.setString(3, student.getLName());
        preparedStatement.setString(4, student.getUsername());
        preparedStatement.setString(5, student.getPassword());
        preparedStatement.setInt(6, student.getId());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows != 1) {
        }
    }

    /**
     * removes a student from database
     * @param student object than should be deleted on database
     * @return true for success deleted student, false for failed.
     */
    @Override
    public boolean removeStudent(Student student) {
        try {
            String sqlStatement = "DELETE FROM Student WHERE StudentId=?";
            PreparedStatement statement = con.prepareStatement(sqlStatement);
            statement.setInt(1, student.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
