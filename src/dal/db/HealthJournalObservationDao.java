package dal.db;

import be.HealthJournal;
import be.HealthJournalObservation;
import dal.interfaces.IHealthJournalObservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class HealthJournalObservationDao implements IHealthJournalObservation {


    private Connection con;

    public HealthJournalObservationDao(Connection con) {
        this.con = con;
    }


    @Override
    public HealthJournalObservation getHealthJournalObservation(int citizenId) throws SQLException {
        try {
            String sqlStatement = "SELECT * FROM HealthjournalObservation Where CitizenHeatlhObservationId = ?";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, citizenId);

            statement.execute();

            ResultSet rs = statement.getResultSet();

            while (rs.next()){

                int id = rs.getInt(1);
                int citizenIdFromDb = rs.getInt(2);
                String condition = rs.getString(3);

                return new HealthJournalObservation(id, citizenIdFromDb, condition);
            }
            return null;

        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }


    @Override
    public HealthJournalObservation createHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException {
        int insertedId = -1;
        try {
            String sqlStatement = "INSERT INTO HealthjournalObservation(CitizenHeatlhObservationId, Observation) VALUES (?,?);";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, healthJournalObservation.getCitizenId());
            statement.setString(2, healthJournalObservation.getObservation());

            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        }
        return new HealthJournalObservation(insertedId, healthJournalObservation.getCitizenId(), healthJournalObservation.getObservation());

    }

    @Override
    public void updateHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException {
        try {
            String sql = "UPDATE HealthjournalObservation SET CitizenHeatlhObservationId = ?, Observation = ? WHERE HeatlhJournalObservationenId=?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, healthJournalObservation.getCitizenId());
            preparedStatement.setString(2, healthJournalObservation.getObservation());
            preparedStatement.setInt(3, healthJournalObservation.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
        } catch (SQLException sqlException) {
            throw sqlException;

        }
    }
}
