package es.unizar.webeng.hello

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import khttp.get // Http library for kotlin
import org.json.*
import java.util.Random
import kotlin.math.*

import org.slf4j.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap;
import org.springframework.ui.Model;


// Libraries for QR Generation
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.*;
import com.google.zxing.client.j2se.*;
import java.util.Base64

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.awt.image.BufferedImage;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.net.URI


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
        var keys = sharedData.keys("[^d]*");// you can use any specific pattern of key
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
        var description = movie.getFirst("description")!!
        sharedData.opsForValue().set(key, value);
        val dkey = "d$key"
        sharedData.opsForValue().set(dkey, description);
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
        val dkey = "d$key"
        sharedData.delete(dkey);
        return ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * See a movie
     *
     * @param key The key of the movie
     *
     * @return Ok if everything goes ok
     */
    @GetMapping("/see")
    @ResponseBody
    fun see(@RequestParam key: String) : Map<String, String> {
        var map = HashMap<String, String>();
        var value = sharedData.opsForValue().get(key);
        var dkey = "d$key"
        var descrip = sharedData.opsForValue().get(dkey);
        map.put(value,descrip);
        return map;
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
     *
     * This endpoint returns a QR Code with a {phrase} encoded
     * @return a Qr object
     */
    @GetMapping("/qr/{phrase}")
    fun qr(@ModelAttribute qr: Qr, @PathVariable phrase: String) : String {
        val qrwriter = QRCodeWriter()

        var oStream = ByteArrayOutputStream()
        var  bitmatrix = MultiFormatWriter().encode("$phrase", BarcodeFormat.QR_CODE, 500, 500)
        MatrixToImageWriter.writeToStream(bitmatrix, MediaType.IMAGE_PNG.getSubtype(), oStream, MatrixToImageConfig())
        var base64Img = Base64.getEncoder().encodeToString(oStream.toByteArray())

        qr.phrase = phrase
        qr.base64 = base64Img

        return "qr"
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
     * Function example to use 'gcd'  with a web GUI
     */
    @GetMapping("/gcdForm")
    fun gcdForm(@ModelAttribute form: GcdResult): String {
        return "form"
    }

    /**
     * Calculate the greatest common divisor with the form values and show the result
     * to the user in a new webpage
     *
     * @param form It must have two keys: "a":value1, "b":value2
     * @return a String in a webpage with the 'gcd' of 'a' & 'b'
     */
    @PostMapping("/gcdForm")
    fun calculateGcd(@ModelAttribute form: GcdResult, model: Model) : String{

        val firstNum = form.firstNum
        val secondNum = form.secondNum
        //Create a new Gcd object to use 'gcd' function
        val result = gcd(Gcd(form.firstNum!!,form.secondNum!!))
        model.addAttribute("firstNum", firstNum)
        model.addAttribute("secondNum", secondNum)
        model.addAttribute("result", result)

        return "result"
    }

    /**
     * "isitdown" form webpage
     */
    @GetMapping("/isitdown")
    fun isItDown(@ModelAttribute msg: Message): String {
        return "isitdown"
    }

    /**
     * Check if the website provided by the user is available and
     * show the response to the user in a new webpage
     *
     * @param form It must have one key: "website":a website's URI
     * @return a String in a webpage saying whether the website was found available or not
     */
    @PostMapping("/isitdown")
    fun checkWebsite(@ModelAttribute form: Website, model: Model) : String{
        if(form.website == null || form.website.equals("")){
            return "isitdown"
        }

        var uriStr = form.website

        if(! (uriStr.startsWith("http://") || uriStr.startsWith("https://")) ){
            uriStr = "http://"+uriStr
        }

        var uri = URI(uriStr)
        var host = uri.getHost()
        var status = host + " looks "

        try {
            val statusCode = get("http://" + host).statusCode

            if (statusCode == 200) {
                status = status + "up from here"
            } else {
                status = status + "down from here"
            }
        } catch (e : java.net.UnknownHostException){
            status = status + "nonexistent from here"
        }

        model.addAttribute("status", status)

        return "isitdown"
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
     * @PathVariable annotation is used to extract a variable from the url
     *  @param t type of shape: triangle,rectangle,rhombus
     *  @return the area of the shape
     */
    @GetMapping("/areacalculator/{t}/{p1}/{p2}")
    fun findPrimes(@ModelAttribute msg: Message,@PathVariable t: String,@PathVariable p1: Int,@PathVariable p2: Int): String {
        msg.message="hello world!"
        var a=0
        if(t=="triangle"){
            a=p1*p2/2
            msg.message="triangle area: $a"
        }
        if(t=="rectangle"){
            a=p1*p2
            msg.message="rectangle area: $a"
        }
        if(t=="rhombus"){
            a=p1*p2/2
            msg.message="rhombus area: $a"
        }
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
    @GetMapping("/games/headsTrails")
    fun headsTrails(@ModelAttribute msg: Message) : String {
        var random = Random()
        var value = random.nextInt(2)
        if (value == 0) {
            msg.message = "Heads"
        } else {
            msg.message = "Tails"
        }
        return "gambling"
    }

    /**
     * Function that simulates gambling machine (slot machine) with 3 cylinders
     * User only rewarded when the cylinders are equals.
     *
     * @param form It must have two keys: "a":balance, "b":bet.
     * PRECONDITION: a => b
     * @return returns string with cylinders and final balance
     */
    @PostMapping("/games/gamblingMachine")
    fun gamblingMachine(@ModelAttribute msg: Message, @ModelAttribute form: Gcd) : String {
        var balance = form.a
        var bet = form.b

        //Check if bet is greater than balance
        if ( bet > balance) {
            msg.message = "Bet cannot be greater then balance, max bet is: " + balance + " $";
        } else {
            //Populate array 1 2 3 3 4 4 4 5 5 5 5 ... 9 9 9 9 9 9 9 9 9
            var cylinderList = Array(39, { i -> ((1) + sqrt((1 + (8 * i)).toDouble()) / 2).toInt().toString()})

            //Output style f: first cylinder, s: second cylinder, t: third cylinder
            var result = "[f] [s] [t]"

            //Random generates cylinder
            var random = Random()
            var first = cylinderList.get(random.nextInt(cylinderList.size))
            var second = cylinderList.get(random.nextInt(cylinderList.size))
            var third = cylinderList.get(random.nextInt(cylinderList.size))
            result = result.replace("f", first).replace("s", second).replace("t", third)

            /* Check if cylinders are equals, and calculates final balance
             * Only check equal cylinders. Can add more win rules.
             */
            if (first == second && second == third) {
                balance = balance + (100 - first.toInt() * 10) * bet
            } else {
                balance = balance - bet
            }
            msg.message = result + " :::: balance = " + balance + " $"
        }
        return "gambling"
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
     * Checks if the DNI letter is correct based its number
     *
     * @param form It must have two keys: "number":value1, "letter":value2
     * @return true if the letter is correct. Otherwise, false
     */
    @PostMapping("/checkLetter")
    @ResponseBody
    fun checkLetter(@ModelAttribute form: Dni): Boolean {
        var number = form.number.toInt()
        var letter = form.letter
        var letters = arrayOf("T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E")

        return letters[number%23] == letter
    }

    /**
     * Checks if the DNI number has only digits
     *
     * @param form It must have two keys: "number":value1, "letter":value2
     * @return true if all the characters are digits. Otherwise, false
     */
    @PostMapping("/onlyNumbers")
    @ResponseBody
    fun onlyNumbers(@ModelAttribute form: Dni): Boolean {
        var number = form.number

        for (i in 0 until number.length){
            if(!number.get(i).isDigit()){
                return false
            }
        }

        return true
    }

    /**
     * Checks if the provided DNI is a valid one.
     *
     * @param form It must have two keys: "number":value1, "letter":value2
     * @return "valid" if the DNI is correct. Otherwise, "invalid".
     */
    @PostMapping("/dni")
    @ResponseBody
    fun dni(@ModelAttribute form: Dni): String {
        var number = form.number
        var letter = form.letter.single()
        var result = "invalid"

        if(letter.isLetter()){
            letter.toUpperCase()
            if(number.length == 8){
                if(onlyNumbers(Dni(form.number,form.letter))){
                    if(checkLetter(Dni(form.number,form.letter))){
                        result = "valid"
                    }
                }
            }
        }

        return result
    }

    /**
     * Function example to use 'dni' with a web GUI
     */
    @GetMapping("/dniForm")
    fun DniForm(@ModelAttribute form: DniResult): String {
        return "dniForm"
    }

    /**
     * Checks if the DNI introduced, numbers and letters,
     * represents a valid document or not
     *
     * @param form It must have two keys: "number":value1, "letter":value2
     * @return a String in a webpage that indicates if the DNI is valid
     */
    @PostMapping("/dniForm")
    fun testDni(@ModelAttribute form: DniResult, model: Model) : String{

        val number = form.Number
        val letter = form.Letter
        //Create a new Dni object to use 'dni' function
        val result = dni(Dni(form.Number!!,form.Letter!!))

        model.addAttribute("number", number)
        model.addAttribute("letter", letter)
        model.addAttribute("result", result)

        return "dniResult"
    }

}
