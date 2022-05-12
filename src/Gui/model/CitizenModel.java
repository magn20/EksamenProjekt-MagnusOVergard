package Gui.model;

import be.*;
import bll.CitizenFacade;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class CitizenModel {

    CitizenFacade citizenFacade = new CitizenFacade();


    // for our Citizens

    public ObservableList<Citizen> getCitizen(int schoolId) throws SQLException {
        return citizenFacade.getCitizen(schoolId);
    }
    public ObservableList<Citizen> getCitizenForStudent(int studentId) throws SQLException {
        return citizenFacade.getCitizenForStudent(studentId);
    }

    public boolean SetStudentWorksOnCitizen(Citizen citizen, Student student) throws SQLException {
        return citizenFacade.setStudentWorksOnCitizen(citizen, student);
    }

    public Citizen createCitizen(Citizen citizen) throws SQLException {
        return citizenFacade.createCitizen(citizen);
    }
    public boolean removeCitizen(Citizen citizen) throws SQLException {
        return citizenFacade.removeCitizen(citizen);
    }
    public void updateCitizen(Citizen citizen) throws SQLException {
        citizenFacade.updateCitizen(citizen);}

    // for GeneralInfo
    public ObservableList<GeneralInfo> getGeneralInfo(int citizenID) throws SQLException {
        return citizenFacade.getGeneralInfo(citizenID);
    }
    public GeneralInfo createGeneralInfo(GeneralInfo generalInfo) throws SQLException {
        return citizenFacade.createGeneralInfo(generalInfo);
    }
    public void updateGeneralInfo(GeneralInfo generalInfo) throws SQLException {
        citizenFacade.updateGeneralInfo(generalInfo);
    }
    public boolean removeGeneralInfo(GeneralInfo generalInfo) throws SQLException {
        return citizenFacade.removeGeneralInfo(generalInfo);
    }

    // for HealthJournal
    public ObservableList<HealthJournal> getHealthJournal(int citizenId) throws SQLException {
        return citizenFacade.getHealthJournal(citizenId);
    }
    public HealthJournal createHealthJournal(HealthJournal healthJournal) throws SQLException {
        return citizenFacade.createHealthJournal(healthJournal);
    }
    public void updateHealthJournal(HealthJournal healthJournal) throws SQLException {
        citizenFacade.updateHealthJournal(healthJournal);
    }

    // for Health Journal Observations
    public HealthJournalObservation getHealthJournalObservation(int citizenId) throws SQLException {
        return citizenFacade.getHealthJournalObservation(citizenId);
    }

    public HealthJournalObservation createHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException {
        return citizenFacade.createHealthJournalObservation(healthJournalObservation);
    }

    public void updateHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException {
        citizenFacade.updateHealthJournalObservation(healthJournalObservation);
    }




    // for FunctionalJournal
    public ObservableList<FunctionalJournal> getFunctionalJournal(int citizenId) throws SQLException {
        return citizenFacade.getFunctionalJournal(citizenId);
    }
    public FunctionalJournal createFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException {
        return citizenFacade.createFunctionalJournal(functionalJournal);
    }
    public void updateFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException {
        citizenFacade.updateFunctionalJournal(functionalJournal);
    }
}
