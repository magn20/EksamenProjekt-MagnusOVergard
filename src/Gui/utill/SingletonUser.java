package Gui.utill;

import be.Citizen;
import be.Teacher;

public class SingletonUser {

    Teacher teacher;
    Citizen citizen;
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

    public void setCitizen(Citizen citizen){
        this.citizen = citizen;
    }
    public Citizen getCitizen(){
        return citizen;
    }

}
