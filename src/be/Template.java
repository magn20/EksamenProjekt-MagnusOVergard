package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Template {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty schoolId = new SimpleIntegerProperty();
    private final StringProperty fName = new SimpleStringProperty();
    private final StringProperty lName = new SimpleStringProperty();
    private final StringProperty age = new SimpleStringProperty();

    public Template(int id, int schoolId, String fName, String lName, String age){
        setid(id);
        setSchoolId(schoolId);
        setFName(fName);
        setlName(lName);
        setAge(age);

    }

    public void setid(int id){
        this.id.set(id);
    }
    public void setSchoolId(int schoolId){
        this.schoolId.set(schoolId);
    }
    public void setFName(String fName){
        this.fName.set(fName);
    }
    public void setlName(String lName){
        this.lName.set(lName);
    }
    public void setAge(String age){
        this.age.set(age);
    }
    public int getId() {
        return id.get();
    }
    public int getSchoolId() {
        return schoolId.get();
    }
    public String getfName() {
        return fName.get();
    }
    public String getlName() {
        return lName.get();
    }
    public String getAge() {
        return age.get();
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

    public StringProperty ageProperty() {
        return age;
    }
}
