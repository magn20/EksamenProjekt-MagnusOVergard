package bll;

import be.TPLGeneralInfo;
import be.TPLHealthJournal;
import be.Teacher;
import be.Template;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.DatabaseConnector;
import dal.db.TPLGeneralInfoDAO;
import dal.db.TPLHealthJournalDAO;
import dal.db.TemplateDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class TPLFacade {

    private TemplateDAO templateDAO;
    private TPLGeneralInfoDAO tplGeneralInfoDAO;
    private TPLHealthJournalDAO tplHealthJournalDAO;
    private DatabaseConnector connector;
    {
        try {
            connector = new DatabaseConnector();
            tplHealthJournalDAO = new TPLHealthJournalDAO(connector.getConnection());
            templateDAO = new TemplateDAO(connector.getConnection());
            tplGeneralInfoDAO = new TPLGeneralInfoDAO(connector.getConnection());
        } catch (SQLServerException | IOException e) {
            e.printStackTrace();
        }
    }


    // for our template Citizens

    public ObservableList<Template> getTemplate(int schoolID){
        return templateDAO.getTemplate(schoolID);
    }
    public Template createTemplate(Template template){
        return templateDAO.createTemplate(template);
    }
    public boolean removeTemplate(Template template) throws SQLException {
        return templateDAO.removeTemplate(template);
    }
    public void updateTemplate(Template template) throws SQLException {
        templateDAO.updateTemplate(template);}

    // for TPLGeneralInfo
    public ObservableList<TPLGeneralInfo> getTPLGeneralInfo(int templateID){
         return tplGeneralInfoDAO.getTPLGeneralInfo(templateID);
    }
    public TPLGeneralInfo createTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo){
       return tplGeneralInfoDAO.createTPLGeneralInfo(tplGeneralInfo);
    }
    public void updateTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo) throws SQLException {
        tplGeneralInfoDAO.updateTPLGeneralInfo(tplGeneralInfo);
    }
    public boolean removeTPLGeneralInfo(TPLGeneralInfo tplGeneralInfo){
        return tplGeneralInfoDAO.removeTPLGeneralInfo(tplGeneralInfo);
    }

    // for our TPLHealthJournal
    public ObservableList<TPLHealthJournal> getTPLHealthJournal(int templateID){
        return tplHealthJournalDAO.getTPLHealthJournal(templateID);
    }
    public TPLHealthJournal createTPLHealthJournal(TPLHealthJournal tplHealthJournal){
        return tplHealthJournalDAO.createTPLHealthJournal(tplHealthJournal);
    }
    public void updateTPLHealthJournal(TPLHealthJournal tplHealthJournal) throws SQLException {
        tplHealthJournalDAO.updateTPLHealthJournal(tplHealthJournal);
    }

}
