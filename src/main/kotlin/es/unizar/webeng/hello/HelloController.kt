package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
class HelloController {
    @Value("\${app.message}")
    var message : String = "Hello World"
        public set;

    /**
     * 
     * This annotation is used to map the welcome function to a GET Request on path: "/"
     * @return a String composed with current date + a "Hello World" message
     */
    @GetMapping("/")
    fun welcome() : ModelAndView {
        var modelView = ModelAndView();
        modelView.getModel().put("time", Date());
        modelView.getModel().put("message", message);
        modelView.setViewName("welcome");
        return modelView;
    }

    /**
     * 
     * This annotation is used to map the personalWelcome function to a GET Request on path: "/{name}"
     * @PathVariable annotation is used to extract a variable from the url
     * @return a String saying "Hello {name}"
     */
    @GetMapping("/{name}")
    fun personalWelcome(@PathVariable name: String) : ModelAndView {
        var modelView = ModelAndView();
        modelView.getModel().put("name", "Hola " + name);
        modelView.setViewName("welcome");
        return modelView;
    }

}
