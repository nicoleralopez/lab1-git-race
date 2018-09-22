package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.lang.NumberFormatException;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;


@Controller
public class HelloController {
    @Value("${app.message:Hello World}")
    private String message;

    /**
     * 
     * This annotation is used to map the welcome function to a GET Request on path: "/"
     * @return a String composed with current date + a "Hello World" message
     */
    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", message);
        return "welcome";
    }

    /**
     * Function example to check the post functionality
     * 
     * @param form It must have two keys: "a":value1, "b":value2
     */
    @PostMapping("/gcd")
    @ResponseBody
    public int gcd(@ModelAttribute Gcd form) {
        int a = form.getA();
        int b = form.getB();

        // https://introcs.cs.princeton.edu/java/23recursion/Euclid.java.html
        // Non-recursive Euclidean way
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        // Result is stored in a
        return a;
    }
    
    /**
     * Found at https://www.baeldung.com/java-generate-prime-numbers
     * This code is separated from "findPrimes" because of the test in
     * HelloControllerUnitTest.java
     * @param n all prime number from 0 to n
     * @return a List object with all prime numbers below the parameter "n"
     */
    public static List<Integer> sieveOfEratosthenes(int n){
    	boolean prime[] = new boolean[n + 1];
	    Arrays.fill(prime, true);
	    for (int p = 2; p * p <= n; p++) {
	        if (prime[p]) {
	            for (int i = p * 2; i <= n; i += p) {
	                prime[i] = false;
	            }
	        }
	    }
	    List<Integer> primeNumbers = new LinkedList<>();
	    for (int i = 2; i <= n; i++) {
	        if (prime[i]) {
	            primeNumbers.add(i);
	        }
	    }
	    return primeNumbers;
    }

    /**
     * @PathVariable annotation is used to extract a variable from the url
     * @param n all prime number from 0 to n
     * @return all prime numbers below the parameter "n"
     */
	@GetMapping("/primes/{n}")
	public static String findPrimes(Map<String, Object> model, @PathVariable int n) {
	    model.put("numbers", sieveOfEratosthenes(n));
	    return "welcome";
	}
    /**
     * 
     * This annotation is used to map the personalWelcome function to a GET Request on path: "/{name}"
     * @PathVariable annotation is used to extract a variable from the url
     * @return a String saying "Hello {name}"
     */
    @GetMapping("/{name}")
    public String personalWelcome(Map<String, Object> model, @PathVariable String name) {
        model.put("name", "Hola " + name);
        return "welcome";
    }
}
