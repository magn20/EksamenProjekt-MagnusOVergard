package dal.db;

import be.FunctionalJournal;
import be.TPLFunctionalJournal;
import dal.interfaces.IFunctionalJournal;
import dal.interfaces.ITPLFunctionalJournal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class FunctionalJournalDAO implements IFunctionalJournal {

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();

    /**
     * Gets all functionaljournals from a citizen.
     *
     * @param citizenId the id of the citizen that the functionaljournals should belong to.
     * @return list of functional journal
     * @throws SQLException
     */
    @Override
    public ObservableList<FunctionalJournal> getFunctionalJournal(int citizenId) throws SQLException, IOException {
        ObservableList<FunctionalJournal> functionalJournalFromCitizen = FXCollections.observableArrayList();
        try (Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM FunctionalJournal Where CitizenFunctionalAbilitiesId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, citizenId);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int id = rs.getInt(1);
                int citizenIdFromDb = rs.getInt(2);
                String condition = rs.getString(3);
                String relevancy = rs.getString(4);
                String lastUpdate = rs.getString(5);
                String niveau = rs.getString(6);
                String expectation = rs.getString(7);
                String note = rs.getString(8);
                String execution = rs.getString(9);
                String executionLimits = rs.getString(10);
                String citizenExpectation = rs.getString(11);

                functionalJournalFromCitizen.add(new FunctionalJournal(id, citizenIdFromDb, condition, lastUpdate,
                        niveau, relevancy, note, expectation, execution, executionLimits, citizenExpectation));
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return functionalJournalFromCitizen;

    }

    /**
     * creates a functionalJournal
     *
     * @param functionalJournal the object for creation
     * @return the object that has been created
     */
    @Override
    public FunctionalJournal createFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException, IOException {
        int insertedId = -1;
        try (Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO FunctionalJournal(CitizenFunctionalAbilitiesId, Condition ,Relevancy " +
                    ", LastUpdate, Niveau, Expectation, Note, Execution, ExucutionLimits, CitizenExpectation) VALUES (?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, functionalJournal.getCitizenId());
            statement.setString(2, functionalJournal.getCondition());
            statement.setString(3, functionalJournal.getRelevancy());
            statement.setString(4, functionalJournal.getLastUpdate());
            statement.setString(5, functionalJournal.getNiveau());
            statement.setString(6, functionalJournal.getExpectation());
            statement.setString(7, functionalJournal.getNote());
            statement.setString(8, functionalJournal.getExecution());
            statement.setString(9, functionalJournal.getExecutionLimits());
            statement.setString(10, functionalJournal.getCitizenExpectation());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
            throw e;
        }
        return new FunctionalJournal(insertedId, functionalJournal.getCitizenId(), functionalJournal.getCondition(), functionalJournal.getLastUpdate(), functionalJournal.getNiveau(), functionalJournal.getRelevancy(), functionalJournal.getNote(), functionalJournal.getExpectation(), functionalJournal.getExecution(), functionalJournal.getExecutionLimits(), functionalJournal.getCitizenExpectation());
    }

    /**
     * Updates a FunctionalJournal
     *
     * @param functionalJournal the object holding the data
     * @throws SQLException
     */
    @Override
    public void updateFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException, IOException {
        try (Connection connection = basicConnectionPool.getConnection()) {
            String sql = "UPDATE FunctionalJournal SET CitizenFunctionalAbilitiesId = ?, Condition = ?, Relevancy = ?, LastUpdate = ?, Niveau = ?, Expectation = ?, Note = ?, Execution = ?, ExucutionLimits = ?, CitizenExpectation = ? WHERE FunctionalAbilitiesID=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, functionalJournal.getCitizenId());
            preparedStatement.setString(2, functionalJournal.getCondition());
            preparedStatement.setString(3, functionalJournal.getRelevancy());
            preparedStatement.setString(4, functionalJournal.getLastUpdate());
            preparedStatement.setString(5, functionalJournal.getNiveau());
            preparedStatement.setString(6, functionalJournal.getExpectation());
            preparedStatement.setString(7, functionalJournal.getNote());
            preparedStatement.setString(8, functionalJournal.getExecution());
            preparedStatement.setString(9, functionalJournal.getExecutionLimits());
            preparedStatement.setString(10, functionalJournal.getCitizenExpectation());
            preparedStatement.setInt(11, functionalJournal.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException e) {
            throw e;
        }


    }

}
