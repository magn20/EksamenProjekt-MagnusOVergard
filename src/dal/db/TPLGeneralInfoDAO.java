package dal.db;

import be.TPLGeneralInfo;
import be.Template;
import dal.interfaces.ITPLGeneralInfo;
import dal.interfaces.ITemplate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TPLGeneralInfoDAO implements ITPLGeneralInfo {

    private Connection con;
    public TPLGeneralInfoDAO(Connection con){
        this.con = con;
    }


    /**
     * gets templates a school from database
     * @return Observablelist of all Teachers
     */
    @Override
    public ObservableList<TPLGeneralInfo> getTPLGeneralInfo(int templateID) {
        ObservableList<TPLGeneralInfo> TPLGeneralInfoFromTemplate = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM TPLGeneralInfo Where TPLCitizenGeneralInfoId = ? ";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, templateID);

            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {

                int id = rs.getInt(1);
                int templateCitizenId = rs.getInt(2);
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


                TPLGeneralInfoFromTemplate.add(new TPLGeneralInfo(id, templateCitizenId, coping,motivation, resource, roles,habits,educationAndJob,lifeStory,healthInfo,equipmentsAids,homeLayout,network));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return TPLGeneralInfoFromTemplate;
    }

    @Override
    public TPLGeneralInfo createTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) {
        int insertedId = -1;
        try {
            String sqlStatement = "INSERT INTO TPLGeneralInfo(TPLCitizenGeneralInfoId, Coping ,Motivation , Ressources, Roles, Habits, EducationAndJob, LifeStory, healthInformation, EquipmentAids, HomeLayout, Network) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, tplGeneralInfo.getTplCitizenId());
            statement.setString(2, tplGeneralInfo.getCoping() );
            statement.setString(3, tplGeneralInfo.getMotivation());
            statement.setString(4, tplGeneralInfo.getResources());
            statement.setString(5, tplGeneralInfo.getRoles());
            statement.setString(6, tplGeneralInfo.getHabits());
            statement.setString(7, tplGeneralInfo.getEducationAndJob());
            statement.setString(8, tplGeneralInfo.getLifeStory());
            statement.setString(9, tplGeneralInfo.getHealthInformation());
            statement.setString(10, tplGeneralInfo.getEquipmentAids());
            statement.setString(11, tplGeneralInfo.getHomeLayout());
            statement.setString(12, tplGeneralInfo.getNetwork());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new TPLGeneralInfo(insertedId, tplGeneralInfo.getTplCitizenId(), tplGeneralInfo.getCoping(), tplGeneralInfo.getMotivation(), tplGeneralInfo.getResources(), tplGeneralInfo.getRoles(), tplGeneralInfo.getHabits(),tplGeneralInfo.getEducationAndJob(), tplGeneralInfo.getLifeStory(), tplGeneralInfo.getHealthInformation(), tplGeneralInfo.getEquipmentAids(),tplGeneralInfo.getHomeLayout(), tplGeneralInfo.getNetwork());
    }

    @Override
    public void updateTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) throws SQLException {
        String sql = "UPDATE TPLGeneralInfo SET TPLCitizenSchoolId = ?, Coping = ?, Motivation = ?, Ressources = ? , Roles = ?, Habits = ?, EducationAndJob = ?, LifeStory = ?, healthInformation = ?, EquitmentAids = ?, HomeLayout = ?, Network = ? WHERE TPLGeneralInfoId=?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, tplGeneralInfo.getTplCitizenId());
        preparedStatement.setString(2, tplGeneralInfo.getCoping());
        preparedStatement.setString(3, tplGeneralInfo.getMotivation());
        preparedStatement.setString(4, tplGeneralInfo.getResources());
        preparedStatement.setString(5, tplGeneralInfo.getRoles());
        preparedStatement.setString(6, tplGeneralInfo.getHabits());
        preparedStatement.setString(7, tplGeneralInfo.getEducationAndJob());
        preparedStatement.setString(8, tplGeneralInfo.getLifeStory());
        preparedStatement.setString(9, tplGeneralInfo.getHealthInformation());
        preparedStatement.setString(10, tplGeneralInfo.getEquipmentAids());
        preparedStatement.setString(11, tplGeneralInfo.getHomeLayout());
        preparedStatement.setString(12, tplGeneralInfo.getNetwork());
        preparedStatement.setInt(13, tplGeneralInfo.getId());

        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows != 1) {
        }
    }

    @Override
    public boolean removeTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) {
        try {
            String sqlStatement = "DELETE FROM TPLGeneralInfo WHERE TPLGeneralInfoId=?";
            PreparedStatement statement = con.prepareStatement(sqlStatement);
            statement.setInt(1, tplGeneralInfo.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
