package Gui.model;

import be.TPLFunctionalJournal;
import be.TPLGeneralInfo;
import be.TPLHealthJournal;
import be.Template;
import bll.TPLFacade;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class TPLModel {


    TPLFacade tplFacade = new TPLFacade();



    public ObservableList<Template> getTemplate(int schoolID) throws SQLException, IOException {
        return tplFacade.getTemplate(schoolID);
    }
    public Template createTemplate(Template template) throws SQLException, IOException {
        return tplFacade.createTemplate(template);
    }
    public boolean removeTemplate(Template template) throws SQLException, IOException {
        return tplFacade.removeTemplate(template);
    }
    public void updateTemplate(Template template) throws SQLException, IOException {
        tplFacade.updateTemplate(template);
    }

    public ObservableList<TPLGeneralInfo> getTPLGeneralInfo(int templateID){
        return tplFacade.getTPLGeneralInfo(templateID);
    }
    public TPLGeneralInfo createTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo){
        return tplFacade.createTPLGeneralInfo(tplGeneralInfo);
    }
    public void updateTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) throws SQLException, IOException {
        tplFacade.updateTPLGeneralInfo(tplGeneralInfo);
    }
    public boolean removeTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) throws SQLException, IOException {
        return tplFacade.removeTPLGeneralInfo(tplGeneralInfo);
    }

    // for our TPLHealthJournal
    public ObservableList<TPLHealthJournal> getTPLHealthJournal(int templateID) throws SQLException, IOException {
        return tplFacade.getTPLHealthJournal(templateID);
    }
    public TPLHealthJournal createTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException, IOException {
        return tplFacade.createTPLHealthJournal(tplHealthJournal);
    }
    public void updateTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException, IOException {
        tplFacade.updateTPLHealthJournal(tplHealthJournal);
    }

    // for TPLFunctionalJournal
    public ObservableList<TPLFunctionalJournal> getTPLFunctionalJournal(int templateID){
        return tplFacade.getTPLFunctionalJournal(templateID);
    }
    public TPLFunctionalJournal createTPLFunctionalJournal(TPLFunctionalJournal tplFunctionalJournal) throws SQLException, IOException {
        return tplFacade.createTPLFunctionalJournal(tplFunctionalJournal);
    }
    public void updateTPLFunctionalJournal(TPLFunctionalJournal tplFunctionalJournal) throws SQLException, IOException {
        tplFacade.updateTPLFunctionalJournal(tplFunctionalJournal);
    }
}
