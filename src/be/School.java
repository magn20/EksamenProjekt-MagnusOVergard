package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class School {

        private final IntegerProperty id = new SimpleIntegerProperty();
        private final StringProperty name = new SimpleStringProperty();

        public School(int id, String name){
            setId(id);
            setName(name);
        }

    public String getName() {
        return name.get();
    }

    public int getId() {
        return id.get();
    }
    public final void setName(String name) {
        this.name.set(name);
    }

    public final void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }
}
