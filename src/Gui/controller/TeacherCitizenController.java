package Gui.controller;

import Gui.model.CitizenModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.*;
import bll.utill.DisplayMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class TeacherCitizenController implements Initializable {



    // used  for all tabs
    @FXML
    private Label lblTemplate;
    @FXML
    private Label lblStatus;


    // Only for HealthFunction
    @FXML
    private ComboBox cbMainHealthCategory;
    @FXML
    private ComboBox cbUnderHealthCategory;
    @FXML
    private ComboBox cbExpectation;


    @FXML
    private TextArea txtNote;
    @FXML
    private TextArea txtEvaluation;
    @FXML
    private Label lblLastUpdate;

    @FXML
    private Label txtCondition;
    // checkbox
    @FXML
    private CheckBox checkboxMaybe;
    @FXML
    private CheckBox checkboxActive;
    @FXML
    private CheckBox checkboxNotRelavant;

    //tableview
    @FXML
    private TableView<HealthJournal> tvTPLHealthJournal;
    @FXML
    private TableColumn<HealthJournal, String> tcCondition;
    @FXML
    private TableColumn<HealthJournal, String> tcRelanacy;
    @FXML
    private TableColumn<HealthJournal, String> tcEvaluation;
    @FXML
    private TableColumn<HealthJournal, String> tcExpactation;
    @FXML
    private TableColumn<HealthJournal, String> tcNote;
    @FXML
    private TableColumn<HealthJournal, String> tcLastUpdate;


    // only for General Information tab
    @FXML
    private TextArea txtCoping;
    @FXML
    private TextArea txtLifestory;
    @FXML
    private TextArea txtMovtivation;
    @FXML
    private TextArea txtHouseLayout;
    @FXML
    private TextArea txtEquitmentAids;
    @FXML
    private TextArea txtHabits;
    @FXML
    private TextArea txtRoles;
    @FXML
    private TextArea txtNetwork;
    @FXML
    private TextArea txtHealthInfo;
    @FXML
    private TextArea txtResources;
    @FXML
    private TextArea txtEducationAndJob;


    // For Functional Journal
    @FXML
    private Button btnCooking;
    @FXML
    private Button btnHouseWork;
    @FXML
    private Button btnDailyRoutines;
    @FXML
    private Button btnShopping;
    @FXML
    private Button btnCarry;
    @FXML
    private Button btnMoveAround;
    @FXML
    private Button btnTransport;
    @FXML
    private Button btnMoveInDiffrentAreas;
    @FXML
    private Button btnMove;
    @FXML
    private Button btnChangePosition;
    @FXML
    private Button btnMuscle;
    @FXML
    private Button btnWalk;
    @FXML
    private Button btnEndurance;
    @FXML
    private Button btnApplyIt;
    @FXML
    private Button btnMemory;
    @FXML
    private Button btnOrientationAbility;
    @FXML
    private Button btnCognitiveFunctions;
    @FXML
    private Button btnEmotion;
    @FXML
    private Button btnEnergy;
    @FXML
    private Button btnSkills;
    @FXML
    private Button btnProblemSolve;
    @FXML
    private Button btnPaidWork;
    @FXML
    private Button btnBath;
    @FXML
    private Button btnPersonalCare;
    @FXML
    private Button btnGettingDressed;
    @FXML
    private Button btnDrinking;
    @FXML
    private Button btnFoodIntake;
    @FXML
    private Button btnEating;
    @FXML
    private Button btnTakeCareOfOwnHealth;
    @FXML
    private Button btnUseToilet;

    @FXML
    private TextArea txtNoteFunction;
    @FXML
    private TextArea txtCitizenExpecationFunction;

    @FXML
    private ComboBox cbExecutionFunction;
    @FXML
    private ComboBox cbExecutionLimitsFunction;
    @FXML
    private ComboBox cbNiveauFunction;
    @FXML
    private ComboBox cbExpectedFunction;

    @FXML
    private CheckBox checkboxFunctionNotActive;
    @FXML
    private CheckBox checkboxFunctionActive;
    @FXML
    private Label lblFunctionLastUpdate;



    private String functionConditionString;
    private ObservableList<String> MainCategory;
    private ObservableList<GeneralInfo> generalInfos;
    private ObservableList<HealthJournal> healthJournals;
    private ObservableList<FunctionalJournal> functionalJournals;

    SingletonUser singletonUser = SingletonUser.getInstance();

    CitizenModel citizenModel;
    Citizen citizen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        citizen = singletonUser.getCitizen();
        citizenModel = new CitizenModel();
        generalInfos = FXCollections.observableArrayList();
        MainCategory = FXCollections.observableArrayList();
        healthJournals = FXCollections.observableArrayList();
        functionalJournals = FXCollections.observableArrayList();
        functionConditionString = "";

        // sets up the combobox for TPLHealthJournal.
        setComboboxMainHealth();
        setComboboxExpectation();

        // gets healthJournals from one specific citizen
        try {
            getHealthJournals();
        } catch (SQLException e) {
            e.printStackTrace();
            DisplayMessage.displayError(e);
        }
        //set up the citizen with all healthJournals from that citizen.
        setupTableviewTPLHealthJournal();

        // Function ComboBox Setup
        setupComboboxForFunctionJournal();

        // gets the Templated that is selected from teacher screen.
        citizen = singletonUser.getCitizen();

        // sets the text to display which Template "Omsorgs Journal" is open
        lblTemplate.setText("Borger: "+ citizen.getfName() + " " + citizen.getlName() + " Borger ID: " + citizen.getId());



        // sets all the general info into the General Information page.
        try {
            if (!citizenModel.getGeneralInfo(singletonUser.getCitizen().getId()).isEmpty()){
                generalInfos.addAll(citizenModel.getGeneralInfo(citizen.getId()));
                setTextFieldsForGeneralInfo(generalInfos.get(0));
            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }


    /**
     * sets up the tableview with all TPLHealthJournal objects that one specific Template has.
     */
    public void setupTableviewTPLHealthJournal(){
        tcCondition.setCellValueFactory(cellData -> cellData.getValue().conditionProperty());
        tcEvaluation.setCellValueFactory(cellData -> cellData.getValue().conditionProperty());
        tcExpactation.setCellValueFactory(cellData -> cellData.getValue().expectationProperty());
        tcLastUpdate.setCellValueFactory(cellData -> cellData.getValue().lastUpdateProperty());
        tcNote.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
        tcRelanacy.setCellValueFactory(cellData -> cellData.getValue().relevancyProperty());

        tvTPLHealthJournal.setItems(healthJournals);


    }



    /**
     * closes the stage
     * @param actionEvent
     */
    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * saves the general information to database.
     * @param actionEvent
     * @throws SQLException
     */
    public void onSaveGeneralInfoBtn(ActionEvent actionEvent) throws SQLException {

        if (citizenModel.getGeneralInfo(citizen.getId()).isEmpty()){
            GeneralInfo generalInfo = new GeneralInfo(-1, citizen.getId(), txtCoping.getText(), txtMovtivation.getText(), txtResources.getText(), txtRoles.getText(), txtHabits.getText(), txtEducationAndJob.getText(), txtLifestory.getText(), txtHealthInfo.getText(), txtEquitmentAids.getText(), txtHouseLayout.getText(), txtNetwork.getText());
            citizenModel.createGeneralInfo(generalInfo);
        }else{

            generalInfos.addAll(citizenModel.getGeneralInfo(citizen.getId()));
            GeneralInfo generalInfo = generalInfos.get(0);

            generalInfo.setCitizenId(citizen.getId());
            generalInfo.setNetwork(txtNetwork.getText());
            generalInfo.setHomeLayout(txtHouseLayout.getText());
            generalInfo.setHealthInformation(txtHealthInfo.getText());
            generalInfo.setEquipmentAids(txtEquitmentAids.getText());
            generalInfo.setLifeStory(txtLifestory.getText());
            generalInfo.setHabits(txtHabits.getText());
            generalInfo.setRoles(txtRoles.getText());
            generalInfo.setResources(txtResources.getText());
            generalInfo.setEducationAndJob(txtEducationAndJob.getText());
            generalInfo.setMotivation(txtMovtivation.getText());
            generalInfo.setCoping(txtCoping.getText());

            citizenModel.updateGeneralInfo(generalInfo);
            setTextFieldsForGeneralInfo(generalInfo);
        }

        lblStatus.setText("Status: " + "Updated Generelle Informationer");
    }

    /**
     * sets the textfields up with data from template general information
     * @param generalInfo the object.
     */
    public void setTextFieldsForGeneralInfo(GeneralInfo generalInfo){
        txtCoping.setText(generalInfo.getCoping());
        txtEducationAndJob.setText(generalInfo.getEducationAndJob());
        txtEquitmentAids.setText(generalInfo.getCoping());
        txtHabits.setText(generalInfo.getHabits());
        txtHealthInfo.setText(generalInfo.getHealthInformation());
        txtHouseLayout.setText(generalInfo.getHomeLayout());
        txtLifestory.setText(generalInfo.getLifeStory());
        txtMovtivation.setText(generalInfo.getMotivation());
        txtNetwork.setText(generalInfo.getNetwork());
        txtResources.setText(generalInfo.getResources());
        txtRoles.setText(generalInfo.getRoles());
    }


    /**
     * imitates a shift og control on a textarea, by hitting tab or enter key.
     * @param event
     */
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.TAB && !event.isShiftDown() && !event.isControlDown() || code == KeyCode.ENTER && !event.isShiftDown() && !event.isControlDown()) {
            event.consume();
            Node node = (Node) event.getSource();
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyCode.CONTROL.getCode());
                robot.keyPress(KeyCode.TAB.getCode());
                robot.delay(10);
                robot.keyRelease(KeyCode.TAB.getCode());
                robot.keyRelease(KeyCode.CONTROL.getCode());
            }
            catch (AWTException e) { }
        }
    }


    /**
     * sets up main Health journal combobox
     */
    public void setComboboxMainHealth(){

        MainCategory.add("Funktionsniveau");
        MainCategory.add("Bevægeapperat");
        MainCategory.add("Ernæring");
        MainCategory.add("Hud og Slimhinder");
        MainCategory.add("Kommunikation");
        MainCategory.add("Psykosociale Forhold");
        MainCategory.add("Respiration og Cirkulation");
        MainCategory.add("Seksualitet");
        MainCategory.add("Smerter og Sanseindtryk");
        MainCategory.add("Søvn og Hvile");
        MainCategory.add("Viden og Udvikling");
        MainCategory.add("Udskillelse af Affaldsstoffer");

        cbMainHealthCategory.setItems(MainCategory);
    }


    /**
     * puts the rightful information into the Combobox underCategoryForHealth
     * depending on the selection off MainHealthCategory combobox
     */
    public void selectUnderCategoryForHealth(){

        int selectMainHealthCombobox = cbMainHealthCategory.getSelectionModel().getSelectedIndex();

        cbUnderHealthCategory.getItems().clear();

        switch (selectMainHealthCombobox){

            case 0:
                cbUnderHealthCategory.getItems().add("Problemer med personlig pjele");
                cbUnderHealthCategory.getItems().add("Problemer med daglige aktiviteter");
                break;
            case 1:
                cbUnderHealthCategory.getItems().add("Problemer med mobilitet og bevægelse");
                break;
            case 2:
                cbUnderHealthCategory.getItems().add("Problemer med væskeindtag");
                cbUnderHealthCategory.getItems().add("Problemer med fødeindtag");
                cbUnderHealthCategory.getItems().add("Uhensigtsmæssig vægtændring");
                cbUnderHealthCategory.getItems().add("Problemer med overvægt");
                cbUnderHealthCategory.getItems().add("Problemer med undervægt");
                break;
            case 3:
                cbUnderHealthCategory.getItems().add("Problemer med kirurgisk sår");
                cbUnderHealthCategory.getItems().add("Problemer med Diabetisk sår");
                cbUnderHealthCategory.getItems().add("Problemer med cancer sår");
                cbUnderHealthCategory.getItems().add("Problemer med tryk sår");
                cbUnderHealthCategory.getItems().add("Problemer med arterielt sår");
                cbUnderHealthCategory.getItems().add("Problemer med venøst sår");
                cbUnderHealthCategory.getItems().add("Problemer med blandings sår");
                cbUnderHealthCategory.getItems().add("Problemer med traume sår");
                cbUnderHealthCategory.getItems().add("Andre problemer med hud og slimhinder");
                break;
            case 4:
                cbUnderHealthCategory.getItems().add("Problemer med kommunikation");
                break;
            case 5:
                cbUnderHealthCategory.getItems().add("Problemer med socialt samvær");
                cbUnderHealthCategory.getItems().add("Emotionelle problemer");
                cbUnderHealthCategory.getItems().add("Problemer med misbrug");
                cbUnderHealthCategory.getItems().add("Mentale problemer");
                break;
            case 6:
                cbUnderHealthCategory.getItems().add("Respirationsproblemer");
                cbUnderHealthCategory.getItems().add("Cirkulationsproblemer");
                break;
            case 7:
                cbUnderHealthCategory.getItems().add("Problemer med seksualitet");
                break;
            case 8:
                cbUnderHealthCategory.getItems().add("Akutte smerter");
                cbUnderHealthCategory.getItems().add("Periodevise smerter");
                cbUnderHealthCategory.getItems().add("Kroniske smerter");
                cbUnderHealthCategory.getItems().add("Problemer med synssans");
                cbUnderHealthCategory.getItems().add("Problemer med lugtesans");
                cbUnderHealthCategory.getItems().add("Problemer med hørelse");
                cbUnderHealthCategory.getItems().add("Problemer med smagssans");
                cbUnderHealthCategory.getItems().add("Problemer med følesans");
                break;
            case 9:
                cbUnderHealthCategory.getItems().add("Døgnrytmeproblemer");
                cbUnderHealthCategory.getItems().add("SøvnProblemer");
                break;
            case 10:
                cbUnderHealthCategory.getItems().add("Problemer med hukommelse");
                cbUnderHealthCategory.getItems().add("Problemer med indsigt i Behandlingsformål");
                cbUnderHealthCategory.getItems().add("Problemer med sygdomindsigt");
                cbUnderHealthCategory.getItems().add("Kognitive problemer");
                break;
            case 11:
                cbUnderHealthCategory.getItems().add("Problemer med vandladning");
                cbUnderHealthCategory.getItems().add("Problemer med urininkontinens");
                cbUnderHealthCategory.getItems().add("Problemer med afføringsinkontinens");
                cbUnderHealthCategory.getItems().add("Problemer problemer med mave og tarm");
                cbUnderHealthCategory.getItems().add("Problemer med væske fra dræn");
                break;

            default: break;
        }

    }


    public void onMainCategoryForHeatlhCb(ActionEvent actionEvent) {
        selectUnderCategoryForHealth();
    }

    public void onSaveHealthjournalBtn(ActionEvent actionEvent) throws SQLException {
        boolean hasSaved = false;
        Date date = new Date();

        if (cbUnderHealthCategory.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Vælg en helbredstilstand først");
        }else {



            getHealthJournals();

        for (HealthJournal healthJournal : healthJournals){
            if (cbUnderHealthCategory.getSelectionModel().getSelectedItem().equals(healthJournal.getCondition())){

                healthJournal.setNote(txtNote.getText());
                healthJournal.setExpectation((String) cbExpectation.getSelectionModel().getSelectedItem());
                healthJournal.setEvaluation(txtEvaluation.getText());
                healthJournal.setLastUpdate(date.toString());

                if (checkboxActive.isSelected()){
                    healthJournal.setRelevancy("Aktiv");
                }else if (checkboxMaybe.isSelected()){
                    healthJournal.setRelevancy("potentiel");
                }else{
                    healthJournal.setRelevancy("Ikke relavant");
                }

                citizenModel.updateHealthJournal(healthJournal);
                hasSaved = true;
                break;
            }
        }
        if (!hasSaved){
            String relavancy = "";
            if (checkboxActive.isSelected()){
                relavancy = "Aktiv";
            }else if (checkboxMaybe.isSelected()){
                relavancy = "potentiel";
            }else{
                relavancy = "Ikke relavant";
            }

            HealthJournal healthJournal = new HealthJournal(-1, citizen.getId(), (String) cbUnderHealthCategory.getSelectionModel().getSelectedItem(), date.toString(), txtEvaluation.getText(), relavancy, txtNote.getText(), (String) cbExpectation.getSelectionModel().getSelectedItem());
            citizenModel.createHealthJournal(healthJournal);
            }
        }

        getHealthJournals();
        setupTableviewTPLHealthJournal();
    }



    public void onSelectedHealthConditionCb(ActionEvent actionEvent) throws SQLException {
        getHealthJournals();
        boolean hasupdated = false;

        if (!healthJournals.isEmpty()){
            for (HealthJournal healthJournal: healthJournals){
                if(cbUnderHealthCategory.getSelectionModel().isEmpty()){

                } else if (cbUnderHealthCategory.getSelectionModel().getSelectedItem().equals(healthJournal.getCondition())){
                    updateHealthScreen(healthJournal);
                    hasupdated = true;
                    break;
                }
            }
        }
        if (!hasupdated){
            if(!cbUnderHealthCategory.getSelectionModel().isEmpty()){

                txtNote.setText("");
                txtEvaluation.setText("");
                cbExpectation.getSelectionModel().clearSelection();
                lblLastUpdate.setText("Sidst Opdateret den:");
                txtCondition.setText("Problem:" + cbUnderHealthCategory.getSelectionModel().getSelectedItem());
                checkboxActive.setSelected(false);
                checkboxNotRelavant.setSelected(false);
                checkboxMaybe.setSelected(false);
            }


        }
    }

    /**
     * get all TPLHealthJournals from one template.
     */
    private void getHealthJournals() throws SQLException {

        if (!citizenModel.getHealthJournal(citizen.getId()).isEmpty()){
            healthJournals.clear();
            healthJournals.addAll(citizenModel.getHealthJournal(citizen.getId()));
        }
    }

    /**
     * sets up the screen with data for the Health journal
     * @param healthJournal the object holding the data
     */
    private void updateHealthScreen(HealthJournal healthJournal){
        txtNote.setText(healthJournal.getNote());
        txtEvaluation.setText(healthJournal.getEvaluation());
        cbExpectation.getSelectionModel().select(healthJournal.getExpectation());
        lblLastUpdate.setText("Sidst Opdateret den: " + healthJournal.getLastUpdate());
        txtCondition.setText("Problem: " + healthJournal.getCondition());

        if (healthJournal.getRelevancy().equals("Aktiv")){
            checkboxActive.setSelected(true);
            checkboxNotRelavant.setSelected(false);
            checkboxMaybe.setSelected(false);
        }else if (healthJournal.getRelevancy().equals("Ikke relavant")){
            checkboxActive.setSelected(false);
            checkboxNotRelavant.setSelected(true);
            checkboxMaybe.setSelected(false);
        }else{
            checkboxActive.setSelected(false);
            checkboxNotRelavant.setSelected(false);
            checkboxMaybe.setSelected(true);
        }

    }

    /**
     * setup the combobox for expectation for health journal
     */
    public void setComboboxExpectation(){
        cbExpectation.getItems().add("Mindskes");
        cbExpectation.getItems().add("Forbliver uændret");
        cbExpectation.getItems().add("Forsvinder");
    }

    /**
     * When a condition for health journal is selected
     */
    public void onSlectetTPLHealthJournal(MouseEvent mouseEvent) {
        updateHealthScreen(tvTPLHealthJournal.getSelectionModel().getSelectedItem());
    }


    /**
     * Calls methods to update the screen of Function Journal, Depending on if there is a function journal for that condition
     * @param actionEvent on button pressed.
     */
    public void onFunctionJournalUpdateScreen(ActionEvent actionEvent) throws SQLException {
        getTPLFunctionsJournals();
        clearFunctionJournalView();

        boolean hasUpdate = false;

        if (!functionalJournals.isEmpty()){
            for (FunctionalJournal functionalJournal : functionalJournals){
                if (functionalJournal.getCondition().equals(getFunctionalCondition())){

                   updateFunctionJournalView(functionalJournal);
                    hasUpdate = true;
                    break;
                }

            }
        }

        if (!hasUpdate){
            clearFunctionJournalView();
        }
        lblStatus.setText(getFunctionalCondition());

    }

    /**
     * updates the View for Functions Journals
     * @param functionalJournal
     */
    public void updateFunctionJournalView(FunctionalJournal functionalJournal){
        cbNiveauFunction.getSelectionModel().select(functionalJournal.getNiveau());
        cbExecutionFunction.getSelectionModel().select(functionalJournal.getExecution());
        cbExecutionLimitsFunction.getSelectionModel().select(functionalJournal.getExecutionLimits());
        cbExpectedFunction.getSelectionModel().select(functionalJournal.getExpectation());
        txtNoteFunction.setText(functionalJournal.getNote());
        txtCitizenExpecationFunction.setText(functionalJournal.getCitizenExpectation());
        lblFunctionLastUpdate.setText(functionalJournal.getLastUpdate());
        lblStatus.setText("Status: " + functionalJournal.getCondition());

        if (functionalJournal.getRelevancy().equals("Aktiv")){
            checkboxFunctionNotActive.setSelected(false);
            checkboxFunctionActive.setSelected(true);
        }else {
            checkboxFunctionNotActive.setSelected(true);
            checkboxFunctionActive.setSelected(false);
        }
    }

    /**
     * clears the view for data
     */
    public void clearFunctionJournalView(){

        cbNiveauFunction.getSelectionModel().clearSelection();
        cbExecutionFunction.getSelectionModel().clearSelection();
        cbExecutionLimitsFunction.getSelectionModel().clearSelection();
        cbExpectedFunction.getSelectionModel().clearSelection();
        txtNoteFunction.setText("");
        txtCitizenExpecationFunction.setText("");
        checkboxFunctionActive.setSelected(false);
        checkboxFunctionNotActive.setSelected(false);
        lblFunctionLastUpdate.setText("Sidst opdateret:");

    }


    /**
     * checks which button is pressed in the list of Function Conditions
     * @return
     */
    public String getFunctionalCondition(){
        String condition = "";

        if (btnBath.isFocused()){
            condition = btnBath.getText();
        }else if(btnApplyIt.isFocused()){
            condition = btnApplyIt.getText();
        }else if(btnCarry.isFocused()){
            condition = btnCarry.getText();
        }else if(btnCognitiveFunctions.isFocused()){
            condition = btnCognitiveFunctions.getText();
        }else if(btnChangePosition.isFocused()){
            condition = btnChangePosition.getText();
        }else if(btnEmotion.isFocused()){
            condition = btnEmotion.getText();
        }else if(btnCooking.isFocused()){
            condition = btnCooking.getText();
        }else if(btnDailyRoutines.isFocused()){
            condition = btnDailyRoutines.getText();
        }else if(btnDrinking.isFocused()){
            condition = btnDrinking.getText();
        }else if(btnEating.isFocused()){
            condition = btnEating.getText();
        }else if(btnEndurance.isFocused()){
            condition = btnEndurance.getText() ;
        }else if(btnEnergy.isFocused()){
            condition = btnEnergy.getText();
        }else if(btnFoodIntake.isFocused()){
            condition = btnFoodIntake.getText();
        }else if(btnGettingDressed.isFocused()){
            condition = btnGettingDressed.getText();
        }else if(btnHouseWork.isFocused()){
            condition = btnHouseWork.getText();
        }else if(btnMove.isFocused()){
            condition = btnMove.getText();
        }else if(btnMemory.isFocused()){
            condition = btnMemory.getText();
        }else if(btnWalk.isFocused()){
            condition = btnWalk.getText();
        }else if(btnUseToilet.isFocused()){
            condition = btnUseToilet.getText();
        }else if(btnTransport.isFocused()){
            condition = btnTransport.getText();
        }else if(btnTakeCareOfOwnHealth.isFocused()){
            condition = btnTakeCareOfOwnHealth.getText();
        }else if(btnSkills.isFocused()){
            condition = btnSkills.getText();
        }else if(btnShopping.isFocused()){
            condition = btnShopping.getText();
        }else if(btnProblemSolve.isFocused()){
            condition = btnProblemSolve.getText();
        }else if(btnPersonalCare.isFocused()){
            condition = btnPersonalCare.getText();
        }else if(btnPaidWork.isFocused()){
            condition = btnPaidWork.getText();
        }else if(btnOrientationAbility.isFocused()){
            condition = btnOrientationAbility.getText();
        }else if(btnMuscle.isFocused()){
            condition = btnMuscle.getText();
        }else if(btnMoveAround.isFocused()){
            condition = btnMoveAround.getText();
        }else if(btnMoveInDiffrentAreas.isFocused()) {
            condition = btnMoveInDiffrentAreas.getText();
        }
        functionConditionString = condition;

        return condition;
    }

    /**
     * sets up the combobox with value for function journal
     */
    public void setupComboboxForFunctionJournal(){

        cbNiveauFunction.getItems().add("0");
        cbNiveauFunction.getItems().add("1");
        cbNiveauFunction.getItems().add("2");
        cbNiveauFunction.getItems().add("3");
        cbNiveauFunction.getItems().add("4");

        cbExpectedFunction.getItems().add("0");
        cbExpectedFunction.getItems().add("1");
        cbExpectedFunction.getItems().add("2");
        cbExpectedFunction.getItems().add("3");
        cbExpectedFunction.getItems().add("4");

        cbExecutionFunction.getItems().add("Udfører selv");
        cbExecutionFunction.getItems().add("Udfører dele selv");
        cbExecutionFunction.getItems().add("Udfører ikke selv");
        cbExecutionFunction.getItems().add("Ikke relavant");

        cbExecutionLimitsFunction.getItems().add("Oplever begrænsinger");
        cbExecutionLimitsFunction.getItems().add("Oplever ikke begrænsinger");
    }

    /**
     * Either updates or Creates a new TPLFunctionJournal for Databasen, depending on if already exist
     * @param actionEvent
     */
    public void onSaveTPLFunctionJournalBtn(ActionEvent actionEvent) throws SQLException {
        try {

            Date date = new Date();
            getTPLFunctionsJournals();
            boolean hasSaved = false;
            if (!functionalJournals.isEmpty()){

                for (FunctionalJournal functionalJournal: functionalJournals){
                    if (functionalJournal.getCondition().equals(getFunctionalCondition())){

                        functionalJournal.setNiveau((String) cbNiveauFunction.getSelectionModel().getSelectedItem());
                        functionalJournal.setExecution((String) cbExecutionFunction.getSelectionModel().getSelectedItem());
                        functionalJournal.setExecutionLimits(cbExecutionLimitsFunction.getSelectionModel().getSelectedItem().toString());
                        functionalJournal.setExpectation(cbExpectedFunction.getSelectionModel().getSelectedItem().toString());
                        functionalJournal.setNote(txtNoteFunction.getText());
                        functionalJournal.setCitizenExpectation(txtCitizenExpecationFunction.getText());
                        functionalJournal.setLastUpdate(date.toString());
                        if (checkboxFunctionActive.isSelected()){
                            functionalJournal.setRelevancy("Aktiv");
                        }else{
                            functionalJournal.setRelevancy("Ikke Relavant");
                        }
                        updateFunctionJournalView(functionalJournal);
                        hasSaved = true;
                        break;
                    }
                }
            }

            if (!hasSaved){

                String relevancy = "";
                if (checkboxFunctionActive.isSelected()){
                    relevancy = "Aktiv";
                }else{
                    relevancy = "Ikke Relavant";
                }
                FunctionalJournal functionalJournal = new FunctionalJournal(-1,citizen.getId(), functionConditionString,date.toString(), cbNiveauFunction.getSelectionModel().getSelectedItem().toString(), relevancy, txtNoteFunction.getText(), cbExpectedFunction.getSelectionModel().getSelectedItem().toString(), cbExecutionFunction.getSelectionModel().getSelectedItem().toString(), cbExecutionLimitsFunction.getSelectionModel().getSelectedItem().toString(), txtCitizenExpecationFunction.getText());
                citizenModel.createFunctionalJournal(functionalJournal);
                updateFunctionJournalView(functionalJournal);

            }
        }catch (Exception exception){
            exception.printStackTrace();
            DisplayMessage.displayError(exception);
        }

    }

    /**
     * gets all TPLFunctionsJournals for a template on the Database
     */
    private void getTPLFunctionsJournals() throws SQLException {

        if (!citizenModel.getFunctionalJournal(citizen.getId()).isEmpty()){
            functionalJournals.clear();
            functionalJournals.addAll(citizenModel.getFunctionalJournal(citizen.getId()));
        }
    }
}
