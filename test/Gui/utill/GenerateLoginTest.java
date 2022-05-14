package Gui.utill;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GenerateLoginTest {
    GenerateLogin generateLogin = new GenerateLogin();

    @Test
    /**
     * checks that CreateUsername apply The fName correct to username.
     */
    void createUsername() throws SQLException, IOException {

        String username = generateLogin.createUsername("hans");
        String username2 = generateLogin.createUsername("ib");

        assertEquals("hans",username.substring(0, 4));
        assertNotEquals("bo", username2.substring(0,2));
    }

    @Test
    /**
     * checks we get rightful amount of Chars.
     */
    void getRandomChars() {
        String randomAmountChars = generateLogin.getRandomChars(4);
        String randomAmountChars2 = generateLogin.getRandomChars(10);
        assertEquals(4, randomAmountChars.length());
        assertNotEquals(9, randomAmountChars2.length());
    }

    @Test
    /**
     * Checks GetRandomNumbers works as correctly. Giving nuberwith selected min, max.
     */
    void checkRandomNumbers() {

    int generatedNum = generateLogin.getRandomNumbers(1000, 2000);
    int generatedNum2 = generateLogin.getRandomNumbers(10, 20);

    assertTrue(generatedNum > 999 & generatedNum < 2001);
    assertFalse(generatedNum2 < 9 || generatedNum2 > 21);
    }



}