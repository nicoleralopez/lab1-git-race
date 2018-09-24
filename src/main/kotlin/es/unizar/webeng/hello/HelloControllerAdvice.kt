package es.unizar.webeng.hello

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice
class HelloControllerAdvice {

	/*
	 * Handles any InvalidWelcomeMessageException thrown by any controller method.
	 * If any controller method throws that exception, this one is executed to return a message
	 * 	with a BAD_REQUEST (400) error code.
	 */
	@ResponseBody
	@ExceptionHandler(InvalidWelcomeMessageException::class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	fun invalidWelcomeMessageHandler(ex: InvalidWelcomeMessageException) : ModelAndView{
		val modelAndView = ModelAndView("welcome")
		modelAndView.model["message"] = ex.msg
		modelAndView.model["time"] = ex.msg.time
		return modelAndView
	}
}