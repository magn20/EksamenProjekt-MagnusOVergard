package dal.db;

import be.FunctionJournalObservation;
import be.HealthJournalObservation;
import dal.interfaces.IFunctionJournalObservation;
import dal.interfaces.IHealthJournalObservation;

import java.sql.*;

public class FunctionJournalObservationDao implements IFunctionJournalObservation {


    private Connection con;

    public FunctionJournalObservationDao(Connection con) {
        this.con = con;
    }


    @Override
    public FunctionJournalObservation getFunctionJournalObservation(int citizenId) throws SQLException {
        try {
            String sqlStatement = "SELECT * FROM FunctionJournalObservation Where CitizenFunctionObservationId = ?";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, citizenId);

            statement.execute();

            ResultSet rs = statement.getResultSet();

            while (rs.next()){

                int id = rs.getInt(1);
                int citizenIdFromDb = rs.getInt(2);
                String condition = rs.getString(3);

                return new FunctionJournalObservation(id, citizenIdFromDb, condition);
            }
            return null;

        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }


    @Override
    public FunctionJournalObservation createFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException {
        int insertedId = -1;
        try {
            String sqlStatement = "INSERT INTO FunctionJournalObservation(CitizenFunctionObservationId, Observation) VALUES (?,?);";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, functionJournalObservation.getCitizenId());
            statement.setString(2, functionJournalObservation.getObservation());

            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        }
        return new FunctionJournalObservation(insertedId, functionJournalObservation.getCitizenId(), functionJournalObservation.getObservation());

    }

    @Override
    public void updateFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException {
        try {
            String sql = "UPDATE HealthjournalObservation SET CitizenHeatlhObservationId = ?, Observation = ? WHERE HeatlhJournalObservationenId=?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, functionJournalObservation.getCitizenId());
            preparedStatement.setString(2, functionJournalObservation.getObservation());
            preparedStatement.setInt(3, functionJournalObservation.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
            }
        } catch (SQLException sqlException) {
            throw sqlException;

        }
    }
}
