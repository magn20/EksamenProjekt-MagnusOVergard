import Gui.utill.GenerateLogin;

import java.io.IOException;
import java.sql.SQLException;

public class Magnus {


    public static void main(String[] args) throws SQLException, IOException {
        GenerateLogin generateLogin = new GenerateLogin();


        System.out.println(generateLogin.createUsername("magnus"));
        System.out.println(generateLogin.createPassword());

    }
}
