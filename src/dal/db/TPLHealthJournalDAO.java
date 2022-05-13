package dal.db;

import be.TPLHealthJournal;
import dal.interfaces.ITPLHealthJournal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class TPLHealthJournalDAO implements ITPLHealthJournal {

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();


    /**
     * get all TPLHealthJournal from a Template.
     * @param templateID The id of the template
     * @return list of TPLHealthJournal
     */
    @Override
    public ObservableList<TPLHealthJournal> getTPLHealthJournal(int templateID) throws SQLException, IOException {
        ObservableList<TPLHealthJournal> TPLHealthJournalFromTemplate = FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM TPLHealthJournal Where TPLCitizenHeatlhjournalId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, templateID);

            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {

                int id = rs.getInt(1);
                int templateCitizenId = rs.getInt(2);
                String condition = rs.getString(3);
                String note = rs.getString(4);
                String evaluation = rs.getString(5);
                String expectation = rs.getString(6);
                String relevancy = rs.getString(7);
                String lastUpdate = rs.getString(8);

                TPLHealthJournalFromTemplate.add(new TPLHealthJournal(id, templateCitizenId, condition,lastUpdate,evaluation,relevancy,note,expectation));
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException sqlException) {
            throw sqlException;
        }
        return TPLHealthJournalFromTemplate;
    }

    /**
     * Creates a TPLHealthJournal
     * @param tplHealthJournal object for creation
     * @return The objected that has been Created
     */
    @Override
    public TPLHealthJournal createTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException, IOException {
        int insertedId = -1;
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO TPLHealthJournal(TPLCitizenHeatlhjournalId, Condition ,LastUpdate , Evaluation, Relevancy, Note, Expectation) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, tplHealthJournal.getTplCitizenId());
            statement.setString(2, tplHealthJournal.getCondition() );
            statement.setString(3, tplHealthJournal.getLastUpdate());
            statement.setString(4, tplHealthJournal.getEvaluation());
            statement.setString(5, tplHealthJournal.getRelevancy());
            statement.setString(6, tplHealthJournal.getNote());
            statement.setString(7, tplHealthJournal.getExpectation());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException exception) {
            throw exception;
        }
        return new TPLHealthJournal(insertedId, tplHealthJournal.getTplCitizenId(), tplHealthJournal.getCondition(), tplHealthJournal.getLastUpdate(), tplHealthJournal.getEvaluation(), tplHealthJournal.getRelevancy(), tplHealthJournal.getNote(),tplHealthJournal.getExpectation());
    }

    /**
     * updates a TPLHealthJournal in Database
     * @param tplHealthJournal object holding the data for updating.
     */
    @Override
    public void updateTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException, IOException {
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sql = "UPDATE TPLHealthJournal SET TPLCitizenHeatlhjournalId = ?, Condition = ?, LastUpdate = ?, Evaluation = ? , Relevancy = ?, Note = ?, Expectation = ? WHERE TPLHeatlhJournalId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tplHealthJournal.getTplCitizenId());
            preparedStatement.setString(2, tplHealthJournal.getCondition());
            preparedStatement.setString(3, tplHealthJournal.getLastUpdate());
            preparedStatement.setString(4, tplHealthJournal.getEvaluation());
            preparedStatement.setString(5, tplHealthJournal.getRelevancy());
            preparedStatement.setString(6, tplHealthJournal.getNote());
            preparedStatement.setString(7, tplHealthJournal.getExpectation());
            preparedStatement.setInt(8, tplHealthJournal.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
        }catch (SQLException | IOException exception){
            throw exception;
        }

    }

}
