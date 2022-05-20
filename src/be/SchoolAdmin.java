package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SchoolAdmin {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty schoolId = new SimpleIntegerProperty();
    private final StringProperty fName = new SimpleStringProperty();
    private final StringProperty lName = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();

    public SchoolAdmin(int id, int schoolId, String fName, String lName, String username, String password){
        setId(id);
        setSchoolId(schoolId);
        setFName(fName);
        setLName(lName);
        setPassword(password);
        setUsername(username);


    }
    public final void setFName(String fName) {this.fName.set(fName);}

    public final void setLName(String lName) {this.lName.set(lName);}

    public final void setId(int id) {this.id.set(id);}

    public final void setSchoolId(int schoolId) {this.schoolId.set(schoolId);}

    public int getId() {
        return id.get();
    }

    public int getSchoolId() {
        return schoolId.get();
    }

    public String getFName() {
        return fName.get();
    }

    public String getLName() {
        return lName.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty schoolIdProperty() {
        return schoolId;
    }

    public StringProperty fNameProperty() {
        return fName;
    }

    public StringProperty lNameProperty() {
        return lName;
    }

    public void setUsername(String username){
        this.username.set(username);
    }
    public void setPassword(String password){
        this.password.set(password);
    }

    public String getPassword() {
        return password.get();
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }
    public StringProperty passwordProperty() {
        return password;
    }
}
