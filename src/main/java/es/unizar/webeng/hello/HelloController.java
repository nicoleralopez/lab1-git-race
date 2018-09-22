package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", message);
        return "wellcome";
    }
    
    /**
     * Found at https://www.baeldung.com/java-generate-prime-numbers
     * @PathVariable annotation is used to extract a variable from the url
     * @return a List object with all prime numbers below the parameter "n"
     */
	@GetMapping("/{n}")
	public static List<Integer> sieveOfEratosthenes(@PathVariable int n) {
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
}
