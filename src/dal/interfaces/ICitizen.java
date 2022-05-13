package dal.interfaces;

import be.Citizen;
import be.Student;
import be.Template;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ICitizen {
    public List<Citizen> getCitizen(int schoolId) throws SQLException, IOException;
    public Citizen createCitizen(Citizen citizen) throws SQLException, IOException;
    public void updateCitizen(Citizen citizen) throws SQLException, IOException;
    public boolean removeCitizen(Citizen citizen) throws SQLException, IOException;
    public ObservableList<Citizen> getCitizenForStudent(int studentID) throws SQLException, IOException;
    public boolean setStudentWorkOnCitizen(Citizen citizen, Student student) throws SQLException, IOException;
}
