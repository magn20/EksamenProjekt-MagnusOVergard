package Gui.utill;

import be.Citizen;
import be.SchoolAdmin;
import be.Student;
import be.Teacher;

public class SingletonUser {

    private Teacher teacher;
    private Student student;
    private SchoolAdmin schoolAdmin;
    private Citizen citizen;
    private static SingletonUser singletonUser;

    public static SingletonUser getInstance() {
        if(singletonUser == null){
            singletonUser = new SingletonUser();
        }
        return singletonUser;
    }

    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }

    public Teacher getTeacher(){
        return teacher;
    }

    public void setStudent(Student student){
        this.student = student;
    }

    public Student getStudent(){
        return student;
    }


    public void setSchoolAdmin(SchoolAdmin schoolAdmin){
        this.schoolAdmin = schoolAdmin;
    }
    public SchoolAdmin getSchoolAdmin(){
        return schoolAdmin;
    }

    public void setCitizen(Citizen citizen){
        this.citizen = citizen;
    }
    public Citizen getCitizen(){
        return citizen;
    }

}
