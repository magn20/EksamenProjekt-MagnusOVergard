package dal.db;

import be.School;
import dal.interfaces.ISchool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolDAO implements ISchool {


    private Connection con;
    public SchoolDAO(Connection con){
        this.con = con;
    }

    /**
     * gets a list of all schools in Database
     * @return list of school
     */
    @Override
    public ObservableList<School> getSchool() throws SQLException {
        ObservableList<School> allSchools =  FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM School";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                String name = rs.getString("SchoolName");

                int id = rs.getInt("SchoolId");
                allSchools.add(new School(id, name));
            }
        } catch (SQLException exception) {
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
    public School createSchool(String name) throws SQLException {
        int insertedId = -1;
        try {
            String sqlStatement = "INSERT INTO School(SchoolName) VALUES (?);";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
        } catch (SQLException e) {
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
    public void updateSchool(School school) throws SQLException {

        try {

            String sql = "UPDATE School SET SchoolName = ? WHERE SchoolId=?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, school.getName());
            preparedStatement.setInt(2, school.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows != 1) {

            }
        }catch (SQLException sqlException){
            throw sqlException;
        }



    }

    /**
     * removes a school from Database
     * @param school object that will be deleted
     * @return true if success, false if failed.
     */
    @Override
    public boolean removeSchool(School school) throws SQLException {
        try {
            String sqlStatement = "DELETE FROM School WHERE SchoolId=?";
            PreparedStatement statement = con.prepareStatement(sqlStatement);
            statement.setInt(1, school.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }
}
