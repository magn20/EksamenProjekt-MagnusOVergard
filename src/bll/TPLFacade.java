package bll;

import be.Teacher;
import be.Template;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.DatabaseConnector;
import dal.db.TemplateDAO;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class TPLFacade {

    private TemplateDAO templateDAO;
    private DatabaseConnector connector;
    {
        try {
            connector = new DatabaseConnector();
            templateDAO = new TemplateDAO(connector.getConnection());
        } catch (SQLServerException | IOException e) {
            e.printStackTrace();
        }
    }

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
        templateDAO.updateTemplate(template);
    }





}
