package dal.db;

import be.GeneralInfo;
import be.TPLGeneralInfo;
import dal.interfaces.IGeneralInfo;
import dal.interfaces.ITPLGeneralInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class GeneralInfoDAO implements IGeneralInfo {

    private Connection con;
    public GeneralInfoDAO(Connection con){
        this.con = con;
    }


    /**
     * gets templates a school from database
     * @return Observablelist of all Teachers
     */
    @Override
    public ObservableList<GeneralInfo> getGeneralInfo(int templateID) throws SQLException {
        ObservableList<GeneralInfo> generalInfoFromCitizen = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM GeneralInfo Where CitizenGeneralInfoId = ? ";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
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
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return generalInfoFromCitizen;
    }

    @Override
    public GeneralInfo createGeneralInfo(GeneralInfo generalInfo) throws SQLException {
        int insertedId = -1;
        String sqlStatement = "INSERT INTO GeneralInfo(CitizenGeneralInfoId, Coping ,Motivation , Ressources, Roles, Habits, EducationAndJob, LifeStory, healthInformation, EquipmentAids, HomeLayout, Network) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
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
        return new GeneralInfo(insertedId, generalInfo.getCitizenId(), generalInfo.getCoping(), generalInfo.getMotivation(), generalInfo.getResources(), generalInfo.getRoles(), generalInfo.getHabits(),generalInfo.getEducationAndJob(), generalInfo.getLifeStory(), generalInfo.getHealthInformation(), generalInfo.getEquipmentAids(),generalInfo.getHomeLayout(), generalInfo.getNetwork());
    }

    @Override
    public void updateGeneralInfo(GeneralInfo generalInfo) throws SQLException {

        try {

            String sql = "UPDATE GeneralInfo SET CitizenGeneralInfoId = ?, Coping = ?, Motivation = ?, Ressources = ? , Roles = ?, Habits = ?, EducationAndJob = ?, LifeStory = ?, healthInformation = ?, EquipmentAids = ?, HomeLayout = ?, Network = ? WHERE GeneralInfoId=?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
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
        }catch (SQLException sqlException){
            throw sqlException;
        }
    }

    @Override
    public boolean removeGeneralInfo(GeneralInfo generalInfo) throws SQLException {
        try {
            String sqlStatement = "DELETE FROM GeneralInfo WHERE GeneralInfoId=?";
            PreparedStatement statement = con.prepareStatement(sqlStatement);
            statement.setInt(1, generalInfo.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

}
