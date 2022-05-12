package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FunctionJournalObservation {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty citizenId = new SimpleIntegerProperty();
    private final StringProperty observation = new SimpleStringProperty();

    public FunctionJournalObservation(int id, int citizenId, String observation){
        this.id.set(id);
        setCitizenId(citizenId);
        setObservation(observation);
    }

    public void setCitizenId(int citizenId){
        this.citizenId.set(citizenId);
    }
    public void setObservation(String observation){
        this.observation.set(observation);
    }

    public int getCitizenId() {
        return citizenId.get();
    }

    public int getId() {
        return id.get();
    }

    public String getObservation() {
        return observation.get();
    }
}
