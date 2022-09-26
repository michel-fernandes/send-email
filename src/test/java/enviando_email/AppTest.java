package enviando_email;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void testeTextFormatEmail() {

        SendEmail sendEmail = new SendEmail("michelfernandes3@gmail.com, michelsigma@gmail.com",
                "Michel Fernandes",
                "Enviar e-mail com Java",
                "Olá, esse é o e-mail geradp pelo nosso app em Java");
        assertEquals(true, sendEmail.sendEmail(false));
    }

    @Test
    public void testeTextHtmlEmail() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Olá, <br/><br/>");
        stringBuilder.append("Você está recebendo um e-mail com formato HTML;<br/><br/>");
        stringBuilder.append(
                "Para acessar o conteúdo deste projeto acesse nosso GitHub, clicabdo no botão abaixo.<br/><br/>");
        stringBuilder.append(
                "<a target=\"_blank\" href=\"https://github.com/michel-fernandes/send-email\" style=\"color:#2525a7; padding: 14px 25px; text-align:center; text-decoration: none; display:inline-block; border-radius:30px; font-family:courier; border:3px solid green; background-color:#99DA39;\">Acessar GitHub</a><br/><br/>");
                stringBuilder.append("<span style=\"font-size:10px\">Ass. Michel Fernandes</span>");

        SendEmail sendEmail = new SendEmail("michelfernandes3@gmail.com, michelsigma@gmail.com",
                "Michel Fernandes",
                "Enviar e-mail com Java",
                stringBuilder.toString());
        assertEquals(true, sendEmail.sendEmail(true));
    }
}
