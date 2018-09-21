package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
        return "wellcome";
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
        return "wellcome";
    }

}
