package Gui.model;

import be.Teacher;
import bll.TeacherManager;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class TeacherModel {

    TeacherManager teacherManager = new TeacherManager();

    public ObservableList<Teacher> getTeachers() throws SQLException, IOException {
        return teacherManager.getTeachers();
    }
    public Teacher createTeacher(Teacher teacher) throws SQLException, IOException {
        return teacherManager.createTeacher(teacher);
    }
    public boolean removeTeacher(Teacher teacher) throws SQLException, IOException {
        return teacherManager.removeTeacher(teacher);
    }
    public void updateTeacher(Teacher teacher) throws SQLException, IOException {
        teacherManager.updateTeacher(teacher);
    }

}
