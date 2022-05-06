package dal.interfaces;

import be.Citizen;
import be.Student;
import be.Template;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ICitizen {
    public List<Citizen> getCitizen(int schoolId) throws SQLException;
    public Citizen createCitizen(Citizen citizen) throws SQLException;
    public void updateCitizen(Citizen citizen) throws SQLException;
    public boolean removeCitizen(Citizen citizen) throws SQLException;
    public ObservableList<Citizen> getCitizenForSchool(int studentID) throws SQLException;
    public boolean setStudentWorkOnCitizen(Citizen citizen, Student student) throws SQLException;
}
