package bll;

import be.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.*;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class TPLFacade {

    private TemplateDAO templateDAO;
    private TPLGeneralInfoDAO tplGeneralInfoDAO;
    private TPLHealthJournalDAO tplHealthJournalDAO;
    private TPLFunctionalJournalDAO tplFunctionalJournalDAO;
    private DatabaseConnector connector;
    {
        try {
            connector = new DatabaseConnector();
            tplHealthJournalDAO = new TPLHealthJournalDAO();
            templateDAO = new TemplateDAO();
            tplGeneralInfoDAO = new TPLGeneralInfoDAO();
            tplFunctionalJournalDAO = new TPLFunctionalJournalDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // for our template Citizens

    public ObservableList<Template> getTemplate(int schoolID) throws SQLException, IOException {
        return templateDAO.getTemplate(schoolID);
    }
    public Template createTemplate(Template template) throws SQLException, IOException {
        return templateDAO.createTemplate(template);
    }
    public boolean removeTemplate(Template template) throws SQLException, IOException {
        return templateDAO.removeTemplate(template);
    }
    public void updateTemplate(Template template) throws SQLException, IOException {
        templateDAO.updateTemplate(template);}

    // for TPLGeneralInfo
    public ObservableList<TPLGeneralInfo> getTPLGeneralInfo(int templateID){
         return tplGeneralInfoDAO.getTPLGeneralInfo(templateID);
    }
    public TPLGeneralInfo createTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo){
       return tplGeneralInfoDAO.createTPLGeneralInfo(tplGeneralInfo);
    }
    public void updateTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) throws SQLException, IOException {
        tplGeneralInfoDAO.updateTPLGeneralInfo(tplGeneralInfo);
    }
    public boolean removeTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) throws SQLException, IOException {
        return tplGeneralInfoDAO.removeTPLGeneralInfo(tplGeneralInfo);
    }

    // for TPLHealthJournal
    public ObservableList<TPLHealthJournal> getTPLHealthJournal(int templateID) throws SQLException, IOException {
        return tplHealthJournalDAO.getTPLHealthJournal(templateID);
    }
    public TPLHealthJournal createTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException, IOException {
        return tplHealthJournalDAO.createTPLHealthJournal(tplHealthJournal);
    }
    public void updateTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException, IOException {
        tplHealthJournalDAO.updateTPLHealthJournal(tplHealthJournal);
    }
    // for TPLFunctionalJournal
    public ObservableList<TPLFunctionalJournal> getTPLFunctionalJournal(int templateID){
        return tplFunctionalJournalDAO.getTPLFunctionalJournal(templateID);
    }
    public TPLFunctionalJournal createTPLFunctionalJournal(TPLFunctionalJournal tplFunctionalJournal) throws SQLException, IOException {
        return tplFunctionalJournalDAO.createTPLFunctionalJournal(tplFunctionalJournal);
    }
    public void updateTPLFunctionalJournal(TPLFunctionalJournal tplFunctionalJournal) throws SQLException, IOException {
        tplFunctionalJournalDAO.updateTPLFunctionalJournal(tplFunctionalJournal);
    }
}
