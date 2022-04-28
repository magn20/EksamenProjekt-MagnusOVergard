package Gui.utill;

public class SingletonUser {

    private static SingletonUser singletonUser;

    public static SingletonUser getInstance() {
        if(singletonUser == null){
            singletonUser = new SingletonUser();
        }
        return singletonUser;
    }

}
