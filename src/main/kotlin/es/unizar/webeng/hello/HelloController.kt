package es.unizar.webeng.hello

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import khttp.get // Http library for kotlin
import org.json.*
import java.util.Random

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap;


@Controller
class HelloController {
    @Value("\${app.message:Hello World}")
    private var message: String = "Hello World"

    @Autowired // Database Persistance
    lateinit private var sharedData : StringRedisTemplate


    /**
     * 
     * This endpoint return all the movies in the database
     * @return a Map<String,String> with all the movies in the database
     */
    @GetMapping("/values")
    @ResponseBody
    fun findAll() : Map<String, String> {
        var map = HashMap<String, String>();
        var keys = sharedData.keys("*");// you can use any specific pattern of key
        // return sharedData.opsForValue().multiGet(keys);
        for(key in keys){
            var value = sharedData.opsForValue().get(key);
            if (value != null) map.put(key, value)
        }
        return map 
    }

    /**
     * Add a movie
     *
     * @param key The key of the movie
     * @param value Name of the movie
     *
     * @return Ok if everything goes ok
     */
    @PostMapping("/add")
    fun add(@RequestParam movie: MultiValueMap<String, String>) : ResponseEntity<String> {
        var key = movie.getFirst("key")!!
        var value = movie.getFirst("value")!!
        sharedData.opsForValue().set(key, value);
        return ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * Delete a movie
     *
     * @param key The key of the movie
     *
     * @return Ok if everything goes ok
     */
    @PostMapping("/delete")
    fun delete(@RequestParam key: String) : ResponseEntity<String>{
        sharedData.delete(key);
        return ResponseEntity<String>(HttpStatus.OK);
    } 

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
     * Found at https://www.baeldung.com/java-generate-prime-numbers
     * This code is separated from "findPrimes" because of the test in
     * HelloControllerUnitTest.kt
     * @param n all prime number from 0 to n
     * @return a List object with all prime numbers below the parameter "n"
     */
    fun sieveOfEratosthenes(n: Int): List<Int> {
    	var prime = BooleanArray(n + 1)
    	var p: Int = 2
	    prime.fill(true, 0, n)
	    for (p in 2 until p*p step 1) {
	        if (prime[p]) {
	            for (i in 2*p until n step p) {
	                prime[i] = false
	            }
	        }
	    }
	    val primeNumbers = (2 until n).filter { prime[it] }
	    return primeNumbers
    }

    /**
     * @PathVariable annotation is used to extract a variable from the url
     * @param n all prime number from 0 to n
     * @return all prime numbers below the parameter "n"
     */
	@GetMapping("/primes/{n}")
	fun findPrimes(@ModelAttribute msg: Message, @PathVariable n: Int): String {
	    msg.message=sieveOfEratosthenes(n).joinToString()
	    return "welcome"
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
