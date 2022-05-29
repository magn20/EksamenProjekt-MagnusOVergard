package dal.db;

import be.Citizen;
import be.Student;
import dal.interfaces.ICitizen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class CitizenDAO implements ICitizen {

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();

    /**
     *
     * Returns a list of all citizens for a school
     * @param schoolId the id for school the citizen belongs to.
     * @return list of citizens
     * @throws SQLException
     */
    @Override
    public ObservableList<Citizen> getCitizen(int schoolId) throws SQLException, IOException {
        ObservableList<Citizen> allCitizenFromSchool = FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM Citizen Where CitizenSchoolId = ? ";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
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
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException sqlException) {
            throw sqlException;
        }
        return allCitizenFromSchool;
    }


    /**
     * gets Citizen for a student from database
     * @return Observablelist of all Citizens
     */
    @Override
    public ObservableList<Citizen> getCitizenForStudent(int studentID) throws SQLException, IOException {
        ObservableList<Citizen> allCitizenFromStudent = FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM Citizen INNER JOIN Works_on ON Works_on.FKCitizenId = Citizen.CitizenId WHERE FKStudentId =(?); ";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
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

                    allCitizenFromStudent.add(new Citizen(id,schoolId ,fName, lName, age));
                }
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw e;
        }
        return allCitizenFromStudent;
    }

    /**
     * setup a student to work on a citizen
     * @param citizen the object that being worked on
     * @param student the object that working on citizen
     * @return return true if successful
     * @throws SQLException
     */
    @Override
    public boolean setStudentWorkOnCitizen(Citizen citizen, Student student) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sql = "INSERT INTO Works_on VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, citizen.getId());
            preparedStatement.setInt(2, student.getId());
            preparedStatement.executeUpdate();
            basicConnectionPool.releaseConnection(connection);
            return true;
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * creates a citizen in datebase
     * @param citizen object for creation on database
     * @return return the created object
     * @throws SQLException
     */
    @Override
    public Citizen createCitizen(Citizen citizen) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO Citizen(CitizenSchoolId, Fname, Lname, age) VALUES (?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, citizen.getSchoolId());
            statement.setString(2, citizen.getfName() );
            statement.setString(3, citizen.getlName());
            statement.setString(4, citizen.getAge());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
            throw e;
        }
        return new Citizen(insertedId,citizen.getSchoolId(), citizen.getfName(),citizen.getlName(),citizen.getAge());
    }

    /**
     * updates a citizen on database
     * @param citizen object holding the changed data.
     */
    @Override
    public void updateCitizen(Citizen citizen) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {

            String sql = "UPDATE Citizen SET CitizenSchoolId = ?, Fname = ?, Lname = ?, age = ? WHERE CitizenId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, citizen.getSchoolId());
            preparedStatement.setString(2, citizen.getfName());
            preparedStatement.setString(3, citizen.getlName());
            preparedStatement.setString(4, citizen.getAge());
            preparedStatement.setInt(5, citizen.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
            basicConnectionPool.releaseConnection(connection);
        }catch (Exception exception){
            throw exception;
        }
    }

    /**
     * Removes a citizen on database
     * @param citizen object for deletion.
     * @return return true if successful
     */
    @Override
    public boolean removeCitizen(Citizen citizen) throws SQLException, IOException {
        try (Connection connection = basicConnectionPool.getConnection()){
            String sqlStatement = "DELETE FROM Citizen WHERE CitizenId=?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, citizen.getId());
            statement.execute();
            basicConnectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException | IOException e) {
            throw e;
        }
    }

}
