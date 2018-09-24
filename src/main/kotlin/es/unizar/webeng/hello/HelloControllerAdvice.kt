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
		modelAndView.model["message"] = ex.mg
		modelAndView.model["time"] = ex.mg.time
		return modelAndView
	}

	/*
	 * Handles any URINotFoundException thrown by any controller method.
	 * If any controller method throws that exception, this one is executed to return a message
	 * 	with a NOT_FOUND (404) error code.
	 */
	@ResponseBody
	@ExceptionHandler(URINotFoundException::class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	fun URINotFoundHandler(ex: URINotFoundException) : ModelAndView{
		val modelAndView = ModelAndView("welcome")
		modelAndView.model["message"] = ex.mg
		modelAndView.model["time"] = ex.mg.time
		return modelAndView
	}
}