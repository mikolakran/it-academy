package validation;

import exception.LoginException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckUserTest {

    private ValidationAuth auth;

    @Before
    public void setUp() {
        auth = new CheckUser();
    }

    /*@Test
    @Ignore
    public void validationPasswordTrue() {
        assertDoesNotThrow(() -> auth.validationPassword("*s9C#nFSNx#A"));
    }*/

    @Test
    public void validationPasswordFalse() {
        assertThrows(LoginException.class, () -> auth.validationPassword("sada"));
    }

    /*@Test
    public void validationPassSamePass2True() {
        assertDoesNotThrow(() -> auth.validationPassSamePass2("*s9C#nFSNx#A", "*s9C#nFSNx#A"));
    }*/

    @Test
    public void validationPassSamePass2False() {
        assertThrows(LoginException.class, () -> auth.validationPassSamePass2("*s9C#nFSNx#A", "*s9C#nFSNasd"));
    }

    /*@Test
    public void validationNameTrue() {
        assertDoesNotThrow(() -> auth.validationName("radfar"));
    }*/

    @Test
    public void validationNameFalse() {
        assertThrows(LoginException.class, () -> auth.validationName("ra"));
    }

    /*@Test
    public void validationEmailTrue() {
        assertDoesNotThrow(() -> auth.validationEmail("radfar@mail.ru"));
    }*/

    @Test
    public void validationEmailFalse() {
        assertThrows(LoginException.class, () -> auth.validationEmail("radfar.mail.ru"));
    }


    @Test
    public void validationSQLExceptionEmailTrue() {
        try {
            throw new LoginException("jkdjf 'mikola@mail.ru' .fmlsss ");
        } catch (LoginException e) {
            assertThrows(LoginException.class, () -> auth.validationSQL(e));
        }
    }

    /*@Test
    public void validationSQLExceptionEmailFalse() {
        try {
            throw new LoginException("jkdjf 'mu' .fmlsss ");
        } catch (LoginException e) {
            assertDoesNotThrow(() -> {
                auth.validationSQL(e);
            });
        }
    }*/

    @Test
    public void validationSQLExceptionNameTrue() {
        try {
            throw new LoginException("jkdjf 'mikola' .fmlsss ");
        } catch (LoginException e) {
            assertThrows(LoginException.class, () -> auth.validationSQL(e));
        }
    }

   /* @Test
    public void validationSQLExceptionNameFalse() {
        try {
            throw new LoginException("jkdjf 'mi' .fmlsss ");
        } catch (LoginException e) {
            assertDoesNotThrow(() -> {
                auth.validationSQL(e);
            });
        }
    }*/
}