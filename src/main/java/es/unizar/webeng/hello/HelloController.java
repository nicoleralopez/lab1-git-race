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
import org.springframework.web.bind.annotation.RequestParam;

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
    @PostMapping("/mcm")
    public ResponseEntity<String> mcm(@RequestParam MultiValueMap<String, String> form) {
        try {
            int a = Integer.parseInt(form.getFirst("a"));
            int b = Integer.parseInt(form.getFirst("b"));
        
            // http://ejerciciosresueltosprogramacion.blogspot.com/2017/01/funciones-minimo-comun-multiplo-de-dos.html
            int m;
            if (a > b)
                m = a;
            else
                m = b;
            while (m % a != 0 || m % b != 0)
                m++;     

            // Return the result in the header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("result", Integer.toString(m));
            return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        } catch(NumberFormatException e) { // Check if parameters aren't a number
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }
}
