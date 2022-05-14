package dal.db;

import be.HealthJournalObservation;
import be.TPLHealthJournalObservation;
import dal.interfaces.IHealthJournalObservation;
import dal.interfaces.ITPLHealthJournalObservation;

import java.io.IOException;
import java.sql.*;

public class TPLHealthJournalObservationDao implements ITPLHealthJournalObservation {

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();


    @Override
    public TPLHealthJournalObservation getTPLHealthJournalObservation(int templateId) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM TPLHealthJournalObservation Where TPLCitizenTPLHeatlhObservationId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, templateId);

            statement.execute();

            ResultSet rs = statement.getResultSet();

            while (rs.next()){

                int id = rs.getInt(1);
                int citizenIdFromDb = rs.getInt(2);
                String condition = rs.getString(3);
                basicConnectionPool.releaseConnection(connection);
                return new TPLHealthJournalObservation(id, citizenIdFromDb, condition);
            }
            return null;

        } catch (SQLException | IOException exception) {
            throw exception;
        }
    }


    @Override
    public TPLHealthJournalObservation createTPLHealthJournalObservation(TPLHealthJournalObservation tplHealthJournalObservation) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO TPLHealthJournalObservation(TPLCitizenTPLHeatlhObservationId, Observation) VALUES (?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, tplHealthJournalObservation.getTemplateId());
            statement.setString(2, tplHealthJournalObservation.getObservation());

            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
            throw e;
        }
        return new TPLHealthJournalObservation(insertedId, tplHealthJournalObservation.getTemplateId(), tplHealthJournalObservation.getObservation());

    }

    @Override
    public void updateTPLHealthJournalObservation(TPLHealthJournalObservation tplHealthJournalObservation) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sql = "UPDATE TPLHealthjournalObservation SET TPLCitizenTPLHeatlhObservationId = ?, Observation = ? WHERE TPLHeatlhJournalObservationenId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tplHealthJournalObservation.getTemplateId());
            preparedStatement.setString(2, tplHealthJournalObservation.getObservation());
            preparedStatement.setInt(3, tplHealthJournalObservation.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException sqlException) {
            throw sqlException;

        }
    }
}
