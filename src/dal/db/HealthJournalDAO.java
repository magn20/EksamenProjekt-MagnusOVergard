package dal.db;

import be.HealthJournal;
import be.TPLHealthJournal;
import dal.interfaces.IHealthJournal;
import dal.interfaces.ITPLHealthJournal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class HealthJournalDAO implements IHealthJournal {

    BasicConnectionPool basicConnectionPool = new BasicConnectionPool();


    /**
     * gets all healthJournals for a citizen.
     * @param citizenId the id of the citizen which health journals belong to.
     * @return lst of HealthJournals
     */
    @Override
    public ObservableList<HealthJournal> getHealthJournal(int citizenId) throws SQLException, IOException {
        ObservableList<HealthJournal> healthJournalFromCitizen = FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM HealthJournal Where CitizenHeatlhjournalId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, citizenId);

            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {

                int id = rs.getInt(1);
                int citizenIdFromDb = rs.getInt(2);
                String condition = rs.getString(3);
                String note = rs.getString(4);
                String evaluation = rs.getString(5);
                String expectation = rs.getString(6);
                String relevancy = rs.getString(7);
                String lastUpdate = rs.getString(8);

                healthJournalFromCitizen.add(new HealthJournal(id, citizenIdFromDb, condition,lastUpdate,evaluation,relevancy,note,expectation));
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException exception) {
            throw exception;
        }
        return healthJournalFromCitizen;
    }

    /**
     * creates a HealthJournal
     * @param healthJournal the object for creation
     * @return the object that is created
     */
    @Override
    public HealthJournal createHealthJournal(HealthJournal healthJournal) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO HealthJournal(CitizenHeatlhjournalId, Condition ,LastUpdate , Evaluation, Relevancy, Note, Expectation) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, healthJournal.getCitizenId());
            statement.setString(2, healthJournal.getCondition() );
            statement.setString(3, healthJournal.getLastUpdate());
            statement.setString(4, healthJournal.getEvaluation());
            statement.setString(5, healthJournal.getRelevancy());
            statement.setString(6, healthJournal.getNote());
            statement.setString(7, healthJournal.getExpectation());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
            throw e;
        }
        return new HealthJournal(insertedId, healthJournal.getCitizenId(), healthJournal.getCondition(), healthJournal.getLastUpdate(), healthJournal.getEvaluation(), healthJournal.getRelevancy(), healthJournal.getNote(),healthJournal.getExpectation());
    }

    /**
     * updates a HealthJournal.
     * @param healthJournal the object holding the data for update.
     */
    @Override
    public void updateHealthJournal(HealthJournal healthJournal) throws SQLException, IOException {
       try(Connection connection = basicConnectionPool.getConnection()) {
           String sql = "UPDATE HealthJournal SET CitizenHeatlhjournalId = ?, Condition = ?, LastUpdate = ?, Evaluation = ? , Relevancy = ?, Note = ?, Expectation = ? WHERE HeatlhJournalId=?;";
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1, healthJournal.getCitizenId());
           preparedStatement.setString(2, healthJournal.getCondition());
           preparedStatement.setString(3, healthJournal.getLastUpdate());
           preparedStatement.setString(4, healthJournal.getEvaluation());
           preparedStatement.setString(5, healthJournal.getRelevancy());
           preparedStatement.setString(6, healthJournal.getNote());
           preparedStatement.setString(7, healthJournal.getExpectation());
           preparedStatement.setInt(8, healthJournal.getId());

           int affectedRows = preparedStatement.executeUpdate();
           if (affectedRows != 1) {
           }
           basicConnectionPool.releaseConnection(connection);
       }catch (SQLException | IOException exception){
           throw exception;
       }


    }

}
