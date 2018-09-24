package es.unizar.webeng.hello.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.stereotype.Controller
import org.springframework.web.context.ServletContextAware
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import javax.servlet.ServletContext


@Configuration
@Controller
class HelloConfig : ServletContextAware {
    @Autowired
    var context: ServletContext? = null

    override fun setServletContext(servletContext: ServletContext?) {
        context = servletContext
    }

    
    /*
     * Using thymeleaf to render data.
     * https://www.baeldung.com/thymeleaf-in-spring-mvc
     */
    @Bean
    @Description("Thymeleaf Template Resolver")
    fun templateResolver() : SpringResourceTemplateResolver {
        val templateResolver = SpringResourceTemplateResolver()
        templateResolver.prefix = "/WEB-INF/views/"
        templateResolver.suffix = ".html"
        templateResolver.setTemplateMode("HTML5")

        return templateResolver
    }
    
    @Bean
    @Description("Thymeleaf Template Engine")
    fun templateEngine() : SpringTemplateEngine {
        val templateEngine = SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver())
        templateEngine.setTemplateEngineMessageSource(messageSource())
        return templateEngine
    }

    @Bean
    @Description("Thymeleaf View Resolver")
    fun viewResolver(): ThymeleafViewResolver {
        val viewResolver = ThymeleafViewResolver()
        viewResolver.templateEngine = templateEngine()
        viewResolver.order = 1
        return viewResolver
    }

    @Bean
    @Description("Spring Message Resolver")
    fun messageSource(): ResourceBundleMessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("messages")
        return messageSource
    }

}