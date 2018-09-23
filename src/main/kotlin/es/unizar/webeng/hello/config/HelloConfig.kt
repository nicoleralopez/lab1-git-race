package es.unizar.webeng.hello.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Description
import org.thymeleaf.spring5.SpringTemplateEngine
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.context.ServletContextAware
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templateresolver.ServletContextTemplateResolver
import javax.servlet.ServletContext


@Configuration
class HelloConfig : ServletContextAware {
    lateinit var servletContext: ServletContext

    override fun setServletContext(servletContext: ServletContext?) {
        set
    }

    
    /*
     * Using thymeleaf to render data.
     * https://www.baeldung.com/thymeleaf-in-spring-mvc
     */
    @Bean
    @Description("Thymeleaf Template Resolver")
    fun templateResolver() : ServletContextTemplateResolver {
        val templateResolver = ServletContextTemplateResolver(servletContext)
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