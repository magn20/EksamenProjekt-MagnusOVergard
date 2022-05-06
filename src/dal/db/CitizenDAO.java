package dal.db;

import be.Citizen;
import be.Student;
import be.Template;
import dal.interfaces.ICitizen;
import dal.interfaces.ITemplate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CitizenDAO implements ICitizen {

    private Connection con;
    public CitizenDAO(Connection con){
        this.con = con;
    }


    @Override
    public ObservableList<Citizen> getCitizen(int schoolId) throws SQLException {
        ObservableList<Citizen> allCitizenFromSchool = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM Citizen Where CitizenSchoolId = ? ";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, schoolId);

            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {

                int id = rs.getInt(1);
                int SchoolId = rs.getInt(2);
                String fName = rs.getString(3);

                String lName = rs.getString(4);

                String age = rs.getString(5);


                allCitizenFromSchool.add(new Citizen(id, SchoolId, fName,lName, age));
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return allCitizenFromSchool;
    }


    /**
     * gets Citizen for a student from database
     * @return Observablelist of all Teachers
     */
    @Override
    public ObservableList<Citizen> getCitizenForStudent(int studentID) throws SQLException {
        ObservableList<Citizen> allCitizenFromSchool = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM Citizen INNER JOIN Works_on ON Works_on.FKCitizenId = Citizen.CitizenId WHERE FKStudentId =(?); ";

            PreparedStatement preparedStatement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, studentID);

            //Extract data from DB
            if(preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                while(resultSet.next()){
                    int id = resultSet.getInt("CitizenId");
                    int schoolId = resultSet.getInt("CitizenSchoolId");
                    String fName = resultSet.getString("fName");
                    String lName = resultSet.getString("Lname");
                    String age = resultSet.getString("age");

                    allCitizenFromSchool.add(new Citizen(id,schoolId ,fName, lName, age));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        }
        return allCitizenFromSchool;
    }

    @Override
    public boolean setStudentWorkOnCitizen(Citizen citizen, Student student) throws SQLException {
        try {
            String sql = "INSERT INTO Works_on VALUES (?,?);";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, citizen.getId());
            preparedStatement.setInt(2, student.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public Citizen createCitizen(Citizen citizen) throws SQLException {
        int insertedId = -1;
        try {
            String sqlStatement = "INSERT INTO Citizen(CitizenSchoolId, Fname, Lname, age) VALUES (?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, citizen.getSchoolId());
            statement.setString(2, citizen.getfName() );
            statement.setString(3, citizen.getlName());
            statement.setString(4, citizen.getAge());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        }
        return new Citizen(insertedId,citizen.getSchoolId(), citizen.getfName(),citizen.getlName(),citizen.getAge());
    }

    @Override
    public void updateCitizen(Citizen citizen) throws SQLException {
        try {

            String sql = "UPDATE Citizen SET CitizenSchoolId = ?, Fname = ?, Lname = ?, age = ? WHERE CitizenId=?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, citizen.getSchoolId());
            preparedStatement.setString(2, citizen.getfName());
            preparedStatement.setString(3, citizen.getlName());
            preparedStatement.setString(4, citizen.getAge());
            preparedStatement.setInt(5, citizen.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
        }catch (SQLException sqlException){
            throw sqlException;
        }
    }

    @Override
    public boolean removeCitizen(Citizen citizen) throws SQLException {
        try {
            String sqlStatement = "DELETE FROM Citizen WHERE CitizenId=?";
            PreparedStatement statement = con.prepareStatement(sqlStatement);
            statement.setInt(1, citizen.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

}
