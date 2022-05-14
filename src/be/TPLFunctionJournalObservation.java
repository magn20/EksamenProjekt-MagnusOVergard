package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TPLFunctionJournalObservation {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty templateId = new SimpleIntegerProperty();
    private final StringProperty observation = new SimpleStringProperty();

    public TPLFunctionJournalObservation(int id, int templateId, String observation){
        this.id.set(id);
        setTemplateId(templateId);
        setObservation(observation);
    }

    public void setTemplateId(int templateId){
        this.templateId.set(templateId);
    }
    public void setObservation(String observation){
        this.observation.set(observation);
    }

    public int getTemplateId() {
        return templateId.get();
    }

    public int getId() {
        return id.get();
    }

    public String getObservation() {
        return observation.get();
    }
}
