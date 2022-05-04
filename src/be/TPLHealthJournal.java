package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TPLHealthJournal {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty tplCitizenId = new SimpleIntegerProperty();
    private final StringProperty condition = new SimpleStringProperty();
    private final StringProperty lastUpdate = new SimpleStringProperty();
    private final StringProperty evaluation = new SimpleStringProperty();
    private final StringProperty relevancy = new SimpleStringProperty();
    private final StringProperty note = new SimpleStringProperty();
    private final StringProperty expectation = new SimpleStringProperty();

    public TPLHealthJournal(int id, int tplCitizenId, String condition, String lastUpdate, String evalution, String relevancy, String note, String expectation){
        setId(id);
        setTplCitizenId(tplCitizenId);
        setCondition(condition);
        setLastUpdate(lastUpdate);
        setEvaluation(evalution);
        setRelevancy(relevancy);
        setNote(note);
        setExpectation(expectation);
    }

    public void setId(int id){
        this.id.set(id);
    }

    public void setTplCitizenId(int tplCitizenId){
        this.tplCitizenId.set(tplCitizenId);
    }
    public void setCondition(String condition){
        this.condition.set(condition);
    }
    public void setLastUpdate(String lastUpdate){
        this.lastUpdate.set(lastUpdate);
    }
    public void setEvaluation(String evaluation){
        this.evaluation.set(evaluation);
    }
    public void setRelevancy(String relevancy){
        this.relevancy.set(relevancy);
    }
    public void setNote(String note){
        this.note.set(note);
    }
    public void setExpectation(String expectation){
        this.expectation.set(expectation);
    }

    public int getTplCitizenId() {
        return tplCitizenId.get();
    }

    public int getId() {
        return id.get();
    }

    public String getCondition() {
        return condition.get();
    }

    public String getEvaluation() {
        return evaluation.get();
    }

    public String getExpectation() {
        return expectation.get();
    }

    public String getLastUpdate() {
        return lastUpdate.get();
    }

    public String getNote() {
        return note.get();
    }

    public String getRelevancy() {
        return relevancy.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty tplCitizenIdProperty() {
        return tplCitizenId;
    }

    public StringProperty conditionProperty() {
        return condition;
    }

    public StringProperty evaluationProperty() {
        return evaluation;
    }

    public StringProperty expectationProperty() {
        return expectation;
    }

    public StringProperty lastUpdateProperty() {
        return lastUpdate;
    }

    public StringProperty noteProperty() {
        return note;
    }

    public StringProperty relevancyProperty() {
        return relevancy;
    }

}
