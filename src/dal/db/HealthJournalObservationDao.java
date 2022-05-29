package dal.db;
import be.HealthJournalObservation;
import dal.interfaces.IHealthJournalObservation;
import java.io.IOException;
import java.sql.*;

public class HealthJournalObservationDao implements IHealthJournalObservation {

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();


    /**
     * gets healthJournalObservation for a citizen
     * @param citizenId the citizen which the healthJournalObservation Belongs to.
     * @return the healthJournalObservation object
     */
    @Override
    public HealthJournalObservation getHealthJournalObservation(int citizenId) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM HealthjournalObservation Where CitizenHeatlhObservationId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, citizenId);

            statement.execute();

            ResultSet rs = statement.getResultSet();

            while (rs.next()){

                int id = rs.getInt(1);
                int citizenIdFromDb = rs.getInt(2);
                String condition = rs.getString(3);
                basicConnectionPool.releaseConnection(connection);
                return new HealthJournalObservation(id, citizenIdFromDb, condition);
            }
            return null;

        } catch (SQLException | IOException exception) {
            throw exception;
        }
    }


    /**
     * Creates a healthJournalObservation in database
     * @param healthJournalObservation the object for creation
     * @return the object that was created
     */
    @Override
    public HealthJournalObservation createHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO HealthjournalObservation(CitizenHeatlhObservationId, Observation) VALUES (?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, healthJournalObservation.getCitizenId());
            statement.setString(2, healthJournalObservation.getObservation());

            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
            throw e;
        }
        return new HealthJournalObservation(insertedId, healthJournalObservation.getCitizenId(), healthJournalObservation.getObservation());

    }

    /**
     * updates a healthJournalObservation
     * @param healthJournalObservation object for update
     */
    @Override
    public void updateHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sql = "UPDATE HealthjournalObservation SET CitizenHeatlhObservationId = ?, Observation = ? WHERE HeatlhJournalObservationenId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, healthJournalObservation.getCitizenId());
            preparedStatement.setString(2, healthJournalObservation.getObservation());
            preparedStatement.setInt(3, healthJournalObservation.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException sqlException) {
            throw sqlException;

        }
    }
}
