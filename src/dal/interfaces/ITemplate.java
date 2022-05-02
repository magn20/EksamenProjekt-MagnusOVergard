package dal.interfaces;

import be.Teacher;
import be.Template;

import java.sql.SQLException;
import java.util.List;

public interface ITemplate {
    public List<Template> getTemplate(int schoolId);
    public Template createTemplate(Template template);
    public void updateTemplate(Template template) throws SQLException;
    public boolean removeTemplate(Template template);
}
