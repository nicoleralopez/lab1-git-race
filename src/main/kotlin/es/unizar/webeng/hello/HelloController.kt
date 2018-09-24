package es.unizar.webeng.hello

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class HelloController {
    @Value("\${app.message:Hello World}")
    private var message: String = "Hello World"

    /**
     *
     * This annotation is used to map the welcome function to a GET Request on path: "/"
     * @return a String composed with current date + a "Hello World" message
     */
    @GetMapping("/")
    fun welcome(@ModelAttribute msg: Message): String {
        msg.message = message
        return "welcome"
    }

    /**
     *
     * This annotation is used to map the personalWelcome function to a GET Request on path: "/{name}"
     * @PathVariable annotation is used to extract a variable from the url.
     * A name is considered valid *only* if it only contains letters (upper and lower) and spaces.
     * @return a String saying "Hello {name}" if name is valid and an error message otherwise
     */
    @GetMapping("/{name}")
    fun personalWelcome(@ModelAttribute msg: Message, @PathVariable name: String): String {
        if (name.matches(Regex("[A-Za-z ]+"))) {
            msg.message = "Hola $name"
            return "welcome"
        } else {
            msg.message = "Invalid request. No one can be named $name"
            throw InvalidWelcomeMessageException(msg)
        }
    }

    /**
     * Function example to check the post functionality
     *
     * @param form It must have two keys: "a":value1, "b":value2
     */
    @PostMapping("/gcd")
    @ResponseBody
    fun gcd(@ModelAttribute form: Gcd): Int {
        var a = form.a
        var b = form.b

        // https://introcs.cs.princeton.edu/java/23recursion/Euclid.java.html
        // Non-recursive Euclidean way
        while (b != 0) {
            val temp = b
            b = a % b
            a = temp
        }
        // Result is stored in a
        return a
    }

}
