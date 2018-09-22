package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Controller
class HelloController {
    @Value("\${app.message}")
    private var message : String = "Hello World"

    /**
     * 
     * This annotation is used to map the welcome function to a GET Request on path: "/"
     * @return a String composed with current date + a "Hello World" message
     */
    @GetMapping("/")
    fun welcome() : ModelAndView {
        var modelView = ModelAndView("welcome");
        modelView.getModel().put("time", Date());
        modelView.getModel().put("message", message);
        return modelView;
    }

    /**
     * 
     * This annotation is used to map the personalWelcome function to a GET Request on path: "/{name}"
     * @PathVariable annotation is used to extract a variable from the url.
     * A name is considered valid *only* if it only contains letters (upper and lower) and spaces.
     * @return a String saying "Hello {name}" if name is valid and an error message otherwise
     */
    @GetMapping("/{name}")
    fun personalWelcome(@PathVariable name: String) : ModelAndView {
        var modelView = ModelAndView("welcome");
        if(name.matches(Regex("[A-Za-z ]+"))){
            modelView.getModel().put("name", "Hola " + name);
        }else{
            modelView.setStatus(HttpStatus.BAD_REQUEST);
            modelView.getModel().put("name", "Invalid request. No one can be named " + name);
        }
        return modelView;
    }

    /**
     * Function example to check the post functionality
     * 
     * @param form It must have two keys: "a":value1, "b":value2
     */
    @PostMapping("/gcd")
    @ResponseBody
    fun gcd(@ModelAttribute form: Gcd) : Int {
        var a = form.getA();
        var b = form.getB();

        // https://introcs.cs.princeton.edu/java/23recursion/Euclid.java.html
        // Non-recursive Euclidean way
        while (b != 0) {
            var temp = b;
            b = a % b;
            a = temp;
        }
        // Result is stored in a
        return a;
    }

}
