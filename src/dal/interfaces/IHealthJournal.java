package dal.interfaces;

import be.HealthJournal;
import be.TPLHealthJournal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IHealthJournal {
    public List<HealthJournal> getHealthJournal(int citizenId) throws SQLException, IOException;
    public HealthJournal createHealthJournal(HealthJournal healthJournal) throws SQLException, IOException;
    public void updateHealthJournal(HealthJournal HealthJournal) throws SQLException, IOException;
}
