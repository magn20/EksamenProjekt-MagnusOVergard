package dal.db;

import be.School;
import dal.interfaces.ISchool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolDAO implements ISchool {


    private final BasicConnectionPool basicConnectionPool = new BasicConnectionPool();
    /**
     * gets a list of all schools in Database
     * @return list of school
     */
    @Override
    public ObservableList<School> getSchool() throws SQLException, IOException {
        ObservableList<School> allSchools =  FXCollections.observableArrayList();
        try (Connection connection = basicConnectionPool.getConnection()){
            String sqlStatement = "SELECT * FROM School";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                String name = rs.getString("SchoolName");

                int id = rs.getInt("SchoolId");
                allSchools.add(new School(id, name));
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }

        return allSchools;
    }


    /**
     * create school for database
     * @param name of the school
     * @return the school object created
     */
    @Override
    public School createSchool(String name) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO School(SchoolName) VALUES (?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
            throw e;
        }
        return new School(insertedId, name);

    }

    /**
     * updates a school Name
     * @param school object holding changed name.
     * @throws SQLException
     */
    @Override
    public void updateSchool(School school) throws SQLException, IOException {

        try(Connection connection = basicConnectionPool.getConnection()) {

            String sql = "UPDATE School SET SchoolName = ? WHERE SchoolId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, school.getName());
            preparedStatement.setInt(2, school.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows != 1) {

            }
            basicConnectionPool.releaseConnection(connection);
        }catch (SQLException | IOException sqlException){
            throw sqlException;
        }



    }

    /**
     * removes a school from Database
     * @param school object that will be deleted
     * @return true if success, false if failed.
     */
    @Override
    public boolean removeSchool(School school) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "DELETE FROM School WHERE SchoolId=?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, school.getId());
            statement.execute();
            basicConnectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException | IOException e) {
            throw e;
        }
    }
}
