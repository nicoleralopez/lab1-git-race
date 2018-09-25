package es.unizar.webeng.hello

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import khttp.get // Http library for kotlin
import org.json.*
import java.util.Random

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
    fun welcome(@ModelAttribute msg: Message) : String {
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
     * 
     * This endpoint returns a random Chuck Norris joke if the api is available and error 404 (NOT FOUND)
     * if it's not
     * @return a String with a funny joke
     */
    @GetMapping("/joke")
    fun joke(@ModelAttribute msg: Message) : String {
        val response = get("https://api.chucknorris.io/jokes/random")
        var statusCode = response.statusCode
        if( statusCode == 200){
            msg.message = response.jsonObject.getString("value")
        }else{
            msg.message = "Error $statusCode NOT FOUND"
            throw URINotFoundException(msg)
        }
        return "welcome"
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

    /**
     * Function returns random value between two integers
     *
     * @Param form It must have two keys: "a":value1, "b":value2
     * @return Random integer between a and b: a<return<b
     */
    @PostMapping("/games/random")
    @ResponseBody
    fun randomInt(@ModelAttribute form: Gcd) : Int {
        var a = form.a
        var b = form.b
        var random = Random()
        return random.nextInt(b - a) + a
    }

    /**
     * Funciton returns Heads or Trails game based on RNG
     *
     * @return returns string with result value [Heads or Trails]
     */
    @GetMapping("/games/HeadsTrails")
    fun headsTrails(@ModelAttribute msg: Message) : String {
        var random = Random()
        var value = random.nextInt(2)
        if (value == 0) {
            msg.message = "Heads"
            return "headsTrails"
        } else {
            msg.message = "Tails"
            return "headsTrails"
        }
    }
}
