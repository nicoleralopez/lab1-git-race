package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Value;
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
     * Root endpoint by default
     * 
     * @return current time + "Hola alumno"
     */
    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", message);
        return "wellcome";
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
}
