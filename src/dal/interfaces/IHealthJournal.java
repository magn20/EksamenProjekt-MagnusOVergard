package dal.interfaces;

import be.HealthJournal;
import be.TPLHealthJournal;

import java.sql.SQLException;
import java.util.List;

public interface IHealthJournal {
    public List<HealthJournal> getHealthJournal(int citizenId) throws SQLException;
    public HealthJournal createHealthJournal(HealthJournal healthJournal) throws SQLException;
    public void updateHealthJournal(HealthJournal HealthJournal) throws SQLException;
}
