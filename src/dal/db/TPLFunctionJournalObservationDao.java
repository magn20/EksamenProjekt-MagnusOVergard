package dal.db;

import be.FunctionJournalObservation;
import be.TPLFunctionJournalObservation;
import dal.interfaces.IFunctionJournalObservation;
import dal.interfaces.ITPLFunctionJournalObservation;

import java.io.IOException;
import java.sql.*;

public class TPLFunctionJournalObservationDao implements ITPLFunctionJournalObservation{

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();


    @Override
    public TPLFunctionJournalObservation getTPLFunctionJournalObservation(int citizenId) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM TPLFunctionJournalObservation Where TPLCitizenTPLFunctionObservationId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, citizenId);

            statement.execute();

            ResultSet rs = statement.getResultSet();

            while (rs.next()){

                int id = rs.getInt(1);
                int citizenIdFromDb = rs.getInt(2);
                String condition = rs.getString(3);

                basicConnectionPool.releaseConnection(connection);
                return new TPLFunctionJournalObservation(id, citizenIdFromDb, condition);
            }
            return null;

        } catch (SQLException | IOException sqlException) {
            throw sqlException;
        }
    }


    @Override
    public TPLFunctionJournalObservation createTPLFunctionJournalObservation(TPLFunctionJournalObservation tplFunctionJournalObservation) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO TPLFunctionJournalObservation(TPLCitizenTPLFunctionObservationId, Observation) VALUES (?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, tplFunctionJournalObservation.getTemplateId());
            statement.setString(2, tplFunctionJournalObservation.getObservation());

            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);

            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
            throw e;
        }
        return new TPLFunctionJournalObservation(insertedId, tplFunctionJournalObservation.getTemplateId(), tplFunctionJournalObservation.getObservation());

    }

    @Override
    public void updateTplFunctionJournalObservation(TPLFunctionJournalObservation tplFunctionJournalObservation) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sql = "UPDATE TPLFunctionJournalObservation SET TPLCitizenTPLFunctionObservationId = ?, Observation = ? WHERE TPLFunctionJournalObservationenId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tplFunctionJournalObservation.getTemplateId());
            preparedStatement.setString(2, tplFunctionJournalObservation.getObservation());
            preparedStatement.setInt(3, tplFunctionJournalObservation.getId());

            int affectedRows = preparedStatement.executeUpdate();
            basicConnectionPool.releaseConnection(connection);
            if (affectedRows != 1) {
            }
        } catch (SQLException | IOException exception) {
            throw exception;

        }
    }
}
