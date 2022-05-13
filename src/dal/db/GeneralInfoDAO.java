package dal.db;

import be.GeneralInfo;
import be.TPLGeneralInfo;
import dal.interfaces.IGeneralInfo;
import dal.interfaces.ITPLGeneralInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class GeneralInfoDAO implements IGeneralInfo {


    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();
    /**
     * gets Generalinfo from a school
     * @return Observablelist of all GeneralInfo
     */
    @Override
    public ObservableList<GeneralInfo> getGeneralInfo(int templateID) throws SQLException, IOException {
        ObservableList<GeneralInfo> generalInfoFromCitizen = FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM GeneralInfo Where CitizenGeneralInfoId = ? ";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, templateID);

            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {

                int id = rs.getInt(1);
                int citizenId = rs.getInt(2);
                String coping = rs.getString(3);
                String motivation = rs.getString(4);
                String resource = rs.getString(5);
                String roles = rs.getString(6);
                String habits = rs.getString(7);
                String educationAndJob = rs.getString(8);
                String lifeStory = rs.getString(9);
                String healthInfo = rs.getString(10);
                String equipmentsAids = rs.getString(11);
                String homeLayout = rs.getString(12);
                String network = rs.getString(13);


                generalInfoFromCitizen.add(new GeneralInfo(id, citizenId, coping,motivation, resource, roles,habits,educationAndJob,lifeStory,healthInfo,equipmentsAids,homeLayout,network));
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException exception) {
            throw exception;
        }
        return generalInfoFromCitizen;
    }


    /**
     * creates GeneralInfo
     * @param generalInfo the object for creation
     * @return the objected that has been created
     */
    @Override
    public GeneralInfo createGeneralInfo(GeneralInfo generalInfo) throws SQLException, IOException {
       try(Connection connection = basicConnectionPool.getConnection()) {

           int insertedId = -1;
           String sqlStatement = "INSERT INTO GeneralInfo(CitizenGeneralInfoId, Coping ,Motivation , Ressources, Roles, Habits, EducationAndJob, LifeStory, healthInformation, EquipmentAids, HomeLayout, Network) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
           PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
           statement.setInt(1, generalInfo.getCitizenId());
           statement.setString(2, generalInfo.getCoping() );
           statement.setString(3, generalInfo.getMotivation());
           statement.setString(4, generalInfo.getResources());
           statement.setString(5, generalInfo.getRoles());
           statement.setString(6, generalInfo.getHabits());
           statement.setString(7, generalInfo.getEducationAndJob());
           statement.setString(8, generalInfo.getLifeStory());
           statement.setString(9, generalInfo.getHealthInformation());
           statement.setString(10, generalInfo.getEquipmentAids());
           statement.setString(11, generalInfo.getHomeLayout());
           statement.setString(12, generalInfo.getNetwork());
           statement.execute();
           ResultSet rs = statement.getGeneratedKeys();
           rs.next();
           insertedId = rs.getInt(1);
           basicConnectionPool.releaseConnection(connection);
           return new GeneralInfo(insertedId, generalInfo.getCitizenId(), generalInfo.getCoping(), generalInfo.getMotivation(), generalInfo.getResources(), generalInfo.getRoles(), generalInfo.getHabits(),generalInfo.getEducationAndJob(), generalInfo.getLifeStory(), generalInfo.getHealthInformation(), generalInfo.getEquipmentAids(),generalInfo.getHomeLayout(), generalInfo.getNetwork());
       }catch (Exception exception){
           throw exception;
       }
    }

    /**
     * updates General Info
     * @param generalInfo the object holding the Data
     */
    @Override
    public void updateGeneralInfo(GeneralInfo generalInfo) throws SQLException, IOException {

        try(Connection connection = basicConnectionPool.getConnection()) {

            String sql = "UPDATE GeneralInfo SET CitizenGeneralInfoId = ?, Coping = ?, Motivation = ?, Ressources = ? , Roles = ?, Habits = ?, EducationAndJob = ?, LifeStory = ?, healthInformation = ?, EquipmentAids = ?, HomeLayout = ?, Network = ? WHERE GeneralInfoId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, generalInfo.getCitizenId());
            preparedStatement.setString(2, generalInfo.getCoping());
            preparedStatement.setString(3, generalInfo.getMotivation());
            preparedStatement.setString(4, generalInfo.getResources());
            preparedStatement.setString(5, generalInfo.getRoles());
            preparedStatement.setString(6, generalInfo.getHabits());
            preparedStatement.setString(7, generalInfo.getEducationAndJob());
            preparedStatement.setString(8, generalInfo.getLifeStory());
            preparedStatement.setString(9, generalInfo.getHealthInformation());
            preparedStatement.setString(10, generalInfo.getEquipmentAids());
            preparedStatement.setString(11, generalInfo.getHomeLayout());
            preparedStatement.setString(12, generalInfo.getNetwork());
            preparedStatement.setInt(13, generalInfo.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
            basicConnectionPool.releaseConnection(connection);
        }catch (SQLException | IOException sqlException){
            throw sqlException;
        }
    }

}
