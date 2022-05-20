package dal.db;

import be.SchoolAdmin;
import be.Teacher;
import dal.interfaces.ISchoolAdmin;
import dal.interfaces.ITeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class SchoolAdminDAO implements ISchoolAdmin {

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();

    /**
     * gets all SchoolAdmins from database
     * @return Observablelist of all SchoolAdmins
     */
    @Override
    public ObservableList<SchoolAdmin> getSchoolAdmins() throws SQLException, IOException {
        ObservableList<SchoolAdmin> schoolAdmins =  FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM SchoolAdmin";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                String fName = rs.getString("fName");
                String lName = rs.getString("Lname");
                String username = rs.getString("Username");
                String password = rs.getString("Password");

                int schoolAdminId = rs.getInt("SchoolAdminID");
                int schoolId = rs.getInt("AdminSchoolId");
                schoolAdmins.add(new SchoolAdmin(schoolAdminId,schoolId, fName, lName,username,password));
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException sqlException) {
            throw sqlException;
        }
        return schoolAdmins;
    }

    /**
     * creates a SchoolAdmin in database
     * @param schoolAdmin object holding values of SchoolAdmin that should be created
     * @return the SchoolAdmin object that were created
     */
    @Override
    public SchoolAdmin createSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO SchoolAdmin(AdminSchoolId, Fname, Lname, Username, Password ) VALUES (?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, schoolAdmin.getSchoolId());
            statement.setString(2, schoolAdmin.getFName() );
            statement.setString(3, schoolAdmin.getLName());
            statement.setString(4, schoolAdmin.getUsername());
            statement.setString(5, schoolAdmin.getPassword());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException exception) {
            throw exception;
        }
        return new SchoolAdmin(insertedId,schoolAdmin.getSchoolId(), schoolAdmin.getFName(),schoolAdmin.getLName(),schoolAdmin.getUsername(),schoolAdmin.getPassword());
    }

    /**
     * updates a SchoolAdmin in database
     * @param schoolAdmin object holding values for updating in database
     */
    @Override
    public void updateSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {

            String sql = "UPDATE SchoolAdmin SET AdminSchoolId = ?, Fname = ?, Lname = ?, Username = ?, Password = ? WHERE SchoolAdminId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, schoolAdmin.getSchoolId());
            preparedStatement.setString(2, schoolAdmin.getFName());
            preparedStatement.setString(3, schoolAdmin.getLName());
            preparedStatement.setString(4, schoolAdmin.getUsername());
            preparedStatement.setString(5, schoolAdmin.getPassword());
            preparedStatement.setInt(6, schoolAdmin.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
            basicConnectionPool.releaseConnection(connection);
        }catch (SQLException | IOException exception){
            throw exception;
        }
    }

    /**
     * removes a SchoolAdmin from database
     * @param schoolAdmin object holding rightful id for deletion.
     * @return true for success, false for failed deletion.
     */
    @Override
    public boolean removeSchoolAdmin(SchoolAdmin schoolAdmin) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "DELETE FROM SchoolAdmin WHERE SchoolAdminId=?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, schoolAdmin.getId());
            statement.execute();
            basicConnectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException | IOException exception) {
            throw exception;
        }
    }
}
