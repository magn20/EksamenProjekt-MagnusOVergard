package bll;

import be.*;
import dal.db.*;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class CitizenFacade {

    private CitizenDAO citizenDAO;
    private GeneralInfoDAO generalInfoDAO;
    private HealthJournalDAO healthJournalDAO;
    private FunctionalJournalDAO functionalJournalDAO;
    private HealthJournalObservationDao healthJournalObservationDao;
    private FunctionJournalObservationDao functionJournalObservationDao;
    {
        //create instances of dao classes
        healthJournalDAO = new HealthJournalDAO();
        citizenDAO = new CitizenDAO();
        generalInfoDAO = new GeneralInfoDAO();
        functionalJournalDAO = new FunctionalJournalDAO();
        healthJournalObservationDao = new HealthJournalObservationDao();
        functionJournalObservationDao = new FunctionJournalObservationDao();
    }


    // for our Citizens

    public ObservableList<Citizen> getCitizen(int schoolId) throws SQLException, IOException {
        return citizenDAO.getCitizen(schoolId);
    }
    public ObservableList<Citizen> getCitizenForStudent(int studentId) throws SQLException, IOException {
        return citizenDAO.getCitizenForStudent(studentId);
    }

    public boolean setStudentWorksOnCitizen(Citizen citizen, Student student) throws SQLException, IOException {
       return citizenDAO.setStudentWorkOnCitizen(citizen, student);
    }

    public Citizen createCitizen(Citizen citizen) throws SQLException, IOException {
        return citizenDAO.createCitizen(citizen);
    }
    public boolean removeCitizen(Citizen citizen) throws SQLException, IOException {
        return citizenDAO.removeCitizen(citizen);
    }
    public void updateCitizen(Citizen citizen) throws SQLException, IOException {
        citizenDAO.updateCitizen(citizen);}

    // for GeneralInfo
    public ObservableList<GeneralInfo> getGeneralInfo(int citizenID) throws SQLException, IOException {
         return generalInfoDAO.getGeneralInfo(citizenID);
    }
    public GeneralInfo createGeneralInfo(GeneralInfo generalInfo) throws SQLException, IOException {
       return generalInfoDAO.createGeneralInfo(generalInfo);
    }
    public void updateGeneralInfo(GeneralInfo generalInfo) throws SQLException, IOException {
        generalInfoDAO.updateGeneralInfo(generalInfo);
    }

    // for HealthJournal
    public ObservableList<HealthJournal> getHealthJournal(int citizenId) throws SQLException, IOException {
        return healthJournalDAO.getHealthJournal(citizenId);
    }
    public HealthJournal createHealthJournal(HealthJournal healthJournal) throws SQLException, IOException {
        return healthJournalDAO.createHealthJournal(healthJournal);
    }
    public void updateHealthJournal(HealthJournal healthJournal) throws SQLException, IOException {
        healthJournalDAO.updateHealthJournal(healthJournal);
    }

    // for Health Journal Observations
    public HealthJournalObservation getHealthJournalObservation(int citizenId) throws SQLException, IOException {
        return healthJournalObservationDao.getHealthJournalObservation(citizenId);
    }

    public HealthJournalObservation createHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException, IOException {
        return healthJournalObservationDao.createHealthJournalObservation(healthJournalObservation);
    }

    public void updateHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException, IOException {
        healthJournalObservationDao.updateHealthJournalObservation(healthJournalObservation);
    }



    // for FunctionalJournal
    public ObservableList<FunctionalJournal> getFunctionalJournal(int citizenId) throws SQLException, IOException {
        return functionalJournalDAO.getFunctionalJournal(citizenId);
    }
    public FunctionalJournal createFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException, IOException {
        return functionalJournalDAO.createFunctionalJournal(functionalJournal);
    }
    public void updateFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException, IOException {
        functionalJournalDAO.updateFunctionalJournal(functionalJournal);
    }

    // for FunctionalJournalObservation

    public FunctionJournalObservation getFunctionJournalObservation(int citizenId) throws SQLException, IOException {
        return functionJournalObservationDao.getFunctionJournalObservation(citizenId);
    }

    public FunctionJournalObservation createFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException, IOException {
        return functionJournalObservationDao.createFunctionJournalObservation(functionJournalObservation);
    }

    public void updateFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException, IOException {
        functionJournalObservationDao.updateFunctionJournalObservation(functionJournalObservation);
    }


}
