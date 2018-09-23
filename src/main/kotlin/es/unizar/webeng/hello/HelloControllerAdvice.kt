package es.unizar.webeng.hello;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
	fun employeeNotFoundHandler(ex: InvalidWelcomeMessageException) : Message {
		return Message(ex.message);
	}
}