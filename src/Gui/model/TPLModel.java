package Gui.model;

import be.TPLGeneralInfo;
import be.Template;
import bll.TPLFacade;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class TPLModel {


    TPLFacade tplFacade = new TPLFacade();



    public ObservableList<Template> getTemplate(int schoolID){
        return tplFacade.getTemplate(schoolID);
    }
    public Template createTemplate(Template template){
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
    public boolean removeTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo){
        return tplFacade.removeTPLGeneralInfo(tplGeneralInfo);
    }
}
