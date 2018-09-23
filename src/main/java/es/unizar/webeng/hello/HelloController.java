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
     * Function example to use 'gcd' {@link #gcd(Gcd)} to use it with a web GUI
     */
    @GetMapping("/gcdForm")
    public String gcdForm() {
        return "new-gcd";
    }

    /**
     * Calculate the greatest common divisor with the form values and show the result
     * to the user in a new webpage
     * 
     * @param form It must have two keys: "a":value1, "b":value2
     * @return a String in a webpage with the 'gcd' of 'a' & 'b'
     */
    @PostMapping("/calculateGcd")
    public String calculateGcd(Map<String, Object> model, @ModelAttribute Gcd form) {
        model.put("firstNum",  form.getA() );
        model.put("secondNum",  form.getB() );
        model.put("result",  gcd(form) );
        return "result";
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
