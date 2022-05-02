package dal.db;

import be.Teacher;
import be.Template;
import dal.interfaces.ITemplate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TemplateDAO implements ITemplate {

    private Connection con;
    public TemplateDAO(Connection con){
        this.con = con;
    }


    /**
     * gets templates a school from database
     * @return Observablelist of all Teachers
     */
    @Override
    public ObservableList<Template> getTemplate(int schoolId) {
        ObservableList<Template> AllTemplatesFromSchool = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM TPLCitizen Where TPLCitizenSchoolId = ? ";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, schoolId);

            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {

                int id = rs.getInt(1);
                int tplSchoolId = rs.getInt(2);
                String fName = rs.getString(3);

                String lName = rs.getString(4);

                String age = rs.getString(5);


                AllTemplatesFromSchool.add(new Template(id, tplSchoolId, fName,lName, age));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return AllTemplatesFromSchool;
    }

    @Override
    public Template createTemplate(Template template) {
        int insertedId = -1;
        try {
            String sqlStatement = "INSERT INTO TPLCitizen(TPLCitizenSchoolId, Fname, Lname, age) VALUES (?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, template.getSchoolId());
            statement.setString(2, template.getfName() );
            statement.setString(3, template.getlName());
            statement.setString(4, template.getAge());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            insertedId = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Template(insertedId,template.getSchoolId(), template.getfName(),template.getlName(),template.getAge());
    }

    @Override
    public void updateTemplate(Template template) throws SQLException {
        String sql = "UPDATE TPLCitizen SET TPLCitizenSchoolId = ?, Fname = ?, Lname = ?, age = ? WHERE TPLCitizenId=?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, template.getSchoolId());
        preparedStatement.setString(2, template.getfName());
        preparedStatement.setString(3, template.getlName());
        preparedStatement.setString(4, template.getAge());
        preparedStatement.setInt(5, template.getId());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows != 1) {
        }
    }

    @Override
    public boolean removeTemplate(Template template) {
        try {
            String sqlStatement = "DELETE FROM TPLCitizen WHERE TPLCitizenId=?";
            PreparedStatement statement = con.prepareStatement(sqlStatement);
            statement.setInt(1, template.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
