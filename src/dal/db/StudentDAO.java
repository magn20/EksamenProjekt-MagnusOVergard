package dal.db;

import be.Citizen;
import be.Student;
import be.Teacher;
import dal.interfaces.IStudent;
import dal.interfaces.ITeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class StudentDAO implements IStudent {

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();

    /**
     * gets all students
     * @return list of students
     */
    @Override
    public ObservableList<Student> getStudents() throws SQLException, IOException {
        ObservableList<Student> allStudents =  FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM Student";
            Statement statement = connection.createStatement();
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
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException exception) {
            throw exception;
        }
        return allStudents;
    }


    /**
     * get all students that work on a specific Citizen
     * @param citizenId the id of the citizen.
     * @return list of students
     */
    @Override
    public ObservableList<Student> getStudentForCitizen(int citizenId) throws SQLException, IOException {
        ObservableList<Student> allStudentForCitizen = FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM Student INNER JOIN Works_on ON Works_on.FKStudentId = Student.StudentId WHERE FKCitizenId =(?); ";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
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
            basicConnectionPool.getConnectionsInUse();
        } catch (SQLException | IOException exception) {
            exception.printStackTrace();
            throw exception;
        }
        return allStudentForCitizen;
    }


    /**
     * gets students for a school.
     * @param schoolID the school id
     * @return list of students from school
     * @throws SQLException
     */
    @Override
    public ObservableList<Student> getStudentsFromSchool(int schoolID) throws SQLException, IOException {
        ObservableList<Student> allStudents =  FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM Student WHERE StudentSchoolId = ?;";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
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
            basicConnectionPool.releaseConnection(connection);
        } catch (Exception exception) {
            throw exception;
        }
        return allStudents;
    }

    /**
     * creates a student in database
     * @param student object that should be created for database
     * @return student object that was created
     */
    @Override
    public Student createStudent(Student student) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO Student(StudentSchoolId, Fname, Lname, Username, Password ) VALUES (?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, student.getSchoolId());
            statement.setString(2, student.getFName() );
            statement.setString(3, student.getLName());
            statement.setString(4, student.getUsername());
            statement.setString(5, student.getPassword());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
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
    public void updateStudent(Student student) throws SQLException, IOException {

        try(Connection connection = basicConnectionPool.getConnection()) {

            String sql = "UPDATE Student SET StudentSchoolId = ?, Fname = ?, Lname = ?, Username = ?, Password = ? WHERE StudentId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getSchoolId());
            preparedStatement.setString(2, student.getFName());
            preparedStatement.setString(3, student.getLName());
            preparedStatement.setString(4, student.getUsername());
            preparedStatement.setString(5, student.getPassword());
            preparedStatement.setInt(6, student.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
            basicConnectionPool.releaseConnection(connection);
        }catch (SQLException | IOException exception){
            throw exception;
        }
    }

    /**
     * removes a student from database
     * @param student object than should be deleted on database
     * @return true for success deleted student, false for failed.
     */
    @Override
    public boolean removeStudent(Student student) throws SQLException, IOException {
        try (Connection connection = basicConnectionPool.getConnection()){
            String sqlStatement = "DELETE FROM Student WHERE StudentId=?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, student.getId());
            statement.execute();
            basicConnectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException | IOException exception) {
            throw exception;
        }
    }
}
