package Gui.model;

import be.*;
import bll.CitizenFacade;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class CitizenModel {

    CitizenFacade citizenFacade = new CitizenFacade();


    // for our Citizens

    public ObservableList<Citizen> getCitizen(int schoolId) throws SQLException, IOException {
        return citizenFacade.getCitizen(schoolId);
    }
    public ObservableList<Citizen> getCitizenForStudent(int studentId) throws SQLException, IOException {
        return citizenFacade.getCitizenForStudent(studentId);
    }

    public boolean SetStudentWorksOnCitizen(Citizen citizen, Student student) throws SQLException, IOException {
        return citizenFacade.setStudentWorksOnCitizen(citizen, student);
    }

    public Citizen createCitizen(Citizen citizen) throws SQLException, IOException {
        return citizenFacade.createCitizen(citizen);
    }
    public boolean removeCitizen(Citizen citizen) throws SQLException, IOException {
        return citizenFacade.removeCitizen(citizen);
    }
    public void updateCitizen(Citizen citizen) throws SQLException, IOException {
        citizenFacade.updateCitizen(citizen);}

    // for GeneralInfo
    public ObservableList<GeneralInfo> getGeneralInfo(int citizenID) throws SQLException, IOException {
        return citizenFacade.getGeneralInfo(citizenID);
    }
    public GeneralInfo createGeneralInfo(GeneralInfo generalInfo) throws SQLException, IOException {
        return citizenFacade.createGeneralInfo(generalInfo);
    }
    public void updateGeneralInfo(GeneralInfo generalInfo) throws SQLException, IOException {
        citizenFacade.updateGeneralInfo(generalInfo);
    }

    // for HealthJournal
    public ObservableList<HealthJournal> getHealthJournal(int citizenId) throws SQLException, IOException {
        return citizenFacade.getHealthJournal(citizenId);
    }
    public HealthJournal createHealthJournal(HealthJournal healthJournal) throws SQLException, IOException {
        return citizenFacade.createHealthJournal(healthJournal);
    }
    public void updateHealthJournal(HealthJournal healthJournal) throws SQLException, IOException {
        citizenFacade.updateHealthJournal(healthJournal);
    }

    // for Health Journal Observations
    public HealthJournalObservation getHealthJournalObservation(int citizenId) throws SQLException, IOException {
        return citizenFacade.getHealthJournalObservation(citizenId);
    }

    public HealthJournalObservation createHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException, IOException {
        return citizenFacade.createHealthJournalObservation(healthJournalObservation);
    }

    public void updateHealthJournalObservation(HealthJournalObservation healthJournalObservation) throws SQLException, IOException {
        citizenFacade.updateHealthJournalObservation(healthJournalObservation);
    }




    // for FunctionalJournal
    public ObservableList<FunctionalJournal> getFunctionalJournal(int citizenId) throws SQLException, IOException {
        return citizenFacade.getFunctionalJournal(citizenId);
    }
    public FunctionalJournal createFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException, IOException {
        return citizenFacade.createFunctionalJournal(functionalJournal);
    }
    public void updateFunctionalJournal(FunctionalJournal functionalJournal) throws SQLException, IOException {
        citizenFacade.updateFunctionalJournal(functionalJournal);
    }

    // for FunctionalJournalObservation
    public FunctionJournalObservation getFunctionJournalObservation(int citizenId) throws SQLException, IOException {
        return citizenFacade.getFunctionJournalObservation(citizenId);
    }

    public FunctionJournalObservation createFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException, IOException {
        return citizenFacade.createFunctionJournalObservation(functionJournalObservation);
    }

    public void updateFunctionJournalObservation(FunctionJournalObservation functionJournalObservation) throws SQLException, IOException {
        citizenFacade.updateFunctionJournalObservation(functionJournalObservation);
    }

}
