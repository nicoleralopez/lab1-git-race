package es.unizar.webeng.hello

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@WebMvcTest(HelloController::class)
class HelloControllerUnitTest {

    @Value("\${app.message}")
    private var message: String = "Hello World"

    @Autowired
    private lateinit var controller: HelloController

    /*
     * Test Generic welcome (MUST success)
     */
    @Test
    @Throws(Exception::class)
    fun `generic welcome`() {
        val message = Message()
        val view = controller.welcome(message)
        assertThat(view, `is`("welcome"))
        assertThat(message.message, `is`(this.message))
    }

    /*
     * Test Personal welcome with valid name (MUST success)
     */
    @Test
    @Throws(Exception::class)
    fun `personal welcome`() {
        val personalMessage = "Web Engineering"
        val message = Message()
        try {
            val view = controller.personalWelcome(message, personalMessage)
            assertThat(message.message, `is`("Hola $personalMessage"))
            assertThat(view, `is`("welcome"))
        } catch (ex: InvalidWelcomeMessageException) {
            fail("An exception is thrown when no exception MUST be thrown.")
        }
    }

    /*
     * Test Personal welcome with invalid name (MUST fail)
     */
    @Test
    @Throws(Exception::class)
    fun `invalid personal welcome`() {
        val personalMessage = "__am"
        val message = Message()
        try {
            controller.personalWelcome(message, personalMessage)
            fail("No exception is thrown.")
        } catch (ex: InvalidWelcomeMessageException) {
            assertThat(ex.message, `is`("Invalid request. No one can be named $personalMessage"))
        }
    }

    /**
     * Check that the POST("/gcd") method works properly when
     * the input is correct
     */
    @Test
    @Throws(Exception::class)
    fun `test Great Common Divisor`() {
        val form = Gcd(a = 20, b = 30)
        val result = controller.gcd(form)
        assertThat(result, `is`(10))
    }
}
