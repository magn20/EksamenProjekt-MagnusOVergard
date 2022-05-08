package dal.db;

import be.Citizen;
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
    public ObservableList<Student> getStudents() throws SQLException {
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
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return allStudents;
    }


    @Override
    public ObservableList<Student> getStudentForCitizen(int citizenId) throws SQLException {
        ObservableList<Student> allStudentForCitizen = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM Student INNER JOIN Works_on ON Works_on.FKStudentId = Student.StudentId WHERE FKCitizenId =(?); ";

            PreparedStatement preparedStatement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, citizenId);

            //Extract data from DB
            if(preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                while(resultSet.next()){
                    int id = resultSet.getInt("StudentId");
                    int schoolId = resultSet.getInt("StudentSchoolId");
                    String fName = resultSet.getString("fName");
                    String lName = resultSet.getString("Lname");
                    String username = resultSet.getString("Username");
                    String password = resultSet.getString("Password");

                    allStudentForCitizen.add(new Student(id,schoolId ,fName, lName, username,password));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        }
        return allStudentForCitizen;
    }


    @Override
    public ObservableList<Student> getStudentsFromSchool(int schoolID) throws SQLException {
        ObservableList<Student> allStudents =  FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM Student WHERE StudentSchoolId = ?;";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, schoolID);

            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                String fName = rs.getString("fName");
                String lName = rs.getString("Lname");
                String username = rs.getString("Username");
                String password = rs.getString("Password");

                int studentId = rs.getInt("StudentId");
                int schoolId = rs.getInt("StudentSchoolId");
                allStudents.add(new Student(studentId,schoolId, fName, lName,username,password));
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return allStudents;
    }

    /**
     * creates a student in database
     * @param student object that should be created for database
     * @return student object that was created
     */
    @Override
    public Student createStudent(Student student) throws SQLException {
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
            throw e;
        }
        return new Student(insertedId,student.getSchoolId(), student.getFName(),
                student.getLName(),student.getUsername(),student.getPassword());
    }

    /**
     * updates a student in database
     * @param student object holding updated values
     * @throws SQLException
     */
    @Override
    public void updateStudent(Student student) throws SQLException {

        try {

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
        }catch (SQLException sqlException){
            throw sqlException;
        }
    }

    /**
     * removes a student from database
     * @param student object than should be deleted on database
     * @return true for success deleted student, false for failed.
     */
    @Override
    public boolean removeStudent(Student student) throws SQLException {
        try {
            String sqlStatement = "DELETE FROM Student WHERE StudentId=?";
            PreparedStatement statement = con.prepareStatement(sqlStatement);
            statement.setInt(1, student.getId());
            statement.execute();
            return true;
        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }
}
