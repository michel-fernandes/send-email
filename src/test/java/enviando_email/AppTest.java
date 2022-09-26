package enviando_email;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void testeEmail() {
        
        SendEmail sendEmail = new SendEmail("michelfernandes3@gmail.com, michelsigma@gmail.com", 
                "Michel Fernandes",
                "Enviar e-mail com Java",
                "Olá, esse é o e-mail geradp pelo nosso app em Java");
        assertEquals(true ,sendEmail.sendEmail());

    }
}
