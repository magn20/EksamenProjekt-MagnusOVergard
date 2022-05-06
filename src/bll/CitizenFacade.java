package bll;

import be.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.*;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class CitizenFacade {

    private CitizenDAO citizenDAO;
    private GeneralInfoDAO generalInfoDAO;
    private HealthJournalDAO healthJournalDAO;
    private FunctionalJournalDAO functionalJournalDAO;
    private DatabaseConnector connector;
    {
        try {
            connector = new DatabaseConnector();
            healthJournalDAO = new HealthJournalDAO(connector.getConnection());
            citizenDAO = new CitizenDAO(connector.getConnection());
            generalInfoDAO = new GeneralInfoDAO(connector.getConnection());
            functionalJournalDAO = new FunctionalJournalDAO(connector.getConnection());
        } catch (SQLServerException | IOException e) {
            e.printStackTrace();
        }
    }


    // for our Citizens

    public ObservableList<Citizen> getCitizen(int schoolId) throws SQLException {
        return citizenDAO.getCitizen(schoolId);
    }
    public ObservableList<Citizen> getCitizenForStudent(int studentId) throws SQLException {
        return citizenDAO.getCitizenForStudent(studentId);
    }

    public boolean setStudentWorksOnCitizen(Citizen citizen, Student student) throws SQLException {
       return citizenDAO.setStudentWorkOnCitizen(citizen, student);
    }

    public Citizen createCitizen(Citizen citizen) throws SQLException {
        return citizenDAO.createCitizen(citizen);
    }
    public boolean removeCitizen(Citizen citizen) throws SQLException {
        return citizenDAO.removeCitizen(citizen);
    }
    public void updateCitizen(Citizen citizen) throws SQLException {
        citizenDAO.updateCitizen(citizen);}

    // for GeneralInfo
    public ObservableList<GeneralInfo> getGeneralInfo(int citizenID) throws SQLException {
         return generalInfoDAO.getGeneralInfo(citizenID);
    }
    public GeneralInfo createGeneralInfo(GeneralInfo generalInfo) throws SQLException {
       return generalInfoDAO.createGeneralInfo(generalInfo);
    }
    public void updateGeneralInfo(GeneralInfo generalInfo) throws SQLException {
        generalInfoDAO.updateGeneralInfo(generalInfo);
    }
    public boolean removeGeneralInfo(GeneralInfo generalInfo) throws SQLException {
        return generalInfoDAO.removeGeneralInfo(generalInfo);
    }

    // for HealthJournal
    public ObservableList<HealthJournal> getHealthJournal(int citizenId) throws SQLException {
        return healthJournalDAO.getHealthJournal(citizenId);
    }
    public HealthJournal createHealthJournal(HealthJournal healthJournal) throws SQLException {
        return healthJournalDAO.createHealthJournal(healthJournal);
    }
    public void updateHealthJournal(HealthJournal healthJournal) throws SQLException {
        healthJournalDAO.updateHealthJournal(healthJournal);
    }
    // for FunctionalJournal
    public ObservableList<FunctionalJournal> getFunctionalJournal(int citizenId) throws SQLException {
        return functionalJournalDAO.getFunctionalJournal(citizenId);
    }
    public FunctionalJournal createFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException {
        return functionalJournalDAO.createFunctionalJournal(functionalJournal);
    }
    public void updateFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException {
        functionalJournalDAO.updateFunctionalJournal(functionalJournal);
    }

}
