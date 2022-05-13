package dal.db;

import be.School;
import be.Teacher;
import dal.interfaces.ISchool;
import dal.interfaces.ITeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class TeacherDAO implements ITeacher {

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();

    /**
     * gets all teachers from database
     * @return Observablelist of all Teachers
     */
    @Override
    public ObservableList<Teacher> getTeachers() throws SQLException, IOException {
        ObservableList<Teacher> allTeachers =  FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM Teacher";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                String fName = rs.getString("fName");
                String lName = rs.getString("Lname");
                String username = rs.getString("Username");
                String password = rs.getString("Password");

                int teacherId = rs.getInt("TeacherId");
                int schoolId = rs.getInt("TeacherSchoolId");
                allTeachers.add(new Teacher(teacherId,schoolId, fName, lName,username,password));
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException sqlException) {
            throw sqlException;
        }
        return allTeachers;
    }

    /**
     * cretes a Teacher in database
     * @param teacher object holding values of teacher that should be created
     * @return the teacher object that were created
     */
    @Override
    public Teacher createTeacher(Teacher teacher) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO Teacher(TeacherSchoolId, Fname, Lname, Username, Password ) VALUES (?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, teacher.getSchoolId());
            statement.setString(2, teacher.getFName() );
            statement.setString(3, teacher.getLName());
            statement.setString(4, teacher.getUsername());
            statement.setString(5, teacher.getPassword());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException exception) {
            throw exception;
        }
        return new Teacher(insertedId,teacher.getSchoolId(), teacher.getFName(),teacher.getLName(),teacher.getUsername(),teacher.getPassword());
    }

    /**
     * updates a teacher in database
     * @param teacher object holding values for updating in database
     * @throws SQLException
     */
    @Override
    public void updateTeacher(Teacher teacher) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {

            String sql = "UPDATE Teacher SET TeacherSchoolId = ?, Fname = ?, Lname = ?, Username = ?, Password = ? WHERE TeacherId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teacher.getSchoolId());
            preparedStatement.setString(2, teacher.getFName());
            preparedStatement.setString(3, teacher.getLName());
            preparedStatement.setString(4, teacher.getUsername());
            preparedStatement.setString(5, teacher.getPassword());
            preparedStatement.setInt(6, teacher.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
            basicConnectionPool.releaseConnection(connection);
        }catch (SQLException | IOException exception){
            throw exception;
        }
    }

    /**
     * removes a teacher from database
     * @param teacher object holding rightful id for deletion.
     * @return true for success, false for failed deletion.
     */
    @Override
    public boolean removeTeacher(Teacher teacher) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "DELETE FROM Teacher WHERE TeacherId=?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, teacher.getId());
            statement.execute();
            basicConnectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException | IOException exception) {
            throw exception;
        }
    }
}
