package Gui.utill;

import Gui.model.StudentModel;
import be.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

public class GenerateLogin {



    public String createUsername(String fName) throws SQLException, IOException {
        try {
            String username = "";
            if (fName.length() > 4){
                username = fName.substring(0, 4);
            }else{username = fName;}

            int numbers = getRandomNumbers(1000, 9999);
            username = username + numbers;
            StudentModel studentModel = new StudentModel();
            for (Student student : studentModel.getStudents()){
                if (student.getUsername().equals(username)){username = "";}
            }
            if (!username.equals("")){
                return username;
            }else {
                return createUsername(fName);
            }
        }catch (StackOverflowError error){
            error.printStackTrace();
        }
        return null;
    }

    public String createPassword(){
        String password = "";

            password = password + getRandomChars(2);
            password = password + getRandomNumbers(10,99);
            password = password + getRandomChars(3);

        return password;
    }

    public String getRandomChars(int amount){
        Random random = new Random();

        StringBuilder chars = new StringBuilder();
        for (int i = 0; i < amount ; i++ ){
            char randomizedCharacter = (char) (random.nextInt(26) + 'a');
            chars.append(randomizedCharacter);
        }


        return chars.toString();
    }
    public int getRandomNumbers(int min, int max){
         return (int) ((Math.random() * (max - min)) + min);
    }

}
