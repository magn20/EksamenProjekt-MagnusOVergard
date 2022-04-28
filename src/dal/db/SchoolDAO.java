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

    @Override
    public ObservableList<School> getSchool() {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allSchools;
    }

    @Override
    public School createSchool(String name) {
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
            e.printStackTrace();
        }
        return new School(insertedId, name);

    }

    @Override
    public void updateSchool(School school) throws SQLException {

        String sql = "UPDATE School SET SchoolName = ? WHERE SchoolId=?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, school.getName());
        preparedStatement.setInt(2, school.getId());
        int affectedRows = preparedStatement.executeUpdate();
        if(affectedRows != 1) {

        }

    }

    @Override
    public boolean removeSchool(School school) {
        try {
            String sqlStatement = "DELETE FROM School WHERE SchoolId=?";
            PreparedStatement statement = con.prepareStatement(sqlStatement);
            statement.setInt(1, school.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
