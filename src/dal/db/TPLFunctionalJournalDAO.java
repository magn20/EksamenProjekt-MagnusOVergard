package dal.db;

import be.TPLFunctionalJournal;
import be.TPLHealthJournal;
import dal.interfaces.ITPLFunctionalJournal;
import dal.interfaces.ITPLHealthJournal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

public class TPLFunctionalJournalDAO implements ITPLFunctionalJournal {

    private BasicConnectionPool basicConnectionPool = new BasicConnectionPool();

    /**
     * gets TPLFunctionalJournals that belongs to a Template
     * @param templateID the id of the template, that functional journals belong to
     * @return list of TPLFunctionalJournals
     */
    @Override
    public ObservableList<TPLFunctionalJournal> getTPLFunctionalJournal(int templateID) {
        ObservableList<TPLFunctionalJournal> TPLFunctionalJournalFromTemplate = FXCollections.observableArrayList();
        try(Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "SELECT * FROM TPLFunctionalJournal Where TPLCitizenTPLFunctionalAbilitiesId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, templateID);

            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {

                int id = rs.getInt(1);
                int templateCitizenId = rs.getInt(2);
                String condition = rs.getString(3);
                String relevancy = rs.getString(4);
                String lastUpdate = rs.getString(5);
                String niveau = rs.getString(6);
                String expectation = rs.getString(7);
                String note = rs.getString(8);
                String execution = rs.getString(9);
                String executionLimits = rs.getString(10);
                String citizenExpectation = rs.getString(11);

                TPLFunctionalJournalFromTemplate.add(new TPLFunctionalJournal(id, templateCitizenId, condition,lastUpdate, niveau,relevancy, note, expectation, execution, executionLimits, citizenExpectation));
            }
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return TPLFunctionalJournalFromTemplate;
    }

    /**
     * creates a TPLFunctionalJournal for database
     * @param tplFunctionalJournal the object for creation
     * @return the object that been created
     */
    @Override
    public TPLFunctionalJournal createTPLFunctionalJournal(TPLFunctionalJournal tplFunctionalJournal) throws SQLException, IOException {
        int insertedId = -1;
        try (Connection connection = basicConnectionPool.getConnection()) {
            String sqlStatement = "INSERT INTO TPLFunctionalJournal(TPLCitizenTPLFunctionalAbilitiesId, Condition ,Relevancy , LastUpdate, Niveau, Expectation, Note, Execution, ExucutionLimits, CitizenExpectation) VALUES (?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, tplFunctionalJournal.getTplCitizenId());
            statement.setString(2, tplFunctionalJournal.getCondition() );
            statement.setString(3, tplFunctionalJournal.getRelevancy());
            statement.setString(4, tplFunctionalJournal.getLastUpdate());
            statement.setString(5, tplFunctionalJournal.getNiveau());
            statement.setString(6, tplFunctionalJournal.getExpectation());
            statement.setString(7, tplFunctionalJournal.getNote());
            statement.setString(8, tplFunctionalJournal.getExecution());
            statement.setString(9, tplFunctionalJournal.getExecutionLimits());
            statement.setString(10, tplFunctionalJournal.getCitizenExpectation());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
            basicConnectionPool.releaseConnection(connection);
        } catch (SQLException | IOException exception) {
            throw exception;
        }
        return new TPLFunctionalJournal(insertedId, tplFunctionalJournal.getTplCitizenId(), tplFunctionalJournal.getCondition(), tplFunctionalJournal.getLastUpdate(), tplFunctionalJournal.getNiveau(), tplFunctionalJournal.getRelevancy(), tplFunctionalJournal.getNote(), tplFunctionalJournal.getExpectation(), tplFunctionalJournal.getExecution(), tplFunctionalJournal.getExecutionLimits(), tplFunctionalJournal.getCitizenExpectation());
    }

    /**
     * updates a TPLFunctionalJournal
     * @param tplFunctionalJournal the object holding the data
     */
    @Override
    public void updateTPLFunctionalJournal(TPLFunctionalJournal tplFunctionalJournal) throws SQLException, IOException {

       try(Connection connection = basicConnectionPool.getConnection()) {
           String sql = "UPDATE TPLFunctionalJournal SET TPLCitizenTPLFunctionalAbilitiesId = ?, Condition = ?, Relevancy = ?, LastUpdate = ?, Niveau = ?, Expectation = ?, Note = ?, Execution = ?, ExucutionLimits = ?, CitizenExpectation = ? WHERE TPLFunctionalAbilitiesID=?;";
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1, tplFunctionalJournal.getTplCitizenId());
           preparedStatement.setString(2, tplFunctionalJournal.getCondition());
           preparedStatement.setString(3, tplFunctionalJournal.getRelevancy());
           preparedStatement.setString(4, tplFunctionalJournal.getLastUpdate());
           preparedStatement.setString(5, tplFunctionalJournal.getNiveau());
           preparedStatement.setString(6, tplFunctionalJournal.getExpectation());
           preparedStatement.setString(7, tplFunctionalJournal.getNote());
           preparedStatement.setString(8, tplFunctionalJournal.getExecution());
           preparedStatement.setString(9, tplFunctionalJournal.getExecutionLimits());
           preparedStatement.setString(10, tplFunctionalJournal.getCitizenExpectation());
           preparedStatement.setInt(11, tplFunctionalJournal.getId());

           int affectedRows = preparedStatement.executeUpdate();
           if (affectedRows != 1) {
           }
           basicConnectionPool.releaseConnection(connection);
       }catch (SQLException | IOException exception){
           throw exception;
       }

    }

}
