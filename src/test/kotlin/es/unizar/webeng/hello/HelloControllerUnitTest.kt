package es.unizar.webeng.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;

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

    /*
     * Test Generic welcome (MUST success)
     */
    @Test
    @Throws(Exception::class)
    fun `generic welcome`() {
        var view = controller.welcome();
        assertThat(view.getViewName(), `is`("welcome"));
        
        assertThat(view.getModel().containsKey("name"), `is`(false));
        assertThat(view.getModel().containsKey("time"), `is`(true));
        assertThat(view.getModel().containsKey("message"), `is`(true));
        
        assertThat(view.getModel().get("message") as String, `is`(message));
    }

    /*
     * Test Personal welcome with valid name (MUST success)
     */
    @Test
    @Throws(Exception::class)
    fun `personal welcome`() {
        val personalMessage = "Web Engineering";
        var view = controller.personalWelcome(personalMessage);
        assertThat(view.getViewName(), `is`("welcome"));
        
        assertThat(view.getModel().containsKey("name"), `is`(true));
        assertThat(view.getModel().containsKey("time"), `is`(false));
        assertThat(view.getModel().containsKey("message"), `is`(false));

        assertThat(view.getModel().get("name") as String, `is`("Hola " + personalMessage));
    }

    /*
     * Test Personal welcome with invalid name (MUST fail)
     */
    @Test
    @Throws(Exception::class)
    fun `invalid personal welcome`() {
        val personalMessage = "__am";
        var view = controller.personalWelcome(personalMessage);
        assertThat(view.getStatus(), `is`(HttpStatus.BAD_REQUEST));
        assertThat(view.getViewName(), `is`("welcome"));
        
        assertThat(view.getModel().containsKey("name"), `is`(true));
        assertThat(view.getModel().containsKey("time"), `is`(false));
        assertThat(view.getModel().containsKey("message"), `is`(false));
        
        assertThat(view.getModel().get("name") as String, `is`("Invalid request. No one can be named " + personalMessage));
    }

    /**
     * Check that the POST("/gcd") method works properly when
     * the input is correct
     */
    @Test
    @Throws(Exception::class)
    fun `test Great Common Divisor`() {
        var form = Gcd();
        form.setA(20);
        form.setB(30);
        var result = controller.gcd(form);
        assertThat(result, `is`(10));
    }
}
