package es.unizar.webeng.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import org.hamcrest.CoreMatchers.`is`;
import org.junit.Assert.assertThat;

@RunWith(SpringRunner::class)
@WebMvcTest(HelloController::class)
class HelloControllerUnitTest {

    @Value("\${app.message}")
    private var message : String = "Hello World";

    @Autowired
    private lateinit var controller : HelloController;

    @Test
    @Throws(Exception::class)
    fun testMessage() {
        var view = controller.welcome();
        assertThat(view.getViewName(), `is`("welcome"));
        assertThat(view.getModel().containsKey("message"), `is`(true));
        assertThat(view.getModel().get("message") as String, `is`(message));
    }
}
