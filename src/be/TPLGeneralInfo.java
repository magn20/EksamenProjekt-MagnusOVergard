package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TPLGeneralInfo {


    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty tplCitizenId = new SimpleIntegerProperty();
    private final StringProperty coping = new SimpleStringProperty();
    private final StringProperty motivation = new SimpleStringProperty();
    private final StringProperty resources = new SimpleStringProperty();
    private final StringProperty roles = new SimpleStringProperty();
    private final StringProperty habits = new SimpleStringProperty();
    private final StringProperty educationAndJob = new SimpleStringProperty();
    private final StringProperty lifeStory = new SimpleStringProperty();
    private final StringProperty healthInformation = new SimpleStringProperty();
    private final StringProperty equipmentAids = new SimpleStringProperty();
    private final StringProperty homeLayout = new SimpleStringProperty();
    private final StringProperty network = new SimpleStringProperty();

    public TPLGeneralInfo(int id, int tplCitizenId, String coping, String motivation, String resources,
                          String roles, String habits, String educationAndJob,
                          String lifeStory, String healthInformation, String equipmentAids,
                          String homeLayout, String network ){
        setId(id);
        setTplCitizenId(tplCitizenId);
        setCoping(coping);
        setMotivation(motivation);
        setResources(resources);
        setRoles(roles);
        setHabits(habits);
        setEducationAndJob(educationAndJob);
        setLifeStory(lifeStory);
        setHealthInformation(healthInformation);
        setEquipmentAids(equipmentAids);
        setHomeLayout(homeLayout);
        setNetwork(network);
    }

    public void setId(int id){
        this.id.set(id);
    }
    public void setTplCitizenId(int tplCitizenId){
        this.tplCitizenId.set(tplCitizenId);
    }
    public void setNetwork(String network){
        this.network.set(network);
    }
    public void setHomeLayout(String homeLayout){
        this.homeLayout.set(homeLayout);
    }
    public void setEquipmentAids(String equipmentAids){
        this.equipmentAids.set(equipmentAids);
    }
    public void setHealthInformation(String healthInformation){
        this.healthInformation.set(healthInformation);
    }
    public void setLifeStory(String lifeStory){
        this.lifeStory.set(lifeStory);
    }
    public void setEducationAndJob(String educationAndJob){
        this.educationAndJob.set(educationAndJob);
    }

    public void setHabits(String habits){
        this.habits.set(habits);
    }
    public void setRoles(String roles){
        this.roles.set(roles);
    }
    public void setResources(String resources){
        this.resources.set(resources);
    }
    public void setMotivation(String motivation){
        this.motivation.set(motivation);
    }
    public void setCoping(String coping){
        this.coping.set(coping);
    }

    public int getId() {
        return id.get();
    }

    public int getTplCitizenId() {
        return tplCitizenId.get();
    }

    public String getCoping() {
        return coping.get();
    }

    public String getMotivation() {
        return motivation.get();
    }

    public String getEducationAndJob() {
        return educationAndJob.get();
    }

    public String getEquipmentAids() {
        return equipmentAids.get();
    }

    public String getHabits() {
        return habits.get();
    }

    public String getHealthInformation() {
        return healthInformation.get();
    }

    public String getHomeLayout() {
        return homeLayout.get();
    }

    public String getLifeStory() {
        return lifeStory.get();
    }

    public String getNetwork() {
        return network.get();
    }

    public String getResources() {
        return resources.get();
    }

    public String getRoles() {
        return roles.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty tplCitizenIdProperty() {
        return tplCitizenId;
    }

    public StringProperty copingProperty() {
        return coping;
    }

    public StringProperty educationAndJobProperty() {
        return educationAndJob;
    }

    public StringProperty motivationProperty() {
        return motivation;
    }

    public StringProperty equipmentAidsProperty() {
        return equipmentAids;
    }

    public StringProperty habitsProperty() {
        return habits;
    }

    public StringProperty healthInformationProperty() {
        return healthInformation;
    }

    public StringProperty homeLayoutProperty() {
        return homeLayout;
    }

    public StringProperty lifeStoryProperty() {
        return lifeStory;
    }

    public StringProperty networkProperty() {
        return network;
    }

    public StringProperty resourcesProperty() {
        return resources;
    }

    public StringProperty rolesProperty() {
        return roles;
    }
}
