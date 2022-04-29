package dal.db;

import be.School;
import be.Teacher;
import dal.interfaces.ISchool;
import dal.interfaces.ITeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TeacherDAO implements ITeacher {


    private Connection con;
    public TeacherDAO(Connection con){
        this.con = con;
    }

    /**
     * gets all teachers from database
     * @return Observablelist of all Teachers
     */
    @Override
    public ObservableList<Teacher> getTeachers() {
        ObservableList<Teacher> allTeachers =  FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM Teacher";
            Statement statement = con.createStatement();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allTeachers;
    }

    /**
     * cretes a Teacher in database
     * @param teacher object holding values of teacher that should be created
     * @return the teacher object that were created
     */
    @Override
    public Teacher createTeacher(Teacher teacher) {
        int insertedId = -1;
        try {
            String sqlStatement = "INSERT INTO Teacher(TeacherSchoolId, Fname, Lname, Username, Password ) VALUES (?,?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, teacher.getSchoolId());
            statement.setString(2, teacher.getFName() );
            statement.setString(3, teacher.getLName());
            statement.setString(4, teacher.getUsername());
            statement.setString(5, teacher.getPassword());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Teacher(insertedId,teacher.getSchoolId(), teacher.getFName(),teacher.getLName(),teacher.getUsername(),teacher.getPassword());
    }

    /**
     * updates a teacher in database
     * @param teacher object holding values for updating in database
     * @throws SQLException
     */
    @Override
    public void updateTeacher(Teacher teacher) throws SQLException {

        String sql = "UPDATE Teacher SET TeacherSchoolId = ?, Fname = ?, Lname = ?, Username = ?, Password = ? WHERE TeacherId=?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, teacher.getSchoolId());
        preparedStatement.setString(2, teacher.getFName());
        preparedStatement.setString(3, teacher.getLName());
        preparedStatement.setString(4, teacher.getUsername());
        preparedStatement.setString(5, teacher.getPassword());
        preparedStatement.setInt(6, teacher.getId());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows != 1) {
        }
    }

    /**
     * removes a teacher from database
     * @param teacher object holding rightful id for deletion.
     * @return true for success, false for failed deletion.
     */
    @Override
    public boolean removeTeacher(Teacher teacher) {
        try {
            String sqlStatement = "DELETE FROM Teacher WHERE TeacherId=?";
            PreparedStatement statement = con.prepareStatement(sqlStatement);
            statement.setInt(1, teacher.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
