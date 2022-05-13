package dal.interfaces;

import be.Teacher;
import be.Template;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ITemplate {
    public List<Template> getTemplate(int schoolId) throws SQLException, IOException;
    public Template createTemplate(Template template) throws SQLException, IOException;
    public void updateTemplate(Template template) throws SQLException, IOException;
    public boolean removeTemplate(Template template) throws SQLException, IOException;
}
