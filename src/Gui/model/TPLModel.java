package Gui.model;

import be.TPLFunctionalJournal;
import be.TPLGeneralInfo;
import be.TPLHealthJournal;
import be.Template;
import bll.TPLFacade;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class TPLModel {


    TPLFacade tplFacade = new TPLFacade();



    public ObservableList<Template> getTemplate(int schoolID) throws SQLException {
        return tplFacade.getTemplate(schoolID);
    }
    public Template createTemplate(Template template) throws SQLException {
        return tplFacade.createTemplate(template);
    }
    public boolean removeTemplate(Template template) throws SQLException {
        return tplFacade.removeTemplate(template);
    }
    public void updateTemplate(Template template) throws SQLException {
        tplFacade.updateTemplate(template);
    }

    public ObservableList<TPLGeneralInfo> getTPLGeneralInfo(int templateID){
        return tplFacade.getTPLGeneralInfo(templateID);
    }
    public TPLGeneralInfo createTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo){
        return tplFacade.createTPLGeneralInfo(tplGeneralInfo);
    }
    public void updateTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) throws SQLException {
        tplFacade.updateTPLGeneralInfo(tplGeneralInfo);
    }
    public boolean removeTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) throws SQLException {
        return tplFacade.removeTPLGeneralInfo(tplGeneralInfo);
    }

    // for our TPLHealthJournal
    public ObservableList<TPLHealthJournal> getTPLHealthJournal(int templateID) throws SQLException {
        return tplFacade.getTPLHealthJournal(templateID);
    }
    public TPLHealthJournal createTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException {
        return tplFacade.createTPLHealthJournal(tplHealthJournal);
    }
    public void updateTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException {
        tplFacade.updateTPLHealthJournal(tplHealthJournal);
    }

    // for TPLFunctionalJournal
    public ObservableList<TPLFunctionalJournal> getTPLFunctionalJournal(int templateID){
        return tplFacade.getTPLFunctionalJournal(templateID);
    }
    public TPLFunctionalJournal createTPLFunctionalJournal(TPLFunctionalJournal tplFunctionalJournal) throws SQLException {
        return tplFacade.createTPLFunctionalJournal(tplFunctionalJournal);
    }
    public void updateTPLFunctionalJournal(TPLFunctionalJournal tplFunctionalJournal) throws SQLException {
        tplFacade.updateTPLFunctionalJournal(tplFunctionalJournal);
    }
}
