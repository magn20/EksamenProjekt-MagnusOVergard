package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Teacher {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty schoolId = new SimpleIntegerProperty();
    private final StringProperty fName = new SimpleStringProperty();
    private final StringProperty lName = new SimpleStringProperty();

    public Teacher(int id, int schoolId, String fName, String lName){

        setId(id);
        setSchoolId(schoolId);
        setFName(fName);
        setLName(lName);

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
}
