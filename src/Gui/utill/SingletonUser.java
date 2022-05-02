package Gui.utill;

import be.Teacher;

public class SingletonUser {

    Teacher teacher;
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

}
