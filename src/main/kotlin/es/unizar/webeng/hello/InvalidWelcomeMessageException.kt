package es.unizar.webeng.hello;

class InvalidWelcomeMessageException(override var message: String) : Exception(message) {

}