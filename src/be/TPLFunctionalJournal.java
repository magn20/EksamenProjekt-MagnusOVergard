package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TPLFunctionalJournal {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty tplCitizenId = new SimpleIntegerProperty();
    private final StringProperty condition = new SimpleStringProperty();
    private final StringProperty lastUpdate = new SimpleStringProperty();
    private final StringProperty relevancy = new SimpleStringProperty();

    private final StringProperty niveau = new SimpleStringProperty();
    private final StringProperty note = new SimpleStringProperty();
    private final StringProperty expectation = new SimpleStringProperty();

    private final StringProperty execution = new SimpleStringProperty();
    private final StringProperty executionLimits = new SimpleStringProperty();
    private final StringProperty citizenExpectation = new SimpleStringProperty();


    public TPLFunctionalJournal(int id, int tplCitizenId, String condition, String lastUpdate, String niveau, String relevancy, String note, String expectation, String execution, String executionLimits, String citizenExpectation){
        setId(id);
        setTplCitizenId(tplCitizenId);
        setCondition(condition);
        setLastUpdate(lastUpdate);
        setNiveau(niveau);
        setRelevancy(relevancy);
        setNote(note);
        setExpectation(expectation);
        setExecution(execution);
        setExecutionLimits(executionLimits);
        setCitizenExpectation(citizenExpectation);
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
    public void setNiveau(String niveau){
        this.niveau.set(niveau);
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

    public void setExecution(String execution){
        this.execution.set(execution);
    }public void setExecutionLimits(String executionLimits){
        this.executionLimits.set(executionLimits);
    }public void setCitizenExpectation(String citizenExpectation){
        this.citizenExpectation.set(citizenExpectation);
    }

    public String getCitizenExpectation() {
        return citizenExpectation.get();
    }
    public String getExecution() {
        return execution.get();
    }
    public String getExecutionLimits() {
        return executionLimits.get();
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

    public String getNiveau() {
        return niveau.get();
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

    public IntegerProperty tplCitizenIdProperty() {
        return tplCitizenId;
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty relevancyProperty() {
        return relevancy;
    }

    public StringProperty conditionProperty() {
        return condition;
    }

    public StringProperty noteProperty() {
        return note;
    }

    public StringProperty lastUpdateProperty() {
        return lastUpdate;
    }

    public StringProperty expectationProperty() {
        return expectation;
    }

    public StringProperty citizenExpectationProperty() {
        return citizenExpectation;
    }

    public StringProperty executionLimitsProperty() {
        return executionLimits;
    }

    public StringProperty executionProperty() {
        return execution;
    }

    public StringProperty niveauProperty() {
        return niveau;
    }
}
